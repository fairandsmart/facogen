package com.fairandsmart.generator.invoices.layout.ngeneric;

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
import com.fairandsmart.generator.invoices.element.ElementBox;
import com.fairandsmart.generator.invoices.element.HAlign;
import com.fairandsmart.generator.invoices.element.VAlign;
import com.fairandsmart.generator.invoices.element.border.BorderBox;
import com.fairandsmart.generator.invoices.element.container.HorizontalContainer;
import com.fairandsmart.generator.invoices.element.container.VerticalContainer;
import com.fairandsmart.generator.invoices.element.head.ClientInfoBox;
import com.fairandsmart.generator.invoices.element.head.CompanyInfoBox;
import com.fairandsmart.generator.invoices.element.image.ImageBox;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NGenericLayout implements InvoiceLayout {
    private float fontSize = 10;
    private PDFont[] fonts;
    InvoiceModel model;
    // 1 available, 0 not available, -1 used
    private int invoiceNumAvailable;
    private int iDateAvailable;
    private int ciDAvailable;
    private int compContactAvailable;
    private int shipAddAvailable;
    private int clNumAvailable;
    private int oNumAvailable;
    private int pInfoAvailable;

    private static final List<PDFont[]> FONTS = new ArrayList<>();
    {
        FONTS.add(new PDFont[] {PDType1Font.HELVETICA, PDType1Font.HELVETICA_BOLD, PDType1Font.HELVETICA_OBLIQUE} );
        FONTS.add(new PDFont[] {PDType1Font.COURIER, PDType1Font.COURIER_BOLD, PDType1Font.COURIER_OBLIQUE} );
        FONTS.add(new PDFont[] {PDType1Font.TIMES_ROMAN, PDType1Font.TIMES_BOLD, PDType1Font.TIMES_ITALIC} );
    }

    @Override
    public String name() {
        return "Generic";
    }

    @Override
    public void builtInvoice(InvoiceModel model, PDDocument document, XMLStreamWriter writer) throws Exception {

        this.model = model;
        this.invoiceNumAvailable = model.getRandom().nextInt(2);
        this.iDateAvailable = model.getRandom().nextInt(2);
        this.ciDAvailable = model.getRandom().nextInt(2);
        this.compContactAvailable = model.getRandom().nextInt(2);
        this.clNumAvailable = model.getRandom().nextInt(2);
        this.oNumAvailable = model.getRandom().nextInt(2);
        this.pInfoAvailable = model.getRandom().nextInt(2);
        float[] configRow2 = {255f, 255f};
        float[] configRow2v1 = {150f, 360f};
        float[] configRow2v2 = {360f, 150f};
        float[] configRow3 = {170f, 170f, 170f};
        this.fonts = FONTS.get(model.getRandom().nextInt(FONTS.size()));
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        writer.writeStartElement("DL_PAGE");
        writer.writeAttribute("gedi_type", "DL_PAGE");
        writer.writeAttribute("pageID", "1");
        writer.writeAttribute("width", "2480");
        writer.writeAttribute("height", "3508");
        writer.writeCharacters(System.getProperty("line.separator"));
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        // TODO : Add alignment of blocks like address,etc.

        VerticalContainer invoicePage = new VerticalContainer(0,0,0);
        CompanyInfoBox companyInfoBox = new CompanyInfoBox(fonts[2], fonts[1], fontSize, model, document);
        ImageBox companyLogo =  companyInfoBox.getLogoBox(42, Color.WHITE);
        TableRowBox firstPart = null, secondPart = null, thirdPart=null, fourthPart= null, fifthPart= null;

        Boolean logoIndependent = model.getRandom().nextBoolean();
        Boolean headElementsInBlock = model.getRandom().nextBoolean();

        if(logoIndependent)
        {
            if(headElementsInBlock) {
                firstPart = new TableRowBox(configRow3, 0, 0);
                CompanyInfoBox companyAddress = new CompanyInfoBox(companyInfoBox.getCompanyAddressBlock());
                CompanyInfoBox companyContact = new CompanyInfoBox(companyInfoBox.getCompanyContactBlock());
                Map<Integer, ElementBox> compElements = new HashMap<>();
                {
                    compElements.put(1, companyLogo);
                    compElements.put(2, companyAddress);
                    compElements.put(3, companyContact);
                }
                int list[] = getRandomList(3);
                for(int i =0; i < list.length; i++)
                firstPart.addElement(compElements.get(list[i]), false);
                this.compContactAvailable = -1;
            }
            else{

                CompanyInfoBox companyAddIDCont = new CompanyInfoBox(companyInfoBox.concatContainersVertically
                    (new ElementBox[]{companyInfoBox.getCompanyAddressLine(), companyInfoBox.getCompanyIdLine(3), companyInfoBox.getCompanyContactLine()}));

                Map<Integer, ElementBox> compElements = new HashMap<>();
                {
                    compElements.put(1, companyLogo);
                    compElements.put(2, companyAddIDCont);
                }
                int list[] = getRandomList(compElements.size());
                if(list[0]==1)
                    firstPart = new TableRowBox(configRow2v1, 0, 0);
                else
                    firstPart = new TableRowBox(configRow2v2, 0, 0);
                for(int i =0; i < list.length; i++)
                    firstPart.addElement(compElements.get(list[i]), false);
                this.compContactAvailable = -1;
                this.ciDAvailable = -1;
            }

        }
        else
        {
            if(headElementsInBlock) {

                firstPart = new TableRowBox(configRow2, 0, 0);
                CompanyInfoBox companyLogoAddCont = new CompanyInfoBox(companyInfoBox.concatContainersVertically
                        (new ElementBox[]{companyLogo, companyInfoBox.getCompanyAddressBlock(), companyInfoBox.getCompanyContactBlock()}));

                VerticalContainer iInfo= new VerticalContainer(0,0,0);
                if(invoiceNumAvailable==1)
                {
                    HorizontalContainer invoiceNum = getInvoiceNum();
                    iInfo.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 8));
                    iInfo.addElement(invoiceNum);
                    this.invoiceNumAvailable = -1;
                }
                if(iDateAvailable==1)
                {
                    HorizontalContainer iDate = getIDate();
                    iInfo.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
                    iInfo.addElement(iDate);
                    this.iDateAvailable = -1;
                }

                CompanyInfoBox iInfor = new CompanyInfoBox(iInfo);
                Map<Integer, ElementBox> compElements = new HashMap<>();
                {
                    compElements.put(1, iInfor);
                    compElements.put(2, companyLogoAddCont);
                }
                int list[] = getRandomList(compElements.size());
                for(int i =0; i < list.length; i++)
                    firstPart.addElement(compElements.get(list[i]), true);
                this.compContactAvailable = -1;
            }

            else{
                    firstPart = new TableRowBox(configRow2, 0, 0);
                    CompanyInfoBox companyLogoAddCont = new CompanyInfoBox(companyInfoBox.concatContainersVertically
                            (new ElementBox[]{companyLogo, companyInfoBox.getCompanyAddressLine(), companyInfoBox.getCompanyContactLine()}));

                    VerticalContainer iInfo= new VerticalContainer(0,0,0);
                    if(invoiceNumAvailable==1)
                    {
                        HorizontalContainer invoiceNum = getInvoiceNum();
                        iInfo.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 8));
                        iInfo.addElement(invoiceNum);
                        this.invoiceNumAvailable = -1;
                    }
                    if(iDateAvailable==1)
                    {
                        HorizontalContainer iDate = getIDate();
                        iInfo.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
                        iInfo.addElement(iDate);
                        this.iDateAvailable = -1;
                    }

                    CompanyInfoBox iInfor = new CompanyInfoBox(iInfo);
                    Map<Integer, ElementBox> compElements = new HashMap<>();
                    {
                        compElements.put(1, iInfor);
                        compElements.put(2, companyLogoAddCont);
                    }
                    int list[] = getRandomList(compElements.size());
                    boolean center_align = false;
                    for(int i =0; i < list.length; i++) {
                        if (compElements.get(list[i]) == iInfor) {
                            center_align = true;
                        }
                        firstPart.addElement(compElements.get(list[i]), center_align);
                        center_align = false;
                    }
                this.compContactAvailable = -1;
            }

        }
        invoicePage.addElement(firstPart);
        secondPart = new TableRowBox(configRow2, 0, 0, VAlign.CENTER);
       // thirdPart = new TableRowBox(configRow2, 0, 0, VAlign.CENTER);
        ClientInfoBox billingInfoBox = new ClientInfoBox(fonts[2], fonts[1], fontSize, model, document, "Billing");
        VerticalContainer clientAddress = new VerticalContainer(0,0,0);
        clientAddress.addElement(billingInfoBox);
        Boolean shipAddAvailable = true;
        int shipAddressAvail = model.getRandom().nextInt(50);
        if ( shipAddressAvail > 42 ) {
            shipAddAvailable = false;
        }

        ClientInfoBox clientInfo = new ClientInfoBox(getLeftInfo(true, true, true, true,true));
        Boolean shipBillhoriz = model.getRandom().nextBoolean();
        if(shipAddAvailable && shipBillhoriz ){

            ClientInfoBox shippingInfoBox = new ClientInfoBox(fonts[2], fonts[1], fontSize, model, document, "Shipping");
            ClientInfoBox clientOtherInfo = new ClientInfoBox(getLeftInfo(false, false, true, true,true));
            shipAddAvailable = false;

            Map<Integer, ElementBox> clientElements = new HashMap<>();
            {
                clientElements.put(1, billingInfoBox);
                clientElements.put(2, shippingInfoBox);
            }
            int list[] = getRandomList(clientElements.size());
            for (int i = 0; i < list.length; i++)
                secondPart.addElement(clientElements.get(list[i]), true);

            thirdPart = new TableRowBox(configRow2, 0, 0, VAlign.CENTER);
            Map<Integer, ElementBox> clientInfos = new HashMap<>();
            {
                clientInfos.put(1, clientInfo);
                clientInfos.put(2, clientOtherInfo);
            }
            int list2[] = getRandomList(clientInfos.size());
            for (int i = 0; i < list2.length; i++)
                thirdPart.addElement(clientInfos.get(list2[i]), true);

            }
        else{

            if(shipAddAvailable && !shipBillhoriz) {
                ClientInfoBox shippingInfoBox = new ClientInfoBox(fonts[2], fonts[1], fontSize, model, document, "Shipping");
                clientAddress.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 10));
                clientAddress.addElement(shippingInfoBox);
            }

            ClientInfoBox clientFullAddress = new ClientInfoBox(clientAddress);
            Map<Integer, ElementBox> clientElements = new HashMap<>();
            {
                clientElements.put(1, clientFullAddress);
                clientElements.put(2, clientInfo);
            }

            int list[] = getRandomList(clientElements.size());
            for (int i = 0; i < list.length; i++)
                secondPart.addElement(clientElements.get(list[i]), true);

            }


        invoicePage.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 15));
        invoicePage.addElement(secondPart);
        if(thirdPart!=null)
        {
            invoicePage.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 50));
            invoicePage.addElement(thirdPart);
        }


        ProductBox productTable = new ProductBox(0, 0, model.getProductContainer(),fonts[2], fonts[1], fontSize);
        invoicePage.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 130));
        invoicePage.addElement(productTable);

        ClientInfoBox randomClientInfo = new ClientInfoBox(getLeftInfo(false, false, false, false, true));
        if(randomClientInfo.getBoundingBox().getWidth()!=0.0){
            fourthPart = new TableRowBox(configRow2, 0, 0, VAlign.CENTER);
            SimpleTextBox emptyBox= new SimpleTextBox(fonts[0], fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER);
            Map<Integer, ElementBox> payInfos = new HashMap<>();
            {
                payInfos.put(1, randomClientInfo);
                payInfos.put(2, emptyBox);
            }
            int list[] = getRandomList(payInfos.size());
            for (int i = 0; i < list.length; i++)
                fourthPart.addElement(payInfos.get(list[i]), true);
            invoicePage.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 40));
            invoicePage.addElement(fourthPart);
        }

        invoicePage.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 700-invoicePage.getBoundingBox().getHeight()));
        boolean footerLine = model.getRandom().nextBoolean();
        boolean footerInLineForm = model.getRandom().nextBoolean();
