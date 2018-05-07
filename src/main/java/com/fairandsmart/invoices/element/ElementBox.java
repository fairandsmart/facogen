package com.fairandsmart.invoices.element;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.util.Collections;
import java.util.List;

public abstract class ElementBox {

    private static int cpt = 0;
    private static float srcDpi = 72;
    private static float destDpi = 300;
    private static float maxX = 595;
    private static float maxY = 842;


    public int nextElementId() {
        cpt++;
        return cpt;
    }

    private BoundingBox convertBox(BoundingBox box) {
        float scale = destDpi / srcDpi;
        BoundingBox newbox = new BoundingBox(
                box.getPosX() * scale,
                (maxY - box.getPosY() - box.getHeight()) * scale,
                box.getWidth() * scale,
                box.getHeight() * scale);
        return newbox;
    }

    public String writeXMLZone(XMLStreamWriter writer, String type, String content, BoundingBox box) throws XMLStreamException {
        return this.writeXMLZone(writer, type, content, box, Collections.emptyList());
    }

    public String writeXMLZone(XMLStreamWriter writer, String type, String content, BoundingBox box, List<String> elements) throws XMLStreamException {
        BoundingBox tbox = convertBox(box);
        //String id = type + "_" + nextElementId();
        String id = "" + nextElementId();
        writer.writeStartElement("DL_ZONE");
        writer.writeAttribute("gedi_type", type);
        writer.writeAttribute("id", id);
        writer.writeAttribute("col", "" + (int) tbox.getPosX());
        writer.writeAttribute("row", "" + (int) tbox.getPosY());
        writer.writeAttribute("width", "" + (int) tbox.getWidth());
        writer.writeAttribute("height", "" + (int) tbox.getHeight());
        writer.writeAttribute("content", content);
        if ( !elements.isEmpty() ) {
            writer.writeAttribute("elements", String.join(";", elements));
        }
        writer.writeEndElement();
        return id;
    }

    public abstract BoundingBox getBoundingBox();

    public abstract void setWidth(float width) throws Exception;

    public abstract void setHeight(float height) throws Exception;

    public abstract void translate(float offsetX, float offsetY);

    public abstract void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception;

}
