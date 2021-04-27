package com.fairandsmart.generator.documents.element;

/*-
 * #%L
 * FacoGen / A tool for annotated GEDI based invoice generation.
 * 
 * Authors:
 * 
 * Xavier Lefevre <xavier.lefevre@fairandsmart.com> / FairAndSmart
 * Nicolas Rueff <nicolas.rueff@fairandsmart.com> / FairAndSmart
 * Alan Balbo <alan.balbo@fairandsmart.com> / FairAndSmart
 * Frederic Pierre <frederic.pierre@fairansmart.com> / FairAndSmart
 * Victor Guillaume <victor.guillaume@fairandsmart.com> / FairAndSmart
 * Jérôme Blanchard <jerome.blanchard@fairandsmart.com> / FairAndSmart
 * Aurore Hubert <aurore.hubert@fairandsmart.com> / FairAndSmart
 * Kevin Meszczynski <kevin.meszczynski@fairandsmart.com> / FairAndSmart
 * %%
 * Copyright (C) 2019 Fair And Smart
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

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

    protected BoundingBox convertBox(BoundingBox box) {
        float scale = destDpi / srcDpi;
        BoundingBox newbox = new BoundingBox(
                box.getPosX() * scale,
                (maxY - box.getPosY() - box.getHeight()) * scale,
                box.getWidth() * scale,
                box.getHeight() * scale);
        return newbox;
    }

    public String writeXMLZone(XMLStreamWriter writer, String type, String content, BoundingBox box, String cclass) throws XMLStreamException {
        //return this.writeXMLZone(writer, type, content, box, Collections.emptyList());

        BoundingBox tbox = convertBox(box);
        String id = "" + nextElementId();
        writer.writeStartElement("DL_ZONE");
        writer.writeAttribute("gedi_type", type);
        writer.writeAttribute("id", id);
        writer.writeAttribute("col", "" + (int) tbox.getPosX());
        writer.writeAttribute("row", "" + (int) tbox.getPosY());
        writer.writeAttribute("width", "" + (int) tbox.getWidth());
        writer.writeAttribute("height", "" + (int) tbox.getHeight());
        writer.writeAttribute("contents", content);
        writer.writeAttribute("correctclass", cclass);
        writer.writeEndElement();
        writer.writeCharacters(System.getProperty("line.separator"));
        return id;

    }

//    public String writeXMLZone(XMLStreamWriter writer, String type, String content, BoundingBox box, List<String> elements) throws XMLStreamException {
//        BoundingBox tbox = convertBox(box);
//        //String id = type + "_" + nextElementId();
//        String id = "" + nextElementId();
//        writer.writeStartElement("DL_ZONE");
//        writer.writeAttribute("gedi_type", type);
//        writer.writeAttribute("id", id);
//        writer.writeAttribute("col", "" + (int) tbox.getPosX());
//        writer.writeAttribute("row", "" + (int) tbox.getPosY());
//        writer.writeAttribute("width", "" + (int) tbox.getWidth());
//        writer.writeAttribute("height", "" + (int) tbox.getHeight());
//        writer.writeAttribute("contents", content);
//        if ( !elements.isEmpty() ) {
//            writer.writeAttribute("elements", String.join(";", elements));
//        }
//        writer.writeEndElement();
//        return id;
//    }

    public abstract BoundingBox getBoundingBox();

    public abstract void setWidth(float width) throws Exception;

    public abstract void setHeight(float height) throws Exception;

    public abstract void translate(float offsetX, float offsetY);

    public abstract void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception;

}
