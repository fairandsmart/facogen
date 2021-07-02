package com.fairandsmart.generator.documents.layout.naturedecouvertes;

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
 * Copyright (C) 2019 - 2020 Fair And Smart
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
import com.fairandsmart.generator.documents.element.image.ImageBox;
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

public class NatureDecouvertesLayout implements InvoiceLayout {


    @Override
    public String name() {
        return "Nature&Decouvertes";
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

        Address address = model.getCompany().getAddress();

        PDFont font = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        String logo = this.getClass().getClassLoader().getResource("common/logo/naturedecouverte.png").getFile();
        PDImageXObject logoHeader = PDImageXObject.createFromFile(logo, document);
        float ratioPage = page.getMediaBox().getWidth()/2480;
        float ratioLogo = (float)logoHeader.getWidth() / (float)logoHeader.getHeight();

        float widthLogo = 596*ratioPage;
        float posLogoX = 147*ratioPage;
        float heightLogo = widthLogo/ratioLogo;
        //l'origine est en bas à gauche de la page
        float posLogoY = page.getMediaBox().getHeight()-(heightLogo+125*ratioPage);
        contentStream.drawImage(logoHeader, posLogoX, posLogoY, widthLogo,heightLogo);

        VerticalContainer headerContainer = new VerticalContainer(1282*ratioPage,page.getMediaBox().getHeight()-132*ratioPage,250);
        headerContainer.addElement(new SimpleTextBox(fontBold,12,0,0,model.getCompany().getName(),"SN"));
        headerContainer.addElement(new SimpleTextBox(font,10,0,0,model.getCompany().getAddress().getLine1(),"SA"));
        headerContainer.addElement(new SimpleTextBox(font,10,0,0,model.getCompany().getAddress().getZip()+" "+model.getCompany().getAddress().getCity(),"SA"));
        headerContainer.addElement(new SimpleTextBox(font,10,0,0,model.getCompany().getAddress().getCountry(),"SA"));
        headerContainer.addElement(new SimpleTextBox(font,10,0,0,"Téléphone : "+model.getCompany().getContact().getphoneValue(),"SA"));
        headerContainer.build(contentStream,writer);

        VerticalContainer shippingContainer = new VerticalContainer(147*ratioPage,page.getMediaBox().getHeight()-499*ratioPage,250);
        shippingContainer.addElement(new SimpleTextBox(font,10,0,0,"Adresse de Livraison"));
        shippingContainer.addElement(new SimpleTextBox(fontBold,11,0,0,model.getClient().getShippingName().toUpperCase(),"SHN"));
        shippingContainer.addElement(new SimpleTextBox(font,10,0,0,model.getClient().getShippingAddress().getLine1().toUpperCase(),"SHA"));
        shippingContainer.addElement(new SimpleTextBox(font,10,0,0,model.getClient().getShippingAddress().getZip()+" "+model.getClient().getShippingAddress().getCity().toUpperCase(),"SHA"));
        shippingContainer.addElement(new SimpleTextBox(font,10,0,0, "Tel : 06"+(int)(1000000+(Math.random()*(99999999 - 1000000)))));

        shippingContainer.build(contentStream,writer);

        VerticalContainer billingContainer = new VerticalContainer(1282*ratioPage,page.getMediaBox().getHeight()-499*ratioPage,250);
        billingContainer.addElement(new SimpleTextBox(font,10, 0,0,"Adresse de Facturation"));
        billingContainer.addElement(new SimpleTextBox(fontBold,11,0,0,model.getClient().getBillingName().toUpperCase(),"BN"));
        billingContainer.addElement(new SimpleTextBox(font,10,0,0,model.getClient().getBillingAddress().getLine1().toUpperCase(),"BA"));
        billingContainer.addElement(new SimpleTextBox(font,10,0,0,model.getClient().getBillingAddress().getZip()+" "+model.getClient().getBillingAddress().getCity().toUpperCase(),"BA"));

        billingContainer.build(contentStream,writer);

        HorizontalContainer infoCommande = new HorizontalContainer(147*ratioPage, page.getMediaBox().getHeight()-866*ratioPage);
        infoCommande.addElement(new SimpleTextBox(PDType1Font.TIMES_BOLD,15, 0,0,"COMMANDE No "+model.getReference().getValueCommand(),"ONUM"));
        infoCommande.build(contentStream,writer);

        HorizontalContainer infoInvoice = new HorizontalContainer(1357*ratioPage, page.getMediaBox().getHeight()-875*ratioPage);
        infoInvoice.addElement(new SimpleTextBox(PDType1Font.TIMES_BOLD,12, 0,300,"Facture No "+model.getReference().getValue(),"IN"));
        infoInvoice.build(contentStream,writer);

        HorizontalContainer dateInvoice = new HorizontalContainer(2000*ratioPage, page.getMediaBox().getHeight()-875*ratioPage);
        dateInvoice.addElement(new SimpleTextBox(PDType1Font.TIMES_BOLD,12, 0,300,"Le "+model.getDate().getValueCommand(),"IDATE"));
        dateInvoice.build(contentStream,writer);

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

        float[] configRow = {236*ratioPage,936*ratioPage,115*ratioPage,112*ratioPage,230*ratioPage,212*ratioPage,290*ratioPage};

        TableRowBox firstLine = new TableRowBox(configRow, 0, 0);
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "Référence Produit", Color.BLACK,null), true);
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "Désignation", Color.BLACK, null), true);
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "Quté Cmde", Color.BLACK, null), true);
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "TVA", Color.BLACK, null), true);
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "Prix Unit. ", Color.BLACK, null), true);
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "Quantité Livrée", Color.BLACK, null), true);
        firstLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "TOTAL TTC", Color.BLACK,null), true);


        VerticalContainer verticalInvoiceItems = new VerticalContainer(170*ratioPage, page.getMediaBox().getHeight()-1046*ratioPage, 2136*ratioPage );
        new BorderBox(Color.BLACK,Color.WHITE, 1,165*ratioPage, page.getMediaBox().getHeight()-2462*ratioPage, 2138*ratioPage,1418*ratioPage ).build(contentStream,writer);
        //new BorderBox(Color.BLACK,new Color (200,200,200, 1.0f), 1,165*ratioPage, page.getMediaBox().getHeight()-2596*ratioPage, 1844*ratioPage,1552*ratioPage ).build(contentStream,writer);
        new BorderBox(Color.BLACK,Color.lightGray, 1,165*ratioPage, page.getMediaBox().getHeight()-1158*ratioPage, 2138*ratioPage,118*ratioPage ).build(contentStream,writer);
        contentStream.drawPolygon(new float[]{165 * ratioPage,2303*ratioPage,2303*ratioPage, 165 * ratioPage},new float[]{page.getMediaBox().getHeight()-2596*ratioPage,page.getMediaBox().getHeight()-2596*ratioPage,page.getMediaBox().getHeight()-1044*ratioPage,page.getMediaBox().getHeight()-1044*ratioPage});
        contentStream.drawPolygon(new float[]{165 * ratioPage,2009*ratioPage,2009*ratioPage, 165 * ratioPage},new float[]{page.getMediaBox().getHeight()-2596*ratioPage,page.getMediaBox().getHeight()-2596*ratioPage,page.getMediaBox().getHeight()-1044*ratioPage,page.getMediaBox().getHeight()-1044*ratioPage});
        contentStream.drawLine(406*ratioPage,page.getMediaBox().getHeight()-1042*ratioPage, 406*ratioPage,page.getMediaBox().getHeight()-2462*ratioPage);
        contentStream.drawLine(1342*ratioPage,page.getMediaBox().getHeight()-1042*ratioPage, 1342*ratioPage,page.getMediaBox().getHeight()-2462*ratioPage);
        contentStream.drawLine(1576*ratioPage,page.getMediaBox().getHeight()-1042*ratioPage, 1576*ratioPage,page.getMediaBox().getHeight()-2462*ratioPage);
        contentStream.drawLine(1467*ratioPage,page.getMediaBox().getHeight()-1042*ratioPage, 1467*ratioPage,page.getMediaBox().getHeight()-2596*ratioPage);
        contentStream.drawLine(1804*ratioPage,page.getMediaBox().getHeight()-1042*ratioPage, 1804*ratioPage,page.getMediaBox().getHeight()-2462*ratioPage);
        verticalInvoiceItems.addElement(firstLine);


        for(int w=0; w< model.getProductContainer().getProducts().size(); w++) {

            Product randomProduct = model.getProductContainer().getProducts().get(w);

            TableRowBox productLine = new TableRowBox(configRow, 0, -10);
            productLine.addElement(new SimpleTextBox(font, 7, 2, 0, randomProduct.getEan(), "SNO"), true);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getName(), "PD"), false);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, Float.toString(randomProduct.getQuantity()), "QTY"), false);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getTaxRate() * 100 + "%", "TXR"), false);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getFormatedPriceWithoutTax(), "PTWTX"), false);
            float puttc = (float)(int)((randomProduct.getPriceWithoutTax() + randomProduct.getPriceWithoutTax() * randomProduct.getTaxRate())*100)/100;
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, puttc + "", "UP"), false);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getFormatedTotalPriceWithTax(), "PTTX"), false);

            verticalInvoiceItems.addElement(productLine);
        }

        verticalInvoiceItems.build(contentStream, writer);

        VerticalContainer paymentTypeContainer = new VerticalContainer(1476*ratioPage,page.getMediaBox().getHeight()-2479*ratioPage,250);
        paymentTypeContainer.addElement(new SimpleTextBox(fontBold,11,0,0,"TOTAL T.T.C"));
        paymentTypeContainer.addElement(new SimpleTextBox(font,8,0,0,"Réglé par : "+model.getPaymentInfo().getLabelType(),"PT"));
        paymentTypeContainer.build(contentStream,writer);

        VerticalContainer totalContainer = new VerticalContainer(2019*ratioPage,page.getMediaBox().getHeight()-2474*ratioPage,250);
        totalContainer.addElement(new SimpleTextBox(font,11,0,0,model.getProductContainer().getFormatedTotalWithTax(),"TA"));
        totalContainer.build(contentStream,writer);

        VerticalContainer backContainer = new VerticalContainer(343*ratioPage, page.getMediaBox().getHeight()-2840*ratioPage, 636*ratioPage);
        backContainer.addElement(new SimpleTextBox(font,10,0,0,"Etiquette à coller sur votre colis en cas de retour. "));
        backContainer.build(contentStream,writer);
        String barcode = this.getClass().getClassLoader().getResource("invoices/parts/nature/barcode.png").getFile();
        PDImageXObject pdBarcode = PDImageXObject.createFromFile(barcode, document);
        new ImageBox(pdBarcode, 343*ratioPage,page.getMediaBox().getHeight()-2940*ratioPage , pdBarcode.getWidth()*ratioPage, pdBarcode.getHeight()*ratioPage, model.getReference().getValueCommand()).build(contentStream,writer);
        backContainer = new VerticalContainer(370*ratioPage, page.getMediaBox().getHeight()-3050*ratioPage, 636*ratioPage);
        backContainer.addElement(new SimpleTextBox(font,8,0,0,model.getReference().getValueCommand()));
        backContainer.build(contentStream,writer);

        float[] row = {190*ratioPage,254*ratioPage,144*ratioPage,268*ratioPage};

        new BorderBox(Color.BLACK,Color.WHITE, 1,1440*ratioPage, page.getMediaBox().getHeight()-3000*ratioPage, 900*ratioPage,228*ratioPage ).build(contentStream,writer);
        new BorderBox(Color.BLACK,Color.lightGray, 1,1440*ratioPage, page.getMediaBox().getHeight()-2890*ratioPage, 900*ratioPage,118*ratioPage ).build(contentStream,writer);

        TableRowBox firstLineTax = new TableRowBox(row, 0, 0);
        firstLineTax.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "Code TVA", Color.BLACK,null), true);
        firstLineTax.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "Base H.T", Color.BLACK, null), true);
        firstLineTax.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "Taux %", Color.BLACK, null), true);
        firstLineTax.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "Montant TVA", Color.BLACK, null), true);
        VerticalContainer tax = new VerticalContainer(1446*ratioPage, page.getMediaBox().getHeight()-2798*ratioPage, 856*ratioPage );
        tax.addElement(firstLineTax);
        contentStream.drawLine(1648*ratioPage,page.getMediaBox().getHeight()-2772*ratioPage, 1648*ratioPage,page.getMediaBox().getHeight()-3000*ratioPage);
        contentStream.drawLine(1900*ratioPage,page.getMediaBox().getHeight()-2772*ratioPage, 1900*ratioPage,page.getMediaBox().getHeight()-3000*ratioPage);
        contentStream.drawLine(2064*ratioPage,page.getMediaBox().getHeight()-2772*ratioPage, 2064*ratioPage,page.getMediaBox().getHeight()-3000*ratioPage);

        TableRowBox taxContent = new TableRowBox(row, 0, -20);
        taxContent.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "1", Color.BLACK,null), true);
        taxContent.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, model.getProductContainer().getFormatedTotalWithoutTax(), Color.BLACK, null), true);
        taxContent.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, ""+model.getProductContainer().getProducts().get(0).getTaxRate(), Color.BLACK, null), true);
        taxContent.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, model.getProductContainer().getFormatedTotalTax(), Color.BLACK, null), true);
        tax.addElement(taxContent);

        tax.build(contentStream, writer);



        contentStream.close();


    }
}
