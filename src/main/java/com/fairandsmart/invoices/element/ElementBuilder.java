package com.fairandsmart.invoices.element;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public abstract class ElementBuilder {

    private static int cpt = 0;
    private static float srcDpi = 72;
    private static float destDpi = 300;
    private static float maxX = 595;
    private static float maxY = 842;


    public int nextElementId() {
        cpt++;
        return cpt;
    }

    private ElementBox convertBox(ElementBox box) {
        float scale = destDpi / srcDpi;
        ElementBox newbox = new ElementBox(
                box.getPosX() * scale,
                (maxY - box.getPosY() - box.getHeight()) * scale,
                box.getWidth() * scale,
                box.getHeight() * scale);
        return newbox;
    }

    public String writeXMLZone(XMLStreamWriter writer, String type, String word, ElementBox box) throws XMLStreamException {
        ElementBox tbox = convertBox(box);
        String id = type + "_" + nextElementId();
        writer.writeStartElement("DL_ZONE");
        writer.writeAttribute("gedi_type", "ocr_" + type);
        writer.writeAttribute("id", id);
        writer.writeAttribute("col", "" + (int) tbox.getPosX());
        writer.writeAttribute("row", "" + (int) tbox.getPosY());
        writer.writeAttribute("width", "" + (int) tbox.getWidth());
        writer.writeAttribute("height", "" + (int) tbox.getHeight());
        writer.writeAttribute("content", word);
        writer.writeEndElement();
        return id;
    }

    public abstract void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception;

}
