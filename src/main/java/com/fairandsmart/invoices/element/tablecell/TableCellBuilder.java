package com.fairandsmart.invoices.element.tablecell;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;


public class TableCellBuilder {

    Float width;
    String text;
    PDPageContentStream contentStream;

    public TableCellBuilder(PDPageContentStream contentStream, float width, String text) {

        this.width = width;
        this.text = text;
        this.contentStream = contentStream;
    }

    public void render() throws Exception {
        PDFont font = PDType1Font.HELVETICA;

        this.contentStream.beginText();

        this.contentStream.setNonStrokingColor(Color.BLACK);
        font = PDType1Font.HELVETICA;
        this.contentStream.setFont(font, 9);
        this.contentStream.newLineAtOffset(20, 300);
        this.contentStream.showText("Nature of Transaction: sale");

        this.contentStream.endText();
    }

}