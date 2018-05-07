package com.fairandsmart.invoices.element.image;

import com.fairandsmart.invoices.element.BoundingBox;
import com.fairandsmart.invoices.element.ElementBox;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.xml.stream.XMLStreamWriter;

public class ImageBox extends ElementBox {

    private PDImageXObject image;
    private String text;
    private BoundingBox box;

    public ImageBox(PDImageXObject image, float posX, float posY, String text) {
        this(image, posX, posY, image.getWidth(), image.getHeight(), text);
    }

    public ImageBox(PDImageXObject image, float posX, float posY, float width, float height, String text) {
        this.image = image;
        this.text = text;
        this.box = new BoundingBox(posX, posY, width, height);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return box;
    }

    @Override
    public void setWidth(float width) {
        float scale = width / box.getWidth();
        box.setHeight(box.getHeight() * scale);
        box.setWidth(box.getWidth() * scale);
    }

    @Override
    public void setHeight(float height) {
        float scale = height / box.getHeight();
        box.setHeight(box.getHeight() * scale);
        box.setWidth(box.getWidth() * scale);
    }

    @Override
    public void translate(float offsetX, float offsetY) {
        box.translate(offsetX, offsetY);
    }

    @Override
    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        stream.drawImage(image, box.getPosX(), box.getPosY(), box.getWidth(), box.getHeight());
        this.writeXMLZone(writer, "ocr_carea", text, box);
    }

}
