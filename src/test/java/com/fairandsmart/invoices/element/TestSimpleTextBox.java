package com.fairandsmart.invoices.element;

import com.fairandsmart.invoices.InvoiceGenerator;
import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.model.InvoiceModel;
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
public class TestSimpleTextBox implements InvoiceLayout {

    @Override
    public void builtInvoice(InvoiceModel model, PDDocument document, XMLStreamWriter writer) throws Exception {

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

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

