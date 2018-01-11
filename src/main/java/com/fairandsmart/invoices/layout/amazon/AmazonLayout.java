package com.fairandsmart.invoices.layout.amazon;

import com.fairandsmart.invoices.element.border.BorderBox;
import com.fairandsmart.invoices.element.container.VerticalElementContainer;
import com.fairandsmart.invoices.element.image.ImageBox;
import com.fairandsmart.invoices.element.textbox.SimpleTextBox;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;

public class AmazonLayout implements InvoiceLayout {

    @Override
    public void builtInvoice(PDDocument document, XMLStreamWriter writer) throws Exception {
        PDPage page = new PDPage(PDRectangle.A4);

        document.addPage(page);
        writer.writeStartElement("DL_PAGE");
        writer.writeAttribute("gedi_type", "DL_PAGE");
        writer.writeAttribute("pageID", "1");
        writer.writeAttribute("width", "2480");
        writer.writeAttribute("height", "3508");

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        new BorderBox(Color.ORANGE, Color.WHITE, 4, 0, 0, page.getMediaBox().getWidth(), page.getMediaBox().getHeight()).build(contentStream, writer);

        //Barcode top
        String barcode = this.getClass().getClassLoader().getResource("parts/amazon/barcode1.jpg").getFile();
        PDImageXObject pdBarcode = PDImageXObject.createFromFile(barcode, document);
        new ImageBox(pdBarcode, page.getMediaBox().getWidth() / 2, 710, pdBarcode.getWidth() / 2, pdBarcode.getHeight() / 2, "DMmZXznqN /-1 of 1 -// std-in-remote").build(contentStream, writer);

        //Text top
        VerticalElementContainer infos = new VerticalElementContainer(25, 810, 500 );
        infos.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "Page 1 of 1, 1-1/1"));
        infos.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "Invoice for DMmZXznqN Oct 5, 2014"));
        infos.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 10, 25, 775, "Retail / TaxInvoice / Cash Memorandum"));
        infos.build(contentStream, writer);

        new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 10, 25, 761, "Sold By").build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 750, "ZIP TECHNOLOGIES").build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 740, "A-43, Ground Floor, Mohan Cooperative Industrial Estate,").build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 730, "Main Matura Road,").build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 720, "New Delhi - 110044").build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 710, "Delhi, India").build(contentStream, writer);

        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 690, "VAT/TIN Number: 07920234124").build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 680, "CST Number: 07920234124").build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, page.getMediaBox().getWidth()/2, 680, "Invoice Number: DL-SDEA-140782631-24").build(contentStream, writer);

        contentStream.moveTo(20, 650);
        contentStream.lineTo( page.getMediaBox().getWidth()-(20*2), 650);
        contentStream.stroke();

        new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 25, 630, "Billing Address").build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, page.getMediaBox().getWidth()/2, 630, "Billing Address").build(contentStream, writer);;
        SimpleTextBox box1 = new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 25, 600, "Nature of Transaction: sale");
        box1.setWidth(30);
        box1.build(contentStream, writer);



        /*
        TableRowBox firstLine = new TableRowBox(contentStream);

        List<TableCellBuilder> cellFirstLine = new ArrayList<TableCellBuilder>();
        cellFirstLine.add(new TableCellBuilder(contentStream,15, "Test"));
        cellFirstLine.add(new TableCellBuilder(contentStream,20, "Test2"));

        firstLine.setCells(cellFirstLine);

        firstLine.build(contentStream, writer);

        /*

        List<TableRowBox> data = new ArrayList<TableRowBox>();
        data.add(new TableRowBox(contentStream,15, "Test"));
        data.add(new TableRowBox(contentStream,20, "Test2"));

        this.createTable(data);

        */

        //footer
        String footer = this.getClass().getClassLoader().getResource("parts/amazon/footer.png").getFile();
        PDImageXObject pdFooter = PDImageXObject.createFromFile(footer, document);
        contentStream.drawImage(pdFooter, 25, 15, pdFooter.getWidth() / 1.7f, pdFooter.getHeight() / 1.7f);



        contentStream.close();

        writer.writeEndElement();
    }


}
