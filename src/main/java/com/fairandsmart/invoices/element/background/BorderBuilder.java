package com.fairandsmart.invoices.element.background;

import com.fairandsmart.invoices.element.ElementBuilder;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.io.IOException;

public class BorderBuilder extends ElementBuilder {

    private Color color;
    private int thick;
    private float width;
    private float height;
    private float posX = 0;
    private float posY = 0;

    public BorderBuilder(Color color, int thick, float posX, float posY, float width, float height) {
        this.color = color;
        this.thick = thick;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public BorderBuilder(Color color, int thick, float width, float height) {
        this.color = color;
        this.thick = thick;
        this.width = width;
        this.height = height;
    }

    public BorderBuilder(Color color, int thick, float posX, float posY, PDPage page) {
        this.color = color;
        this.thick = thick;
        this.posX = posX;
        this.posY = posY;
        this.width = page.getMediaBox().getWidth();
        this.height = page.getMediaBox().getHeight();
    }

    public BorderBuilder(Color color, int thick, PDPage page) {
        this.color = color;
        this.thick = thick;
        this.width = page.getMediaBox().getWidth();
        this.height = page.getMediaBox().getHeight();
    }

    @Override
    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        stream.setNonStrokingColor(color);
        stream.addRect(posX, posY, width, height);
        stream.fill();
        stream.setNonStrokingColor(Color.WHITE);
        stream.addRect(posX+thick, posY+thick, width-(thick*2), height-(thick*2));
        stream.fill();
    }
}
