package com.fairandsmart.invoices.element;

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

import com.fairandsmart.invoices.InvoiceGenerator;
import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import com.fairandsmart.invoices.element.table.TableRowBox;
import com.fairandsmart.invoices.element.textbox.SimpleTextBox;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class TestTableRowBox implements InvoiceLayout {

    @Override
    public void builtInvoice(InvoiceModel model, PDDocument document, XMLStreamWriter writer) throws Exception {

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        writer.writeStartElement("DL_PAGE");
        writer.writeAttribute("gedi_type", "DL_PAGE");
        writer.writeAttribute("pageID", "1");
        writer.writeAttribute("width", "2480");
        writer.writeAttribute("height", "3508");

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        int currentPosY = 750;

        PDFont font = PDType1Font.HELVETICA_BOLD;

        contentStream.moveTo( 20, 750);
        contentStream.lineTo( 400, 750);
        contentStream.stroke();

        System.out.println("posY: " + currentPosY);

        VerticalContainer container = new VerticalContainer(25, currentPosY, 0);

        float[] config = new float[] {50, 150, 50, 50};
        TableRowBox box1 = new TableRowBox(config, 0, 0);
        SimpleTextBox stb1 = new SimpleTextBox(font, 12, 0, 0, "COL11");
        box1.addElement(stb1, false);
        SimpleTextBox stb2 = new SimpleTextBox(font, 12, 0, 0, "COL12");
        box1.addElement(stb2, false);
        SimpleTextBox stb3 = new SimpleTextBox(font, 12, 0, 0, "COL13");
        box1.addElement(stb3, false);
        SimpleTextBox stb4 = new SimpleTextBox(font, 12, 0, 0, "COL14");
        box1.addElement(stb4, false);
        box1.setBackgroundColor(Color.GRAY);
        container.addElement(box1);

        TableRowBox box2 = new TableRowBox(config, 0,0);
        SimpleTextBox stb21 = new SimpleTextBox(font, 12, 0, 0, "COL21");
        box2.addElement(stb21, false);
        SimpleTextBox stb22 = new SimpleTextBox(font, 12, 0, 0, "COL22");
        box2.addElement(stb22, false);
        SimpleTextBox stb23 = new SimpleTextBox(font, 12, 0, 0, "COL23");
        box2.addElement(stb23, false);
        SimpleTextBox stb24 = new SimpleTextBox(font, 12, 0, 0, "COL24");
        box2.addElement(stb24, false);
        container.addElement(box2);

        TableRowBox box3 = new TableRowBox(config, 0,0, VAlign.BOTTOM);
        SimpleTextBox stb31 = new SimpleTextBox(font, 12, 0, 0, "This col is going to be more thant one line");
        box3.addElement(stb31, false);
        SimpleTextBox stb32 = new SimpleTextBox(font, 12, 0, 0, "COL32");
        box3.addElement(stb32, false);
        SimpleTextBox stb33 = new SimpleTextBox(font, 12, 0, 0, "COL33");
        box3.addElement(stb33, false);
        SimpleTextBox stb34 = new SimpleTextBox(font, 12, 0, 0, "COL34");
        box3.addElement(stb34, false);
        box3.setBackgroundColor(Color.LIGHT_GRAY);
        container.addElement(box3);

        container.build(contentStream, writer);
        contentStream.close();

        writer.writeEndElement();
    }

    @Test
    public void test() throws Exception {
        String ts = "" + System.currentTimeMillis();
        Path pdf = Paths.get("target/tablerow-"+ ts + ".pdf");
        Path xml = Paths.get("target/tablerow-"+ ts + ".xml");
        Path img = Paths.get("target/tablerow-"+ ts + ".tiff");

        GenerationContext ctx = GenerationContext.generate();
        InvoiceModel model = new InvoiceModel.Generator().generate(ctx);
        InvoiceGenerator.getInstance().generateInvoice(this, model, pdf, xml, img);
    }
}

