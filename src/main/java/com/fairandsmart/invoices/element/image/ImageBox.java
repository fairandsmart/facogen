package com.fairandsmart.invoices.element.image;

import com.fairandsmart.invoices.element.BoxBoundary;
import com.fairandsmart.invoices.element.ElementBox;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.xml.stream.XMLStreamWriter;

public class ImageBox extends ElementBox {

    private PDImageXObject image;
    private String text;
    private BoxBoundary box;

    public ImageBox(PDImageXObject image, float posX, float posY, String text) {
        this(image, posX, posY, image.getWidth(), image.getHeight(), text);
    }

    public ImageBox(PDImageXObject image, float posX, float posY, float width, float height, String text) {
        this.image = image;
        this.text = text;
        this.box = new BoxBoundary(posX, posY, width, height);
    }

    @Override
    public void setMaxWidth(float maxWidth) {
        if ( maxWidth < box.getWidth() ) {
            float scale = maxWidth / box.getWidth();
            box.setHeight(box.getHeight() * scale);
            box.setWidth(box.getWidth() * scale);
        }
    }

    @Override
    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        stream.drawImage(image, box.getPosX(), box.getPosY(), box.getWidth(), box.getHeight());
        this.writeXMLZone(writer, "carea", text, box);
    }

    @Override
    public BoxBoundary getBoxBoundary() {
        return box;
    }

}
