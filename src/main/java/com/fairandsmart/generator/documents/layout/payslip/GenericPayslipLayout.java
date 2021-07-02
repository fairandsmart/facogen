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
 * Djedjiga Belhadj <djedjiga.belhadj@gmail.com> / Loria
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

import com.fairandsmart.generator.documents.data.model.Model;
import com.fairandsmart.generator.documents.data.model.PayslipModel;
import com.fairandsmart.generator.documents.element.ElementBox;
import com.fairandsmart.generator.documents.element.HAlign;
import com.fairandsmart.generator.documents.element.container.VerticalContainer;
import com.fairandsmart.generator.documents.element.footer.SumUpSalaryPayslipBox;
import com.fairandsmart.generator.documents.element.head.*;
import com.fairandsmart.generator.documents.element.image.ImageBox;
import com.fairandsmart.generator.documents.element.salary.SalaryBox;
import com.fairandsmart.generator.documents.element.table.TableRowBox;
import com.fairandsmart.generator.documents.element.textbox.SimpleTextBox;
import com.fairandsmart.generator.documents.element.textbox.SimpleTextBoxForEvaluation;
import com.fairandsmart.generator.documents.layout.SSDLayout;
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
public class GenericPayslipLayout implements SSDLayout {
    private float fontSize = 9.5f;
    private PDFont[] fonts;
    PayslipModel model;

