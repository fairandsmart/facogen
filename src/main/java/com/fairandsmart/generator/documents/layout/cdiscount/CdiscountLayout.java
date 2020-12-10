package com.fairandsmart.generator.documents.layout.cdiscount;

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
import com.fairandsmart.generator.documents.data.model.Product;
import com.fairandsmart.generator.documents.element.border.BorderBox;
import com.fairandsmart.generator.documents.element.container.HorizontalContainer;
import com.fairandsmart.generator.documents.element.container.VerticalContainer;
import com.fairandsmart.generator.documents.element.textbox.SimpleTextBox;
import com.fairandsmart.generator.documents.layout.InvoiceLayout;
import com.fairandsmart.generator.documents.data.model.InvoiceModel;
import com.fairandsmart.generator.documents.element.table.TableRowBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.util.Random;

@ApplicationScoped
public class CdiscountLayout implements InvoiceLayout {

    @Override
    public String name() {
        return "CDiscount";
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

        IDNumbers idnumObj = model.getCompany().getIdNumbers();

        PDFont font = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        Color gris = new Color(239,239,239);

        String logo = this.getClass().getClassLoader().getResource("common/logo/cdiscount.jpg").getFile();
        PDImageXObject logoHeader = PDImageXObject.createFromFile(logo, document);
        float ratioLogo = (float)logoHeader.getWidth() / (float)logoHeader.getHeight();
        int tailleLogo = 133;
        float posLogoX = 34;
        float posLogoY = page.getMediaBox().getHeight()-tailleLogo/ratioLogo-2;
        contentStream.drawImage(logoHeader, posLogoX, posLogoY, tailleLogo, tailleLogo/ratioLogo);

        VerticalContainer verticalHeaderContainer = new VerticalContainer(34, page.getMediaBox().getHeight()-34-tailleLogo/ratioLogo/2+15, 250 );
        verticalHeaderContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getCompany().getName()+"","SN"));
        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,3));
        verticalHeaderContainer.addElement(new SimpleTextBox(font, 9, 0, 0, "Capital de " + (int)(100000+(Math.random()*(9999999 - 100000))) +" euros"));
        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,3));
        verticalHeaderContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getCompany().getAddress().getLine1(),"SA"));
        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,3));
        verticalHeaderContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getCompany().getAddress().getZip() +"  "+ model.getCompany().getAddress().getCity(),"SA"));
        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,3));

        HorizontalContainer rcs = new HorizontalContainer(0,0);
        rcs.addElement(new SimpleTextBox(font,9,0,0,"N°RCS "));
        rcs.addElement(new SimpleTextBox(font,9,0,0, idnumObj.getCidValue(), "SCID"));
        rcs.addElement(new SimpleTextBox(font,9,0,0, " " +model.getCompany().getAddress().getCity(), "SA"));

        HorizontalContainer siret = new HorizontalContainer(0,0);
        siret.addElement(new SimpleTextBox(font,9,0,0,"Siret "));
        siret.addElement(new SimpleTextBox(font,9,0,0, idnumObj.getSiretValue(), "SSIRET"));

        HorizontalContainer vat = new HorizontalContainer(0,0);
        vat.addElement(new SimpleTextBox(font,9,0,0, idnumObj.getVatLabel()+" : "));
        vat.addElement(new SimpleTextBox(font,9,0,0, idnumObj.getVatValue(), "SVAT"));

        HorizontalContainer naf = new HorizontalContainer(0,0);
        naf.addElement(new SimpleTextBox(font,9,0,0, idnumObj.getToaLabel()+" : "));
        naf.addElement(new SimpleTextBox(font,9,0,0, idnumObj.getToaValue(), "STOA"));

        verticalHeaderContainer.addElement(rcs);
        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,3));
        verticalHeaderContainer.addElement(vat);
        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,3));
        verticalHeaderContainer.addElement(naf);
        verticalHeaderContainer.build(contentStream,writer);

        float posHeaderX = page.getMediaBox().getWidth()-31-252;
        float posHeaderY = page.getMediaBox().getHeight()-31-14;

        new BorderBox(gris,gris,1,posHeaderX,posHeaderY,252,14).build(contentStream,writer);
        new BorderBox(gris,gris,1,posHeaderX,posHeaderY-14-2,252,14).build(contentStream,writer);

        HorizontalContainer numFac = new HorizontalContainer(posHeaderX+68,posHeaderY+12);
        numFac.addElement(new SimpleTextBox(fontBold,9,0,0, model.getReference().getLabel()+" "));
        numFac.addElement(new SimpleTextBox(fontBold,9,0,0, model.getReference().getValue(), "IN"));

        HorizontalContainer containerDate = new HorizontalContainer(posHeaderX+252/3,posHeaderY-4);
        containerDate.addElement(new SimpleTextBox(fontBold,9,0,0, "du "));
        containerDate.addElement(new SimpleTextBox(fontBold,9,0,0, model.getDate().getValue(), "IDATE"));

        numFac.build(contentStream,writer);
        containerDate.build(contentStream,writer);

        new BorderBox(Color.BLACK,Color.WHITE,1,34,42,530,652).build(contentStream,writer);

        new SimpleTextBox(fontBold,9,87,page.getMediaBox().getHeight()-158,"FACTURATION",Color.RED,Color.WHITE).build(contentStream,writer);
        new SimpleTextBox(fontBold,9,87+229,page.getMediaBox().getHeight()-158,"LIVRAISON",Color.RED,Color.WHITE).build(contentStream,writer);

        new BorderBox(Color.RED,Color.WHITE,1,87,page.getMediaBox().getHeight()-170-51,229,51).build(contentStream,writer);
        new BorderBox(Color.RED,Color.WHITE,1,87+230,page.getMediaBox().getHeight()-170-51,189,51).build(contentStream,writer);

        Address bA = model.getClient().getBillingAddress();
        VerticalContainer facturationContainer = new VerticalContainer(90, page.getMediaBox().getHeight()-172, 250 );
        facturationContainer.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getClient().getBillingName(),"BN"));
        facturationContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,5));
        facturationContainer.addElement(new SimpleTextBox(font, 9, 0, 0, bA.getLine1(),"BA"));
        facturationContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,10));
        facturationContainer.addElement(new SimpleTextBox(font, 9, 0, 0, bA.getZip()+" "+ bA.getCity().toUpperCase() ,"BA"));

        facturationContainer.build(contentStream,writer);

        Address sA = model.getClient().getShippingAddress();
        VerticalContainer livraisonContainer = new VerticalContainer(320, page.getMediaBox().getHeight()-172, 250 );
        livraisonContainer.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getClient().getBillingName(),"SHN"));
        livraisonContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,5));
        livraisonContainer.addElement(new SimpleTextBox(font, 9, 0, 0, sA.getLine1(),"SHA"));
        livraisonContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,10));
        livraisonContainer.addElement(new SimpleTextBox(font, 9, 0, 0, sA.getZip()+" "+ bA.getCity().toUpperCase() ,"SHA"));

        livraisonContainer.build(contentStream,writer);

        VerticalContainer boxInfoClient = new VerticalContainer(87,page.getMediaBox().getHeight()-223-14,250);
        boxInfoClient.addElement(new BorderBox(gris,gris,1,0,0,85,11));
        boxInfoClient.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,0,1));
        boxInfoClient.addElement(new BorderBox(gris,gris,1,0,0,85,11));
        boxInfoClient.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,0,1));
        boxInfoClient.addElement(new BorderBox(gris,gris,1,0,0,85,11));
        boxInfoClient.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,0,1));
        boxInfoClient.addElement(new BorderBox(gris,gris,1,0,0,85,11));

        boxInfoClient.build(contentStream,writer);

        VerticalContainer labelInfoClient = new VerticalContainer(88,page.getMediaBox().getHeight()-227,250);
        labelInfoClient.addElement(new SimpleTextBox(font,8,0,0,"Adresse e-mail"));
        labelInfoClient.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2.5f));
        labelInfoClient.addElement(new SimpleTextBox(font,8,0,0,model.getReference().getLabelCommand()));
        labelInfoClient.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2.5f));
        labelInfoClient.addElement(new SimpleTextBox(font,8,0,0,model.getPaymentInfo().getLabelType()+""));
        labelInfoClient.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2.5f));
        labelInfoClient.addElement(new SimpleTextBox(font,8,0,0,model.getDate().getLabelPayment()));

        labelInfoClient.build(contentStream,writer);

        VerticalContainer valueInfoClient = new VerticalContainer(175,page.getMediaBox().getHeight()-227,250);
        valueInfoClient.addElement(new SimpleTextBox(font,8,0,0,"hjk@gmail.com"));
        valueInfoClient.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2.5f));
        valueInfoClient.addElement(new SimpleTextBox(font,8,0,0,model.getReference().getValueCommand(),"ONUM"));
        valueInfoClient.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2.5f));
        valueInfoClient.addElement(new SimpleTextBox(font,8,0,0,model.getPaymentInfo().getValueType(),"PMODE"));
        valueInfoClient.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2.5f));
        valueInfoClient.addElement(new SimpleTextBox(font,8,0,0,model.getDate().getValuePayment(),"IDATE"));

        valueInfoClient.build(contentStream,writer);

        VerticalContainer boxInfoClient2 = new VerticalContainer(320,page.getMediaBox().getHeight()-223-14,250);
        boxInfoClient2.addElement(new BorderBox(gris,gris,1,0,0,73,11));
        boxInfoClient2.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,0,1));
        boxInfoClient2.addElement(new BorderBox(gris,gris,1,0,0,73,11));
        boxInfoClient2.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,0,1));
        boxInfoClient2.addElement(new BorderBox(gris,gris,1,0,0,73,11));
        boxInfoClient2.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,0,1));
        boxInfoClient2.addElement(new BorderBox(gris,gris,1,0,0,73,11));

        boxInfoClient2.build(contentStream,writer);

        VerticalContainer labelInfoClient2 = new VerticalContainer(321,page.getMediaBox().getHeight()-227,250);
        labelInfoClient2.addElement(new SimpleTextBox(font,8,0,0,"ID Client"));
        labelInfoClient2.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2.5f));
        labelInfoClient2.addElement(new SimpleTextBox(font,8,0,0,model.getDate().getLabelCommand()));
        labelInfoClient2.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2.5f));
        labelInfoClient2.addElement(new SimpleTextBox(font,8,0,0,"Date de validation"));
        labelInfoClient2.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2.5f));
        labelInfoClient2.addElement(new SimpleTextBox(font,8,0,0,"Date d'envoi "));
        labelInfoClient2.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2.5f));
        labelInfoClient2.addElement(new SimpleTextBox(font,8,0,0,model.getDate().getValue()));

        labelInfoClient2.build(contentStream,writer);

        new BorderBox(gris,gris,1,396,page.getMediaBox().getHeight()-272,110,11).build(contentStream,writer);

        VerticalContainer valeurInfoClient2 = new VerticalContainer(397,page.getMediaBox().getHeight()-227,250);
        valeurInfoClient2.addElement(new SimpleTextBox(font,8,0,0,model.getReference().getValueClient(),"CNUM"));
        valeurInfoClient2.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2.5f));
        valeurInfoClient2.addElement(new SimpleTextBox(font,8,0,0,model.getDate().getValueCommand(),"IDATE"));
        valeurInfoClient2.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2.5f));
        valeurInfoClient2.addElement(new SimpleTextBox(font,8,0,0,model.getDate().getValueCommand()));
        valeurInfoClient2.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2.5f));
        valeurInfoClient2.addElement(new SimpleTextBox(font,8,0,0,"N° de colis "));
        valeurInfoClient2.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,2.5f));
        int codeColis = 100000000 + new Random().nextInt(900000000);
        valeurInfoClient2.addElement(new SimpleTextBox(font,8,0,0, "DT"+codeColis+"FR"));

        valeurInfoClient2.build(contentStream,writer);

        new SimpleTextBox(fontBold,9,36,521,"Votre Commande",Color.RED,Color.WHITE).build(contentStream,writer);

        float[] configRow = {82f, 189f, 28f, 62f, 62f, 62f,39f};
        TableRowBox firstLine = new TableRowBox(configRow, 0, 0);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Réf.", Color.BLACK, gris), true);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Libellé", Color.BLACK, gris), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Qté", Color.BLACK, gris), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "PUHT", Color.BLACK, gris), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "PUTTC", Color.BLACK, gris), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "PVTTC",Color.BLACK, gris), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "TVA", Color.BLACK, gris), false);

        VerticalContainer verticalInvoiceItems = new VerticalContainer(36, 510, 600 );
        verticalInvoiceItems.addElement(new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,0,0,0,page.getMediaBox().getWidth()-(72),0.3f));
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 1));
        verticalInvoiceItems.addElement(firstLine);
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0,0, 0, 1));
        verticalInvoiceItems.addElement(new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,0,0,0,page.getMediaBox().getWidth()-(72),0.3f));


        for(int w=0; w< model.getProductContainer().getProducts().size(); w++) {

            Product randomProduct = model.getProductContainer().getProducts().get(w);

            TableRowBox productLine = new TableRowBox(configRow, 0, 0);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getEan(), "SNO"), true);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getName(), "PD"), false);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, Float.toString(randomProduct.getQuantity()), "QTY"), false);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getFormatedPriceWithoutTax(), "UP"), false);
            float puttcR = (float)(int)((randomProduct.getPriceWithoutTax() + randomProduct.getPriceWithoutTax() * randomProduct.getTaxRate())*100)/100;
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, puttcR + "", "undefined"), false);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getFormatedTotalPriceWithTax(), "undefined"), false);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getTaxRate() * 100 + "%", "TXR"), false);

            verticalInvoiceItems.addElement(productLine);
            verticalInvoiceItems.addElement(new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,0,0,0,page.getMediaBox().getWidth()-(72),0.3f));
        }

        String msg = this.getClass().getClassLoader().getResource("invoices/parts/cdiscount/msg.png").getFile();
        PDImageXObject logoMsg = PDImageXObject.createFromFile(msg, document);
        float ratioMsg= (float)logoMsg.getWidth() / (float)logoMsg.getHeight();
        float tailleMsg = 320;
        float posMsgX = 36;
        float posMsgY = verticalInvoiceItems.getBoundingBox().getPosY()-verticalInvoiceItems.getBoundingBox().getHeight()-tailleMsg/ratioMsg-10;
        contentStream.drawImage(logoMsg, posMsgX, posMsgY, tailleMsg, tailleMsg/ratioMsg);

        verticalInvoiceItems.build(contentStream,writer);

        float tailleCase = 13.28f;
        float posTab = posMsgY-tailleMsg/ratioMsg-15+93;
        new BorderBox(Color.LIGHT_GRAY,gris,1,379,posTab-93,100,93).build(contentStream,writer);
        new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,379,posTab-tailleCase,100,1).build(contentStream,writer);
        new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,379,posTab-tailleCase*2,100,1).build(contentStream,writer);
        new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,379,posTab-tailleCase*3,100,1).build(contentStream,writer);
        new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,379,posTab-tailleCase*4,100,1).build(contentStream,writer);
        new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,379,posTab-tailleCase*5,100,1).build(contentStream,writer);
        new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,379,posTab-tailleCase*6,100,1).build(contentStream,writer);

        new BorderBox(Color.LIGHT_GRAY,Color.WHITE,1,478,posTab-93,82,93).build(contentStream,writer);
        new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,478,posTab-tailleCase,82,1).build(contentStream,writer);
        new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,478,posTab-tailleCase*2,82,1).build(contentStream,writer);
        new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,478,posTab-tailleCase*3,82,1).build(contentStream,writer);
        new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,478,posTab-tailleCase*4,82,1).build(contentStream,writer);
        new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,478,posTab-tailleCase*5,82,tailleCase).build(contentStream,writer);
        new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,478,posTab-tailleCase*6,82,1).build(contentStream,writer);



        VerticalContainer labelTotal = new VerticalContainer(380,posTab-2,250);
        labelTotal.addElement(new SimpleTextBox(font,8,0,0,model.getProductContainer().getTotalAmountHead().toUpperCase()));
        labelTotal.addElement(new BorderBox(gris,gris,0,5,0,0,4));
        labelTotal.addElement(new SimpleTextBox(font,8,0,0,"AVOIR/CARTE CADEAU"));
        labelTotal.addElement(new BorderBox(gris,gris,0,0,0,0,4));
        labelTotal.addElement(new SimpleTextBox(font,8,0,0,"REMISE"));
        labelTotal.addElement(new BorderBox(gris,gris,0,0,0,0,4));
        labelTotal.addElement(new SimpleTextBox(font,8,0,0,"FRAIS PREPA* "));
        labelTotal.addElement(new BorderBox(gris,gris,0,0,0,0,4));
        labelTotal.addElement(new SimpleTextBox(font,8,0,0,"TOTAL NET TTC"));
        labelTotal.addElement(new BorderBox(gris,gris,0,0,0,0,4));
        labelTotal.addElement(new SimpleTextBox(font,8,0,0,model.getProductContainer().getTotalTaxHead().toUpperCase()));
        labelTotal.addElement(new BorderBox(gris,gris,0,0,0,0,4));
        labelTotal.addElement(new SimpleTextBox(font,8,0,0,model.getProductContainer().getTotalWithoutTaxHead().toUpperCase()));
        labelTotal.build(contentStream,writer);

        VerticalContainer valeurTotal = new VerticalContainer(480,posTab-2,250);
        valeurTotal.addElement(new SimpleTextBox(font,8,0,0,model.getProductContainer().getFormatedTotalWithTax(),"TA"));
        valeurTotal.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,4));
        valeurTotal.addElement(new SimpleTextBox(font,8,0,0,"0,00"));
        valeurTotal.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,4));
        valeurTotal.addElement(new SimpleTextBox(font,8,0,0,""));
        valeurTotal.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0, 0,4));
        valeurTotal.addElement(new SimpleTextBox(font,8,0,0,"0,00"));
        valeurTotal.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,4));
        valeurTotal.addElement(new SimpleTextBox(font,8,0,0,model.getProductContainer().getFormatedTotalWithTax(),"TA"));
        valeurTotal.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,4));
        valeurTotal.addElement(new SimpleTextBox(font,8,0,0,model.getProductContainer().getFormatedTotalTax(),"TTX"));
        valeurTotal.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,4));
        valeurTotal.addElement(new SimpleTextBox(font,8,0,0,model.getProductContainer().getFormatedTotalWithoutTax(),"TWTX"));
        valeurTotal.build(contentStream,writer);

        new SimpleTextBox(fontItalic,9,170,posMsgY-76,"Aucun escompte ne sera appliqué en case de paiement anticipé").build(contentStream,writer);

        new BorderBox(Color.LIGHT_GRAY,Color.WHITE,1,124,posMsgY-110,351,14).build(contentStream,writer);
        new BorderBox(Color.LIGHT_GRAY,Color.WHITE,1,124,posMsgY-123,351,14).build(contentStream,writer);

        int tailleTab;
        for(tailleTab=0; tailleTab< model.getProductContainer().getProducts().size(); tailleTab++) {

            new BorderBox(Color.LIGHT_GRAY,Color.WHITE,1,124,posMsgY-123-13*tailleTab,351,14).build(contentStream,writer);
        }

        new BorderBox(Color.LIGHT_GRAY,Color.WHITE,1,124,posMsgY-123-13*(tailleTab),351,14).build(contentStream,writer);

        new SimpleTextBox(font,8,189,posMsgY-97,"Détails des éco-Participation et rémuneration copie privée").build(contentStream,writer);
        new SimpleTextBox(font,8,170,posMsgY-111,"Libellé").build(contentStream,writer);
        new SimpleTextBox(font,8,250,posMsgY-111,"ECO-PARTICIPATION TTC").build(contentStream,writer);
        new SimpleTextBox(font,8,380,posMsgY-111,"COPIE PRIVEE TTC").build(contentStream,writer);

        //new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,240,posMsgY-136,1,27).build(contentStream,writer);
        //new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,360,posMsgY-136,1,27).build(contentStream,writer);

        new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,240,posMsgY-136-14*(tailleTab-1)+tailleTab,1,13*(tailleTab+1)).build(contentStream,writer);
        new BorderBox(Color.LIGHT_GRAY,Color.LIGHT_GRAY,1,360,posMsgY-136-14*(tailleTab-1)+tailleTab,1,13*(tailleTab+1)).build(contentStream,writer);

        String footer = this.getClass().getClassLoader().getResource("invoices/parts/cdiscount/footer.png").getFile();
        PDImageXObject logofooter = PDImageXObject.createFromFile(footer, document);
        float ratioFooter= (float)logofooter.getWidth() / (float)logofooter.getHeight();
        float tailleFooter = 520;
        float posFooterX = 36;
        float posFooterY = 48;
        contentStream.drawImage(logofooter, posFooterX, posFooterY, tailleFooter, tailleFooter/ratioFooter);

        new SimpleTextBox(fontBold,8,263,36,model.getCompany().getWebsite()).build(contentStream,writer);
        HorizontalContainer footercontainer = new HorizontalContainer(249,22);
        footercontainer.addElement(new SimpleTextBox(font,8,0,0,"N°RCS : "));
        footercontainer.addElement(new SimpleTextBox(font,8,0,0,idnumObj.getCidValue(),"SCID"));
        footercontainer.addElement(new SimpleTextBox(font,8,0,0," "+model.getCompany().getAddress().getCity()));
        footercontainer.build(contentStream,writer);

        int tailleTab2;
        for(tailleTab2=0; tailleTab2< model.getProductContainer().getProducts().size(); tailleTab2++) {

            Product randomProduct = model.getProductContainer().getProducts().get(tailleTab2);

            new SimpleTextBox(font, 8, 155, posMsgY-123-13*tailleTab2, randomProduct.getEan(), "SNO").build(contentStream,writer);
            new SimpleTextBox(font, 8, 283, posMsgY-123-13*tailleTab2, randomProduct.getEcoParticipationWithoutTax()+"").build(contentStream,writer);
        }

        contentStream.close();
    }
}
