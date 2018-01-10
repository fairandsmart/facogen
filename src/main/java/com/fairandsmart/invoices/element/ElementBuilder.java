package com.fairandsmart.invoices.element;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamWriter;

public abstract class ElementBuilder {

    private static int cpt = 0;
    private static int srcDpi = 72;
    private static int destDpi = 300;
    private static float maxX = 595;
    private static float maxY = 842;


    public int nextElementNumber() {
        cpt++;
        return cpt;
    }

    public float[] convertZone(float posX, float posY, float width, float height) {
        float scale = destDpi / srcDpi;
        float[] conv = new float[4];
        conv[0] = posX * scale;
        conv[1] = (maxY - posY - height) * scale;
        conv[2] = width * scale;
        conv[3] = height * scale;
        return conv;
    }

    //TODO create a helper for position determination including the inversion of Y axis.

    public abstract void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception;

}
