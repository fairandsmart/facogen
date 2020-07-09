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

import com.fairandsmart.generator.documents.InvoiceGenerator;
import com.fairandsmart.generator.documents.element.textbox.SimpleTextBox;
import com.fairandsmart.generator.documents.data.generator.GenerationContext;
import com.fairandsmart.generator.documents.data.model.InvoiceModel;
import com.fairandsmart.generator.documents.layout.InvoiceLayout;
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
public class TestSimpleTextBox implements InvoiceLayout {

    @Override
    public String name() {
        return "TestSimpleTextBox";
    }

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
        SimpleTextBox stb = new SimpleTextBox(font, 12, 20, currentPosY, "Simple title of text");
        stb.setBackgroundColor(Color.ORANGE);
        stb.build(contentStream, writer);
        currentPosY -= stb.getBoundingBox().getHeight();

        System.out.println("posY: " + currentPosY);
        SimpleTextBox stb2 = new SimpleTextBox(font, 12, 20, currentPosY, "Simple title of text which is a little bit longer without max width");
        stb2.setBackgroundColor(Color.CYAN);
        stb2.build(contentStream, writer);
        currentPosY -= stb2.getBoundingBox().getHeight();

        System.out.println("posY: " + currentPosY);
        SimpleTextBox stb3 = new SimpleTextBox(font, 12, 60, currentPosY, "Simple title of text which is a little bit longer but WITH max width");
        stb3.setWidth(100);
        stb3.setBackgroundColor(Color.PINK);
        stb3.build(contentStream, writer);
        currentPosY -= stb3.getBoundingBox().getHeight();

        System.out.println("posY: " + currentPosY);
        SimpleTextBox stb4 = new SimpleTextBox(font, 12, 20, currentPosY, "Simple text with padding");
        stb4.setBackgroundColor(Color.YELLOW);
        stb4.setPadding(20, 10, 75, 50);
        stb4.build(contentStream, writer);
        currentPosY -= stb4.getBoundingBox().getHeight();

        System.out.println("posY: " + currentPosY);
        SimpleTextBox stb5 = new SimpleTextBox(font, 12, 20, currentPosY, "Align Right");
        stb5.setBackgroundColor(Color.LIGHT_GRAY);
        stb5.setWidth(150);
        stb5.setHalign(HAlign.RIGHT);
        stb5.build(contentStream, writer);
        currentPosY -= stb5.getBoundingBox().getHeight();

        System.out.println("posY: " + currentPosY);
        SimpleTextBox stb6 = new SimpleTextBox(font, 12, 20, currentPosY, "Align right with padding");
        stb6.setBackgroundColor(Color.ORANGE);
        stb6.setPadding(10, 0, 10, 0);
        stb6.setWidth(200);
        stb6.setHalign(HAlign.RIGHT);
        stb6.build(contentStream, writer);
        currentPosY -= stb6.getBoundingBox().getHeight();

        System.out.println("posY: " + currentPosY);
        SimpleTextBox stb7 = new SimpleTextBox(font, 12, 20, currentPosY, "Align center");
        stb7.setBackgroundColor(Color.LIGHT_GRAY);
        stb7.setWidth(150);
        stb7.setHalign(HAlign.CENTER);
        stb7.build(contentStream, writer);
        currentPosY -= stb7.getBoundingBox().getHeight();

        System.out.println("posY: " + currentPosY);
        SimpleTextBox stb8 = new SimpleTextBox(font, 12, 20, currentPosY, "Align center padding asym");
        stb8.setBackgroundColor(Color.ORANGE);
        stb8.setPadding(10, 0, 50, 0);
        stb8.setWidth(250);
        stb8.setHalign(HAlign.CENTER);
        stb8.build(contentStream, writer);
        currentPosY -= stb8.getBoundingBox().getHeight();

        System.out.println("posY: " + currentPosY);
        SimpleTextBox stb9 = new SimpleTextBox(font, 12, 20, currentPosY, "Align right multi line of text should be placed");
        stb9.setBackgroundColor(Color.LIGHT_GRAY);
        stb9.setWidth(100);
        stb9.setHalign(HAlign.RIGHT);
        stb9.build(contentStream, writer);
        currentPosY -= stb9.getBoundingBox().getHeight();

        System.out.println("posY: " + currentPosY);
        SimpleTextBox stb10 = new SimpleTextBox(font, 12, 20, currentPosY, "Align center multi line of text should be placed");
        stb10.setBackgroundColor(Color.YELLOW);
        stb10.setWidth(100);
        stb10.setHalign(HAlign.CENTER);
        stb10.build(contentStream, writer);
        currentPosY -= stb10.getBoundingBox().getHeight();

        System.out.println("posY: " + currentPosY);
        SimpleTextBox stb11 = new SimpleTextBox(font, 12, 20, currentPosY, "Align center multi line of text with padding");
        stb11.setBackgroundColor(Color.LIGHT_GRAY);
        stb11.setPadding(10, 20, 20, 20);
        stb11.setWidth(150);
        stb11.setHalign(HAlign.CENTER);
        stb11.build(contentStream, writer);

        contentStream.close();

        writer.writeEndElement();
    }

    @Test
    public void test() throws Exception {
        String ts = "" + System.currentTimeMillis();
        Path pdf = Paths.get("target/textbox-"+ ts + ".pdf");
        Path xml = Paths.get("target/textbox-"+ ts + ".xml");
        Path img = Paths.get("target/textbox-"+ ts + ".tiff");

        GenerationContext ctx = GenerationContext.generate();
        InvoiceModel model = new InvoiceModel.Generator().generate(ctx);
        InvoiceGenerator.getInstance().generateInvoice(this, model, pdf, xml, img);
    }
}

