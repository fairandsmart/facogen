package com.fairandsmart.invoices.element.border;

import com.fairandsmart.invoices.element.BoundingBox;
import com.fairandsmart.invoices.element.ElementBox;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;

public class BorderBox extends ElementBox {

    private Color borderColor;
    private Color color;
    private int thick;
    private BoundingBox box;

    public BorderBox(Color borderColor, Color color, int thick, float posX, float posY, float width, float height) {
        this.borderColor = borderColor;
        this.color = color;
        this.thick = thick;
        this.box = new BoundingBox(posX, posY, width, height);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return box;
    }

    @Override
    public void translate(float offsetX, float offsetY) {
        this.getBoundingBox().translate(offsetX, offsetY);
    }

    @Override
    public void setWidth(float width) throws Exception {
        this.getBoundingBox().setWidth(width);
    }

    @Override
    public void setHeight(float height) throws Exception {
        this.getBoundingBox().setHeight(height);
    }

    @Override
    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        stream.setNonStrokingColor(borderColor);
        stream.addRect(box.getPosX(), box.getPosY(), box.getWidth(), box.getHeight());
        stream.fill();
        stream.setNonStrokingColor(color);
        stream.addRect(box.getPosX()+thick, box.getPosY()+thick, box.getWidth()-(thick*2), box.getHeight()-(thick*2));
        stream.fill();
    }

}
