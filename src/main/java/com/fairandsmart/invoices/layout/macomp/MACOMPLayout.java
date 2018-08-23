package com.fairandsmart.invoices.layout.macomp;

import com.fairandsmart.invoices.data.model.Client;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.element.HAlign;
import com.fairandsmart.invoices.element.VAlign;
import com.fairandsmart.invoices.element.line.HorizontalLineBox;
import com.fairandsmart.invoices.element.table.TableRowBox;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import com.fairandsmart.invoices.element.table.TableRowBox;
import com.fairandsmart.invoices.element.textbox.SimpleTextBox;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import com.fairandsmart.invoices.layout.footer.FootBox;
import com.fairandsmart.invoices.layout.head.CompanyInfoBox;
import com.fairandsmart.invoices.layout.head.ClientInfoBox;
import com.fairandsmart.invoices.layout.product.ProductBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import com.fairandsmart.invoices.element.border.BorderBox;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MACOMPLayout implements InvoiceLayout {


    private static final List<PDFont[]> FONTS = new ArrayList<>();
    {
        FONTS.add(new PDFont[] {PDType1Font.HELVETICA, PDType1Font.HELVETICA_BOLD, PDType1Font.HELVETICA_OBLIQUE} );
        FONTS.add(new PDFont[] {PDType1Font.COURIER, PDType1Font.COURIER_BOLD, PDType1Font.COURIER_OBLIQUE} );
        FONTS.add(new PDFont[] {PDType1Font.TIMES_ROMAN, PDType1Font.TIMES_BOLD, PDType1Font.TIMES_ITALIC} );
    }

    @Override
    public void builtInvoice(InvoiceModel model, PDDocument document, XMLStreamWriter writer) throws Exception {

        float fontSize = 10;
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        writer.writeStartElement("DL_PAGE");
        writer.writeAttribute("gedi_type", "DL_PAGE");
        writer.writeAttribute("pageID", "1");
        writer.writeAttribute("width", "2480");
        writer.writeAttribute("height", "3508");
        PDFont[] fonts = FONTS.get(model.getRandom().nextInt(FONTS.size()));
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Facture No. 123
        new SimpleTextBox(fonts[1], fontSize+2, 310, 748, model.getReference().getLabel()+" : "+ model.getReference().getValue()).build(contentStream, writer);
       // new SimpleTextBox(fonts[1], fontSize+2, 310, 730, model.getReference().getLabelCommand()+" : "+ model.getReference().getValueCommand()).build(contentStream, writer);
        //new SimpleTextBox(fonts[1], fontSize+2, 310, 712, model.getReference().getLabelClient()+" : "+ model.getReference().getValueClient()).build(contentStream, writer);

        // Company Info Box
        CompanyInfoBox companyInfoBox = new CompanyInfoBox(fonts[2], fonts[1], fontSize, model, document);
        companyInfoBox.build(contentStream, writer);
        companyInfoBox.translate(50, 785);
        companyInfoBox.build(contentStream, writer);

        // Billing Address Block
        ClientInfoBox billingInfoBox = new ClientInfoBox(fonts[2], fonts[1], fontSize, model, document, "Billing");
        billingInfoBox.build(contentStream, writer);
        billingInfoBox.translate(50, 610);
        billingInfoBox.build(contentStream, writer);

        // Shipping Address Block
        ClientInfoBox shippingInfoBox = new ClientInfoBox(fonts[2], fonts[1], fontSize, model, document, "Shipping");
        shippingInfoBox.build(contentStream, writer);
        shippingInfoBox.translate(50, 540);
        shippingInfoBox.build(contentStream, writer);

        VerticalContainer invoiceInfo = new VerticalContainer(310, 580, 400);

        float[] configRow = {150f, 200f};
        TableRowBox elementInfoContainer = new TableRowBox(configRow, 0, 0);
        SimpleTextBox Label = new SimpleTextBox(fonts[1], fontSize+1, 0,0, model.getDate().getLabel(),Color.BLACK, null, HAlign.LEFT);
        elementInfoContainer.addElement(Label, false);
        SimpleTextBox Value = new SimpleTextBox(fonts[0], fontSize, 0,0, model.getDate().getValue());
        elementInfoContainer.addElement(Value, false);
        invoiceInfo.addElement(elementInfoContainer);
        invoiceInfo.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));

        TableRowBox elementInfoContainer1 = new TableRowBox(configRow,0, 0);
        SimpleTextBox Label1 = new SimpleTextBox(fonts[1], fontSize+1, 0,0, model.getReference().getLabelClient(),Color.BLACK, null, HAlign.LEFT);
        elementInfoContainer1.addElement(Label1, false);
        SimpleTextBox Value1 = new SimpleTextBox(fonts[0], fontSize, 0,0, model.getReference().getValueClient());
        elementInfoContainer1.addElement(Value1, false);
        invoiceInfo.addElement(elementInfoContainer1);
        invoiceInfo.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));

        TableRowBox elementInfoContainer2 = new TableRowBox(configRow,0, 0);
        SimpleTextBox Label2 = new SimpleTextBox(fonts[1], fontSize+1, 0,0, model.getReference().getLabelCommand(),Color.BLACK, null, HAlign.LEFT);
        elementInfoContainer2.addElement(Label2, false);
        SimpleTextBox Value2 = new SimpleTextBox(fonts[0], fontSize, 0,0, model.getReference().getValueCommand(),Color.BLACK, null, HAlign.LEFT);
        elementInfoContainer2.addElement(Value2, false);
        invoiceInfo.addElement(elementInfoContainer2);
        invoiceInfo.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));

        TableRowBox elementInfoContainer3 = new TableRowBox(configRow,0, 0);
        SimpleTextBox Label3 = new SimpleTextBox(fonts[1], fontSize+1, 0,0, model.getPaymentInfo().getLabelType(),Color.BLACK, null, HAlign.LEFT);
        elementInfoContainer3.addElement(Label3, false);
        SimpleTextBox Value3 = new SimpleTextBox(fonts[0], fontSize, 0,0, model.getPaymentInfo().getValueType(),Color.BLACK, null, HAlign.LEFT);
        elementInfoContainer3.addElement(Value3, false);
        invoiceInfo.addElement(elementInfoContainer3);
        invoiceInfo.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));

        invoiceInfo.build(contentStream, writer);

        ProductBox products = new ProductBox(30, 400, model.getProductContainer(),fonts[2], fonts[1], fontSize);
        products.build(contentStream, writer);

        VerticalContainer footer = new VerticalContainer(50, 100, 1000);
        footer.addElement(new HorizontalLineBox(0,0,530, 0));
        footer.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 3));
        FootBox footBox = new FootBox(fonts[0], fonts[1], fonts[2], 11, model, document);
       // footBox.translate(30,150);
      //  footBox.build(contentStream, writer);
        footer.addElement(footBox);
        footer.build(contentStream, writer);

        contentStream.close();
        writer.writeEndElement();

    }
}
