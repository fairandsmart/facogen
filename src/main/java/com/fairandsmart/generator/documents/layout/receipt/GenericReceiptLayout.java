package com.fairandsmart.generator.documents.layout.receipt;

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

import com.fairandsmart.generator.documents.data.model.ReceiptModel;
import com.fairandsmart.generator.documents.element.ElementBox;
import com.fairandsmart.generator.documents.element.HAlign;
import com.fairandsmart.generator.documents.element.border.BorderBox;
import com.fairandsmart.generator.documents.element.container.HorizontalContainer;
import com.fairandsmart.generator.documents.element.container.VerticalContainer;
import com.fairandsmart.generator.documents.element.head.CompanyInfoBox;
import com.fairandsmart.generator.documents.element.head.CompanyInfoBoxPayslip;
import com.fairandsmart.generator.documents.data.model.Address;
import com.fairandsmart.generator.documents.data.model.IDNumbers;
import com.fairandsmart.generator.documents.element.image.ImageBox;
import com.fairandsmart.generator.documents.element.product.ProductBox;
import com.fairandsmart.generator.documents.element.product.ReceiptProductBox;
import com.fairandsmart.generator.documents.element.salary.SalaryBox;
import com.fairandsmart.generator.documents.element.table.TableRowBox;
import com.fairandsmart.generator.documents.element.textbox.SimpleTextBox;
import com.fairandsmart.generator.documents.layout.ReceiptLayout;
import com.mifmif.common.regex.Generex;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.enterprise.context.ApplicationScoped;
import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class GenericReceiptLayout implements ReceiptLayout {
    private float fontSize = 9.5f;
    private PDFont[] fonts;
    ReceiptModel model;

    // 1 available, 0 not available, -1 used
    private int LeaveInfosAvailable;
    // TODO
    private int employeeInfoAvailable;
    private int ciDAvailable;
    private int compContactAvailable;
    private int shipAddAvailable;
    private int clNumAvailable;
    private int oNumAvailable;
    private int pInfoAvailable;
    private TableRowBox firstPart;

    @Override
    public String name() {
        return "Generic Receipt";
    }
    private static final List<PDFont[]> FONTS = new ArrayList<>();
    {
        FONTS.add(new PDFont[] {PDType1Font.HELVETICA, PDType1Font.HELVETICA_BOLD, PDType1Font.HELVETICA_OBLIQUE} );
        FONTS.add(new PDFont[] {PDType1Font.COURIER, PDType1Font.COURIER_BOLD, PDType1Font.COURIER_OBLIQUE} );
        FONTS.add(new PDFont[] {PDType1Font.TIMES_ROMAN, PDType1Font.TIMES_BOLD, PDType1Font.TIMES_ITALIC} );
    }

    @Override
    public void builtReceipt(ReceiptModel model, PDDocument document, XMLStreamWriter writer) throws Exception {
        this.model = model;
        this.LeaveInfosAvailable = model.getRandom().nextInt(2);

        // sets of table row possible sizes
        float[] configRow2 = {255f, 255f};
        float[] configRow2v1 = {360f, 160f};
        float[] configRow2v2 = {360f, 150f};
        float[] configRow1v1 = {300f};//500f};
        float[] configRow3 = {170f, 170f, 170f};

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        writer.writeStartElement("DL_PAGE");
        writer.writeAttribute("gedi_type", "DL_PAGE");
        writer.writeAttribute("pageID", "1");
        writer.writeAttribute("width", "2480");
        writer.writeAttribute("height", "3508"); // 3508
        writer.writeCharacters(System.getProperty("line.separator"));
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        PDFont font = PDType1Font.HELVETICA_BOLD;
        this.fonts = FONTS.get(model.getRandom().nextInt(FONTS.size()));

        float ratioPage = page.getMediaBox().getWidth()/2480;

        VerticalContainer receiptPage = new VerticalContainer(0,0,0);
        //payslip parts
        TableRowBox firstPart = null,firstPart2 = null, secondPart = null, secondPart1 = null, sumup = null, thirdPart = null, fourthPart= null, fifthPart= null, sixthPart= null;



        //companyInfo
        CompanyInfoBoxPayslip companyInfoBox = new CompanyInfoBoxPayslip(fonts[2], fonts[1], fontSize, model, document);


        ImageBox companyLogo =  companyInfoBox.getLogoBox(12, Color.WHITE); // 42


        // Title and date
        SimpleTextBox title = new SimpleTextBox(fonts[1], fontSize, 0, 0, model.getHeadTitle());//,Color.BLACK, null, HAlign.CENTER);
        SimpleTextBox companyName = new SimpleTextBox(fonts[0], fontSize, 0, 0, model.getCompany().getName().toUpperCase(), Color.BLACK, null, HAlign.CENTER, "SN");
        SimpleTextBox emptyBox= new SimpleTextBox(fonts[0], fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER);
        SimpleTextBox companyAddressline1 = new SimpleTextBox(fonts[0], fontSize, 0, 0, model.getCompany().getAddress().getLine1(), Color.BLACK, null, HAlign.CENTER, "SA");
        SimpleTextBox companyAddressline2 = new SimpleTextBox(fonts[0], fontSize, 0, 0, model.getCompany().getAddress().getZip() + " " +model.getCompany().getAddress().getCity(), Color.BLACK, null, HAlign.CENTER, "SA");
        SimpleTextBox companyAddressline3 = new SimpleTextBox(fonts[0], fontSize, 0, 0, model.getCompany().getAddress().getCountry(), Color.BLACK, null, HAlign.CENTER, "SA");

        SimpleTextBox phone = new SimpleTextBox(font, 9, 0, 0, model.getCompany().getContact().getphoneLabel()+" "+model.getCompany().getContact().getphoneValue(), Color.BLACK, null, HAlign.CENTER, "SCN");
        SimpleTextBox fax=new SimpleTextBox(font, 9, 0, 0, model.getCompany().getContact().getfaxLabel()+"  "+model.getCompany().getContact().getfaxValue(),"SFAX");
        SimpleTextBox reference=new SimpleTextBox(font, 9, 0, 0, model.getReference().getValue(),Color.BLACK, null, HAlign.CENTER,"ONUM");


        firstPart = new TableRowBox(configRow1v1, 0, 0);
        VerticalContainer a = new VerticalContainer(0,0,300f);
        a.addElement(companyName);
        a.addElement(reference);
        a.alignElements("CENTER",300f);
        HorizontalContainer b = new HorizontalContainer(0,0);
        b.addElement(a);

        firstPart.addElement(a,true);
        receiptPage.addElement(firstPart);

        secondPart = new TableRowBox(configRow1v1, 0, 0);

        VerticalContainer a1 = new VerticalContainer(0,0,300f);
        a1.addElement(companyAddressline1);
        a1.addElement(companyAddressline2);
        a1.addElement(companyAddressline3);
        a1.alignElements("CENTER",300f);
        HorizontalContainer b1 = new HorizontalContainer(0,0);
        b1.addElement(a1);

        secondPart.addElement(b1,true);
        receiptPage.addElement(secondPart);


        thirdPart = new TableRowBox(configRow1v1, 0, 0);
        VerticalContainer a2 = new VerticalContainer(0,0,150f);
        a2.addElement(phone);
        System.out.println("P " + phone.getBoundingBox().getWidth());

        VerticalContainer a3 = new VerticalContainer(0,0,150f);
        a3.addElement(fax);
        HorizontalContainer b2 = new HorizontalContainer(0,0);
        b2.addElement(a2);
        b2.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 5, 0));
        b2.addElement(a3);

        VerticalContainer a4 = new VerticalContainer(0,0,300f);
        a4.addElement(b2);
        a4.alignElements("CENTER",300f);
        thirdPart.addElement(a4, true);
        receiptPage.addElement(thirdPart);

        fourthPart = new TableRowBox(configRow1v1, 0, 0);
        title.setHalign(HAlign.CENTER);
        fourthPart.addElement(title,true);
        receiptPage.addElement(fourthPart);

        fifthPart = new TableRowBox(configRow1v1, 0, 0);
        HorizontalContainer b3 = new HorizontalContainer(0,0);
        b3.addElement(new SimpleTextBox(font, 9, 0, 0, model.getDate().getValue(),Color.BLACK, null, HAlign.CENTER, "IDATE" ));
        b3.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 5, 0));
        b3.addElement(new SimpleTextBox(font, 9, 0, 0, model.getDate().getTime(),Color.BLACK, null, HAlign.CENTER, "IDATE" ));
        VerticalContainer a5 = new VerticalContainer(0,0,300f);
        a5.addElement(b3);
        a5.alignElements("CENTER",300f);
        fifthPart.addElement(a5,true);
        receiptPage.addElement(fifthPart);

        ReceiptProductBox productTable = new ReceiptProductBox(0, 0, model.getProductReceiptContainer(),fonts[2], fonts[1], fontSize);
        receiptPage.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 10));
        receiptPage.addElement(productTable);

        ////
        sumup = new TableRowBox(configRow1v1, 0, 0);
        VerticalContainer a6 = new VerticalContainer(0,0,300f);

        HorizontalContainer totalHT = new HorizontalContainer(0,0);
        totalHT.addElement(new SimpleTextBox(font, fontSize+1, 0, 0, model.getProductReceiptContainer().getTotalWithoutTaxHead(), Color.BLACK, null, HAlign.LEFT));
        totalHT.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 5, 0));
        totalHT.addElement(new SimpleTextBox(font, fontSize, 0, 0, model.getProductReceiptContainer().getFormatedTotalWithoutTax(), Color.BLACK, null, HAlign.CENTER , "TWTX"));

        HorizontalContainer totalTax = new HorizontalContainer(0,0);
        totalTax.addElement(new SimpleTextBox(font, fontSize+1, 0, 0, model.getProductReceiptContainer().getTotalTaxHead(), Color.BLACK, null, HAlign.LEFT));
        totalTax.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 5, 0));
        totalTax.addElement(new SimpleTextBox(font, fontSize, 0, 0, model.getProductReceiptContainer().getFormatedTotalTax(), Color.BLACK, null, HAlign.CENTER , "TTX"));

        HorizontalContainer totalTTC = new HorizontalContainer(0,0);
        totalTTC.addElement(new SimpleTextBox(font, fontSize+1, 0, 0, model.getProductReceiptContainer().getTotalAmountHead(), Color.BLACK, null, HAlign.LEFT));
        totalTTC.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 5, 0));
        totalTTC.addElement(new SimpleTextBox(font, fontSize, 0, 0, model.getProductReceiptContainer().getFormatedTotalWithTax(), Color.BLACK, null, HAlign.CENTER,"TA" ));

        HorizontalContainer totalRounding = new HorizontalContainer(0,0);
        totalRounding.addElement(new SimpleTextBox(font, fontSize+1, 0, 0, model.getProductReceiptContainer().getRoundingHead(), Color.BLACK, null, HAlign.LEFT));
        totalRounding.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 5, 0));
        totalRounding.addElement(new SimpleTextBox(font, fontSize, 0, 0, model.getProductReceiptContainer().getTotalRounding(), Color.BLACK, null, HAlign.CENTER,"TA" ));

        HorizontalContainer totalRounded = new HorizontalContainer(0,0);
        totalRounded.addElement(new SimpleTextBox(font, fontSize+1, 0, 0, model.getProductReceiptContainer().getRoundedHead(), Color.BLACK, null, HAlign.LEFT));
        totalRounded.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 5, 0));
        totalRounded.addElement(new SimpleTextBox(font, fontSize, 0, 0, model.getProductReceiptContainer().getTotalRounded(), Color.BLACK, null, HAlign.CENTER,"TA" ));

        HorizontalContainer cash = new HorizontalContainer(0,0);
        cash.addElement(new SimpleTextBox(font, fontSize+1, 0, 0, model.getProductReceiptContainer().getCashHead(), Color.BLACK, null, HAlign.LEFT));
        cash.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 5, 0));
        cash.addElement(new SimpleTextBox(font, fontSize, 0, 0, model.getProductReceiptContainer().getCash(), Color.BLACK, null, HAlign.CENTER,"TA" ));

        HorizontalContainer change = new HorizontalContainer(0,0);
        change.addElement(new SimpleTextBox(font, fontSize+1, 0, 0, model.getProductReceiptContainer().getChangeHead(), Color.BLACK, null, HAlign.LEFT));
        change.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 5, 0));
        change.addElement(new SimpleTextBox(font, fontSize, 0, 0, model.getProductReceiptContainer().getChange(), Color.BLACK, null, HAlign.CENTER,"TA" ));

        a6.addElement(totalHT);
        a6.addElement(totalTax);
        a6.addElement(totalTTC);
        a6.addElement(totalRounding);
        a6.addElement(totalRounded);
        a6.addElement(cash);
        a6.addElement(change);
        a6.alignElements("RIGHT",300f);
        sumup.addElement(a6,true);
        receiptPage.addElement(sumup);

        sixthPart = new TableRowBox(configRow1v1, 0, 0);

        ////
        String footerText = "Thank You ! Please Come Again \n" +
                "Goods Sold are not Returnable \n" +
                "Dealing In Wholesale And Retail";

        receiptPage.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 100));
        sixthPart.addElement(new SimpleTextBox(font, 9, 0, 0, footerText, Color.BLACK, null, HAlign.CENTER),true);
        receiptPage.addElement(sixthPart);

        receiptPage.translate(30,830);
        receiptPage.build(contentStream, writer);
        contentStream.close();
        writer.writeEndElement();

    }

    private String getHeaderLabel(String lang){
        Map<String, String> headerLabels = new HashMap<>();

        {
            headerLabels.put("Reçu", "en");
            headerLabels.put("Reçu de paiement", "fr");
        }
        List<String> localizedHeaderLabel = headerLabels.entrySet().stream().filter(entry -> entry.getValue().equals(lang)).map(Map.Entry::getKey).collect(Collectors.toList());
        int idxvL = new Random().nextInt(localizedHeaderLabel.size());
        Generex generex2 = new Generex(localizedHeaderLabel.get(idxvL));
        String label = generex2.random();

        return label;
    }



    private int[] getRandomList(int n){

        List<Integer> list = new ArrayList<Integer>();
        for (int i= 1; i<= n; i++)
        {
            list.add(i);
        }

        Collections.shuffle(list);
        return list.stream().mapToInt(i->i).toArray();
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

    private HorizontalContainer getPTitle() throws Exception{

        HorizontalContainer iDateContainer = new HorizontalContainer(0, 0);
        SimpleTextBox dateLabel = new SimpleTextBox(fonts[1], fontSize+2, 0, 0, model.getHeadTitle());
        dateLabel.setPadding(0,0,5,0);
        iDateContainer.addElement(dateLabel);

        return  iDateContainer;
    }
}
