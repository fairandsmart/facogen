package com.fairandsmart.generator.documents.layout.materielnet;

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

import com.fairandsmart.generator.documents.data.model.Address;
import com.fairandsmart.generator.documents.data.model.IDNumbers;
import com.fairandsmart.generator.documents.data.model.InvoiceModel;
import com.fairandsmart.generator.documents.data.model.Product;
import com.fairandsmart.generator.documents.element.border.BorderBox;
import com.fairandsmart.generator.documents.element.container.HorizontalContainer;
import com.fairandsmart.generator.documents.element.container.VerticalContainer;
import com.fairandsmart.generator.documents.element.table.TableRowBox;
import com.fairandsmart.generator.documents.element.textbox.SimpleTextBox;
import com.fairandsmart.generator.documents.layout.InvoiceLayout;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;

public class MaterielnetLayout implements InvoiceLayout {

    @Override
    public String name() {
        return "Materiel.net";
    }

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


        PDFont font = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        Address address = model.getCompany().getAddress();


        VerticalContainer verticalHeaderContainer = new VerticalContainer(22, page.getMediaBox().getHeight()-11, 250 );
        verticalHeaderContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getCompany().getName()+"","SN"));
        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2));
        verticalHeaderContainer.addElement(new SimpleTextBox(font, 9, 0, 0, address.getLine1(),"SA"));
        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2));
        verticalHeaderContainer.addElement(new SimpleTextBox(font, 9, 0, 0, address.getZip() +"  "+ address.getCity(),"SA"));

        HorizontalContainer tel = new HorizontalContainer(0,0);
        tel.addElement(new SimpleTextBox(font,9,0,0,model.getCompany().getContact().getphoneLabel()+" "));
        tel.addElement(new SimpleTextBox(font,9,0,0, model.getCompany().getContact().getphoneValue(), "SCN"));

        HorizontalContainer fax = new HorizontalContainer(0,0);
        fax.addElement(new SimpleTextBox(font,9,0,0,model.getCompany().getContact().getfaxLabel()+" "));
        fax.addElement(new SimpleTextBox(font,9,0,0, model.getCompany().getContact().getfaxValue(), "SFAX"));

        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2));
        verticalHeaderContainer.addElement(tel);
        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2));
        verticalHeaderContainer.addElement(fax);
        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2));
        verticalHeaderContainer.addElement(new SimpleTextBox(font, 9,0,0,model.getCompany().getWebsite()));

        verticalHeaderContainer.build(contentStream,writer);

        VerticalContainer numContainer = new VerticalContainer(274, page.getMediaBox().getHeight()-45, 250 );

        HorizontalContainer numFact = new HorizontalContainer(0,0);
        numFact.addElement(new SimpleTextBox(font,9,0,0,model.getReference().getLabel()+" "));
        numFact.addElement(new SimpleTextBox(font,9,0,0, model.getReference().getValue(), "IN"));
        numFact.addElement(new SimpleTextBox(font,9,0,0, " - "));
        numFact.addElement(new SimpleTextBox(font,9,0,0, model.getDate().getValue(), "IDATE"));

        HorizontalContainer dateFact = new HorizontalContainer(0,0);
        dateFact.addElement(new SimpleTextBox(font,9,0,0, address.getCity()+" , "));
        dateFact.addElement(new SimpleTextBox(font,9,0,0,model.getDate().getValueExpedition()));

        numContainer.addElement(numFact);
        numContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2));
        numContainer.addElement(dateFact);

        numContainer.build(contentStream,writer);

        VerticalContainer numComContainer = new VerticalContainer(22, page.getMediaBox().getHeight()-133, 250 );

        HorizontalContainer numCom = new HorizontalContainer(0,0);
        numCom.addElement(new SimpleTextBox(font,9,0,0,model.getReference().getLabelCommand()+" "));
        numCom.addElement(new SimpleTextBox(font,9,0,0,model.getReference().getValueCommand()));

        numComContainer.addElement(numCom);
        numComContainer.addElement(new SimpleTextBox(fontItalic,8,0,0,"Livraison par Chronopost"));

        numComContainer.build(contentStream,writer);

        VerticalContainer billingContainer = new VerticalContainer(311,page.getMediaBox().getHeight()-104,250);
        billingContainer.addElement(new SimpleTextBox(fontBold,9,0,0,model.getClient().getBillingHead()));
        billingContainer.addElement(new SimpleTextBox(font,9,0,0,model.getClient().getBillingName(),"BN"));
        billingContainer.addElement(new SimpleTextBox(font,9,0,0,model.getClient().getBillingAddress().getLine1(),"BA"));
        billingContainer.addElement(new SimpleTextBox(font,9,0,0,model.getClient().getBillingAddress().getZip()+" "+model.getClient().getBillingAddress().getCity(),"BA"));
        billingContainer.addElement(new SimpleTextBox(font,9,0,0,model.getClient().getBillingAddress().getCountry(),"BA"));

        billingContainer.build(contentStream,writer);

        VerticalContainer shippingContainer = new VerticalContainer(453,page.getMediaBox().getHeight()-104,250);
        shippingContainer.addElement(new SimpleTextBox(fontBold,9,0,0,model.getClient().getShippingHead()));
        shippingContainer.addElement(new SimpleTextBox(font,9,0,0,model.getClient().getShippingName(),"SHN"));
        shippingContainer.addElement(new SimpleTextBox(font,9,0,0,model.getClient().getShippingAddress().getLine1(),"SHA"));
        shippingContainer.addElement(new SimpleTextBox(font,9,0,0,model.getClient().getShippingAddress().getZip()+" "+model.getClient().getShippingAddress().getCity(),"SHA"));
        shippingContainer.addElement(new SimpleTextBox(font,9,0,0, "06"+(int)(1000000+(Math.random()*(99999999 - 1000000)))));

        shippingContainer.build(contentStream,writer);

        float[] configRow = {48f, 283f, 121f, 121f};
        TableRowBox firstLine = new TableRowBox(configRow, 0, 0);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "QTE (1)"), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Désigniation"), true);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Prix Unitaire H.T."), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Prix Total H.T"), false);

        VerticalContainer verticalInvoiceItems = new VerticalContainer(22, page.getMediaBox().getHeight()-190, 600 );
        verticalInvoiceItems.addElement(firstLine);

        for(int w=0; w< model.getProductContainer().getProducts().size(); w++) {

            Product randomProduct = model.getProductContainer().getProducts().get(w);

            TableRowBox productLine = new TableRowBox(configRow, 0, 0);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, Float.toString(randomProduct.getQuantity()), "QTY"), true);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getName(), "PD"), false);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getFormatedPriceWithoutTax(), "PU"), true);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getFormatedTotalPriceWithoutTax(), "PTWTX"), true);


            verticalInvoiceItems.addElement(productLine);
        }

        verticalInvoiceItems.build(contentStream,writer);

        HorizontalContainer fdp = new HorizontalContainer(388,297);
        fdp.addElement(new SimpleTextBox(fontBold,8,0,0,"Frais de port : "));
        fdp.addElement(new BorderBox(Color.WHITE,Color.WHITE,1,0,0,80,0));
        fdp.addElement(new SimpleTextBox(font,8,0,0,"0,00 "));

        fdp.build(contentStream,writer);

        VerticalContainer totalContainer = new VerticalContainer(416,269,250);

        HorizontalContainer baseHT = new HorizontalContainer(0,0);
        baseHT.addElement(new SimpleTextBox(fontBold,9,0,0,model.getProductContainer().getTotalWithoutTaxHead()));
        baseHT.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,50,0));
        baseHT.addElement(new SimpleTextBox(font,9,0,0,model.getProductContainer().getFormatedTotalWithoutTax(),"TWTX"));

        HorizontalContainer taxeTVA = new HorizontalContainer(0,0);
        taxeTVA.addElement(new SimpleTextBox(fontBold,9,0,0,model.getProductContainer().getTotalTaxHead()));
        taxeTVA.addElement(new SimpleTextBox(fontBold,9,0,0," 20%"));
        taxeTVA.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,50,0));
        taxeTVA.addElement(new SimpleTextBox(font,9,0,0,model.getProductContainer().getFormatedTotalTax(),"TTX"));

        HorizontalContainer totalTTC = new HorizontalContainer(0,0);
        totalTTC.addElement(new SimpleTextBox(fontBold,9,0,0,model.getProductContainer().getTotalAmountHead()));
        totalTTC.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,50,0));
        totalTTC.addElement(new SimpleTextBox(font,9,0,0,model.getProductContainer().getFormatedTotalWithTax(),"TA"));

        totalContainer.addElement(baseHT);
        totalContainer.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,0,4));
        totalContainer.addElement(taxeTVA);
        totalContainer.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,0,4));
        totalContainer.addElement(totalTTC);

        totalContainer.build(contentStream,writer);

        new SimpleTextBox(fontBold,9,209,204,"Modalité et conditions de règlement").build(contentStream,writer);

        VerticalContainer paiementLabel = new VerticalContainer(22,190,250);
        paiementLabel.addElement(new SimpleTextBox(font, 9,0,0,model.getPaymentInfo().getLabelType()+" : "));
        paiementLabel.addElement(new SimpleTextBox(font, 9,0,0,"Echéance : "));

        VerticalContainer paiementValeur = new VerticalContainer(317,190,250);
        paiementValeur.addElement(new SimpleTextBox(fontItalic, 9,0,0,model.getPaymentInfo().getValueType(),"PMODE"));
        paiementValeur.addElement(new SimpleTextBox(fontItalic, 9,0,0,"Comptant"));

        paiementLabel.build(contentStream,writer);
        paiementValeur.build(contentStream,writer);

        String footer = this.getClass().getClassLoader().getResource("invoices/parts/materielnet/footer.png").getFile();
        PDImageXObject logoFooter = PDImageXObject.createFromFile(footer, document);
        float ratioFooter = (float)logoFooter.getWidth() / (float)logoFooter.getHeight();
        float tailleFooter = 530;
        float posFooterX = 22;
        float posFooterY = 85;
        contentStream.drawImage(logoFooter, posFooterX, posFooterY, tailleFooter, tailleFooter/ratioFooter);

        HorizontalContainer infoEntreprise = new HorizontalContainer(0,0);
        infoEntreprise.addElement(new SimpleTextBox(font,7,0,0, model.getCompany().getName()+" - "));
        infoEntreprise.addElement(new SimpleTextBox(font,7,0,0, address.getLine1()));
        infoEntreprise.addElement(new SimpleTextBox(font,7,0,0, " - "+address.getZip()+ " "+address.getCity()));

        HorizontalContainer infoEntreprise2 = new HorizontalContainer(0,0);
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, model.getCompany().getContact().getphoneLabel()+" "));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, model.getCompany().getContact().getphoneValue(),"SCN"));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, " - "+model.getCompany().getContact().getfaxLabel()+" "));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, model.getCompany().getContact().getfaxValue(),"SFAX"));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, " - Internet. "));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, model.getCompany().getWebsite()));

        HorizontalContainer infoEntreprise3 = new HorizontalContainer(0,0);
        infoEntreprise3.addElement(new SimpleTextBox(font,7,0,0, "SAS au capital de "+(int)(100000+(Math.random()*(9999999 - 100000)))+" Eur - "));
        infoEntreprise3.addElement(new SimpleTextBox(font,7,0,0, idNumbers.getSiretLabel()+" "));
        infoEntreprise3.addElement(new SimpleTextBox(font,7,0,0, idNumbers.getSiretValue(),"SSIRET"));
        infoEntreprise3.addElement(new SimpleTextBox(font,7,0,0, " - "+ idNumbers.getToaLabel()));
        infoEntreprise3.addElement(new SimpleTextBox(font,7,0,0, idNumbers.getToaValue(),"STOA"));
        infoEntreprise3.addElement(new SimpleTextBox(font,7,0,0, " - "+ idNumbers.getVatLabel() +" : "));
        infoEntreprise3.addElement(new SimpleTextBox(font,7,0,0, idNumbers.getVatValue(),"SVAT"));

        float millieuPageX = page.getMediaBox().getWidth()/2;
        infoEntreprise.translate(millieuPageX-infoEntreprise.getBoundingBox().getWidth()/2,82);
        infoEntreprise2.translate(millieuPageX-infoEntreprise2.getBoundingBox().getWidth()/2,72);
        infoEntreprise3.translate(millieuPageX-infoEntreprise3.getBoundingBox().getWidth()/2,62);

        infoEntreprise.build(contentStream,writer);
        infoEntreprise2.build(contentStream,writer);
        infoEntreprise3.build(contentStream,writer);

        contentStream.close();
    }
}
