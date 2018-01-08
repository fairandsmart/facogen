package com.fairandsmart.invoices.element;

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;

public abstract class ElementBuilder {

    private static int cpt = 0;

    public int nextElementNumber() {
        cpt++;
        return cpt;
    }

    public abstract void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception;

}
