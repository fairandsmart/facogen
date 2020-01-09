package com.fairandsmart.invoices.layout.darty;

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

import com.fairandsmart.invoices.data.model.Address;
import com.fairandsmart.invoices.data.model.IDNumbers;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.data.model.Product;
import com.fairandsmart.invoices.element.border.BorderBox;
import com.fairandsmart.invoices.element.container.HorizontalContainer;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import com.fairandsmart.invoices.element.line.HorizontalLineBox;
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

public class DartyLayout implements InvoiceLayout {

    @Override
    public void builtInvoice(InvoiceModel model, PDDocument document, XMLStreamWriter writer) throws Exception {

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        writer.writeStartElement("DL_PAGE");
        writer.writeAttribute("gedi_type", "DL_PAGE");
        writer.writeAttribute("pageID", "1");
        writer.writeAttribute("width", "2480");
        writer.writeAttribute("height", "3508");

        Address address = model.getCompany().getAddress();
        IDNumbers idNumbers = model.getCompany().getIdNumbers();
        Color gris = new Color(220,220,220);

        PDFont font = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        String logo = this.getClass().getClassLoader().getResource("logo/Darty.png").getFile();
        PDImageXObject logoHeader = PDImageXObject.createFromFile(logo, document);
        float ratioLogo = (float)logoHeader.getWidth() / (float)logoHeader.getHeight();
        int tailleLogo = 60;
        float posLogoX = 42;
        float posLogoY = page.getMediaBox().getHeight()-tailleLogo-42;
        contentStream.drawImage(logoHeader, posLogoX, posLogoY, tailleLogo, tailleLogo/ratioLogo);

        String factLiv = this.getClass().getClassLoader().getResource("parts/darty/factLiv.jpg").getFile();
        PDImageXObject factLivImage = PDImageXObject.createFromFile(factLiv, document);
        float ratioFactLiv = (float)factLivImage.getHeight() / (float)factLivImage.getWidth();
        int tailleFactLiv = 133;
        float posFactLivX = 306;
        float posFactLivY = page.getMediaBox().getHeight()-tailleFactLiv-39;
        contentStream.drawImage(factLivImage, posFactLivX, posFactLivY, tailleFactLiv/ratioFactLiv, tailleFactLiv);

        VerticalContainer verticalHeaderContainer = new VerticalContainer(107, posFactLivY+tailleFactLiv, 250 );
        verticalHeaderContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getCompany().getName().toUpperCase(),"SN"));
        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,8));
        verticalHeaderContainer.addElement(new SimpleTextBox(font, 9, 0, 0, "Services Comptabilité"));
        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,8));
        verticalHeaderContainer.addElement(new SimpleTextBox(font, 9, 0, 0, "TSA n°80 004"));
        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,8));
        verticalHeaderContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getCompany().getAddress().getZip() +" - "+ model.getCompany().getAddress().getCity(),"SA"));
        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,8));
        verticalHeaderContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getCompany().getContact().getphoneLabel()+" :  "+model.getCompany().getContact().getphoneValue(),"SCN"));
        verticalHeaderContainer.addElement(new BorderBox(Color.white,Color.WHITE,0,0,0,0,8));
        verticalHeaderContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getCompany().getContact().getfaxLabel()+" :  "+model.getCompany().getContact().getfaxValue(),"SFAX"));
        verticalHeaderContainer.build(contentStream,writer);

        //Billing Shipping Address
        float hauteurBH = 133;
        float largeurBH = 226;
        float posBHX = 325;
        float posBHY = posFactLivY+tailleFactLiv-hauteurBH;
        new BorderBox(Color.BLACK, Color.WHITE, 1,posBHX, posBHY, largeurBH,hauteurBH).build(contentStream, writer);
        VerticalContainer verticalAddressContainer = new VerticalContainer(posBHX+5, posBHY+hauteurBH-11, 500 );
        verticalAddressContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getClient().getShippingName().toUpperCase(),"SHN" ));
        verticalAddressContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getClient().getShippingAddress().getLine1().toUpperCase(),"SHA" ));
        verticalAddressContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getClient().getShippingAddress().getZip().toUpperCase() + " "+model.getClient().getShippingAddress().getCity().toUpperCase(),"SHA" ));
        verticalAddressContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getClient().getShippingAddress().getCountry().toUpperCase(), "SHA" ));
        verticalAddressContainer.addElement(new BorderBox(Color.WHITE,Color.WHITE,1,0,0,0,28));
        verticalAddressContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getClient().getBillingName().toUpperCase(), "BN" ));
        verticalAddressContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getClient().getBillingAddress().getLine1().toUpperCase(), "BA" ));
        verticalAddressContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getClient().getBillingAddress().getZip().toUpperCase() + " "+model.getClient().getBillingAddress().getCity().toUpperCase(), "BA" ));
        verticalAddressContainer.addElement(new SimpleTextBox(font, 9, 0, 0, model.getClient().getBillingAddress().getCountry().toUpperCase(), "BA" ));

        verticalAddressContainer.build(contentStream, writer);

        new BorderBox(Color.BLACK,Color.BLACK,1,posBHX,posBHY+hauteurBH/2,largeurBH,1).build(contentStream,writer);

        float posRectGris = page.getMediaBox().getHeight()-175;

        new BorderBox(gris,gris,1,42,posRectGris-42,240,42).build(contentStream,writer);
        new SimpleTextBox(fontBold, 9, 136, posRectGris-3,"FACTURE" ).build(contentStream,writer);

        HorizontalContainer HorizontalNumDateContainer = new HorizontalContainer(42+240/4, posRectGris-25 );
        HorizontalNumDateContainer.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "N°"));
        HorizontalNumDateContainer.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getReference().getValue(), "IN" ));
        HorizontalNumDateContainer.addElement(new SimpleTextBox(fontBold, 9, 0, 0, " du " ));
        HorizontalNumDateContainer.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getDate().getValue(), "IDATE" ));
        HorizontalNumDateContainer.build(contentStream,writer);

        HorizontalContainer HorizontalComDateContainer = new HorizontalContainer(42, 595 );
        HorizontalComDateContainer.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getReference().getLabelCommand()+" "));
        HorizontalComDateContainer.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getReference().getValueCommand(),"ONUM" ));
        HorizontalComDateContainer.addElement(new SimpleTextBox(fontBold, 9, 0, 0, " du " ));
        HorizontalComDateContainer.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getDate().getValue(), "IDATE" ));
        HorizontalComDateContainer.build(contentStream,writer);

        float[] configRow = {56f, 22f, 141f, 80f, 62f, 90f, 51f};
        TableRowBox firstLine = new TableRowBox(configRow, 0, 0);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Référence", Color.BLACK, Color.WHITE), true);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Qté", Color.BLACK, Color.WHITE), true);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Libellé", Color.BLACK, Color.WHITE), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Garantie", Color.BLACK, Color.WHITE), true);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Total HT", Color.BLACK, Color.WHITE), true);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Base/Taux",Color.BLACK, Color.WHITE), true);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Total TTC", Color.BLACK, Color.WHITE), true);

        TableRowBox line2 = new TableRowBox(configRow, 0, 0);
        line2.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "", Color.BLACK, Color.WHITE), true);
        line2.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "", Color.BLACK, Color.WHITE), true);
        line2.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "", Color.BLACK, Color.WHITE), true);
        line2.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Darty", Color.BLACK, Color.WHITE), true);
        line2.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "", Color.BLACK, Color.WHITE), true);
        line2.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "TVA ou TCA",Color.BLACK, Color.WHITE), true);
        line2.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "", Color.BLACK, Color.WHITE), true);

        TableRowBox line3 = new TableRowBox(configRow, 0, 0);
        line3.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "", Color.BLACK, Color.WHITE), true);
        line3.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "", Color.BLACK, Color.WHITE), true);
        line3.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "", Color.BLACK, Color.WHITE), true);
        line3.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "jusqu'au", Color.BLACK, Color.WHITE), true);
        line3.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "", Color.BLACK, Color.WHITE), true);
        line3.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "",Color.BLACK, Color.WHITE), true);
        line3.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "", Color.BLACK, Color.WHITE), true);

        VerticalContainer verticalInvoiceItems = new VerticalContainer(42, 566, 600 );
        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0, page.getMediaBox().getWidth()-(42), 0));
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalInvoiceItems.addElement(firstLine);
        verticalInvoiceItems.addElement(line2);
        verticalInvoiceItems.addElement(line3);
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0,0, 0, 5));
        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0, page.getMediaBox().getWidth()-(42), 0));

        verticalInvoiceItems.build(contentStream,writer);


        for(int w=0; w< model.getProductContainer().getProducts().size(); w++) {

            Product randomProduct = model.getProductContainer().getProducts().get(w);

            TableRowBox productLine = new TableRowBox(configRow, 0, 0);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getEan(), "TBL"), true);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, Float.toString(randomProduct.getQuantity()), "TBL"), true);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getName(), "TBL"), false);

            String anneeGarantie = model.getDate().getValue().substring(model.getDate().getValue().length()-4,model.getDate().getValue().length());
            int nouvAnnee;
            String dateGarantie;

            if(anneeGarantie.contains("/")){
                nouvAnnee = Integer.parseInt(anneeGarantie.substring(anneeGarantie.length()-2,anneeGarantie.length()))+2;
                anneeGarantie = model.getDate().getValue().substring(model.getDate().getValue().length()-2,model.getDate().getValue().length());
                dateGarantie = model.getDate().getValue().replace(anneeGarantie,nouvAnnee+"");
            }
            else {
                nouvAnnee = Integer.parseInt(anneeGarantie) + 2;
                dateGarantie = model.getDate().getValue().replace(anneeGarantie,nouvAnnee+"");
            }

            productLine.addElement(new SimpleTextBox(font, 8, 2, 0,dateGarantie ), true);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getFormatedTotalPriceWithoutTax(), "TBL"), true);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getFormatedTotalTax(),"TBL"),true);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getFormatedTotalPriceWithTax()+"","TBL"),true);

            TableRowBox productLine2 = new TableRowBox(configRow, 0, 0);
            productLine2.addElement(new SimpleTextBox(font, 8, 2, 0, ""), true);
            productLine2.addElement(new SimpleTextBox(font, 8, 2, 0, ""), true);
            productLine2.addElement(new SimpleTextBox(font, 8, 2, 0, ""),true);
            productLine2.addElement(new SimpleTextBox(font, 8, 2, 0, ""), true);
            productLine2.addElement(new SimpleTextBox(font, 8, 2, 0, ""), true);
            productLine2.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getTaxRate()*100+""),true);
            productLine2.addElement(new SimpleTextBox(font, 8, 2, 0, ""),true);


            verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
            verticalInvoiceItems.addElement(productLine);
            verticalInvoiceItems.addElement(productLine2);
            verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        }

        TableRowBox productLine3 = new TableRowBox(configRow, 0, 0);
        productLine3.addElement(new SimpleTextBox(font, 8, 2, 0, ""), true);
        productLine3.addElement(new SimpleTextBox(font, 8, 2, 0, ""), true);
        productLine3.addElement(new SimpleTextBox(font, 8, 2, 0, "Frais d'expédition"),false);
        productLine3.addElement(new SimpleTextBox(font, 8, 2, 0, ""), true);
        productLine3.addElement(new SimpleTextBox(font, 8, 2, 0, ""), true);
        productLine3.addElement(new SimpleTextBox(font, 8, 2, 0, ""),true);
        productLine3.addElement(new SimpleTextBox(font, 8, 2, 0, "OFFERT"),true);

        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0,page.getMediaBox().getWidth()-(42), 0));
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalInvoiceItems.addElement(productLine3);
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0,page.getMediaBox().getWidth()-(42), 0));

        verticalInvoiceItems.build(contentStream, writer);


        String msg = this.getClass().getClassLoader().getResource("parts/darty/msg.png").getFile();
        PDImageXObject logoMsg = PDImageXObject.createFromFile(msg, document);
        float ratioMsg= (float)logoMsg.getWidth() / (float)logoMsg.getHeight();
        float tailleMsg = 113;
        float posMsgX = 59;
        float posMsgY = verticalInvoiceItems.getBoundingBox().getPosY()-verticalInvoiceItems.getBoundingBox().getHeight()-70;
        contentStream.drawImage(logoMsg, posMsgX, posMsgY, tailleMsg, tailleMsg/ratioMsg);

        new BorderBox(Color.BLACK,Color.WHITE,1,195,posMsgY,360,39).build(contentStream,writer);

        HorizontalContainer totaux = new HorizontalContainer(250,posMsgY+39-3);
        totaux.addElement(new SimpleTextBox(fontBold,9,0,0,"Total facturé : "));
        totaux.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,50,0));
        SimpleTextBox stb = new SimpleTextBox(font,9,0,0,model.getProductContainer().getFormatedTotalWithoutTax()+"","TBL");
        totaux.addElement(stb);
        totaux.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,70-stb.getBoundingBox().getWidth(),0));
        SimpleTextBox stb2 = new SimpleTextBox(font,9,0,0,model.getProductContainer().getFormatedTotalTax()+"","TBL");
        totaux.addElement(stb2);
        totaux.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,73-stb2.getBoundingBox().getWidth(),0));
        totaux.addElement(new SimpleTextBox(font,9,0,0,model.getProductContainer().getFormatedTotalWithTax()+"","TBL"));
        totaux.build(contentStream,writer);

        new SimpleTextBox(fontBold,9,226,posMsgY-33,"Montant réglé par : ").build(contentStream,writer);
        new SimpleTextBox(fontBold,9,340,posMsgY-33,model.getPaymentInfo().getValueType(),"PMODE").build(contentStream,writer);
        new SimpleTextBox(fontBold,9,411,posMsgY-66,"Soldes à régler : ").build(contentStream,writer);
        new SimpleTextBox(fontBold,9,page.getMediaBox().getWidth()-90,posMsgY-33,model.getProductContainer().getFormatedTotalWithTax(),"TBL").build(contentStream,writer);
        new SimpleTextBox(fontBold,9,page.getMediaBox().getWidth()-90,posMsgY-66,"0,00 €").build(contentStream,writer);


        HorizontalContainer infoEntreprise = new HorizontalContainer(0,0);
        infoEntreprise.addElement(new SimpleTextBox(font,7,0,0, model.getCompany().getName(),"SN"));
        infoEntreprise.addElement(new SimpleTextBox(font,7,0,0, " - "));
        infoEntreprise.addElement(new SimpleTextBox(font,7,0,0, "SNC au capital de "+(int)(100000+(Math.random()*(9999999 - 100000)))+" Eur - "));
        infoEntreprise.addElement(new SimpleTextBox(font,7,0,0, " "+idNumbers.getSiretLabel()+" "));
        infoEntreprise.addElement(new SimpleTextBox(font,7,0,0, idNumbers.getSiretValue(),"SSIRET"));
        infoEntreprise.addElement(new SimpleTextBox(font,7,0,0, " - "+idNumbers.getToaLabel()+" "));
        infoEntreprise.addElement(new SimpleTextBox(font,7,0,0, idNumbers.getToaValue(),"STOA"));

        HorizontalContainer infoEntreprise2 = new HorizontalContainer(0,0);
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, "Adresse : ","SA"));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, address.getLine1()+" ","SA"));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, " - "));
        infoEntreprise2.addElement(new SimpleTextBox(font,7,0,0, address.getZip() + " - " +address.getCity(),"SA"));

        HorizontalContainer infoEntreprise3 = new HorizontalContainer(0,0);
        infoEntreprise3.addElement(new SimpleTextBox(font,7,0,0, idNumbers.getVatLabel() +" : "));
        infoEntreprise3.addElement(new SimpleTextBox(font,7,0,0, idNumbers.getVatValue(),"SVAT"));

        HorizontalContainer infoEntreprise4 = new HorizontalContainer(0,0);
        infoEntreprise4.addElement(new SimpleTextBox(font,7,0,0, "Notice officielle d'information sur la copie privée : http://www.copieprivee.culture.gouv.fr"));

        HorizontalContainer infoEntreprise5 = new HorizontalContainer(0,0);
        infoEntreprise5.addElement(new SimpleTextBox(font,7,0,0, "Remboursement/exoneration de la rénumeration pour usage professionnel : http://www.copiefrance.fr"));

        float millieuPageX = page.getMediaBox().getWidth()/2;
        infoEntreprise.translate(millieuPageX-infoEntreprise.getBoundingBox().getWidth()/2,120);
        infoEntreprise2.translate(millieuPageX-infoEntreprise2.getBoundingBox().getWidth()/2,111);
        infoEntreprise3.translate(millieuPageX-infoEntreprise3.getBoundingBox().getWidth()/2,102);
        infoEntreprise4.translate(millieuPageX-infoEntreprise4.getBoundingBox().getWidth()/2,93);
        infoEntreprise5.translate(millieuPageX-infoEntreprise5.getBoundingBox().getWidth()/2,84);

        infoEntreprise.build(contentStream,writer);
        infoEntreprise2.build(contentStream,writer);
        infoEntreprise3.build(contentStream,writer);
        infoEntreprise4.build(contentStream,writer);
        infoEntreprise5.build(contentStream,writer);


        contentStream.close();

    }
}
