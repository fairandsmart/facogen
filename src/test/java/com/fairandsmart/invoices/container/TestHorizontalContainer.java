package com.fairandsmart.invoices.container;

import com.fairandsmart.invoices.InvoiceGenerator;
import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.element.border.BorderBox;
import com.fairandsmart.invoices.element.container.HorizontalContainer;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import com.fairandsmart.invoices.element.textbox.SimpleTextBox;
import com.fairandsmart.invoices.layout.InvoiceLayout;
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
