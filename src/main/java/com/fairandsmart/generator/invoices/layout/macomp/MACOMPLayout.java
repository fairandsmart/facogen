package com.fairandsmart.generator.invoices.layout.macomp;

/*-
 * #%L
 * FacoGen / A tool for annotated GEDI based invoice generation.
 * 
 * Authors:
 * 
 * Xavier Lefevre <xavier.lefevre@fairandsmart.com> / FairAndSmart
 * Nicolas Rueff <nicolas.rueff@fairandsmart.com> / FairAndSmart
 * Alan Balbo <alan.balbo@fairandsmart.com> / FairAndSmart
 * Frederic Pierre <frederic.pierre@fairansmart.com> / FairAndSmart
 * Victor Guillaume <victor.guillaume@fairandsmart.com> / FairAndSmart
 * Jérôme Blanchard <jerome.blanchard@fairandsmart.com> / FairAndSmart
 * Aurore Hubert <aurore.hubert@fairandsmart.com> / FairAndSmart
 * Kevin Meszczynski <kevin.meszczynski@fairandsmart.com> / FairAndSmart
 * %%
 * Copyright (C) 2019 Fair And Smart
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.fairandsmart.generator.invoices.data.model.InvoiceModel;
import com.fairandsmart.generator.invoices.element.HAlign;
import com.fairandsmart.generator.invoices.element.border.BorderBox;
import com.fairandsmart.generator.invoices.element.container.VerticalContainer;
import com.fairandsmart.generator.invoices.element.footer.FootBox;
import com.fairandsmart.generator.invoices.element.head.ClientInfoBox;
import com.fairandsmart.generator.invoices.element.head.CompanyInfoBox;
import com.fairandsmart.generator.invoices.element.line.HorizontalLineBox;
import com.fairandsmart.generator.invoices.element.product.ProductBox;
import com.fairandsmart.generator.invoices.element.table.TableRowBox;
import com.fairandsmart.generator.invoices.element.textbox.SimpleTextBox;
import com.fairandsmart.generator.invoices.layout.InvoiceLayout;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

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
    public String name() {
        return "MACOMP";
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
