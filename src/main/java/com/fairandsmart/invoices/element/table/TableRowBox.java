package com.fairandsmart.invoices.element.table;

import com.fairandsmart.invoices.element.BoxBoundary;
import com.fairandsmart.invoices.element.ElementBox;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.util.List;

public class TableRowBox extends ElementBox {

    private float[] columnSize;
    private List<ElementBox> elements;
    private BoxBoundary box;

    public TableRowBox(float[] columnSize, List<ElementBox> elements, float posX, float posY) {
        this.columnSize = columnSize;
        this.elements = elements;
        box = new BoxBoundary(posX, posY, -1, -1);
    }

    @Override
    public BoxBoundary getBoxBoundary() {
        return null;
    }

    @Override
    public void setMaxWidth(float width) throws IOException {

    }

    @Override
    public void translate(float offsetX, float offsetY) {
        this.getBoxBoundary().translate(offsetX, offsetY);
    }

    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {

        int posWidth = 10;
        int posHeight = 400;

        /*
        for(TableCellBuilder oneCell : this.cells) {
            oneCell.build(stream, writer, posWidth, posHeight);
            posWidth += 10;
        }
        */

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