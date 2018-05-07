package com.fairandsmart.invoices.element.container;

import com.fairandsmart.invoices.element.BoundingBox;
import com.fairandsmart.invoices.element.ElementBox;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class HorizontalContainer extends ElementBox {

    private static final Logger LOGGER = Logger.getLogger(VerticalContainer.class.getName());

    private List<ElementBox> elements;
    private BoundingBox box;
    private Color backgroundColor;

    public HorizontalContainer(float posX, float posY) {
        this.elements = new ArrayList<>();
        this.box = new BoundingBox(posX, posY, 0, 0);
    }

    public void addElement(ElementBox element) throws Exception {
        this.elements.add(element);
        element.getBoundingBox().setPosX(0);
        element.getBoundingBox().setPosY(0);
        element.translate(box.getPosX() + this.box.getWidth(), box.getPosY());
        if ( element.getBoundingBox().getHeight() > box.getHeight() ) {
            this.box.setHeight(element.getBoundingBox().getHeight());
        }
        this.box.setWidth(this.box.getWidth() + element.getBoundingBox().getWidth());
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return box;
    }

    @Override
    public void setWidth(float width) throws Exception {
        throw new Exception("Not allowed");
    }

    @Override
    public void setHeight(float height) throws Exception {
        throw new Exception("Not allowed");
    }

    @Override
    public void translate(float offsetX, float offsetY) {
        box.translate(offsetX, offsetY);
        for ( ElementBox element : elements ) {
            element.translate(offsetX, offsetY);
        }
    }

    @Override
    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        if ( backgroundColor != null ) {
            stream.setNonStrokingColor(backgroundColor);
            stream.addRect(box.getPosX(), box.getPosY()-box.getHeight(), box.getWidth(), box.getHeight());
            stream.fill();
        }

        for ( ElementBox element : elements ) {
            element.build(stream, writer);
        }
    }
}
