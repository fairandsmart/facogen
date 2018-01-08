package com.fairandsmart.invoices.layout.amazon;

import com.fairandsmart.invoices.element.background.BorderBuilder;
import com.fairandsmart.invoices.element.image.ImageBuilder;
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
        String barcode = this.getClass().getClassLoader().getResource("parts/amazon/barcode1.png").getFile();
        PDImageXObject pdBarcode = PDImageXObject.createFromFile(barcode, document);
        new ImageBuilder(pdBarcode, page.getMediaBox().getWidth() / 2, 710, 0.5f, "DMmZXznqN /-1 of 1 -// std-in-remote").build(contentStream, writer);

        //Text top
        contentStream.beginText();
        contentStream.setNonStrokingColor(Color.BLACK);
        PDFont font = PDType1Font.HELVETICA;
        contentStream.setFont(font, 9);
        contentStream.newLineAtOffset(25, 800);
        contentStream.showText("Page 1 of 1, 1-1/1");
        contentStream.newLineAtOffset(0, -10);
        contentStream.showText("Invoice for DMmZXznqN Oct 5, 2014");
        font = PDType1Font.HELVETICA_BOLD;
        contentStream.setFont(font, 10);
        contentStream.newLineAtOffset(0, -10);
        contentStream.showText("Retail / TaxInvoice / Cash Memorandum");
        contentStream.setFont(font, 9);
        contentStream.newLineAtOffset(0, -20);
        contentStream.showText("Sold By");
        font = PDType1Font.HELVETICA;
        contentStream.setFont(font, 9);

        contentStream.endText();

        //footer
        String footer = this.getClass().getClassLoader().getResource("parts/amazon/footer.png").getFile();
        PDImageXObject pdFooter = PDImageXObject.createFromFile(footer, document);
        contentStream.drawImage(pdFooter, 25, 15, pdFooter.getWidth() / 1.7f, pdFooter.getHeight() / 1.7f);

        contentStream.close();

        writer.writeEndElement();
    }
}
