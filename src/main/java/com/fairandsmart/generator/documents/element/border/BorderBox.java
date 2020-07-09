package com.fairandsmart.generator.documents.element.border;

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
import java.awt.*;

public class BorderBox extends ElementBox {

    private Color borderColor;
    private Color color;
    private int thick;
    private BoundingBox box;

    public BorderBox(Color borderColor, Color color, int thick, float posX, float posY, float width, float height) {
        this.borderColor = borderColor;
        this.color = color;
        this.thick = thick;
        this.box = new BoundingBox(posX, posY, width, height);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return box;
    }

    @Override
    public void translate(float offsetX, float offsetY) {
        this.getBoundingBox().translate(offsetX, offsetY);
    }

    @Override
    public void setWidth(float width) throws Exception {
        this.getBoundingBox().setWidth(width);
    }

    @Override
    public void setHeight(float height) throws Exception {
        this.getBoundingBox().setHeight(height);
    }

    @Override
    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        stream.setNonStrokingColor(borderColor);
        stream.addRect(box.getPosX(), box.getPosY(), box.getWidth(), box.getHeight());
        stream.fill();
        stream.setNonStrokingColor(color);
        stream.addRect(box.getPosX()+thick, box.getPosY()+thick, box.getWidth()-(thick*2), box.getHeight()-(thick*2));
        stream.fill();
    }

}
