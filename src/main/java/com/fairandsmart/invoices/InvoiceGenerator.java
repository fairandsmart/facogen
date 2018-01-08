package com.fairandsmart.invoices;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.datatable.DataTable;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
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
        page.setMediaBox(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));
        document.addPage(page);

        //Global values
        float margin = 10;
        float bottomMargin = 0;

        PDFont font = PDType1Font.HELVETICA_BOLD;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);


        //Logo
        String logo = this.getClass().getClassLoader().getResource("logo/logo.png").getFile();
        PDImageXObject pdImage = PDImageXObject.createFromFile(logo, document);
        contentStream.drawImage(pdImage, margin, 700, pdImage.getWidth() / 4, pdImage.getHeight() / 4);

        //Company address
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.newLineAtOffset(margin, 700);
        contentStream.showText("Fair and Smart SAS");
        contentStream.newLineAtOffset(0, -16);
        contentStream.showText("11 Rempart St Thiébault");
        contentStream.newLineAtOffset(0, -16);
        contentStream.showText("57000 Metz - France");
        contentStream.endText();



        //Invoice Number
        contentStream.beginText();
        contentStream.setFont(font, 20);
        contentStream.setNonStrokingColor(Color.ORANGE);
        contentStream.newLineAtOffset(400, 750);
        contentStream.showText("INVOICE N°1276551");
        contentStream.endText();

        //Invoice content
        //Initialize table
        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
        float yStart = yStartNewPage;

        BaseTable pdfTable = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true,true);
        DataTable table = new DataTable(pdfTable, page);
        table.addListToTable();
        pdfTable.draw();

        contentStream.close();

        document.save(output.toFile());
        document.close();
    }

    public enum Model {
        BASIC
    }



}
