package com.fairandsmart.invoices.element.tablecell;

import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.xml.stream.XMLStreamWriter;
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

    public void build(PDPageContentStream stream, XMLStreamWriter writer, int posX, int posY) throws Exception {
        stream.beginText();
        stream.setNonStrokingColor(Color.BLACK);
        stream.setFont(font, fontSize);
        stream.newLineAtOffset(posX, posY-height);
        stream.showText(text);
        stream.endText();

        float[] convPos = convertZone(posX, posY, width, height);
        writer.writeStartElement("DL_ZONE");
        writer.writeAttribute("gedi_type", "ocr_line");
        writer.writeAttribute("id", "line_1_" + nextElementNumber());
        writer.writeAttribute("col", "" + (int) convPos[0]);
        writer.writeAttribute("row", "" + (int) convPos[1]);
        writer.writeAttribute("width", "" + (int) convPos[2]);
        writer.writeAttribute("height", "" + (int) convPos[3]);
        writer.writeAttribute("contents", text);
        writer.writeEndElement();
    }

}