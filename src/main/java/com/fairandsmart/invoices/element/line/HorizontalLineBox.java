package com.fairandsmart.invoices.element.line;

import com.fairandsmart.invoices.element.BoundingBox;
import com.fairandsmart.invoices.element.ElementBox;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;

public class HorizontalLineBox extends ElementBox {

    private float targetX;
    private float targetY;
    private BoundingBox box;

    public HorizontalLineBox(float posX, float posY, float targetX, float targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.box = new BoundingBox(posX, posY, 0, 0);
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
        stream.moveTo(this.getBoundingBox().getPosX(), this.getBoundingBox().getPosY());
        stream.lineTo( this.targetX, this.getBoundingBox().getPosY());
        stream.stroke();
    }

}
