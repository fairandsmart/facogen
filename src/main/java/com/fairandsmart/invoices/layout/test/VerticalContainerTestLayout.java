package com.fairandsmart.invoices.layout.test;

import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.element.border.BorderBox;
import com.fairandsmart.invoices.element.container.VerticalElementContainer;
import com.fairandsmart.invoices.element.textbox.SimpleTextBox;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;

public class VerticalContainerTestLayout implements InvoiceLayout {

    @Override
    public void builtInvoice(InvoiceModel model, PDDocument document, XMLStreamWriter writer) throws Exception {

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(document, page);


        new BorderBox(Color.RED, Color.WHITE, 15, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight()).build(contentStream, writer);

        VerticalElementContainer container = new VerticalElementContainer(50,500,100);
        PDFont font = PDType1Font.HELVETICA_BOLD;
        container.addElement(new SimpleTextBox(font, 12, 0,0, "Simple title of text"));
        font = PDType1Font.HELVETICA;
        container.addElement(new SimpleTextBox(font, 9, 0,0, "pretty subtitle"));
        container.build(contentStream, writer);


        contentStream.close();
    }
}

