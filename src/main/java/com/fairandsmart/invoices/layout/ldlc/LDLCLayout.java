package com.fairandsmart.invoices.layout.ldlc;

import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import com.fairandsmart.invoices.element.textbox.SimpleTextBox;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import com.fairandsmart.invoices.layout.head.CompanyInfoBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;

public class LDLCLayout implements InvoiceLayout {

    @Override
    public void builtInvoice(InvoiceModel model, PDDocument document, XMLStreamWriter writer) throws Exception {

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        writer.writeStartElement("DL_PAGE");
        writer.writeAttribute("gedi_type", "DL_PAGE");
        writer.writeAttribute("pageID", "1");
        writer.writeAttribute("width", "2480");
        writer.writeAttribute("height", "3508");

        PDFont font = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);



        VerticalContainer container = new VerticalContainer(20, 800, 0);
        SimpleTextBox simpleTextBox = new SimpleTextBox(fontBold, 12, 0, 0, "This is my first Text");
        simpleTextBox.setBackgroundColor(Color.GRAY);
        simpleTextBox.setTextColor(Color.RED);
        simpleTextBox.setPadding(25, 10, 25, 10);
        simpleTextBox.setEntityName("SN");
        container.addElement(simpleTextBox);

        SimpleTextBox simpleTextBox2 = new SimpleTextBox(fontBold, 12, 0, 0, "Line2");
        simpleTextBox2.setBackgroundColor(Color.GRAY);
        simpleTextBox2.setTextColor(Color.YELLOW);
        simpleTextBox2.setPadding(25, 10, 25, 10);
        simpleTextBox2.setEntityName("SN");
        container.addElement(simpleTextBox2);

        container.build(contentStream, writer);

        contentStream.close();

    }
}
