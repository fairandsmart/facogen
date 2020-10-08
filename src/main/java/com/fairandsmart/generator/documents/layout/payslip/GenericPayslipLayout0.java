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
import com.fairandsmart.generator.documents.element.border.BorderBox;
import com.fairandsmart.generator.documents.element.container.HorizontalContainer;
import com.fairandsmart.generator.documents.element.container.VerticalContainer;
import com.fairandsmart.generator.documents.element.head.CompanyInfoBox;
import com.fairandsmart.generator.documents.element.head.EmployeeInfoBox;
import com.fairandsmart.generator.documents.element.head.EmployeeInfoPayslipBox;
import com.fairandsmart.generator.documents.element.image.ImageBox;
import com.fairandsmart.generator.documents.element.salary.SalaryBox;
import com.fairandsmart.generator.documents.element.textbox.SimpleTextBox;
import com.mifmif.common.regex.Generex;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class GenericPayslipLayout0 {
    private float fontSize = 10;
    private PDFont[] fonts;
    PayslipModel model;

    // 1 available, 0 not available, -1 used
    private int dateAvailable;
    // TODO
    private int invoiceNumAvailable;
    private int ciDAvailable;
    private int compContactAvailable;
    private int shipAddAvailable;
    private int clNumAvailable;
    private int oNumAvailable;
    private int pInfoAvailable;

    public String name() {
        return "Generic Payslip";
    }
    private static final List<PDFont[]> FONTS = new ArrayList<>();
    {
        FONTS.add(new PDFont[] {PDType1Font.HELVETICA, PDType1Font.HELVETICA_BOLD, PDType1Font.HELVETICA_OBLIQUE} );
        FONTS.add(new PDFont[] {PDType1Font.COURIER, PDType1Font.COURIER_BOLD, PDType1Font.COURIER_OBLIQUE} );
        FONTS.add(new PDFont[] {PDType1Font.TIMES_ROMAN, PDType1Font.TIMES_BOLD, PDType1Font.TIMES_ITALIC} );
    }

    public void builtPayslip(PayslipModel model, PDDocument document, XMLStreamWriter writer) throws Exception {
        this.model = model;

        this.dateAvailable = model.getRandom().nextInt(2);

        // sets of table row possible sizes
        float[] configRow2 = {255f, 255f};
        float[] configRow2v1 = {150f, 360f};
        float[] configRow2v2 = {360f, 150f};
        float[] configRow3 = {170f, 170f, 170f};

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        writer.writeStartElement("DL_PAGE");
        writer.writeAttribute("gedi_type", "DL_PAGE");
        writer.writeAttribute("pageID", "1");
        writer.writeAttribute("width", "2480");
        writer.writeAttribute("height", "3508");
        writer.writeCharacters(System.getProperty("line.separator"));
        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        float ratioPage = page.getMediaBox().getWidth()/2480;
        PDFont font = PDType1Font.HELVETICA_BOLD;
        this.fonts = FONTS.get(model.getRandom().nextInt(FONTS.size()));

        VerticalContainer payslipPage = new VerticalContainer(0,0,0);
        //payslip parts
        VerticalContainer firstPart = null,secondPart = null, thirdPart = null, fourthPart= null, fifthPart= null, sixthPart= null;

        //generation des booleens de placement
        boolean titleCenter = model.getRandom().nextBoolean();
        boolean titleDate = model.getRandom().nextBoolean();
        boolean periodMonth = model.getRandom().nextBoolean();
        boolean logo = model.getRandom().nextBoolean();

        //companyInfo
        CompanyInfoBox companyInfoBox = new CompanyInfoBox(fonts[2], fonts[1], fontSize, model, document);

        CompanyInfoBox companyAddress = new CompanyInfoBox(companyInfoBox.getCompanyAddressBlock());
        CompanyInfoBox companyContact = new CompanyInfoBox(companyInfoBox.getCompanyContactBlock());
        CompanyInfoBox companyId = new CompanyInfoBox(companyInfoBox.getCompanyIdBlock());

        EmployeeInfoBox employeeInfoBox = new EmployeeInfoBox(fonts[2], fonts[1], fontSize, model, document);
        EmployeeInfoBox employeeAddress = new EmployeeInfoBox(employeeInfoBox.getEmployeeAddressBlock());
        ImageBox companyLogo =  companyInfoBox.getLogoBox(42, Color.WHITE);

        // Employee Information Payslip
        EmployeeInfoPayslipBox employeeInfoPayslipBox = new EmployeeInfoPayslipBox(fonts[2],fonts[1], fontSize, model, document);
        EmployeeInfoPayslipBox employeeCode = new EmployeeInfoPayslipBox(employeeInfoPayslipBox.getEmployeeCodeBlock());
        EmployeeInfoPayslipBox employeeMat = new EmployeeInfoPayslipBox(employeeInfoPayslipBox.getEmployeeRegNumberBlock());
        EmployeeInfoPayslipBox employeeSSN = new EmployeeInfoPayslipBox(employeeInfoPayslipBox.getEmployeeSucurityNumberBlock());
        
        //titleCenter test
        if(true){
            firstPart = new VerticalContainer(976 * ratioPage,page.getMediaBox().getHeight()-21*ratioPage,0);

            firstPart.addElement(new SimpleTextBox(fonts[1], fontSize, 0, 0, model.getHeadTitle(), "SA"));

            if(titleDate){
                dateAvailable = -1;

                if(periodMonth){
                    firstPart.addElement(new SimpleTextBox(fonts[0], fontSize, 0, 0, model.getDate().getLabel() + ": " + model.getDate().getValue(), "SA"));
                }else {
                    firstPart.addElement(new SimpleTextBox(fonts[0], fontSize, 0, 0, model.getDate().getLabelStart() + ": " + model.getDate().getValueStart(), "SA"));
                    firstPart.addElement(new SimpleTextBox(fonts[0], fontSize, 0, 0, model.getDate().getLabelEnd() + ": " + model.getDate().getValueEnd(), "SA"));
                }
            }


            //bloc society
            secondPart = new VerticalContainer(55 * ratioPage,page.getMediaBox().getHeight()-200*ratioPage,0);


            if(logo){
                //secondPart.addElement(companyLogo);
               String img = this.getClass().getClassLoader().getResource("common/logo/" + model.getCompany().getLogo().getFullPath()).getFile();
               PDImageXObject imgLogo =  PDImageXObject.createFromFile(img, document);
               float ratioImage = (float)imgLogo.getWidth() / (float)imgLogo.getHeight();
                contentStream.drawImage(imgLogo,35,page.getMediaBox().getHeight()-100,42,ratioImage*42);
                companyLogo.build(contentStream,writer);
            }
            secondPart.addElement(companyAddress);
            secondPart.addElement(companyContact);
            secondPart.addElement(companyId);


            //bloc employee
            thirdPart = new VerticalContainer(1299*ratioPage,page.getMediaBox().getHeight()-200*ratioPage,0);
            thirdPart.addElement(employeeAddress);

            // Info employee
            fourthPart = new VerticalContainer(1000 * ratioPage,page.getMediaBox().getHeight()-400*ratioPage,0);
            fourthPart.addElement(employeeCode);
            fourthPart.addElement(employeeMat);
            fourthPart.addElement(employeeSSN);

            fifthPart = new VerticalContainer(50 * ratioPage,page.getMediaBox().getHeight()-400*ratioPage,0);
            SalaryBox salaryTable = new SalaryBox(0, 0, model.getSalaryTable(),fonts[2], fonts[1], fontSize);
            fifthPart.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 130));
            fifthPart.addElement(salaryTable);



        }else{
            boolean titleLeft = model.getRandom().nextBoolean();
            if(true){

                firstPart = new VerticalContainer(90 * ratioPage,page.getMediaBox().getHeight()-33*ratioPage,0);

                firstPart.addElement(new SimpleTextBox(fonts[1], fontSize, 0, 0, model.getHeadTitle(), "SA"));

                if(titleDate){
                    dateAvailable = -1;

                    if(periodMonth){
                        firstPart.addElement(new SimpleTextBox(fonts[0], fontSize, 0, 0, model.getDate().getLabel() + ": " + model.getDate().getValue(), "SA"));
                    }else {
                        firstPart.addElement(new SimpleTextBox(fonts[0], fontSize, 0, 0, model.getDate().getLabelStart() + ": " + model.getDate().getValueStart(), "SA"));
                        firstPart.addElement(new SimpleTextBox(fonts[0], fontSize, 0, 0, model.getDate().getLabelEnd() + ": " + model.getDate().getValueEnd(), "SA"));
                    }
                }

                //bloc society
                secondPart = new VerticalContainer(1080 * ratioPage,page.getMediaBox().getHeight()-33*ratioPage,0);
                if(true){
                    HorizontalContainer society = new HorizontalContainer(0,0);
                    companyLogo.translate(200,0);
                    society.addElement(companyLogo);
                    VerticalContainer employerInfo = new VerticalContainer(0,0,0);
                    employerInfo.addElement(companyAddress);
                    employerInfo.addElement(companyContact);
                    employerInfo.addElement(companyId);
                    society.addElement(employerInfo);
                    secondPart.addElement(society);
                }

                //bloc employee
                contentStream.setNonStrokingColor(201,103,49);
                float cursorX = 87 * ratioPage;
                float cursorY = page.getMediaBox().getHeight()-909*ratioPage;
                contentStream.fillRect(cursorX,cursorY,2376*ratioPage, 521 * ratioPage);
                contentStream.setNonStrokingColor(0, 0, 0); //black text
                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.moveTextPositionByAmount(cursorX, cursorY);
                contentStream.drawString("BLOC EMPLOYE");
                contentStream.endText();

            }else{

                firstPart = new VerticalContainer(1444 * ratioPage,page.getMediaBox().getHeight()-33*ratioPage,0);

                firstPart.addElement(new SimpleTextBox(fonts[1], fontSize, 0, 0, model.getHeadTitle(), "SA"));

                if(titleDate){
                    dateAvailable = -1;

                    if(periodMonth){
                        firstPart.addElement(new SimpleTextBox(fonts[0], fontSize, 0, 0, model.getDate().getLabel() + ": " + model.getDate().getValue(), "SA"));
                    }else {
                        firstPart.addElement(new SimpleTextBox(fonts[0], fontSize, 0, 0, model.getDate().getLabelStart() + ": " + model.getDate().getValueStart(), "SA"));
                        firstPart.addElement(new SimpleTextBox(fonts[0], fontSize, 0, 0, model.getDate().getLabelEnd() + ": " + model.getDate().getValueEnd(), "SA"));
                    }
                }


            }
            //bloc info salaire
            contentStream.setNonStrokingColor(156,163,131);
            float cursorX = 72 * ratioPage;
            float cursorY = page.getMediaBox().getHeight()-3352 *ratioPage;
            contentStream.fillRect(cursorX,cursorY,2376*ratioPage, 2440* ratioPage);
            contentStream.setNonStrokingColor(0, 0, 0); //black text
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.moveTextPositionByAmount(cursorX, cursorY);
            contentStream.drawString("BLOC INFOS SALAIRE");
            contentStream.endText();
        }

        //bas de page

        firstPart.build(contentStream,writer);
        secondPart.build(contentStream,writer);
        thirdPart.build(contentStream,writer);
        fourthPart.build(contentStream,writer);
        fifthPart.build(contentStream,writer);

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

        Collections.shuffle(list);
        return list.stream().mapToInt(i->i).toArray();
    }
}
