package com.fairandsmart.invoices;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;

public class InvoiceGenerator {

    private static class InvoiceGeneratorHolder {
        private final static InvoiceGenerator instance = new InvoiceGenerator();
    }

    public static InvoiceGenerator getInstance() {
        return InvoiceGeneratorHolder.instance;
    }

    private InvoiceGenerator() {
    }

    public void generateInvoice(Model model, Path output) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        PDFont font = PDType1Font.HELVETICA_BOLD;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        String logo = this.getClass().getClassLoader().getResource("logo/logo.png").getFile();
        PDImageXObject pdImage = PDImageXObject.createFromFile(logo, document);

        contentStream.drawImage(pdImage, 20, 700, pdImage.getWidth() / 4, pdImage.getHeight() / 4);

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.newLineAtOffset(36, 700);
        contentStream.showText("Fair and Smart SAS");
        contentStream.newLineAtOffset(0, -16);
        contentStream.showText("11 Rempart St Thiébault");
        contentStream.newLineAtOffset(0, -16);
        contentStream.showText("57000 Metz - France");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 20);
        contentStream.setNonStrokingColor(Color.ORANGE);
        contentStream.newLineAtOffset(400, 750);
        contentStream.showText("INVOICE N°1276551");
        contentStream.endText();



        contentStream.close();

        document.save(output.toFile());
        document.close();
    }

    public enum Model {
        BASIC
    }

}
