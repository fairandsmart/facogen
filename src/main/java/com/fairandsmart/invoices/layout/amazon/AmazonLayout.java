package com.fairandsmart.invoices.layout.amazon;

import com.fairandsmart.invoices.element.background.BorderBuilder;
import com.fairandsmart.invoices.element.image.ImageBuilder;
import com.fairandsmart.invoices.element.tablerow.TableRowBuilder;
import com.fairandsmart.invoices.element.textbox.SingleLineTextBoxBuilder;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.util.ArrayList;
import java.util.List;

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
        new BorderBuilder(Color.ORANGE, 4, page).build(contentStream, writer);

        //Barcode top
        String barcode = this.getClass().getClassLoader().getResource("parts/amazon/barcode1.jpg").getFile();
        PDImageXObject pdBarcode = PDImageXObject.createFromFile(barcode, document);
        new ImageBuilder(pdBarcode, page.getMediaBox().getWidth() / 2, 710, pdBarcode.getWidth() / 2, pdBarcode.getHeight() / 2, "DMmZXznqN /-1 of 1 -// std-in-remote").build(contentStream, writer);

        //Text top
        new SingleLineTextBoxBuilder(PDType1Font.HELVETICA, 9, 25, 800, "Page 1 of 1, 1-1/1").build(contentStream, writer);
        new SingleLineTextBoxBuilder(PDType1Font.HELVETICA, 9, 25, 789, "Invoice for DMmZXznqN Oct 5, 2014").build(contentStream, writer);
        new SingleLineTextBoxBuilder(PDType1Font.HELVETICA_BOLD, 10, 25, 775, "Retail / TaxInvoice / Cash Memorandum").build(contentStream, writer);
        new SingleLineTextBoxBuilder(PDType1Font.HELVETICA_BOLD, 10, 25, 761, "Sold By").build(contentStream, writer);


        new SingleLineTextBoxBuilder(PDType1Font.HELVETICA, 9, 25, 750, "ZIP TECHNOLOGIES").build(contentStream, writer);
        new SingleLineTextBoxBuilder(PDType1Font.HELVETICA, 9, 25, 740, "A-43, Ground Floor, Mohan Cooperative Industrial Estate,").build(contentStream, writer);
        new SingleLineTextBoxBuilder(PDType1Font.HELVETICA, 9, 25, 730, "Main Matura Road,").build(contentStream, writer);
        new SingleLineTextBoxBuilder(PDType1Font.HELVETICA, 9, 25, 720, "New Delhi - 110044").build(contentStream, writer);
        new SingleLineTextBoxBuilder(PDType1Font.HELVETICA, 9, 25, 710, "Delhi, India").build(contentStream, writer);

        new SingleLineTextBoxBuilder(PDType1Font.HELVETICA, 9, 25, 690, "VAT/TIN Number: 07920234124").build(contentStream, writer);
        new SingleLineTextBoxBuilder(PDType1Font.HELVETICA, 9, 25, 680, "CST Number: 07920234124").build(contentStream, writer);
        new SingleLineTextBoxBuilder(PDType1Font.HELVETICA, 9, page.getMediaBox().getWidth()/2, 680, "Invoice Number: DL-SDEA-140782631-24").build(contentStream, writer);

        contentStream.moveTo(20, 650);
        contentStream.lineTo( page.getMediaBox().getWidth()-(20*2), 650);
        contentStream.stroke();

        new SingleLineTextBoxBuilder(PDType1Font.HELVETICA_BOLD, 9, 25, 630, "Billing Address").build(contentStream, writer);
        new SingleLineTextBoxBuilder(PDType1Font.HELVETICA_BOLD, 9, page.getMediaBox().getWidth()/2, 630, "Billing Address").build(contentStream, writer);;
        new SingleLineTextBoxBuilder(PDType1Font.HELVETICA_BOLD, 9, 25, 600, "Nature of Transaction: sale").build(contentStream, writer);

        /*
        TableRowBuilder firstLine = new TableRowBuilder(contentStream);
        firstLine.setCell()
        */


        List<TableRowBuilder> data = new ArrayList<TableRowBuilder>();
        data.add(new TableRowBuilder(contentStream,15, "Test"));
        data.add(new TableRowBuilder(contentStream,20, "Test2"));

        this.createTable(data);

        //footer
        String footer = this.getClass().getClassLoader().getResource("parts/amazon/footer.png").getFile();
        PDImageXObject pdFooter = PDImageXObject.createFromFile(footer, document);
        contentStream.drawImage(pdFooter, 25, 15, pdFooter.getWidth() / 1.7f, pdFooter.getHeight() / 1.7f);



        contentStream.close();

        writer.writeEndElement();
    }

    public void createTable(List<TableRowBuilder> parameters) throws Exception {

        for(TableRowBuilder oneLine : parameters) {
            oneLine.render();
        }



    }
}
