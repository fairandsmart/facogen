package com.fairandsmart.invoices.layout.ldlc;

import com.fairandsmart.invoices.data.model.Address;
import com.fairandsmart.invoices.data.model.IDNumbers;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.data.model.Product;
import com.fairandsmart.invoices.element.border.BorderBox;
import com.fairandsmart.invoices.element.container.HorizontalContainer;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import com.fairandsmart.invoices.element.image.ImageBox;
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

public class LDLCLayout implements InvoiceLayout {

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

        PDFont font = PDType1Font.HELVETICA;
        PDFont fontBold = PDType1Font.HELVETICA_BOLD;
        PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        String logo = this.getClass().getClassLoader().getResource("logo/LDLC.png").getFile();
        PDImageXObject logoHeader = PDImageXObject.createFromFile(logo, document);
        float ratioLogo = (float)logoHeader.getWidth() / (float)logoHeader.getHeight();
        int tailleLogo = 140;
        float posLogoX = 20;
        float posLogoY = page.getMediaBox().getHeight()-tailleLogo/ratioLogo - 20;
        contentStream.drawImage(logoHeader, posLogoX, posLogoY, tailleLogo, tailleLogo/ratioLogo);


        float posHeaderX = 15;
        float posHeaderY = page.getMediaBox().getHeight()-138;

        VerticalContainer infoEntreprise = new VerticalContainer(20,page.getMediaBox().getHeight()-85,400);

        HorizontalContainer infoEntreprise1 = new HorizontalContainer(0,0);
        infoEntreprise1.addElement(new SimpleTextBox(font,6,0,0, address.getLine1()+" ","SA"));
        infoEntreprise1.addElement(new SimpleTextBox(font,6,0,0, ", "));
        infoEntreprise1.addElement(new SimpleTextBox(font,6,0,0, address.getZip() + " - " +address.getCity(),"SA"));

        HorizontalContainer infoEntreprise2 = new HorizontalContainer(0,0);
        infoEntreprise2.addElement(new SimpleTextBox(font,6,0,0, "Numéro non surtaxé du Service Client : "));
        infoEntreprise2.addElement(new SimpleTextBox(font,6,0,0, model.getCompany().getContact().getphoneValue(),"SCN"));

        HorizontalContainer infoEntreprise3 = new HorizontalContainer(0,0);
        infoEntreprise3.addElement(new SimpleTextBox(font,6,0,0, "Retrouvez l'ensemblz de nos coordonnées sur notre site "));
        infoEntreprise3.addElement(new SimpleTextBox(font,6,0,0, model.getCompany().getWebsite()));
        infoEntreprise3.addElement(new SimpleTextBox(font,6,0,0, ", rubrique \"Besoin d'aide\""));

