package com.fairandsmart.invoices.layout.generic;

import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import com.fairandsmart.invoices.layout.head.CompanyInfoBox;
import com.fairandsmart.invoices.layout.head.HeadBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.xml.stream.XMLStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenericLayout implements InvoiceLayout {

    private static final List<PDFont[]> FONTS = new ArrayList<>();
    {
        FONTS.add(new PDFont[] {PDType1Font.HELVETICA, PDType1Font.HELVETICA_BOLD, PDType1Font.HELVETICA_OBLIQUE} );
        FONTS.add(new PDFont[] {PDType1Font.COURIER, PDType1Font.COURIER_BOLD, PDType1Font.COURIER_OBLIQUE} );
        FONTS.add(new PDFont[] {PDType1Font.TIMES_ROMAN, PDType1Font.TIMES_BOLD, PDType1Font.TIMES_ITALIC} );
    }

    @Override
    public void builtInvoice(InvoiceModel model, PDDocument document, XMLStreamWriter writer) throws Exception {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        writer.writeStartElement("DL_PAGE");
        writer.writeAttribute("gedi_type", "DL_PAGE");
        writer.writeAttribute("pageID", "1");
        writer.writeAttribute("width", "2480");
        writer.writeAttribute("height", "3508");

        Random random = new Random();
        PDFont[] fonts = FONTS.get(random.nextInt(FONTS.size()));

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        HeadBox headBox = new HeadBox(fonts[0], fonts[1], fonts[2], 11, model, document);
        headBox.translate(25,820);
        //CompanyInfoBox companyInfoBox = new CompanyInfoBox(fonts[0], fonts[1], 11, model, document);
        //companyInfoBox.translate(25, 820);
        //companyInfoBox.build(contentStream, writer);
        headBox.build(contentStream, writer);

        contentStream.close();
    }

}
