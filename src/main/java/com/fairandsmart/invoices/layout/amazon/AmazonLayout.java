package com.fairandsmart.invoices.layout.amazon;

import com.fairandsmart.invoices.element.background.BorderBuilder;
import com.fairandsmart.invoices.element.image.ImageBuilder;
import com.fairandsmart.invoices.element.textbox.TextBoxBuilder;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;

public class AmazonLayout implements InvoiceLayout {

    @Override
    public void builtInvoice(PDDocument document, XMLStreamWriter writer) throws Exception {
        PDPage page = new PDPage(PDRectangle.A4);

        document.addPage(page);
        writer.writeStartElement("DL_PAGE");
        writer.writeAttribute("gedi_type", "DL_PAGE");
        writer.writeAttribute("pageID", "1");
        writer.writeAttribute("width", "2480");
        writer.writeAttribute("height", "3508");

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        new BorderBuilder(Color.ORANGE, 4, page).build(contentStream, writer);

        //Barcode top
        String barcode = this.getClass().getClassLoader().getResource("parts/amazon/barcode1.jpg").getFile();
        PDImageXObject pdBarcode = PDImageXObject.createFromFile(barcode, document);
        new ImageBuilder(pdBarcode, page.getMediaBox().getWidth() / 2, 710, pdBarcode.getWidth() / 2, pdBarcode.getHeight() / 2, "DMmZXznqN /-1 of 1 -// std-in-remote").build(contentStream, writer);

        //Text top
        TextBoxBuilder tbPage = new TextBoxBuilder(PDType1Font.HELVETICA, 9, 25, 800, "Page 1 of 1, 1-1/1");
        TextBoxBuilder tbInvoice = new TextBoxBuilder(PDType1Font.HELVETICA, 9, 25, 790, "Invoice for DMmZXznqN Oct 5, 2014");
        TextBoxBuilder tbDescription = new TextBoxBuilder(PDType1Font.HELVETICA_BOLD, 10, 25, 780, "Retail / TaxInvoice / Cash Memorandum");
        TextBoxBuilder tbSold = new TextBoxBuilder(PDType1Font.HELVETICA_BOLD, 10, 25, 760, "Sold By");
        tbPage.build(contentStream, writer);
        tbInvoice.build(contentStream, writer);
        tbDescription.build(contentStream, writer);
        tbSold.build(contentStream, writer);

        //footer
        String footer = this.getClass().getClassLoader().getResource("parts/amazon/footer.png").getFile();
        PDImageXObject pdFooter = PDImageXObject.createFromFile(footer, document);
        contentStream.drawImage(pdFooter, 25, 15, pdFooter.getWidth() / 1.7f, pdFooter.getHeight() / 1.7f);

        contentStream.close();

        writer.writeEndElement();
    }
}
