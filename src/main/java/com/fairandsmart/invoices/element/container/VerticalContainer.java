package com.fairandsmart.invoices.element.container;

import com.fairandsmart.invoices.element.BoundingBox;
import com.fairandsmart.invoices.element.ElementBox;
import com.fairandsmart.invoices.element.Padding;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VerticalContainer extends ElementBox {

    private static final Logger LOGGER = Logger.getLogger(VerticalContainer.class.getName());

    private List<ElementBox> elements;
    private BoundingBox box;
    private Color backgroundColor;
    private float maxWidth;

    public VerticalContainer(float posX, float posY, float maxWidth) {
        this.elements = new ArrayList<>();
        this.maxWidth = maxWidth;
        this.box = new BoundingBox(posX, posY, 0, 0);
    }

    public void addElement(ElementBox element) throws Exception {
        this.elements.add(element);
        if ( maxWidth > 0 && element.getBoundingBox().getWidth() > maxWidth ) {
            element.setWidth(maxWidth);
        }
        if ( element.getBoundingBox().getWidth() > this.getBoundingBox().getWidth() ) {
            this.getBoundingBox().setWidth(element.getBoundingBox().getWidth());
        }
        element.getBoundingBox().setPosX(0);
        element.getBoundingBox().setPosY(0);
        element.translate(box.getPosX(), box.getPosY() - this.box.getHeight());
        this.box.setHeight(this.box.getHeight() + element.getBoundingBox().getHeight());
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    public void alignElements(String align, float width) {
        for ( ElementBox element : elements ) {
            float posX = box.getPosX();
            System.out.println(width);
            switch ( align ) {
                case "CENTER":
                    posX = (width - box.getPosX() - element.getBoundingBox().getWidth())/2; break;
                case "RIGHT":
                    posX = (width - box.getPosX()) - element.getBoundingBox().getWidth(); break;
            }
            float transX = posX - element.getBoundingBox().getPosX();
            element.translate(transX, 0);
        }
    }


    @Override
    public BoundingBox getBoundingBox() {
        return box;
    }

    @Override
    public void setWidth(float width) throws Exception {
        this.setHeight(0);
        this.box.setWidth(width);
        for ( ElementBox element : elements ) {
            element.setWidth(width);
            element.getBoundingBox().setPosX(0);
            element.getBoundingBox().setPosY(0);
            float offsetY = this.box.getHeight();
            element.translate(box.getPosX(), box.getPosY() - offsetY);
            this.box.setHeight(this.box.getHeight() + element.getBoundingBox().getHeight());
        }
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
