package com.fairandsmart.generator.invoices.container;

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

import com.fairandsmart.generator.invoices.InvoiceGenerator;
import com.fairandsmart.generator.invoices.element.border.BorderBox;
import com.fairandsmart.generator.invoices.element.container.HorizontalContainer;
import com.fairandsmart.generator.invoices.element.textbox.SimpleTextBox;
import com.fairandsmart.generator.invoices.layout.InvoiceLayout;
import com.fairandsmart.generator.invoices.data.generator.GenerationContext;
import com.fairandsmart.generator.invoices.data.model.InvoiceModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.Test;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestHorizontalContainer implements InvoiceLayout {

    @Override
    public String name() {
        return "TestHorizontal";
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


        new BorderBox(Color.RED, Color.WHITE, 15, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight()).build(contentStream, writer);

        HorizontalContainer container = new HorizontalContainer(50,700);
        container.setBackgroundColor(Color.GRAY);

        PDFont font = PDType1Font.HELVETICA_BOLD;
        SimpleTextBox stb = new SimpleTextBox(font, 12, 0, 0, "Simple title of text");
        stb.setBackgroundColor(Color.ORANGE);
        container.addElement(stb);
        font = PDType1Font.HELVETICA;
        SimpleTextBox stb2 = new SimpleTextBox(font, 9, 0, 0, "pretty subtitle");
        stb2.setBackgroundColor(Color.RED);
        container.addElement(stb2);
        SimpleTextBox stb3 = new SimpleTextBox(font, 9, 0, 0, "line3");
        stb3.setBackgroundColor(Color.YELLOW);
        container.addElement(stb3);
        container.build(contentStream, writer);


        contentStream.close();

        writer.writeEndElement();
    }

    @Test
    public void test() throws Exception {
        String ts = "" + System.currentTimeMillis();
        Path pdf = Paths.get("target/texthorizontal-"+ ts + ".pdf");
        Path xml = Paths.get("target/texthorizontal-"+ ts + ".xml");
        Path img = Paths.get("target/texthorizontal-"+ ts + ".tiff");

        GenerationContext ctx = GenerationContext.generate();
        InvoiceModel model = new InvoiceModel.Generator().generate(ctx);
        InvoiceGenerator.getInstance().generateInvoice(this, model, pdf, xml, img);
    }

}
