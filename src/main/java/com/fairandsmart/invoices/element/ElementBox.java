package com.fairandsmart.invoices.element;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;

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

    private BoxBoundary convertBox(BoxBoundary box) {
        float scale = destDpi / srcDpi;
        BoxBoundary newbox = new BoxBoundary(
                box.getPosX() * scale,
                (maxY - box.getPosY() - box.getHeight()) * scale,
                box.getWidth() * scale,
                box.getHeight() * scale);
        return newbox;
    }

    public String writeXMLZone(XMLStreamWriter writer, String type, String word, BoxBoundary box) throws XMLStreamException {
        BoxBoundary tbox = convertBox(box);
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

    public abstract BoxBoundary getBoxBoundary();

    public abstract void setMaxWidth(float width) throws IOException;


}
