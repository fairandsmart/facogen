package com.fairandsmart.invoices.layout.bdmobilier;

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

import com.fairandsmart.invoices.data.model.*;
import com.fairandsmart.invoices.element.border.BorderBox;
import com.fairandsmart.invoices.element.container.HorizontalContainer;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import com.fairandsmart.invoices.element.table.TableRowBox;
import com.fairandsmart.invoices.element.textbox.SimpleTextBox;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;

public class BDmobilierLayout implements InvoiceLayout {

    @Override
    public void builtInvoice(InvoiceModel model, PDDocument document, XMLStreamWriter writer) throws Exception {

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        writer.writeStartElement("DL_PAGE");
        writer.writeAttribute("gedi_type", "DL_PAGE");
        writer.writeAttribute("pageID", "1");
        writer.writeAttribute("width", "2480");
        writer.writeAttribute("height", "3508");

        IDNumbers idNumbers = model.getCompany().getIdNumbers();

        Address address = model.getCompany().getAddress();

        PDFont font = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        String logo = this.getClass().getClassLoader().getResource("logo/bdmobilier.png").getFile();
        PDImageXObject logoHeader = PDImageXObject.createFromFile(logo, document);
        float ratioLogo = (float)logoHeader.getWidth() / (float)logoHeader.getHeight();
        int tailleLogo = 235;
        float posLogoX = 28;
        float posLogoY = page.getMediaBox().getHeight()-tailleLogo/ratioLogo-20;
        contentStream.drawImage(logoHeader, posLogoX, posLogoY, tailleLogo, tailleLogo/ratioLogo);

        VerticalContainer billingContainer = new VerticalContainer(127,page.getMediaBox().getHeight()-121,250);
        billingContainer.addElement(new SimpleTextBox(fontBold,9, 0,0,model.getClient().getBillingHead(),Color.LIGHT_GRAY,Color.WHITE));
        billingContainer.addElement(new SimpleTextBox(font,9,0,0,model.getClient().getBillingName(),"BN"));
        billingContainer.addElement(new SimpleTextBox(font,9,0,0,model.getClient().getBillingAddress().getLine1(),"BA"));
        billingContainer.addElement(new SimpleTextBox(font,9,0,0,model.getClient().getBillingAddress().getZip()+" "+model.getClient().getBillingAddress().getCity(),"BA"));
        billingContainer.addElement(new SimpleTextBox(font,9,0,0,model.getClient().getBillingAddress().getCountry(),"BA"));
        billingContainer.addElement(new SimpleTextBox(font,9,0,0,"06"+(int)(1000000+(Math.random()*(99999999 - 1000000)))));
        billingContainer.addElement(new SimpleTextBox(font,9,0,0,"06"+(int)(1000000+(Math.random()*(99999999 - 1000000)))));

        billingContainer.build(contentStream,writer);

        VerticalContainer shippingContainer = new VerticalContainer(345,page.getMediaBox().getHeight()-121,250);
        shippingContainer.addElement(new SimpleTextBox(fontBold,9,0,0,model.getClient().getShippingHead(),Color.LIGHT_GRAY,Color.WHITE));
        shippingContainer.addElement(new SimpleTextBox(font,9,0,0,model.getClient().getShippingName(),"SHN"));
        shippingContainer.addElement(new SimpleTextBox(font,9,0,0,model.getClient().getShippingAddress().getLine1(),"SHA"));
        shippingContainer.addElement(new SimpleTextBox(font,9,0,0,model.getClient().getShippingAddress().getZip()+" "+model.getClient().getShippingAddress().getCity(),"SHA"));
        shippingContainer.addElement(new SimpleTextBox(font,9,0,0,model.getClient().getShippingAddress().getCountry(),"SHA"));
        shippingContainer.addElement(new SimpleTextBox(font,9,0,0, "06"+(int)(1000000+(Math.random()*(99999999 - 1000000)))));
        shippingContainer.addElement(new SimpleTextBox(font,9,0,0, "06"+(int)(1000000+(Math.random()*(99999999 - 1000000)))));

        shippingContainer.build(contentStream,writer);

        VerticalContainer headerContainer = new VerticalContainer(487,page.getMediaBox().getHeight()-9,250);
        headerContainer.addElement(new SimpleTextBox(font,10,0,0,model.getCompany().getName(),"SN"));
        headerContainer.addElement(new SimpleTextBox(font,10,0,0,model.getDate().getValue(),Color.LIGHT_GRAY,Color.WHITE,"IDATE"));

        HorizontalContainer numFact = new HorizontalContainer(0,0);
        numFact.addElement(new SimpleTextBox(font,10,0,0,model.getReference().getLabel()+" ",Color.LIGHT_GRAY,Color.WHITE));
        numFact.addElement(new SimpleTextBox(font,10,0,0,model.getReference().getValue(),Color.LIGHT_GRAY,Color.WHITE,"IN"));

        headerContainer.addElement(numFact);

        headerContainer.build(contentStream,writer);

        VerticalContainer infoCommande = new VerticalContainer(28,page.getMediaBox().getHeight()-211,76);
        infoCommande.addElement(new SimpleTextBox(font,8, 0,0,model.getReference().getLabelCommand()));
        infoCommande.addElement(new SimpleTextBox(font,8,0,0,model.getReference().getValueCommand(),"ONUM"));
        infoCommande.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,0,9));
        infoCommande.addElement(new SimpleTextBox(font,8,0,0,model.getDate().getLabelCommand()));
        infoCommande.addElement(new SimpleTextBox(font,8,0,0,model.getDate().getValueCommand(),"IDATE"));
        infoCommande.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,0,9));
        infoCommande.addElement(new SimpleTextBox(font,8,0,0,model.getPaymentInfo().getLabelType()));
        infoCommande.addElement(new SimpleTextBox(font,8,0,0,model.getPaymentInfo().getValueType(),"PMODE"));
        infoCommande.addElement(new SimpleTextBox(font,8,0,0,model.getProductContainer().getFormatedTotalWithTax(),"TTX"));
        infoCommande.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,0,9));
        infoCommande.addElement(new SimpleTextBox(font,8,0,0,"Transporteur : "));
        infoCommande.addElement(new SimpleTextBox(font,8,0,0,"Transporteur 1"));

        infoCommande.build(contentStream,writer);

        float[] configRow = {209,56,45,90,51};
        TableRowBox firstLine = new TableRowBox(configRow, 0, 0);
        firstLine.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "Produit / Réference",Color.WHITE,Color.BLACK), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "Prix unitaire",Color.WHITE,Color.BLACK), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "Discount",Color.WHITE,Color.BLACK), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "Quantité",Color.WHITE,Color.BLACK), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "Total (HT)",Color.WHITE,Color.BLACK), false);

        TableRowBox line2 = new TableRowBox(configRow, 0, 0);
        line2.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "",Color.WHITE,Color.BLACK), false);
        line2.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "(HT)",Color.WHITE,Color.BLACK), false);
        line2.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "",Color.WHITE,Color.BLACK), false);
        line2.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "",Color.WHITE,Color.BLACK), false);
        line2.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "",Color.WHITE,Color.BLACK), false);


        VerticalContainer verticalInvoiceItems = new VerticalContainer(110, page.getMediaBox().getHeight()-209, 500 );
        verticalInvoiceItems.addElement(firstLine);
        verticalInvoiceItems.addElement(line2);

        String discount ="";

        for(int w=0; w< model.getProductContainer().getProducts().size(); w++) {

            Product randomProduct = model.getProductContainer().getProducts().get(w);

            TableRowBox productLine = new TableRowBox(configRow, 0, 0);
            productLine.addElement(new SimpleTextBox(font, 9, 2, 0, randomProduct.getName(), "TBL"), false);
            productLine.addElement(new SimpleTextBox(font, 9, 2, 0, randomProduct.getFormatedPriceWithoutTax(), "TBL"), false);
            if(randomProduct.getDiscount() == 0.0){
                discount = "--";
            }
            productLine.addElement(new SimpleTextBox(font, 9, 2, 0, discount, "TBL"), false);
            productLine.addElement(new SimpleTextBox(font, 9, 2, 0, (int)(randomProduct.getQuantity())+"", "TBL"), false);
            productLine.addElement(new SimpleTextBox(font, 9, 2, 0, randomProduct.getFormatedTotalPriceWithoutTax(), "TBL"), false);


            verticalInvoiceItems.addElement(productLine);
        }

        TableRowBox lineLivraison = new TableRowBox(configRow, 0, 0);
        lineLivraison.addElement(new SimpleTextBox(font, 9, 2, 0, "Frais de livraison",Color.BLACK,Color.LIGHT_GRAY), false);
        lineLivraison.addElement(new SimpleTextBox(font, 9, 2, 0, "0,00 Eur",Color.BLACK,Color.LIGHT_GRAY), false);
        lineLivraison.addElement(new SimpleTextBox(font, 9, 2, 0, "--",Color.BLACK,Color.LIGHT_GRAY), false);
        lineLivraison.addElement(new SimpleTextBox(font, 9, 2, 0, "1",Color.BLACK,Color.LIGHT_GRAY), false);
        lineLivraison.addElement(new SimpleTextBox(font, 9, 2, 0, "0,00",Color.BLACK,Color.LIGHT_GRAY), false);

        verticalInvoiceItems.addElement(lineLivraison);

        verticalInvoiceItems.build(contentStream,writer);

        ProductContainer pc= model.getProductContainer();

        float posYTotal = verticalInvoiceItems.getBoundingBox().getPosY()-verticalInvoiceItems.getBoundingBox().getHeight()-13;
        new BorderBox(Color.BLACK,Color.BLACK,1,405,posYTotal,158,13).build(contentStream,writer);
        new BorderBox(Color.BLACK,Color.BLACK,1,405,posYTotal-28,158,13).build(contentStream,writer);

        new SimpleTextBox(font, 9, 447, posYTotal+11, pc.getTotalWithoutTaxHead(),Color.WHITE,Color.BLACK).build(contentStream,writer);
        new SimpleTextBox(font, 9, 508, posYTotal+11, pc.getFormatedTotalWithoutTax(),Color.WHITE,Color.BLACK,"TBL").build(contentStream,writer);
        new SimpleTextBox(font, 9, 447, posYTotal-2, pc.getTotalTaxHead(),Color.BLACK,Color.WHITE).build(contentStream,writer);
        new SimpleTextBox(font, 9, 508, posYTotal-2, pc.getFormatedTotalTax(),Color.BLACK,Color.WHITE,"TBL").build(contentStream,writer);
        new SimpleTextBox(font, 9, 447, posYTotal-16, pc.getTotalAmountHead(),Color.WHITE,Color.BLACK).build(contentStream,writer);
        new SimpleTextBox(font, 9, 508, posYTotal-16, pc.getFormatedTotalWithTax(),Color.WHITE,Color.BLACK,"TBL").build(contentStream,writer);


        HorizontalContainer infoEntreprise = new HorizontalContainer(0,0);
        infoEntreprise.addElement(new SimpleTextBox(font,7,0,0, model.getCompany().getName(),"SN"));
        infoEntreprise.addElement(new SimpleTextBox(font,7,0,0, " - "));
        infoEntreprise.addElement(new SimpleTextBox(font,7,0,0, address.getCountry(),"SA"));

        HorizontalContainer infoEntreprise2 = new HorizontalContainer(0,0);
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, address.getLine1()+" ","SA"));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, " - "));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, address.getZip() + " " +address.getCity(),"SA"));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, " "+idNumbers.getSiretLabel()+" "));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, idNumbers.getSiretValue(),"SSIRET"));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, " - "+ idNumbers.getVatLabel() +" : "));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, idNumbers.getVatValue(),"SVAT"));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, " - "+model.getCompany().getContact().getfaxLabel()+" : "));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, model.getCompany().getContact().getfaxValue(),"SFAX"));

        HorizontalContainer infoEntreprise3 = new HorizontalContainer(0,0);
        infoEntreprise3.addElement(new SimpleTextBox(font,7,0,0, model.getCompany().getContact().getphoneLabel()+" : "));
        infoEntreprise3.addElement(new SimpleTextBox(font,7,0,0, model.getCompany().getContact().getphoneValue(),"SCN"));

        float millieuPageX = page.getMediaBox().getWidth()/2;
        infoEntreprise.translate(millieuPageX-infoEntreprise.getBoundingBox().getWidth()/2,58);
        infoEntreprise2.translate(millieuPageX-infoEntreprise2.getBoundingBox().getWidth()/2,51);
        infoEntreprise3.translate(millieuPageX-infoEntreprise3.getBoundingBox().getWidth()/2,45);

        infoEntreprise.build(contentStream,writer);
        infoEntreprise2.build(contentStream,writer);
        infoEntreprise3.build(contentStream,writer);

        contentStream.close();
    }
}
