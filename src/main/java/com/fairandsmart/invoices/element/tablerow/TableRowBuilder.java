package com.fairandsmart.invoices.element.tablerow;

import com.fairandsmart.invoices.element.tablecell.TableCellBuilder;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TableRowBuilder {

    Float width;
    String text;
    PDPageContentStream contentStream;
    List<TableCellBuilder> cells;

    public TableRowBuilder(PDPageContentStream contentStream) {

        this.contentStream = contentStream;
    }

    public void setCells(List<TableCellBuilder> cells) {

        this.cells = cells;
    }

    public List<TableCellBuilder> getCells() {

        return this.cells;

    }

    public void render() throws Exception {

        for(TableCellBuilder oneCell : this.cells) {
            oneCell.render();
        }



        PDFont font = PDType1Font.HELVETICA;

        this.contentStream.beginText();

        this.contentStream.setNonStrokingColor(Color.BLACK);
        font = PDType1Font.HELVETICA;
        this.contentStream.setFont(font, 9);
        this.contentStream.newLineAtOffset(20, 300);
        this.contentStream.showText("Nature of Transaction: sale");

        this.contentStream.endText();
    }

    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {

        int posWidth = 10;
        int posHeight = 400;

        for(TableCellBuilder oneCell : this.cells) {
            oneCell.build(stream, writer, posWidth, posHeight);
            posWidth += 10;
        }

        /*
        stream.beginText();
        stream.setNonStrokingColor(Color.BLACK);
        stream.setFont(font, fontSize);
        stream.newLineAtOffset(posX, posY-height);
        stream.showText(text);
        stream.endText();

        */

        /*

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

        */
    }

}