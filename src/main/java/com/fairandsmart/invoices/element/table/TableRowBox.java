package com.fairandsmart.invoices.element.table;

import com.fairandsmart.invoices.element.*;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class TableRowBox extends ElementBox {

    private static final Logger LOGGER = Logger.getLogger(VerticalContainer.class.getName());

    private float[] config;
    private Color backgroundColor;
    private BoundingBox box;
    private List<ElementBox> elements;
    private VAlign valign;

    public TableRowBox(float[] config, float posX, float posY) {
        this(config, posX, posY, VAlign.TOP);
    }

    public TableRowBox(float[] config, float posX, float posY, VAlign valign) {
        this.config = config;
        this.elements = new ArrayList<>();
        float width = 0;
        for (float row : config) {
            width += row;
        }
        box = new BoundingBox(posX, posY, width, 0);
        this.valign = valign;
    }

    public void addElement(ElementBox element) throws Exception {
        if ( elements.size() == config.length ) {
            throw new Exception("Row is full, no more element allowed");
        }
        elements.add(element);
        element.setWidth(config[elements.size() - 1]);
        element.getBoundingBox().setPosX(0);
        element.getBoundingBox().setPosY(0);
        element.translate(box.getPosX() + this.getColumnOffsetX(elements.size()-1), box.getPosY() );
        if ( element.getBoundingBox().getHeight() > box.getHeight() ) {
            this.getBoundingBox().setHeight(element.getBoundingBox().getHeight());
        }
        this.realignElements();
    }

    private void realignElements() {
        int cpt = 0;
        for ( ElementBox element : elements ) {
            float posY = box.getPosY();
            switch ( valign ) {
                case BOTTOM:
                    posY = box.getPosY() - box.getHeight() + element.getBoundingBox().getHeight(); break;
                case CENTER:
                    posY = box.getPosY() - box.getHeight() + (element.getBoundingBox().getHeight()/2); break;
            }
            float transY = posY - element.getBoundingBox().getPosY();
            element.translate(0, transY);
            cpt++;
        }
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
        for (ElementBox element : elements) {
            element.translate(offsetX, offsetY);
        }
        this.getBoundingBox().translate(offsetX, offsetY);

    }

    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        if ( backgroundColor != null ) {
            stream.setNonStrokingColor(backgroundColor);
            stream.addRect(box.getPosX(), box.getPosY() - box.getHeight(), box.getWidth(), box.getHeight());
            stream.fill();
        }

        for(ElementBox element : this.elements) {
            //if(element.getBoundingBox().getHeight() < this.getBoundingBox().getHeight()) {
            //    element.translate(0,  this.getBoundingBox().getHeight() - element.getBoundingBox().getHeight());
            //}
            element.build(stream, writer);
        }
    }

    private float getColumnOffsetX (int numCol) {
        //TODO maybe the offset X could be store in a flaot[] like config...
        float posX = 0;
        for (int i=0; i<numCol; i++) {
            posX += config[i];
        }
        return posX;
    }

}