package com.fairandsmart.invoices.element.table;

import com.fairandsmart.invoices.element.BoundingBox;
import com.fairandsmart.invoices.element.ElementBox;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.util.List;

public class TableRowBox extends ElementBox {

    private float[] columnSize;
    private List<ElementBox> elements;
    private BoundingBox box;

    public TableRowBox(float[] columnSize, List<ElementBox> elements, float posX, float posY) {
        this.columnSize = columnSize;
        this.elements = elements;
        box = new BoundingBox(posX, posY, -1, -1);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return null;
    }

    @Override
    public void setWidth(float width) throws IOException {

    }

    @Override
    public void setHeight(float height) throws Exception {

    }

    @Override
    public void translate(float offsetX, float offsetY) {
        this.getBoundingBox().translate(offsetX, offsetY);
    }

    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {


        float width = 0;

        int pos = 0;

        for(ElementBox oneElement : this.elements) {

            float computeWidth = Float.sum(this.getBoundingBox().getPosX(), width);

            oneElement.translate(computeWidth, 0);
            oneElement.setWidth(columnSize[pos]);
            oneElement.build(stream, writer);



            width += columnSize[pos];
            pos++;
        }

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