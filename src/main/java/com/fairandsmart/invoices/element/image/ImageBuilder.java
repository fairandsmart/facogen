package com.fairandsmart.invoices.element.image;

import com.fairandsmart.invoices.element.ElementBox;
import com.fairandsmart.invoices.element.ElementBuilder;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.xml.stream.XMLStreamWriter;

public class ImageBuilder extends ElementBuilder {

    private PDImageXObject image;
    private String text;
    private ElementBox box;

    public ImageBuilder(PDImageXObject image, float posX, float posY, String text) {
        this(image, posX, posY, image.getWidth(), image.getHeight(), text);
    }

    public ImageBuilder(PDImageXObject image, float posX, float posY, float width, float height, String text) {
        this.image = image;
        this.text = text;
        this.box = new ElementBox(posX, posY, width, height);
    }

    @Override
    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        stream.drawImage(image, box.getPosX(), box.getPosY(), box.getWidth(), box.getHeight());
        this.writeXMLZone(writer, "carea", text, box);
        writer.writeEndElement();
    }

}
