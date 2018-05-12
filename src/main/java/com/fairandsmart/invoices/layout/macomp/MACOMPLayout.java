package com.fairandsmart.invoices.layout.macomp;

import com.fairandsmart.invoices.data.model.Client;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import com.fairandsmart.invoices.element.textbox.SimpleTextBox;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import com.fairandsmart.invoices.layout.head.CompanyInfoBox;
import com.fairandsmart.invoices.layout.head.ClientInfoBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MACOMPLayout implements InvoiceLayout {

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

        // Facture No. 123
        new SimpleTextBox(fonts[1], 13, 350, 748, "Facture No. "+ model.getReference().getValue()).build(contentStream, writer);

        // Company Info Box
        CompanyInfoBox companyInfoBox = new CompanyInfoBox(fonts[2], fonts[1], 10, model, document);
        companyInfoBox.build(contentStream, writer);
        companyInfoBox.translate(50, 785);
        companyInfoBox.build(contentStream, writer);

        // Billing Address Block
        ClientInfoBox billingInfoBox = new ClientInfoBox(fonts[2], fonts[1], 10, model, document, "Billing");
        billingInfoBox.build(contentStream, writer);
        billingInfoBox.translate(50, 650);
        billingInfoBox.build(contentStream, writer);

        // Shipping Address Block
        ClientInfoBox shippingInfoBox = new ClientInfoBox(fonts[2], fonts[1], 10, model, document, "Shipping");
        shippingInfoBox.build(contentStream, writer);
        shippingInfoBox.translate(50, 580);
        shippingInfoBox.build(contentStream, writer);


        contentStream.close();

    }
}
