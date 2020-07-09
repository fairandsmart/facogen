package com.fairandsmart.generator.documents.element.container;

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

import com.fairandsmart.generator.documents.element.BoundingBox;
import com.fairandsmart.generator.documents.element.ElementBox;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.xml.stream.XMLStreamWriter;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

public class LayeredContainer extends ElementBox {

    private static final Logger LOGGER = Logger.getLogger(LayeredContainer.class.getName());

    private Map<Integer, ElementBox> elements;
    private BoundingBox box;

    //TODO manage aligment and padding
    public LayeredContainer(float posX, float posY, float width, float height) {
        this.elements = new TreeMap<>();
        this.box = new BoundingBox(posX, posY, width, height);
    }

    public void addElement(int layer, ElementBox element) {
        this.elements.put(layer, element);
        element.getBoundingBox().setPosX(box.getPosX());
        element.getBoundingBox().setPosY(box.getPosY());
        if ( element.getBoundingBox().getWidth() > box.getWidth() ) {
            box.setWidth(element.getBoundingBox().getWidth());
            //TODO maybe resize all existing elements
        }
        if ( element.getBoundingBox().getHeight() > box.getHeight() ) {
            box.setHeight(element.getBoundingBox().getHeight());
            //TODO maybe resize all existing elements
        }
    }

    @Override
    public BoundingBox getBoundingBox() {
        return box;
    }

    @Override
    public void setWidth(float width) throws Exception {
        for ( ElementBox element : elements.values() ) {
            element.setWidth(width);
        }
        this.box.setWidth(width);
    }

    @Override
    public void setHeight(float height) throws Exception {
        for ( ElementBox element : elements.values() ) {
            element.setHeight(height);
        }
        this.box.setHeight(height);
    }

    @Override
    public void translate(float offsetX, float offsetY) {
        box.translate(offsetX, offsetY);
        for ( ElementBox element : elements.values() ) {
            element.translate(offsetX, offsetY);
        }
    }

    @Override
    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        for ( ElementBox element : elements.values() ) {
            element.build(stream, writer);
        }
    }

}
