package com.fairandsmart.invoices.element.container;

import com.fairandsmart.invoices.element.BoundingBox;
import com.fairandsmart.invoices.element.ElementBox;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamWriter;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

public class LayeredElementContainer extends ElementBox {

    private static final Logger LOGGER = Logger.getLogger(LayeredElementContainer.class.getName());

    private Map<Integer, ElementBox> elements;
    private BoundingBox box;

    //TODO manage aligment and padding
    public LayeredElementContainer(float posX, float posY, float width, float height) {
        this.elements = new TreeMap<>();
        this.box = new BoundingBox(posX, posY, width, height);
    }

    public void addElement(int layer, ElementBox element) {
        this.elements.put(layer, element);
        element.getBoundingBox().setPosX(box.getPosX());
        element.getBoundingBox().setPosY(box.getPosY());
        if ( element.getBoundingBox().getWidth() > box.getWidth() ) {
            box.setWidth(element.getBoundingBox().getWidth());
            //TODO maybe resize all existing elements
        }
        if ( element.getBoundingBox().getHeight() > box.getHeight() ) {
            box.setHeight(element.getBoundingBox().getHeight());
            //TODO maybe resize all existing elements
        }
    }

    @Override
    public BoundingBox getBoundingBox() {
        return box;
    }

    @Override
    public void setWidth(float width) throws Exception {
        for ( ElementBox element : elements.values() ) {
            element.setWidth(width);
        }
        this.box.setWidth(width);
    }

    @Override
    public void setHeight(float height) throws Exception {
        for ( ElementBox element : elements.values() ) {
            element.setHeight(height);
        }
        this.box.setHeight(height);
    }

    @Override
    public void translate(float offsetX, float offsetY) {
        box.translate(offsetX, offsetY);
        for ( ElementBox element : elements.values() ) {
            element.translate(offsetX, offsetY);
        }
    }

    @Override
    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        for ( ElementBox element : elements.values() ) {
            element.build(stream, writer);
        }
    }

}
