package com.fairandsmart.invoices.element.container;

import com.fairandsmart.invoices.element.BoundingBox;
import com.fairandsmart.invoices.element.ElementBox;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class VerticalElementContainer extends ElementBox {

    private static final Logger LOGGER = Logger.getLogger(VerticalElementContainer.class.getName());

    private List<ElementBox> elements;
    private BoundingBox box;
    private float maxWidth;

    public VerticalElementContainer(float posX, float posY, float maxWidth) {
        this.elements = new ArrayList<>();
        this.box = new BoundingBox(posX, posY, 0, 0);
    }

    public void addElement(ElementBox element) throws Exception {
        this.elements.add(element);
        if ( maxWidth > 0 && element.getBoundingBox().getWidth() > maxWidth ) {
            element.setWidth(box.getWidth());
        }
        element.getBoundingBox().setPosX(0);
        element.getBoundingBox().setPosY(0);
        element.translate(box.getPosX(), box.getPosY() - this.box.getHeight() - element.getBoundingBox().getHeight());
        this.box.setHeight(this.box.getHeight() + element.getBoundingBox().getHeight());
    }

    @Override
    public BoundingBox getBoundingBox() {
        return box;
    }

    @Override
    public void setWidth(float width) throws Exception {
        for ( ElementBox element : elements ) {
            element.setWidth(width);
        }
        this.box.setWidth(width);
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
        for ( ElementBox element : elements ) {
            element.build(stream, writer);
        }
    }

}