//        footerLine=true;
//        footerInLineForm=false;
        if(footerLine)
        {
            invoicePage.addElement(new HorizontalLineBox(0,0, 30+invoicePage.getBoundingBox().getWidth(), 0));
            invoicePage.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        }

        if(footerInLineForm) {
            String[] alignmentOptions = {"LEFT", "CENTER", "RIGHT"};
            List<ElementBox> compInfo = new ArrayList<ElementBox>();
            compInfo.add(companyInfoBox.getCompanyNameLine());
            compInfo.add(companyInfoBox.getCompanyAddLine());
            compInfo.add(companyInfoBox.getCompanyIdLine(0));
            if(compContactAvailable!=-1)
            {
               compInfo.add(companyInfoBox.getCompanyContactLine());
            }
            VerticalContainer companyFoot = companyInfoBox.concatContainersVertically(compInfo.toArray(new ElementBox[compInfo.size()]));
            companyFoot.alignElements(alignmentOptions[model.getRandom().nextInt(alignmentOptions.length)], invoicePage.getBoundingBox().getWidth());
            CompanyInfoBox companyAddIDCont = new CompanyInfoBox(companyFoot);
            invoicePage.addElement(companyAddIDCont);

        }
        else{
            fifthPart = new TableRowBox(configRow2, 0, 0);
            CompanyInfoBox companyAddress = new CompanyInfoBox(companyInfoBox.getCompanyAddressBlock());
            List<ElementBox> compIDBox = new ArrayList<ElementBox>();
            compIDBox.add(companyInfoBox.getCompanyIdBlock());
            if(compContactAvailable!=-1)
            {
                compIDBox.add(companyInfoBox.getCompanyContactBlock());
            }
            VerticalContainer companyIDBlock = companyInfoBox.concatContainersVertically(compIDBox.toArray(new ElementBox[compIDBox.size()]));
            CompanyInfoBox companyIDs = new CompanyInfoBox(companyIDBlock);
            Map<Integer, ElementBox> compElements = new HashMap<>();
            {
                compElements.put(1, companyAddress);
                compElements.put(2, companyIDs);
            }


            int list[] = getRandomList(compElements.size());
            for(int i =0; i < list.length; i++)
                fifthPart.addElement(compElements.get(list[i]), false);
            invoicePage.addElement(fifthPart);
        }


        invoicePage.translate(30,785);
        invoicePage.build(contentStream, writer);
        contentStream.close();
        writer.writeEndElement();

    }

    private int[] getRandomList(int n){

        List<Integer> list = new ArrayList<Integer>();
        for (int i= 1; i<= n; i++)
        {
            list.add(i);
        }

        java.util.Collections.shuffle(list);
        return list.stream().mapToInt(i->i).toArray();
    }

    private HorizontalContainer getInvoiceNum() throws Exception{

        HorizontalContainer iNumContainer = new HorizontalContainer(0, 0);
        SimpleTextBox iNumLabel = new SimpleTextBox(fonts[1], fontSize+2, 0, 0, model.getReference().getLabel());
        iNumLabel.setPadding(0,0,5,0);
        iNumContainer.addElement(iNumLabel);
        SimpleTextBox iNumValue = new SimpleTextBox(fonts[2], fontSize+1, 0, 0, model.getReference().getValue(), "IN");
        iNumValue.setPadding(5,0,0,0);
        iNumContainer.addElement(iNumValue);
        return iNumContainer;
    }

    private HorizontalContainer getIDate() throws Exception{

        HorizontalContainer iDateContainer = new HorizontalContainer(0, 0);
        SimpleTextBox dateLabel = new SimpleTextBox(fonts[1], fontSize+2, 0, 0, model.getDate().getLabel());
        dateLabel.setPadding(0,0,5,0);
        iDateContainer.addElement(dateLabel);
        SimpleTextBox dateValue = new SimpleTextBox(fonts[2], fontSize+1, 0, 0, model.getDate().getValue(), "IDATE");
        dateValue.setPadding(5,0,0,0);
        iDateContainer.addElement(dateValue);
        return  iDateContainer;
    }

    private VerticalContainer getLeftInfo(Boolean iNumNeeded, Boolean iDateNeeded, Boolean clNumDesired, Boolean oNumDesired, Boolean pInfoDesired) throws Exception{

        VerticalContainer invoiceInfo = new VerticalContainer(0, 0, 0);
        if(iNumNeeded && this.invoiceNumAvailable!=-1)
        {
            invoiceInfo.addElement(getInfoAsLabelValue(model.getReference().getLabel(), model.getReference().getValue(), "IN"));
            invoiceInfo.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
            this.invoiceNumAvailable = -1;
        }

        if(iDateNeeded && this.iDateAvailable!=-1)
        {
            invoiceInfo.addElement(getInfoAsLabelValue(model.getDate().getLabel(), model.getDate().getValue(), "IDATE"));
            invoiceInfo.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
            this.iDateAvailable= -1;
        }

        if(clNumDesired && this.clNumAvailable==1)
        {
            invoiceInfo.addElement(getInfoAsLabelValue(model.getReference().getLabelClient(), model.getReference().getValueClient(), "CNUM"));
            invoiceInfo.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
            this.clNumAvailable = -1;
        }

        if(oNumDesired && this.oNumAvailable==1)
        {
            invoiceInfo.addElement(getInfoAsLabelValue(model.getReference().getLabelCommand(), model.getReference().getValueCommand(), "ONUM"));
            invoiceInfo.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
            this.oNumAvailable = -1;
        }

        if(pInfoDesired && this.pInfoAvailable==1)
        {
            invoiceInfo.addElement(getInfoAsLabelValue(model.getPaymentInfo().getLabelType(), model.getPaymentInfo().getValueType(), "PMODE"));
            invoiceInfo.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
            this.pInfoAvailable = -1;
        }

        if(this.clNumAvailable!=-1){ this.clNumAvailable = model.getRandom().nextInt(2);}
        if(this.oNumAvailable!=-1){ this.oNumAvailable = model.getRandom().nextInt(2);}
        if(this.pInfoAvailable!=-1){ this.pInfoAvailable = model.getRandom().nextInt(2);}
        return invoiceInfo;
    }

    private TableRowBox getInfoAsLabelValue(String label, String value, String entityClass) throws  Exception{
        float[] configRow = {150f, 200f};
        TableRowBox elementInfoContainer = new TableRowBox(configRow, 0, 0);
        SimpleTextBox Label = new SimpleTextBox(fonts[1], fontSize+1, 0,0, label,Color.BLACK, null, HAlign.LEFT);
        elementInfoContainer.addElement(Label, false);
        SimpleTextBox Value = new SimpleTextBox(fonts[0], fontSize, 0,0, value, entityClass);
        elementInfoContainer.addElement(Value, false);
        return  elementInfoContainer;
    }


}
