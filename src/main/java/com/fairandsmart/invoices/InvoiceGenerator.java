package com.fairandsmart.invoices;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

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

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.moveTextPositionByAmount(100, 700);
        contentStream.drawString("Hello World");
        contentStream.endText();

        contentStream.close();

        document.save(output.toFile());
        document.close();
    }

    public enum Model {
        BASIC
    }

}
