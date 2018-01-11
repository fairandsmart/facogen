package com.fairandsmart.invoices.element.background;

import com.fairandsmart.invoices.element.BoxBoundary;
import com.fairandsmart.invoices.element.ElementBox;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;

public class BackgroundBox extends ElementBox {

    private Color borderColor;
    private Color color;
    private int thick;
    private BoxBoundary box;

    public BackgroundBox(Color borderColor, Color color, int thick, float posX, float posY, float width, float height) {
        this.borderColor = borderColor;
        this.color = color;
        this.thick = thick;
        this.box = new BoxBoundary(posX, posY, width, height);
    }

    public BackgroundBox(Color borderColor, Color color, int thick, PDPage page) {
        this.borderColor = borderColor;
        this.color = color;
        this.thick = thick;
        this.box = new BoxBoundary(0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight());
    }

    public void setMaxWidth(float maxWidth) {
        //
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

    @Override
    public BoxBoundary getBoxBoundary() {
        return box;
    }
}
