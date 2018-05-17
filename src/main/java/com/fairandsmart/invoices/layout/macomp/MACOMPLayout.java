package com.fairandsmart.invoices.layout.macomp;

import com.fairandsmart.invoices.data.model.Client;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import com.fairandsmart.invoices.element.textbox.SimpleTextBox;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import com.fairandsmart.invoices.layout.head.CompanyInfoBox;
import com.fairandsmart.invoices.layout.head.ClientInfoBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MACOMPLayout implements InvoiceLayout {


    private static List<String> cNum_heads_fr = new ArrayList<>();
    private static List<String> cNum_heads_en = new ArrayList<>();
    private static List<String> paymentInfo_heads_fr = new ArrayList<>();
    private static List<String> paymentInfo_heads_en = new ArrayList<>();


    {
        cNum_heads_fr.add("Numéro de client");
        cNum_heads_fr.add("Client N°");
        cNum_heads_fr.add("Référence de la client");
    }

    {
        cNum_heads_en.add("Client Number");
        cNum_heads_en.add("Client Reference");
        cNum_heads_en.add("Ref Client");
    }

    {
        paymentInfo_heads_fr.add("Mode de règlement");
        paymentInfo_heads_fr.add("Moyen de paiement");
    }

    {
        paymentInfo_heads_en.add("Payment means");
        paymentInfo_heads_en.add("Payed through");
        paymentInfo_heads_en.add("Mode of Payment");
    }

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
        Random random = new Random();
        PDFont[] fonts = FONTS.get(random.nextInt(FONTS.size()));
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        // Facture No. 123
        new SimpleTextBox(fonts[1], 13, 310, 748, model.getReference().getLabel()+" : "+ model.getReference().getValue()).build(contentStream, writer);

        // Company Info Box
        CompanyInfoBox companyInfoBox = new CompanyInfoBox(fonts[2], fonts[1], fontSize, model, document);
        companyInfoBox.build(contentStream, writer);
        companyInfoBox.translate(50, 785);
        companyInfoBox.build(contentStream, writer);

        // Billing Address Block
        ClientInfoBox billingInfoBox = new ClientInfoBox(fonts[2], fonts[1], fontSize, model, document, "Billing");
        billingInfoBox.build(contentStream, writer);
        billingInfoBox.translate(50, 650);
        billingInfoBox.build(contentStream, writer);

        // Shipping Address Block
        ClientInfoBox shippingInfoBox = new ClientInfoBox(fonts[2], fonts[1], fontSize, model, document, "Shipping");
        shippingInfoBox.build(contentStream, writer);
        shippingInfoBox.translate(50, 580);
        shippingInfoBox.build(contentStream, writer);

        VerticalContainer invoiceInfo = new VerticalContainer(300, 650, 400);
        SimpleTextBox dateHead = new SimpleTextBox(fonts[1], fontSize+1, 0,0, model.getDate().getLabel());
        invoiceInfo.addElement(dateHead);

        SimpleTextBox idate = new SimpleTextBox(fonts[0], fontSize, 330,650, model.getDate().getValue());
        invoiceInfo.addElement(idate);

        SimpleTextBox vatHead = new SimpleTextBox(fonts[1], fontSize+1, 0,0, model.getCompany().getIdNumbers().getVatLabel());
        invoiceInfo.addElement(vatHead);

        SimpleTextBox svat = new SimpleTextBox(fonts[0], fontSize, 330,0, model.getCompany().getIdNumbers().getVatValue());
        invoiceInfo.addElement(svat);

        SimpleTextBox cidHead = new SimpleTextBox(fonts[1], fontSize+1, 0,0, model.getCompany().getIdNumbers().getCidLabel());
        invoiceInfo.addElement(cidHead);

        SimpleTextBox scid = new SimpleTextBox(fonts[0], fontSize, 330,0, model.getCompany().getIdNumbers().getCidValue());
        invoiceInfo.addElement(scid);

        SimpleTextBox siretHead = new SimpleTextBox(fonts[1], fontSize+1, 0,0, model.getCompany().getIdNumbers().getSiretLabel());
        invoiceInfo.addElement(siretHead);

        SimpleTextBox ssiret = new SimpleTextBox(fonts[0], fontSize, 0,0, model.getCompany().getIdNumbers().getSiretValue());
        invoiceInfo.addElement(ssiret);

        SimpleTextBox toaHead = new SimpleTextBox(fonts[1], fontSize+1, 0,0, model.getCompany().getIdNumbers().getToaLabel());
        invoiceInfo.addElement(toaHead);

        SimpleTextBox stoa = new SimpleTextBox(fonts[0], fontSize, 0,0, model.getCompany().getIdNumbers().getToaValue());
        invoiceInfo.addElement(stoa);

        invoiceInfo.build(contentStream, writer);
        contentStream.close();

    }
}
