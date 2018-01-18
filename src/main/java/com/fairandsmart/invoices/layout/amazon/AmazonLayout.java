package com.fairandsmart.invoices.layout.amazon;

import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.element.border.BorderBox;
import com.fairandsmart.invoices.element.container.VerticalElementContainer;
import com.fairandsmart.invoices.element.image.ImageBox;
import com.fairandsmart.invoices.element.table.TableRowBox;
import com.fairandsmart.invoices.element.line.HorizontalLineBox;
import com.fairandsmart.invoices.element.textbox.SimpleTextBox;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import com.fairandsmart.invoices.data.Product;
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
    public void builtInvoice(InvoiceModel model, PDDocument document, XMLStreamWriter writer) throws Exception {
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
        infos.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "Invoice for "+model.getReference().getValue()+" "+model.getDate().getValue()));
        infos.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 10, 0, 0, "Retail / TaxInvoice / Cash Memorandum"));
        infos.build(contentStream, writer);

        new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 10, 25, 761, "Sold By").build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 750,  model.getCompany().getLogo().getName()).build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 740, "A-43, Ground Floor, Mohan Cooperative Industrial Estate,").build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 730, "Main Matura Road,").build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 720, "New Delhi - 110044").build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 710, "Delhi, India").build(contentStream, writer);
        String vatSentence = model.getCompany().getVatNumber().getLabel()+" "+model.getCompany().getVatNumber().getValue();
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 690, vatSentence).build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 680, "CST Number: "+model.getCompany().getVatNumber().getValue()).build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, page.getMediaBox().getWidth()/2, 680, model.getReference().getValue()).build(contentStream, writer);

        contentStream.moveTo(20, 650);
        contentStream.lineTo( page.getMediaBox().getWidth()-(20*2), 650);
        contentStream.stroke();

        new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 25, 630, "Billing Address").build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, page.getMediaBox().getWidth()/2, 630, "Shipping Address").build(contentStream, writer);;
        SimpleTextBox box1 = new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 25, 600, "Nature of Transaction: sale");
        box1.build(contentStream, writer);

        float[] configRow = {20f, 120f, 60f, 60f, 60f, 60f, 60f, 60f};
        TableRowBox firstLine = new TableRowBox(configRow, 0, 0);
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "QTY"));
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "DESCRIPTION"));
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "GROSS AMOUNT"));
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "DISCOUNT"));
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "NET AMOUNT (tax inclusive)"));
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "TAX TYPE"));
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "TAX RATE"));
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "TAX AMOUNT(included in net)"));

        VerticalElementContainer verticalInvoiceItems = new VerticalElementContainer(25, 550, 500 );
        verticalInvoiceItems.addElement(firstLine);
        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0, page.getMediaBox().getWidth()-(20*2), 0));

        Product product = new Product(
            1F,
            "Microsoft Xbox 360 Controller for windows",
            2390F,
            2390F,
            "CST",
            12.5F,
            0F
        );

         /* TODO
        VerticalElementContainer descriptionC = new VerticalElementContainer(0F,0F, 30);
        descriptionC.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 0, 0, product.getDescription()));
        descriptionC.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "SERIAL NUMBER 096"));
        */

        TableRowBox firstProduct = new TableRowBox(configRow, 0, 0);
        firstProduct.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, Float.toString(product.getQty())));
        firstProduct.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, product.getDescription()));
        firstProduct.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, Float.toString(product.getPriceByUnitWithoutVAT())));
        firstProduct.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, Float.toString(product.getDiscount())));
        firstProduct.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, Float.toString((product.getNetAmount()))));
        firstProduct.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, product.getTaxType()));
        firstProduct.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, Float.toString(product.getTaxRate())));
        firstProduct.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, Float.toString(product.getTaxAmount())));

        verticalInvoiceItems.addElement(firstProduct);

        TableRowBox shipping = new TableRowBox(configRow, 0, 0);
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, ""));
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, "Shipping"));
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, "Rs 0.00"));
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, ""));
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, "Rs 0.00"));
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, "Tax"));
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, "0"));
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, "Rs 0.00"));

        verticalInvoiceItems.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, ""));
        verticalInvoiceItems.addElement(shipping);
        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0, page.getMediaBox().getWidth()-(20*2), 0));
        verticalInvoiceItems.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, ""));

        TableRowBox titleTotalInvoice = new TableRowBox(configRow, 0, 0);
        titleTotalInvoice.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 0, 0, ""));
        titleTotalInvoice.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 0, 0, ""));
        titleTotalInvoice.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 0, 0, "TOTAL GROSS AMOUNT"));
        titleTotalInvoice.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 0, 0, "TOTAL DISCOUNT"));
        titleTotalInvoice.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 0, 0, "FINAL NET AMOUNT"));
        titleTotalInvoice.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 0, 0, "TAX TYPE"));
        titleTotalInvoice.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 0, 0, "TAX RATE"));
        titleTotalInvoice.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 0, 0, "TAX AMOUNT"));
        verticalInvoiceItems.addElement(titleTotalInvoice);

        verticalInvoiceItems.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, ""));
        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0, page.getMediaBox().getWidth()-(20*2), 0));

        TableRowBox totalInvoice1 = new TableRowBox(configRow, 0, 0);
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, ""));
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, ""));
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "Rs. 2,390.00"));
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, ""));
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "Rs. 2,390.00"));
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "CST@"));
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "12.5%"));
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "Rs. 265.56"));
        verticalInvoiceItems.addElement(totalInvoice1);

        TableRowBox totalInvoice2 = new TableRowBox(configRow, 0, 0);
        totalInvoice2.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, ""));
        totalInvoice2.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, ""));
        totalInvoice2.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, ""));
        totalInvoice2.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, ""));
        totalInvoice2.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, ""));
        totalInvoice2.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "Tax@"));
        totalInvoice2.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "0%"));
        totalInvoice2.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "Rs. 0.00"));
        verticalInvoiceItems.addElement(totalInvoice2);

        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0, page.getMediaBox().getWidth()-(20*2), 0));

        String certification = this.getClass().getClassLoader().getResource("parts/amazon/certification.png").getFile();
        PDImageXObject pdCertification = PDImageXObject.createFromFile(certification, document);
        ImageBox imageFooter = new ImageBox(pdCertification, 0,0, page.getMediaBox().getWidth()-40, pdBarcode.getHeight(), "certification");

        verticalInvoiceItems.addElement(imageFooter);
        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0, page.getMediaBox().getWidth()-(20*2), 0));

        SimpleTextBox addressFooter = new SimpleTextBox(PDType1Font.HELVETICA, 10, 0, 0, "Registered Address for ZIP TECHNOLOGIES, 245A, SANT NAGAR, 2nd Floor, East of Kailash, New Delhi - 110065, New Delhi, In");
        addressFooter.setWidth(500);
        verticalInvoiceItems.addElement(addressFooter);
        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0, page.getMediaBox().getWidth()-(20*2), 0));

        verticalInvoiceItems.build(contentStream, writer);

        /*
        VerticalElementContainer footerBody = new VerticalElementContainer(25, 300, 500 );

        SimpleTextBox condition = new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "I/ We hereby certify that " +
                "my/ our registration certificate " +
                "under the Delhi value added Added Tax Act, 2004 is in force on the date on which the sale of the " +
                "goods specified in this tax invoice is made by me / us and that the transaction of sale covered by this " +
                "tax invoice has been effected by me / us and it shall be accounted for in the turnover of sales while " +
                "filing of return and the due tax, if any, payable on the sale has been paid or shall be paid");

        condition.setWidth(350);

        footerBody.addElement(condition);

        footerBody.build(contentStream, writer);
        */

        /*
        String certification = this.getClass().getClassLoader().getResource("parts/amazon/certification.png").getFile();
        PDImageXObject pdCertification = PDImageXObject.createFromFile(certification, document);
        new ImageBox(pdCertification, 25, 150, page.getMediaBox().getWidth()-40, pdBarcode.getHeight(), "certification").build(contentStream, writer);

        */

        new HorizontalLineBox(20,110, page.getMediaBox().getWidth()-(20*2), 0).build(contentStream, writer);

        /*
        List<TableCellBuilder> cellFirstLine = new ArrayList<TableCellBuilder>();
        cellFirstLine.add(new TableCellBuilder(contentStream,15, "Test"));
        cellFirstLine.add(new TableCellBuilder(contentStream,20, "Test2"));

        firstLine.setCells(cellFirstLine);
        */

        // firstLine.build(contentStream, writer);

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