        infoEntreprise.addElement(infoEntreprise1);
        infoEntreprise.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,0,9));
        infoEntreprise.addElement(infoEntreprise2);
        infoEntreprise.addElement(new SimpleTextBox(font, 6, 0, 0, "Votre numéro de commande vous sera demandé pour tout appel"));
        infoEntreprise.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,0,9));
        infoEntreprise.addElement(infoEntreprise3);

        infoEntreprise.build(contentStream,writer);


        //Barcode top
        String barcode = this.getClass().getClassLoader().getResource("parts/ldlc/barcode.jpg").getFile();
        PDImageXObject pdBarcode = PDImageXObject.createFromFile(barcode, document);
        float ratioBarcode = pdBarcode.getWidth() / pdBarcode.getHeight();
        int tailleBarcode = 150;
        float posBarcodeX = page.getMediaBox().getWidth()/2-tailleBarcode/2;
        float posBarcodeY = posHeaderY-(tailleBarcode/ratioBarcode)/2;
        new ImageBox(pdBarcode, posBarcodeX, posBarcodeY, tailleBarcode, tailleBarcode/ratioBarcode,"").build(contentStream, writer);

        //Billing Address
        int hBH = tailleBarcode-65 ;
        int wBH = (int) (posBarcodeX-20);
        float posBHX = 25;
        float posBHY = (posBarcodeY-hBH-10);
        new BorderBox(Color.BLACK, Color.WHITE, 1,posBHX, posBHY, posBarcodeX-20,hBH).build(contentStream, writer);
        VerticalContainer verticalAddressContainer = new VerticalContainer(28, posBarcodeY-12, 250 );
        verticalAddressContainer.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "Client livré :"));
        verticalAddressContainer.addElement(new SimpleTextBox(fontItalic, 9, 0, 0, model.getClient().getBillingName().toUpperCase(), "BN" ));
        verticalAddressContainer.addElement(new SimpleTextBox(fontItalic, 9, 0, 0, model.getClient().getBillingAddress().getLine1().toUpperCase(), "BA" ));
        verticalAddressContainer.addElement(new SimpleTextBox(fontItalic, 9, 0, 0, model.getClient().getBillingAddress().getZip().toUpperCase() + " "+model.getClient().getBillingAddress().getCity().toUpperCase(), "BA" ));
        verticalAddressContainer.addElement(new SimpleTextBox(fontItalic, 9, 0, 0, model.getClient().getBillingAddress().getCountry().toUpperCase(), "BA" ));
        verticalAddressContainer.build(contentStream, writer);

        //Shipping Address
        int wSH = wBH;
        int hSH = hBH;
        float posSHX = posBarcodeX+tailleBarcode;
        float posSHY = posBarcodeY-hSH-10;
        new BorderBox(Color.BLACK, Color.WHITE, 1,posSHX, posSHY, wSH,hSH).build(contentStream, writer);
        VerticalContainer verticalAddressContainer2 = new VerticalContainer(posBarcodeX+tailleBarcode+2, posBarcodeY-12, 250 );
        verticalAddressContainer2.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "Client Facturé :"));
        verticalAddressContainer2.addElement(new SimpleTextBox(fontItalic, 9, 0, 0, model.getClient().getShippingName().toUpperCase(),"SHN" ));
        verticalAddressContainer2.addElement(new SimpleTextBox(fontItalic, 9, 0, 0, model.getClient().getShippingAddress().getLine1().toUpperCase(),"SHA" ));
        verticalAddressContainer2.addElement(new SimpleTextBox(fontItalic, 9, 0, 0, model.getClient().getShippingAddress().getZip().toUpperCase() + " "+model.getClient().getShippingAddress().getCity().toUpperCase(),"SHA" ));
        verticalAddressContainer2.addElement(new SimpleTextBox(fontItalic, 9, 0, 0, model.getClient().getShippingAddress().getCountry().toUpperCase(), "SHA" ));
        verticalAddressContainer2.build(contentStream, writer);

        //REF
        float posRefX = page.getMediaBox().getWidth()-page.getMediaBox().getWidth()/3+20;
        float posRefY = page.getMediaBox().getHeight()-49;
        new BorderBox(Color.BLACK, Color.WHITE, 1,posRefX, posRefY, page.getMediaBox().getWidth()-page.getMediaBox().getWidth()/3,50).build(contentStream, writer);
        new SimpleTextBox(font, 9, posRefX+5, posRefY+48, "Référence à rappeler lors du réglement").build(contentStream, writer);
        VerticalContainer verticalREF1 = new VerticalContainer(posRefX+5, posRefY+38, 250 );
        verticalREF1.addElement(new SimpleTextBox(font, 9, 0, 0, model.getReference().getLabelClient()));
        verticalREF1.addElement(new SimpleTextBox(font, 9, 0, 0, model.getReference().getLabel()));
        verticalREF1.addElement(new SimpleTextBox(font, 9, 0, 0, model.getDate().getLabel()));
        verticalREF1.build(contentStream, writer);

        VerticalContainer verticalREF2 = new VerticalContainer(posRefX+100, posRefY+38, 250 );
        verticalREF2.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getReference().getValueClient(),"CNUM"));
        verticalREF2.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getReference().getValue(),"IN"));
        verticalREF2.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getDate().getValue(),"IDATE"));
        verticalREF2.build(contentStream, writer);

        //Numero et Ref
        float posNumRefX = posSHX+10;
        float posNumRefY = posSHY+hSH+5;
        int hNumRef = (int) (posRefY-posNumRefY-5);
        BorderBox bdNumRef = new BorderBox(Color.BLACK, Color.WHITE, 1,posNumRefX, posNumRefY, wSH-10,hNumRef);
        bdNumRef.build(contentStream, writer);
        VerticalContainer verticalNumREF = new VerticalContainer(posNumRefX+5, posRefY-10, 250 );
        verticalNumREF.addElement(new SimpleTextBox(font, 9, 0, 0, model.getReference().getLabel()));
        verticalNumREF.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalNumREF.addElement(new SimpleTextBox(font, 9, 0, 0, model.getDate().getLabel()));
        verticalNumREF.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalNumREF.addElement(new SimpleTextBox(font, 9, 0, 0, model.getReference().getLabelClient()));
        verticalNumREF.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalNumREF.addElement(new SimpleTextBox(font, 9, 0, 0, model.getReference().getLabelCommand()));
        verticalNumREF.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalNumREF.addElement(new SimpleTextBox(font, 9, 0, 0, "Ref Client : "));
        verticalNumREF.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalNumREF.addElement(new SimpleTextBox(font, 9, 0, 0, "Representant :"));
        verticalNumREF.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalNumREF.addElement(new SimpleTextBox(font, 9, 0, 0, "N° Intra Comm :"));
        verticalNumREF.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalNumREF.addElement(new SimpleTextBox(font, 9, 0, 0, "N° Siret :"));
        verticalNumREF.build(contentStream, writer);

        VerticalContainer verticalNumREF2 = new VerticalContainer(verticalNumREF.getBoundingBox().getPosX()+verticalNumREF.getBoundingBox().getWidth()+15, posRefY-10, 250 );
        verticalNumREF2.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getReference().getValue(),"IN"));
        verticalNumREF2.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalNumREF2.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getDate().getValue(),"IDATE"));
        verticalNumREF2.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalNumREF2.addElement(new SimpleTextBox(font, 9, 0, 0, model.getReference().getValueClient(),"CNUM"));
        verticalNumREF2.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalNumREF2.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getReference().getValueCommand(),"ONUM"));
        verticalNumREF2.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalNumREF2.addElement(new SimpleTextBox(font, 9, 0, 0, ""));
        verticalNumREF2.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalNumREF2.addElement(new SimpleTextBox(font, 9, 0, 0, "INTERNET"));
        verticalNumREF2.build(contentStream, writer);

        //num facture ss code barre
        new SimpleTextBox(font, 9, posBarcodeX+tailleBarcode/2-model.getReference().getValue().length()*2, posBarcodeY-tailleBarcode/ratioBarcode, model.getReference().getValue(),"IN").build(contentStream,writer);

        //ligne garantie + nb page
        int posGarantieY = (int) (posBHY-3);
        new SimpleTextBox(fontBold, 7, posBHX, posBHY-3, "GARANTIE : Les étiquettes collées sur les pièces neuves sont nécessaire pour la garantie. Les emballages doivent être conservés").build(contentStream,writer);
        new SimpleTextBox(fontBold, 9, page.getMediaBox().getWidth()-70, posBHY-2, "page 1/"+document.getNumberOfPages()).build(contentStream,writer);

        float[] configRow = {70f, 210f, 28f, 45f, 35f, 50f, 40f, 40f, 20f};
        TableRowBox firstLine = new TableRowBox(configRow, 0, 0);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Code", Color.BLACK, Color.WHITE), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Designation", Color.BLACK, Color.WHITE), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Qté", Color.BLACK, Color.WHITE), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Px Unit. €", Color.BLACK, Color.WHITE), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Rem %", Color.BLACK, Color.WHITE), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Mtt base. €", Color.BLACK, Color.WHITE), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Eco-P. HT", Color.BLACK, Color.WHITE), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "Uni. Vte", Color.BLACK, Color.WHITE), false);
        firstLine.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "T", Color.BLACK, Color.WHITE), false);

        VerticalContainer verticalInvoiceItems = new VerticalContainer(25, posGarantieY-15, 600 );
        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0, page.getMediaBox().getWidth()-(5), 0));
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalInvoiceItems.addElement(firstLine);
        verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalInvoiceItems.addElement(new HorizontalLineBox(0,0, page.getMediaBox().getWidth()-(25), 0));

        /*new BorderBox(Color.BLACK,Color.BLACK, 0,firstLine.getBoundingBox().getPosX(), firstLine.getBoundingBox().getPosY()-15, 1, 20).build(contentStream,writer);
        new BorderBox(Color.BLACK,Color.BLACK, 0,firstLine.getBoundingBox().getPosX()+70f, firstLine.getBoundingBox().getPosY()-15, 1, 20).build(contentStream,writer);
        new BorderBox(Color.BLACK,Color.BLACK, 0,firstLine.getBoundingBox().getPosX()+280f, firstLine.getBoundingBox().getPosY()-15, 1, 20).build(contentStream,writer);
        new BorderBox(Color.BLACK,Color.BLACK, 0,firstLine.getBoundingBox().getPosX()+308f, firstLine.getBoundingBox().getPosY()-15, 1, 20).build(contentStream,writer);
        new BorderBox(Color.BLACK,Color.BLACK, 0,firstLine.getBoundingBox().getPosX()+353f, firstLine.getBoundingBox().getPosY()-15, 1, 20).build(contentStream,writer);
        new BorderBox(Color.BLACK,Color.BLACK, 0,firstLine.getBoundingBox().getPosX()+388f, firstLine.getBoundingBox().getPosY()-15, 1, 20).build(contentStream,writer);
        new BorderBox(Color.BLACK,Color.BLACK, 0,firstLine.getBoundingBox().getPosX()+438f, firstLine.getBoundingBox().getPosY()-15, 1, 20).build(contentStream,writer);
        new BorderBox(Color.BLACK,Color.BLACK, 0,firstLine.getBoundingBox().getPosX()+478f, firstLine.getBoundingBox().getPosY()-15, 1, 20).build(contentStream,writer);
        new BorderBox(Color.BLACK,Color.BLACK, 0,firstLine.getBoundingBox().getPosX()+518f, firstLine.getBoundingBox().getPosY()-15, 1, 20).build(contentStream,writer);
        new BorderBox(Color.BLACK,Color.BLACK, 0,firstLine.getBoundingBox().getPosX()+530f, firstLine.getBoundingBox().getPosY()-15, 1, 20).build(contentStream,writer);*/


        float reduc = 10;
        float TVA;
        String TVACode = "";
        String[][] tabTVA = new String[2][model.getProductContainer().getProducts().size()];

        for(int w=0; w< model.getProductContainer().getProducts().size(); w++) {

            Product randomProduct = model.getProductContainer().getProducts().get(w);

            TableRowBox productLine = new TableRowBox(configRow, 0, 0);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getEan(), "EAN"), false);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, randomProduct.getName().toUpperCase().toUpperCase(), "PD"), false);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, Float.toString(randomProduct.getQuantity()), "QTY"), false);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, Float.toString(randomProduct.getPriceWithoutTax()), "UP"), false);
            reduc = randomProduct.getDiscount();
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, Float.toString(reduc)), false);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, Float.toString(randomProduct.getTotalPriceWithoutTax()), "PTWTX" ), false);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, Float.toString(randomProduct.getTotalPriceWithoutTax()*(reduc/100)) ), false);
            productLine.addElement(new SimpleTextBox(font, 8, 2, 0, "PIE"), false);
            TVA = randomProduct.getTaxRate()*1000;

            switch ((int)TVA){
                case 200 :
                    TVACode = "4";
                    tabTVA[0][w] = TVACode;
                    tabTVA[1][w] = Float.toString(randomProduct.getTotalPriceWithoutTax());
                    break;
                case 100 :
                    TVACode = "3";
                    tabTVA[0][w] = TVACode;
                    tabTVA[1][w] = Float.toString(randomProduct.getTotalPriceWithoutTax());
                    break;
                case 55 :
                    TVACode ="2";
                    tabTVA[0][w] = TVACode;
                    tabTVA[1][w] = Float.toString(randomProduct.getTotalPriceWithoutTax());
                    break;

                case 21 :
                    TVACode = "1";
                    tabTVA[0][w] = TVACode;
                    tabTVA[1][w] = Float.toString(randomProduct.getTotalPriceWithoutTax());
                    break;
            }
            productLine.addElement(new SimpleTextBox(font, 8, 0, 0, TVACode), false);

            verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
            verticalInvoiceItems.addElement(productLine);
            verticalInvoiceItems.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        }

        verticalInvoiceItems.build(contentStream, writer);

        new HorizontalLineBox(25,255, page.getMediaBox().getWidth()-(25), 0).build(contentStream,writer);

        float[] configRowTVA = {30f, 42f, 90f, 90f};
        TableRowBox firstLineTVA = new TableRowBox(configRowTVA, 0, 0);
        firstLineTVA.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "T", Color.BLACK, Color.WHITE), true);
        firstLineTVA.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "% TVA", Color.BLACK, Color.WHITE), true);
        firstLineTVA.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "BASE DE CALCUL", Color.BLACK, Color.WHITE), true);
        firstLineTVA.addElement(new SimpleTextBox(fontBold, 8, 2, 0, "TOTAL", Color.BLACK, Color.WHITE), true);

        VerticalContainer verticalInvoiceTVA = new VerticalContainer(25, 250, 600 );
        verticalInvoiceTVA.addElement(new HorizontalLineBox(0,0, 250, 0));
        verticalInvoiceTVA.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalInvoiceTVA.addElement(firstLineTVA);
        verticalInvoiceTVA.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalInvoiceTVA.addElement(new HorizontalLineBox(0,0, 250, 0));

        new HorizontalLineBox(25,190, 250, 0).build(contentStream,writer);

        float totalTVA4 = 0 ;
        float totalTVA3 = 0 ;
        float totalTVA2 = 0 ;
        float totalTVA1 = 0 ;

        for(int i=0; i<tabTVA[0].length; i++) {
            switch (tabTVA[0][i]){
                case "4" :
                    totalTVA4 += Float.parseFloat(tabTVA[1][i]);
                    break;
                case "3" :
                    totalTVA2 +=  Float.parseFloat(tabTVA[1][i]);
                    break;
                case "2" :
                    totalTVA2 +=  Float.parseFloat(tabTVA[1][i]);
                    break;
                case "1" :
                    totalTVA1 +=  Float.parseFloat(tabTVA[1][i]);
                    break;
            }
        }

        TableRowBox TVALine = new TableRowBox(configRowTVA, 0, 0);

        if(totalTVA4 != 0){
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, "4"), true);
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, "20", "TXR"), true);
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, totalTVA4+"","TTX"), true);
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, (float)((int)(totalTVA4*0.2*1000))/1000 +"", ""), true);
        }
        if(totalTVA3 != 0){
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, "3"), true);
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, "10", "TXR"), true);
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, totalTVA3+"", "TTX"), true);
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, (float)((int)(totalTVA3*0.1*1000))/1000 +"", ""), true);
        }
        if(totalTVA2 != 0){
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, "2"), true);
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, "5,5", "TXR"), true);
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, totalTVA2+"", "TTX"), true);
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, (float)((int)(totalTVA2*0.055*1000))/1000 +"", ""), true);
        }
        if(totalTVA1 != 0){
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, "1"), true);
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, "2,1", "TXR"), true);
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, totalTVA1+"", "TTX"), true);
            TVALine.addElement(new SimpleTextBox(font, 8, 2, 0, (float)((int)(totalTVA1*0.021*1000))/1000 +"", ""), true);
        }
        verticalInvoiceTVA.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        verticalInvoiceTVA.addElement(TVALine);
        verticalInvoiceTVA.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));

        verticalInvoiceTVA.build(contentStream, writer);

        //TOTAUX
        float posTotauxX = 340;
        float posTotauxY = 165;
        new BorderBox(Color.BLACK, Color.WHITE, 1,posTotauxX, posTotauxY, 225,85).build(contentStream, writer);
        VerticalContainer verticalTotaux = new VerticalContainer(posTotauxX+2, posTotauxY+85-2, 250 );
        verticalTotaux.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "Montant remise"));
        verticalTotaux.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "Montant port"));
        verticalTotaux.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getProductContainer().getTotalWithoutTaxHead()));
        verticalTotaux.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "Dont eco-participation"));
        verticalTotaux.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getProductContainer().getTaxRateHead()));
        verticalTotaux.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getProductContainer().getTotalAmountHead()));
        verticalTotaux.addElement(new SimpleTextBox(fontBold, 9, 0, 0,"Votre reglèment"));
        verticalTotaux.build(contentStream, writer);

        VerticalContainer verticalTotaux2 = new VerticalContainer(posTotauxX+190, posTotauxY+85-2, 250 );
        verticalTotaux2.addElement(new SimpleTextBox(font, 9, 0, 0, "0,00"));
        verticalTotaux2.addElement(new SimpleTextBox(font, 9, 0, 0, "0,00"));
        verticalTotaux2.addElement(new SimpleTextBox(font, 9, 0, 0, (float)((int)model.getProductContainer().getTotalWithoutTax()*100)/100+"","TWTX"));
        verticalTotaux2.addElement(new SimpleTextBox(font, 9, 0, 0, "0,00"));
        verticalTotaux2.addElement(new SimpleTextBox(font, 9, 0, 0, (float)((int)model.getProductContainer().getTotalTax()*100)/100+"", "TTX"));
        verticalTotaux2.addElement(new SimpleTextBox(font, 9, 0, 0, (float)((int)model.getProductContainer().getTotalWithTax()*100)/100+"","TA"));
        verticalTotaux2.addElement(new SimpleTextBox(font, 9, 0, 0,(float)((int)model.getProductContainer().getTotalWithTax()*100)/100+"", "TA"));
        verticalTotaux2.build(contentStream, writer);

        new BorderBox(Color.BLACK, Color.WHITE, 1,posTotauxX, posTotauxY-30, 225,28).build(contentStream, writer);
        VerticalContainer verticalNet = new VerticalContainer(posTotauxX+3, posTotauxY-5, 250 );
        verticalNet.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "NET A PAYER"));
        verticalNet.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "Date limite de paiement"));
        verticalNet.build(contentStream, writer);

        VerticalContainer verticalNet2 = new VerticalContainer(posTotauxX+170, posTotauxY-5, 250 );
        verticalNet2.addElement(new SimpleTextBox(fontBold, 9, 0, 0, "0,00 "));
        verticalNet2.addElement(new SimpleTextBox(font, 9, 0, 0, model.getDate().getValue(),"IDATE"));
        verticalNet2.build(contentStream, writer);

        new SimpleTextBox(fontBold, 9, 30, 182, "Nos factures sont libellées en Euros.").build(contentStream,writer);
        new SimpleTextBox(fontBold, 9, 30, 162, "Aucun escompte pour paiment anticipé ne sera ccordé.").build(contentStream,writer);

        /*String footer = this.getClass().getClassLoader().getResource("parts/ldlc/footer.jpg").getFile();
        PDImageXObject logoFooter = PDImageXObject.createFromFile(footer, document);
        float ratioFooter = (float)logoFooter.getWidth() / (float)logoFooter.getHeight();
        float tailleFooter = 368;
        float posFooterX = 192;
        float posFooterY = 40;
        contentStream.drawImage(logoFooter, posFooterX, posFooterY, tailleFooter, tailleFooter/ratioFooter);*/

        new BorderBox(Color.BLACK, Color.WHITE, 1,25, 56, 160,80).build(contentStream, writer);
        VerticalContainer verticalPaiement = new VerticalContainer(25+3, 140-5, 250 );
        verticalPaiement.addElement(new SimpleTextBox(font, 9, 0, 0, "CONDITION DE REGLEMENT"));
        verticalPaiement.addElement(new SimpleTextBox(fontBold, 9, 0, 0, model.getPaymentInfo().getValueType()));
        new BorderBox(Color.BLACK, Color.BLACK, 1,25, 113, 160,1).build(contentStream, writer);
        verticalPaiement.build(contentStream, writer);

        String footer2 = this.getClass().getClassLoader().getResource("parts/ldlc/footer2.jpg").getFile();
        PDImageXObject logoFooter2 = PDImageXObject.createFromFile(footer2, document);
        float ratioFooter2 = (float)logoFooter2.getWidth() / (float)logoFooter2.getHeight();
        float tailleFooter2 = 155;
        float posFooter2X = 27;
        float posFooter2Y = 58;
        contentStream.drawImage(logoFooter2, posFooter2X, posFooter2Y, tailleFooter2, tailleFooter2/ratioFooter2);

        VerticalContainer infoFooter = new VerticalContainer(192,135,400);

        HorizontalContainer infoFooter1 = new HorizontalContainer(0,0);
        infoFooter1.addElement(new SimpleTextBox(font,6,0,0, model.getCompany().getName()+" ","SN"));
        infoFooter1.addElement(new SimpleTextBox(font,6,0,0, " - SA à directoire et conseil de surveillance au capital de "+(int)(100000+(Math.random()*(9999999 - 100000)))+" Eur - "));
        infoFooter1.addElement(new SimpleTextBox(font,6,0,0, idNumbers.getCidValue(),"SCID"));


        HorizontalContainer infoFooter2 = new HorizontalContainer(0,0);
        infoFooter2.addElement(new SimpleTextBox(font,6,0,0, idNumbers.getToaLabel()+" "));
        infoFooter2.addElement(new SimpleTextBox(font,6,0,0, idNumbers.getToaValue(),"STOA"));
        infoFooter2.addElement(new SimpleTextBox(font,6,0,0, " - "+idNumbers.getVatLabel()+" "));
        infoFooter2.addElement(new SimpleTextBox(font,6,0,0, idNumbers.getVatValue(),"SVAT"));

        HorizontalContainer infoFooter3 = new HorizontalContainer(0,0);
        infoFooter3.addElement(new SimpleTextBox(font,6,0,0, idNumbers.getSiretLabel()+" : "));
        infoFooter3.addElement(new SimpleTextBox(font,6,0,0,idNumbers.getSiretValue()));

        infoFooter.addElement(new SimpleTextBox(font, 6, 0, 0, "Les conditions générales de ventes (\"CGV\") appilcables a votre commande acceptées lors de son enregistrement sont"));
        infoFooter.addElement(new SimpleTextBox(font, 6, 0, 0, "également disponibles sur notre site."));
        infoFooter.addElement(new BorderBox(Color.WHITE,Color.WHITE,0,0,0,0,9));
        infoFooter.addElement(infoFooter1);
        infoFooter.addElement(infoFooter2);
        infoFooter.addElement(infoFooter3);

        infoFooter.build(contentStream,writer);

        new SimpleTextBox(fontItalic, 6, 238, 56, "Ne pas jeter sur la voie publique").build(contentStream,writer);

        contentStream.close();

    }
}
