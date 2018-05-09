package com.fairandsmart.invoices.layout.amazon;

import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.data.model.Product;
import com.fairandsmart.invoices.element.border.BorderBox;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import com.fairandsmart.invoices.element.image.ImageBox;
import com.fairandsmart.invoices.element.table.TableRowBox;
import com.fairandsmart.invoices.element.line.HorizontalLineBox;
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
        new ImageBox(pdBarcode, page.getMediaBox().getWidth() / 2, 810, pdBarcode.getWidth() / 2, pdBarcode.getHeight() / 2, "DMmZXznqN /-1 of 1 -// std-in-remote").build(contentStream, writer);

        //Text top
        VerticalContainer infos = new VerticalContainer(25, 810, 500 );
        infos.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "Page 1 of 1, 1-1/1"));
        infos.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "Invoice for "+model.getReference().getValue()+" "+model.getDate().getValue()));
        infos.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 10, 0, 0, "Retail / TaxInvoice / Cash Memorandum"));
        infos.build(contentStream, writer);

        new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 10, 25, 761, "Sold By").build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 750, model.getCompany().getLogo().getName()).build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 740, model.getCompany().getAddress().getLine1() ).build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 730,model.getCompany().getAddress().getZip()+" "+model.getCompany().getAddress().getCity()).build(contentStream, writer);
        String vatSentence = model.getCompany().getVatNumber().getLabel()+" "+model.getCompany().getVatNumber().getValue();
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 690, vatSentence).build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, 25, 680, "CST Number: "+model.getCompany().getVatNumber().getValue()).build(contentStream, writer);
        new SimpleTextBox(PDType1Font.HELVETICA, 9, page.getMediaBox().getWidth()/2, 680, model.getReference().getValue()).build(contentStream, writer);

        contentStream.moveTo(20, 650);
        contentStream.lineTo( page.getMediaBox().getWidth()-(20*2), 650);
        contentStream.stroke();

        VerticalContainer verticalAddressContainer = new VerticalContainer(25, 630, 250 );

        verticalAddressContainer.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 0, 0, "Billing Address"));
        verticalAddressContainer.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 2, 5));
        verticalAddressContainer.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, model.getClient().getBillingName() ));
        verticalAddressContainer.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, model.getClient().getBillingAddress().getLine1() ));
        verticalAddressContainer.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, model.getClient().getBillingAddress().getZip() + " "+model.getClient().getBillingAddress().getCity() ));

        verticalAddressContainer.build(contentStream, writer);

        VerticalContainer verticalAddressContainer2 = new VerticalContainer(page.getMediaBox().getWidth()/2, 630, 250 );

        verticalAddressContainer2.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 0, 0, "Shipping Address"));
        verticalAddressContainer2.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 2, 5));
        verticalAddressContainer2.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, model.getClient().getShippingName() ));
        verticalAddressContainer2.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, model.getClient().getShippingAddress().getLine1() ));
        verticalAddressContainer2.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, model.getClient().getShippingAddress().getZip() + " "+model.getClient().getShippingAddress().getCity() ));

        verticalAddressContainer2.build(contentStream, writer);

        SimpleTextBox box1 = new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 25, 560, "Nature of Transaction: sale");
        box1.build(contentStream, writer);

        float[] configRow = {20f, 120f, 60f, 60f, 60f, 60f, 60f, 60f};
        TableRowBox firstLine = new TableRowBox(configRow, 0, 0);
        firstLine.setBackgroundColor(Color.YELLOW);
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "QTY", Color.BLACK, Color.GRAY));
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "DESCRIPTION", Color.BLACK, Color.GRAY));
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "UNIT PRICE", Color.BLACK, Color.GRAY));
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "DISCOUNT", Color.BLACK, Color.GRAY));
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "TOTAL WITHOUT TAX", Color.BLACK, Color.GRAY));
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "TAX TYPE", Color.BLACK, Color.GRAY));
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "TAX RATE", Color.BLACK, Color.GRAY));
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "TAX AMOUNT", Color.BLACK, Color.GRAY));

        VerticalContainer verticalInvoiceItems = new VerticalContainer(25, 520, 500 );
        verticalInvoiceItems.addElement(firstLine);
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 2, 5));
        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0, page.getMediaBox().getWidth()-(20*2), 0));

         /* TODO
        VerticalElementContainer descriptionC = new VerticalElementContainer(0F,0F, 30);
        descriptionC.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 0, 0, product.getDescription()));
        descriptionC.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "SERIAL NUMBER 096"));
        */

        for(int w=0; w< model.getProductContainer().getProducts().size(); w++) {

            Product randomProduct = model.getProductContainer().getProducts().get(w);

            TableRowBox productLine = new TableRowBox(configRow, 0, 0);
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, Float.toString(randomProduct.getQuantity())));
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, randomProduct.getName()));
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, randomProduct.getFormatedPriceWithoutTax()));
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, ""));
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, randomProduct.getFormatedTotalPriceWithoutTax() ));
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, ""));
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, Float.toString(randomProduct.getTaxRate() * 100)+"%"));
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, randomProduct.getFormatedTotalTax() ));

            verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 2, 5));
            verticalInvoiceItems.addElement(productLine);
            verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 2, 5));
        }

        TableRowBox shipping = new TableRowBox(configRow, 0, 0);
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, ""));
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, "Shipping"));
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, "0.00"));
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, ""));
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, "0.00"));
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, "Tax"));
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, "0"));
        shipping.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, "0.00"));

        verticalInvoiceItems.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, ""));
        verticalInvoiceItems.addElement(shipping);
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 2, 5));
        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0, page.getMediaBox().getWidth()-(20*2), 0));
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 2, 5));
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
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 2, 5));
        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0, page.getMediaBox().getWidth()-(20*2), 0));
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 2, 5));

        TableRowBox totalInvoice1 = new TableRowBox(configRow, 0, 0);
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, ""));
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, ""));
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, model.getProductContainer().getFormatedTotalWithoutTax() ));
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, ""));
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, model.getProductContainer().getFormatedTotalWithTax() ));
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "CST@"));
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, Float.toString(model.getProductContainer().getProducts().get(0).getTaxRate() * 100)+"%"));
        totalInvoice1.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, model.getProductContainer().getFormatedTotalTax() ));
        verticalInvoiceItems.addElement(totalInvoice1);

        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 2, 5));
        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0, page.getMediaBox().getWidth()-(20*2), 0));
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 2, 5));

        String certification = this.getClass().getClassLoader().getResource("parts/amazon/certification.png").getFile();
        PDImageXObject pdCertification = PDImageXObject.createFromFile(certification, document);
        ImageBox imageFooter = new ImageBox(pdCertification, 0,0, page.getMediaBox().getWidth()-40, pdBarcode.getHeight(), "certification");

        verticalInvoiceItems.addElement(imageFooter);
        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0, page.getMediaBox().getWidth()-(20*2), 0));
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 2, 5));

        SimpleTextBox addressFooter = new SimpleTextBox(PDType1Font.HELVETICA, 10, 0, 0, "Registered Address for ZIP TECHNOLOGIES, 245A, SANT NAGAR, 2nd Floor, East of Kailash, New Delhi - 110065, New Delhi, In");
        addressFooter.setWidth(500);
        verticalInvoiceItems.addElement(addressFooter);
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 2, 5));
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

        VerticalContainer verticalFooterContainer = new VerticalContainer(25, 100, 450 );

        verticalFooterContainer.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 0, 0, "To return an item, visit http://www.amazon.in/returns"));
        verticalFooterContainer.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 0, 0, "For more information on your orders, visit http://"));
        verticalFooterContainer.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 9, 0, 0, "www.amazon.in/your-account"));
        verticalFooterContainer.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 9, 0, 0, "DMmZXznqN /-1 of 1 -// std-in-remote / 1005-17:40 / 1006-09:30"));
        verticalFooterContainer.build(contentStream, writer);

        String logo = this.getClass().getClassLoader().getResource("logo/"+model.getCompany().getLogo().getFullPath()).getFile();
        PDImageXObject logoFooter = PDImageXObject.createFromFile(logo, document);
        float ratio = logoFooter.getWidth() / logoFooter.getHeight();
        contentStream.drawImage(logoFooter, 480, 10, 80, 80 / ratio);


        String barCode = this.getClass().getClassLoader().getResource("parts/amazon/barcode2.jpg").getFile();
        PDImageXObject barCodeFooter = PDImageXObject.createFromFile(barCode, document);
        contentStream.drawImage(barCodeFooter, 25, 10, barCodeFooter.getWidth(), barCodeFooter.getHeight());
        contentStream.close();


        /*
        //footer
        String footer = this.getClass().getClassLoader().getResource("parts/amazon/footer.png").getFile();
        PDImageXObject pdFooter = PDImageXObject.createFromFile(footer, document);
        contentStream.drawImage(pdFooter, 25, 15, pdFooter.getWidth() / 1.7f, pdFooter.getHeight() / 1.7f);
        contentStream.close();
        */

        writer.writeEndElement();
    }


}
