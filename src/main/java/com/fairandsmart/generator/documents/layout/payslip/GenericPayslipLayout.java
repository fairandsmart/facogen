package com.fairandsmart.generator.documents.layout.payslip;

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

import com.fairandsmart.generator.documents.data.model.PayslipModel;
import com.fairandsmart.generator.documents.element.ElementBox;
import com.fairandsmart.generator.documents.element.HAlign;
import com.fairandsmart.generator.documents.element.border.BorderBox;
import com.fairandsmart.generator.documents.element.container.HorizontalContainer;
import com.fairandsmart.generator.documents.element.container.VerticalContainer;
import com.fairandsmart.generator.documents.element.footer.SumUpSalaryPayslipBox;
import com.fairandsmart.generator.documents.element.head.*;
import com.fairandsmart.generator.documents.element.image.ImageBox;
import com.fairandsmart.generator.documents.element.line.HorizontalLineBox;
import com.fairandsmart.generator.documents.element.line.HorizontalLineBoxV2;
import com.fairandsmart.generator.documents.element.line.VerticalLineBox;
import com.fairandsmart.generator.documents.element.product.ProductBox;
import com.fairandsmart.generator.documents.element.salary.SalaryBox;
import com.fairandsmart.generator.documents.element.table.TableRowBox;
import com.fairandsmart.generator.documents.element.textbox.SimpleTextBox;
import com.fairandsmart.generator.documents.layout.PayslipLayout;
import com.mifmif.common.regex.Generex;
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
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class GenericPayslipLayout implements PayslipLayout {
    private float fontSize = 9.5f;
    private PDFont[] fonts;
    PayslipModel model;

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
        return "Generic Payslip";
    }
    private static final List<PDFont[]> FONTS = new ArrayList<>();
    {
        FONTS.add(new PDFont[] {PDType1Font.HELVETICA, PDType1Font.HELVETICA_BOLD, PDType1Font.HELVETICA_OBLIQUE} );
        FONTS.add(new PDFont[] {PDType1Font.COURIER, PDType1Font.COURIER_BOLD, PDType1Font.COURIER_OBLIQUE} );
        FONTS.add(new PDFont[] {PDType1Font.TIMES_ROMAN, PDType1Font.TIMES_BOLD, PDType1Font.TIMES_ITALIC} );
    }

    @Override
    public void builtPayslip(PayslipModel model, PDDocument document, XMLStreamWriter writer) throws Exception {
        this.model = model;
        this.LeaveInfosAvailable = model.getRandom().nextInt(2);

        // sets of table row possible sizes
        float[] configRow2 = {255f, 255f};
        float[] configRow2v1 = {360f, 160f};
        float[] configRow2v2 = {360f, 150f};
        float[] configRow1v1 = {500f};
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



        float ratioPage = page.getMediaBox().getWidth()/2480;

        PDFont font = PDType1Font.HELVETICA_BOLD;
        this.fonts = FONTS.get(model.getRandom().nextInt(FONTS.size()));

        VerticalContainer payslipPage = new VerticalContainer(0,0,0);
        //payslip parts
        TableRowBox firstPart = null,firstPart2 = null, secondPart = null, thirdPart = null, fourthPart= null, fifthPart= null, sixthPart= null;

        //generation des booleens de placement
        boolean logo = model.getRandom().nextBoolean();
        boolean titleSeparate = model.getRandom().nextBoolean();
        boolean conventionWithComp = model.getRandom().nextBoolean();
        boolean employeUnderCmp = model.getRandom().nextBoolean();

        //companyInfo
        CompanyInfoBoxPayslip companyInfoBox = new CompanyInfoBoxPayslip(fonts[2], fonts[1], fontSize, model, document);


        EmployeeInfoBox employeeInfoBox = new EmployeeInfoBox(fonts[2], fonts[1], fontSize, model, document);
        EmployeeInfoBox employeeAddress = new EmployeeInfoBox(employeeInfoBox.getEmployeeAddressBlock());
        ImageBox companyLogo =  companyInfoBox.getLogoBox(12, Color.WHITE); // 42

        // Employee Information Payslip
        EmployeeInfoPayslipBox employeeInfoPayslipBox = new EmployeeInfoPayslipBox(fonts[2],fonts[1], fontSize, model, document);

        // Leave Information
        LeaveInfoPayslipBox leaveInfoPayslipBox = new LeaveInfoPayslipBox(fonts[2],fonts[1], fontSize, model, document);
        LeaveInfoPayslipBox leaveDatePayslipBox = new LeaveInfoPayslipBox(leaveInfoPayslipBox.getLeaveDateBlock());
        LeaveInfoPayslipBox leaveAmountPayslipBox = new LeaveInfoPayslipBox(leaveInfoPayslipBox.getAMountLeaveBlock());

        // SumUp Information
        SumUpSalaryPayslipBox sumUpSalaryPayslipBox = new SumUpSalaryPayslipBox(fonts[2],fonts[1], fontSize, model, document);

        Boolean leaveInformationInTop = model.getRandom().nextBoolean();

        // Title and date
        SimpleTextBox title = new SimpleTextBox(fonts[1], fontSize, 0, 0, model.getHeadTitle());
        SimpleTextBox emptyBox= new SimpleTextBox(fonts[0], fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER);
       // VerticalContainer period = new VerticalContainer(0, 0,170);
        SimpleTextBox period = new SimpleTextBox(fonts[0], fontSize, 0, 0, model.getDate().getLabel() + ": " + model.getDate().getValue());
        VerticalContainer title_period = new VerticalContainer(0,0,0);


        Map<Integer, ElementBox> titleElements = new HashMap<>();
        {
            titleElements.put(1, title);
            titleElements.put(2, period);
            titleElements.put(3, emptyBox);
        }
        firstPart = new TableRowBox(configRow3, 0, 0);
        if(titleSeparate){
            firstPart.addElement(emptyBox,true);
            firstPart.addElement(title,true);
            firstPart.addElement(emptyBox,true);
            payslipPage.addElement(firstPart);
        }else
            title_period.addElement(title);


        CompanyInfoBoxPayslip companyAddIDCont;

        if(logo){
            companyAddIDCont = new CompanyInfoBoxPayslip(companyInfoBox.concatContainersVertically
                    (new ElementBox[]{companyLogo, companyInfoBox.getCompanyAddressBlock(), companyInfoBox.getCompanyIdBlock() }));
        }else {
            companyAddIDCont = new CompanyInfoBoxPayslip(companyInfoBox.concatContainersVertically
                    (new ElementBox[]{companyInfoBox.getCompanyAddressBlock(), companyInfoBox.getCompanyIdBlock() }));
        }

        CompanyInfoBoxPayslip companyInfoFinal;

        if(conventionWithComp){
            companyInfoFinal = new CompanyInfoBoxPayslip(companyInfoBox.concatContainersVertically
                    (new ElementBox[]{companyAddIDCont, employeeInfoPayslipBox.getConvCollectBlock() }));//emptyBox
        }else {
            companyInfoFinal = new CompanyInfoBoxPayslip(companyInfoBox.concatContainersVertically
                    (new ElementBox[]{companyAddIDCont }));//emptyBox

        }

        EmployeeInfoPayslipBox employeeInfo;
        int ran= model.getRandom().nextInt(2);
        switch (ran){
            case 1:
                employeeInfo  = new EmployeeInfoPayslipBox(employeeInfoPayslipBox.getEmployeeInformationTable1());
                break;
            default:
                employeeInfo  = new EmployeeInfoPayslipBox(employeeInfoPayslipBox.getEmployeeInformationTable2());
                break;
        }
        CompanyInfoBoxPayslip companyInfoEmplFinal;
        if(employeUnderCmp){
            companyInfoEmplFinal = new CompanyInfoBoxPayslip(companyInfoBox.concatContainersVertically
                    (new ElementBox[]{companyInfoFinal,emptyBox, employeeInfo }));//emptyBox
                    employeeInfoAvailable=1;
        }else {
            companyInfoEmplFinal = new CompanyInfoBoxPayslip(companyInfoBox.concatContainersVertically
                    (new ElementBox[]{companyInfoFinal}));//emptyBox
            employeeInfoAvailable=2;
        }

        title_period.addElement(employeeInfoPayslipBox.getPeriodBlock()); //period
        title_period.addElement(employeeInfoPayslipBox.getPaymentDateBlock());
        title_period.addElement(employeeInfoPayslipBox.getPaymentPeriodeDatesBlock());
        title_period.addElement(emptyBox);
        title_period.addElement(employeeAddress);
        title_period.addElement(emptyBox);

        if (leaveInformationInTop) {
            int rand= model.getRandom().nextInt(2);
            switch (rand){
                case 0:
                    /*title_period.addElement(leaveDatePayslipBox);
                    title_period.addElement(leaveAmountPayslipBox);*/
                    title_period.addElement(new LeaveInfoPayslipBox (leaveInfoPayslipBox.getLeaveInformationTable2()));

                    break;
                case 1:
                    title_period.addElement(new LeaveInfoPayslipBox (leaveInfoPayslipBox.getLeaveInformationTable1()));
                    break;
            }
            LeaveInfosAvailable = -1;
        }

        CompanyInfoBoxPayslip iInfor = new CompanyInfoBoxPayslip(title_period);

        Map<Integer, ElementBox> compElements = new HashMap<>();
        {
            compElements.put(1, companyInfoEmplFinal); //companyAddIDCont);
            compElements.put(2, iInfor); // title
        }
        int list[] = getRandomList(compElements.size());
        /*if(list[0]==1)
            secondPart = new TableRowBox(configRow2v1, 0, 0);
        else*/
        secondPart = new TableRowBox(configRow2v2, 0, 0);
        for(int i =0; i < compElements.size(); i++)
            secondPart.addElement(compElements.get(i+1), false);
        payslipPage.addElement(secondPart);

        if (employeeInfoAvailable==2){
            int ran2= model.getRandom().nextInt(6);
            switch (ran2){
                case 1:
                    payslipPage.addElement(employeeInfoPayslipBox.getEmployeeInformationTable3());
                    break;
                case 2:
                    payslipPage.addElement(employeeInfoPayslipBox.getEmployeeInformationTable4());
                    break;
                case 3:
                    payslipPage.addElement(employeeInfoPayslipBox.getEmployeeInformationTable5());
                    break;
                case 4:
                    payslipPage.addElement(employeeInfoPayslipBox.getEmployeeInformationTable6());
                    break;
                case 5:
                    payslipPage.addElement(employeeInfoPayslipBox.getEmployeeInformationTable8());
                    break;
                default:
                    payslipPage.addElement(employeeInfoPayslipBox.getEmployeeInformationTable7());
                    break;
            }
        }

            this.compContactAvailable = -1;
        this.ciDAvailable = -1;

        // table Third part
        thirdPart =  new TableRowBox(configRow1v1,0,0);
        SalaryBox salaryTable = new SalaryBox(0, 0, model.getSalaryTable(),fonts[2], fonts[1], fontSize);


        CompanyInfoBoxPayslip employeeInfotry = new CompanyInfoBoxPayslip(companyInfoBox.concatContainersVertically
                (new ElementBox[]{emptyBox, emptyBox, salaryTable }));

        thirdPart.addElement(employeeInfotry,false);


        payslipPage.addElement(thirdPart);



        if (LeaveInfosAvailable != -1){
        fourthPart =  new TableRowBox(configRow2v1,0,0);
            Map<Integer, ElementBox> sumUpElements = new HashMap<>();
            {
                sumUpElements.put(1, new SumUpSalaryPayslipBox(sumUpSalaryPayslipBox.getSumUpSalaryTable1())); //companyAddIDCont); iSumUpr
            }

            int rnd = model.getRandom().nextInt(4);
            switch (rnd){
                case 0 :
                    sumUpElements.put(2,new LeaveInfoPayslipBox (leaveInfoPayslipBox.getLeaveInformationTable1()));
                    break;
                case 1 :
                    sumUpElements.put(2,new LeaveInfoPayslipBox (leaveInfoPayslipBox.getLeaveInformationTable2()));
                    break;
                case 2 :
                    sumUpElements.put(2,new LeaveInfoPayslipBox (leaveInfoPayslipBox.getLeaveInformationTable3()));
                    break;
                case 3 :
                    sumUpElements.put(2,new LeaveInfoPayslipBox (leaveInfoPayslipBox.getLeaveInformationTable4()));
                    break;
            }
            for(int i =0; i < sumUpElements.size(); i++)
                fourthPart.addElement(sumUpElements.get(i+1), false);
        }else {
            fourthPart =  new TableRowBox(configRow1v1,0,0);
            Map<Integer, ElementBox> sumUpElements = new HashMap<>();
            {
                sumUpElements.put(1, new SumUpSalaryPayslipBox(sumUpSalaryPayslipBox.getSumUpSalaryTable2())); //companyAddIDCont); iSumUpr
            }
            for(int i =0; i < sumUpElements.size(); i++)
                fourthPart.addElement(sumUpElements.get(i+1), false);
        }

        payslipPage.addElement(fourthPart);

        if(!conventionWithComp){
            fifthPart =  new TableRowBox(configRow1v1,0,0);
            fifthPart.addElement(new EmployeeInfoPayslipBox(employeeInfoPayslipBox.concatContainersVertically
                    (new ElementBox[]{emptyBox,employeeInfoPayslipBox.getConvCollectBlock()})),false);
            payslipPage.addElement(fifthPart);
        }
        payslipPage.translate(30,830);
        payslipPage.build(contentStream, writer);
        contentStream.close();
        writer.writeEndElement();

    }

    private String getHeaderLabel(String lang){
        Map<String, String> headerLabels = new HashMap<>();

        {
            headerLabels.put("Payslip", "en");
            headerLabels.put("Bulletin de paie", "fr");
            headerLabels.put("Fiche de paie", "fr");
            headerLabels.put("Bulletin de salaire", "fr");
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

        java.util.Collections.shuffle(list);
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