    // 1 available, 0 not available, -1 used
    private int LeaveInfosAvailable;
    private int employeeInfoAvailable;
    private int ciDAvailable;
    private int compContactAvailable;
    private int pos_element;

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
    public void builtSSD(Model model, PDDocument document, XMLStreamWriter writer, XMLStreamWriter writerEval) throws Exception {
        this.model = (PayslipModel)model;
        this.LeaveInfosAvailable = model.getRandom().nextInt(2);
        // sets of table row possible sizes
        float[] configRow2v1 = {360f, 160f};
        float[] configRow2v2 = {360f, 150f};
        float[] configRow1v1 = {500f};
        float[] configRow3 = {170f, 170f, 170f};
        this.pos_element =0;
        Boolean modeEval = true;
        if(writerEval== null) {
            modeEval = false;
        }
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        writer.writeStartElement("DL_PAGE");
        writer.writeAttribute("gedi_type", "DL_PAGE");
        writer.writeAttribute("pageID", "1");
        writer.writeAttribute("width", "2480");
        writer.writeAttribute("height", "3508"); // 3508
        writer.writeCharacters(System.getProperty("line.separator"));
        ///
        if(modeEval) {
            writerEval.writeStartElement("DL_PAGE");
            writerEval.writeAttribute("gedi_type", "DL_PAGE");
            writerEval.writeAttribute("pageID", "1");
            writerEval.writeCharacters(System.getProperty("line.separator"));
        }
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        this.fonts = FONTS.get(model.getRandom().nextInt(FONTS.size()));
        VerticalContainer payslipPage = new VerticalContainer(0,0,0);
        //payslip parts
        TableRowBox firstPart = null, secondPart = null, thirdPart = null, fourthPart= null, fifthPart= null;
        boolean logo = model.getRandom().nextBoolean();
        boolean titleSeparate = model.getRandom().nextBoolean();
        boolean conventionWithComp = model.getRandom().nextBoolean();
        boolean employeUnderCmp = model.getRandom().nextBoolean();
        //companyInfo
        CompanyInfoBoxPayslip companyInfoBox = new CompanyInfoBoxPayslip(fonts[2], fonts[1], fontSize, model, document);
        EmployeeInfoBox employeeInfoBox = new EmployeeInfoBox(fonts[2], fonts[1], fontSize, this.model , document);
        EmployeeInfoBox employeeAddress = new EmployeeInfoBox(employeeInfoBox.getEmployeeAddressBlock());
        ImageBox companyLogo =  companyInfoBox.getLogoBox(12, Color.WHITE); // 42
        // Employee Information Payslip
        EmployeeInfoPayslipBox employeeInfoPayslipBox = new EmployeeInfoPayslipBox(fonts[2],fonts[1], fontSize, this.model , document);
        // Leave Information
        LeaveInfoPayslipBox leaveInfoPayslipBox = new LeaveInfoPayslipBox(fonts[2],fonts[1], fontSize, this.model , document);
        // SumUp Information
        SumUpSalaryPayslipBox sumUpSalaryPayslipBox = new SumUpSalaryPayslipBox(fonts[2],fonts[1], fontSize, this.model , document);
        Boolean leaveInformationInTop = model.getRandom().nextBoolean();
        // Title and date
        SimpleTextBox title = new SimpleTextBox(fonts[1], fontSize, 0, 0, this.model .getHeadTitle());
        SimpleTextBox emptyBox= new SimpleTextBox(fonts[0], fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER);
        SimpleTextBox period = new SimpleTextBox(fonts[0], fontSize, 0, 0, this.model .getDate().getLabel() + ": " + this.model .getDate().getValue());
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
            if(modeEval) {
                pos_element++;
                new SimpleTextBoxForEvaluation("title", pos_element).build(writerEval);
            }
            firstPart.addElement(emptyBox,true);
            payslipPage.addElement(firstPart);
        }else {
            title_period.addElement(title);
        }
        CompanyInfoBoxPayslip companyAddIDCont;
        if(logo){
            companyAddIDCont = new CompanyInfoBoxPayslip(companyInfoBox.concatContainersVertically
                    (new ElementBox[]{companyLogo, companyInfoBox.getCompanyAddressBlock(), companyInfoBox.getCompanyIdBlock() }));
            if(modeEval) {
                pos_element++;
                new SimpleTextBoxForEvaluation("logo", pos_element).build(writerEval);
                pos_element++;
                new SimpleTextBoxForEvaluation("address", pos_element).build(writerEval);
                for (int i = 0; i < companyInfoBox.getIdNames().length; i++) {
                    pos_element++;
                    new SimpleTextBoxForEvaluation(companyInfoBox.getIdNames()[i], pos_element).build(writerEval);
                }
            }
        }else {
            companyAddIDCont = new CompanyInfoBoxPayslip(companyInfoBox.concatContainersVertically
                    (new ElementBox[]{companyInfoBox.getCompanyAddressBlock(), companyInfoBox.getCompanyIdBlock() }));
            if(modeEval) {
                pos_element++;
                new SimpleTextBoxForEvaluation("address", pos_element).build(writerEval);
                for (int i = 0; i < companyInfoBox.getIdNames().length; i++) {
                    pos_element++;
                    new SimpleTextBoxForEvaluation(companyInfoBox.getIdNames()[i], pos_element).build(writerEval);
                }
            }
        }
        CompanyInfoBoxPayslip companyInfoFinal;
        if(conventionWithComp){
            companyInfoFinal = new CompanyInfoBoxPayslip(companyInfoBox.concatContainersVertically
                    (new ElementBox[]{companyAddIDCont, employeeInfoPayslipBox.getConvCollectBlock() }));//emptyBox
            if(modeEval) {
                pos_element++;
                new SimpleTextBoxForEvaluation("CCN", pos_element).build(writerEval);
            }
        }else {
            companyInfoFinal = new CompanyInfoBoxPayslip(companyInfoBox.concatContainersVertically
                    (new ElementBox[]{companyAddIDCont }));//emptyBox

        }
        CompanyInfoBoxPayslip companyInfoEmplFinal;
        if(employeUnderCmp){
            EmployeeInfoPayslipBox employeeInfo;
            int ran= model.getRandom().nextInt(2);
            switch (ran){
                case 1:
                    employeeInfo  = new EmployeeInfoPayslipBox(employeeInfoPayslipBox.getEmployeeInformationTable1());
                    for(int i=0;i<employeeInfoPayslipBox.getListOptClasses().size();i++){
                        if(modeEval) {
                            pos_element++;
                            new SimpleTextBoxForEvaluation(employeeInfoPayslipBox.getListOptClasses().get(i), pos_element).build(writerEval);
                        }
                    }
                    break;
                default:
                    employeeInfo  = new EmployeeInfoPayslipBox(employeeInfoPayslipBox.getEmployeeInformationTable2());
                    for(int i=0;i<employeeInfoPayslipBox.getListOptClasses().size();i++){
                        if(modeEval) {
                            pos_element++;
                            new SimpleTextBoxForEvaluation(employeeInfoPayslipBox.getListOptClasses().get(i), pos_element).build(writerEval);
                        }
                    }
                    break;
            }
            companyInfoEmplFinal = new CompanyInfoBoxPayslip(companyInfoBox.concatContainersVertically
                    (new ElementBox[]{companyInfoFinal,emptyBox, employeeInfo }));//emptyBox
                    employeeInfoAvailable=1;
        }else {
            companyInfoEmplFinal = new CompanyInfoBoxPayslip(companyInfoBox.concatContainersVertically
                    (new ElementBox[]{companyInfoFinal}));//emptyBox
            employeeInfoAvailable=2;
        }
        if(modeEval) {
            if (!titleSeparate) {
                pos_element++;
                new SimpleTextBoxForEvaluation("title", pos_element).build(writerEval);
            }
        }
        title_period.addElement(employeeInfoPayslipBox.getPeriodBlock()); //period
        if(modeEval) {
            pos_element++;
            new SimpleTextBoxForEvaluation("Period", pos_element).build(writerEval);
        }
        title_period.addElement(employeeInfoPayslipBox.getPaymentDateBlock());
        if(modeEval) {
            pos_element++;
            new SimpleTextBoxForEvaluation("PaymentDate", pos_element).build(writerEval);
        }
        title_period.addElement(employeeInfoPayslipBox.getPaymentPeriodeDatesBlock());
        if(modeEval) {
            pos_element++;
            new SimpleTextBoxForEvaluation("PaymentPeriod", pos_element).build(writerEval);
        }
        title_period.addElement(emptyBox);
        title_period.addElement(employeeAddress);
        if(modeEval) {
            pos_element++;
            new SimpleTextBoxForEvaluation("EmpAddress", pos_element).build(writerEval);
        }
        title_period.addElement(emptyBox);
        if (leaveInformationInTop) {
            int rand= model.getRandom().nextInt(2);
            switch (rand){
                case 0:
                    title_period.addElement(new LeaveInfoPayslipBox (leaveInfoPayslipBox.getLeaveInformationTable2()));
                    if(modeEval) {
                        pos_element++;
                        new SimpleTextBoxForEvaluation("LeaveInfoTable2", pos_element).build(writerEval);
                    }
                    break;
                case 1:
                    title_period.addElement(new LeaveInfoPayslipBox (leaveInfoPayslipBox.getLeaveInformationTable1()));
                    if(modeEval) {
                        pos_element++;
                        new SimpleTextBoxForEvaluation("LeaveInfoTable1", pos_element).build(writerEval);
                    }
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
            if(modeEval) {
                for (int i = 0; i < employeeInfoPayslipBox.getListOptClasses().size(); i++) {
                    pos_element++;
                    new SimpleTextBoxForEvaluation(employeeInfoPayslipBox.getListOptClasses().get(i), pos_element).build(writerEval);
                }
            }
        }
        this.compContactAvailable = -1;
        this.ciDAvailable = -1;
        // table Third part
        thirdPart =  new TableRowBox(configRow1v1,0,0);
        SalaryBox salaryTable = new SalaryBox(0, 0, this.model .getSalaryTable(),fonts[2], fonts[1], fontSize);
        CompanyInfoBoxPayslip employeeInfotry = new CompanyInfoBoxPayslip(companyInfoBox.concatContainersVertically
                (new ElementBox[]{emptyBox, emptyBox, salaryTable }));

        thirdPart.addElement(employeeInfotry,false);
        payslipPage.addElement(thirdPart);
        if(modeEval) {
            for (int i = 0; i < salaryTable.getChosenFormatForEval().length; i++) {
                pos_element++;
                new SimpleTextBoxForEvaluation(salaryTable.getChosenFormatForEval()[i], pos_element).build(writerEval);
            }
        }
        if (LeaveInfosAvailable != -1){
        fourthPart =  new TableRowBox(configRow2v1,0,0);
            Map<Integer, ElementBox> sumUpElements = new HashMap<>();
            {
                sumUpElements.put(1, new SumUpSalaryPayslipBox(sumUpSalaryPayslipBox.getSumUpSalaryTable1())); //companyAddIDCont); iSumUpr
            }
            if(modeEval) {
                pos_element++;
                new SimpleTextBoxForEvaluation("SumUpTable1", pos_element).build(writerEval);
            }
            int rnd = model.getRandom().nextInt(4);
            switch (rnd){
                case 0 :
                    sumUpElements.put(2,new LeaveInfoPayslipBox (leaveInfoPayslipBox.getLeaveInformationTable1()));
                    if(modeEval) {
                        pos_element++;
                        new SimpleTextBoxForEvaluation("LeaveInfoTable1", pos_element).build(writerEval);
                    }
                    break;
                case 1 :
                    sumUpElements.put(2,new LeaveInfoPayslipBox (leaveInfoPayslipBox.getLeaveInformationTable2()));
                    if(modeEval) {
                        pos_element++;
                        new SimpleTextBoxForEvaluation("LeaveInfoTable2", pos_element).build(writerEval);
                    }
                    break;
                case 2 :
                    sumUpElements.put(2,new LeaveInfoPayslipBox (leaveInfoPayslipBox.getLeaveInformationTable3()));
                    if(modeEval) {
                        pos_element++;
                        new SimpleTextBoxForEvaluation("LeaveInfoTable3", pos_element).build(writerEval);
                    }
                    break;
                case 3 :
                    sumUpElements.put(2,new LeaveInfoPayslipBox (leaveInfoPayslipBox.getLeaveInformationTable4()));
                    if(modeEval) {
                        pos_element++;
                        new SimpleTextBoxForEvaluation("LeaveInfoTable4", pos_element).build(writerEval);
                    }
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
            if(modeEval) {
                pos_element++;
                new SimpleTextBoxForEvaluation("SumUpTable2", pos_element).build(writerEval);
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
            if(modeEval) {
                pos_element++;
                new SimpleTextBoxForEvaluation("CCN", pos_element).build(writerEval);
            }
        }
        payslipPage.translate(30,830);
        payslipPage.build(contentStream, writer);
        contentStream.close();
        writer.writeEndElement();
        if(modeEval) {
            writerEval.writeEndElement();
        }
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
 }
