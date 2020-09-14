package com.fairandsmart.generator.documents.layout.payslip;

import com.fairandsmart.generator.documents.data.model.PayslipModel;
import com.fairandsmart.generator.documents.element.ElementBox;
import com.fairandsmart.generator.documents.element.HAlign;
import com.fairandsmart.generator.documents.element.border.BorderBox;
import com.fairandsmart.generator.documents.element.container.HorizontalContainer;
import com.fairandsmart.generator.documents.element.container.VerticalContainer;
import com.fairandsmart.generator.documents.element.footer.SumUpSalaryPayslipBox;
import com.fairandsmart.generator.documents.element.head.CompanyInfoBox;
import com.fairandsmart.generator.documents.element.head.EmployeeInfoBox;
import com.fairandsmart.generator.documents.element.head.EmployeeInfoPayslipBox;
import com.fairandsmart.generator.documents.element.head.LeaveInfoPayslipBox;
import com.fairandsmart.generator.documents.element.image.ImageBox;
import com.fairandsmart.generator.documents.element.line.HorizontalLineBox;
import com.fairandsmart.generator.documents.element.line.HorizontalLineBoxV2;
import com.fairandsmart.generator.documents.element.line.VerticalLineBox;
import com.fairandsmart.generator.documents.element.product.ProductBox;
import com.fairandsmart.generator.documents.element.salary.SalaryBox;
import com.fairandsmart.generator.documents.element.table.TableRowBox;
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

public class GenericPayslipLayout {
    private float fontSize = 10;
    private PDFont[] fonts;
    PayslipModel model;

    // 1 available, 0 not available, -1 used
    private int LeaveInfosAvailable;
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

        this.LeaveInfosAvailable = model.getRandom().nextInt(2);

        // sets of table row possible sizes
        float[] configRow2 = {255f, 255f};
        float[] configRow2v1 = {150f, 360f};
        float[] configRow2v2 = {360f, 150f};
        float[] configRow1v1 = {500f};
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
        TableRowBox firstPart = null,secondPart = null, thirdPart = null, fourthPart= null, fifthPart= null, sixthPart= null;

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
        ImageBox companyLogo =  companyInfoBox.getLogoBox(32, Color.WHITE); // 42

        // Employee Information Payslip
        EmployeeInfoPayslipBox employeeInfoPayslipBox = new EmployeeInfoPayslipBox(fonts[2],fonts[1], fontSize, model, document);
        EmployeeInfoPayslipBox employeeCode = new EmployeeInfoPayslipBox(employeeInfoPayslipBox.getEmployeeCodeBlock());
        EmployeeInfoPayslipBox employeeMat = new EmployeeInfoPayslipBox(employeeInfoPayslipBox.getEmployeeRegNumberBlock());
        EmployeeInfoPayslipBox employeeSSN = new EmployeeInfoPayslipBox(employeeInfoPayslipBox.getEmployeeSucurityNumberBlock());

        // Leave Information
        LeaveInfoPayslipBox leaveInfoPayslipBox = new LeaveInfoPayslipBox(fonts[2],fonts[1], fontSize, model, document);
        LeaveInfoPayslipBox leaveDatePayslipBox = new LeaveInfoPayslipBox(leaveInfoPayslipBox.getLeaveDateBlock());
        LeaveInfoPayslipBox leaveAmountPayslipBox = new LeaveInfoPayslipBox(leaveInfoPayslipBox.getAMountLeaveBlock());

        // SumUp Information
        SumUpSalaryPayslipBox sumUpSalaryPayslipBox = new SumUpSalaryPayslipBox(fonts[2],fonts[1], fontSize, model, document);

        Boolean logoIndependent = true ; // model.getRandom().nextBoolean();
        Boolean headElementsInBlock = false; //model.getRandom().nextBoolean();
        Boolean leaveInformationInTop = model.getRandom().nextBoolean();

        // Title and date
        SimpleTextBox title = new SimpleTextBox(fonts[1], fontSize, 0, 0, model.getHeadTitle(), "SA");
        SimpleTextBox emptyBox= new SimpleTextBox(fonts[0], fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER);
       // VerticalContainer period = new VerticalContainer(0, 0,170);
        SimpleTextBox period = new SimpleTextBox(fonts[0], fontSize, 0, 0, model.getDate().getLabel() + ": " + model.getDate().getValue(), "SA");



        Map<Integer, ElementBox> titleElements = new HashMap<>();
        {
            titleElements.put(1, title);
            titleElements.put(2, period);
            titleElements.put(3, emptyBox);
        }
        firstPart = new TableRowBox(configRow3, 0, 0);

        CompanyInfoBox companyAddIDCont = new CompanyInfoBox(companyInfoBox.concatContainersVertically
                (new ElementBox[]{companyLogo, companyInfoBox.getCompanyAddressBlock(), companyInfoBox.getCompanyIdBlock() }));

        CompanyInfoBox employeeInfo = new CompanyInfoBox(companyInfoBox.concatContainersVertically
                (new ElementBox[]{employeeCode, employeeMat, employeeSSN }));

        CompanyInfoBox employeeInfoFinal = new CompanyInfoBox(companyInfoBox.concatContainersVertically
                (new ElementBox[]{companyAddIDCont, emptyBox, employeeInfo }));

        VerticalContainer title_period = new VerticalContainer(0,0,0);

        title_period.addElement(title);
        title_period.addElement(period);
        title_period.addElement(emptyBox);
        title_period.addElement(employeeAddress);
        title_period.addElement(emptyBox);
        title_period.addElement(emptyBox);
        //title_period.addElement(new SimpleTextBox(fonts[1], fontSize+2, 0, 0," Congé " ));

        if (leaveInformationInTop) {
            int rand= model.getRandom().nextInt(2);
            switch (rand){
                case 0:
                    title_period.addElement(leaveDatePayslipBox);
                    title_period.addElement(leaveAmountPayslipBox);
                    break;
                case 1:
                    title_period.addElement(new LeaveInfoPayslipBox (leaveInfoPayslipBox.getLeaveInformationTable1()));
                    break;
            }
            LeaveInfosAvailable = -1;
        }

        CompanyInfoBox iInfor = new CompanyInfoBox(title_period);

        Map<Integer, ElementBox> compElements = new HashMap<>();
        {
            compElements.put(1, employeeInfoFinal); //companyAddIDCont);
            compElements.put(2, iInfor); // title
        }
        int list[] = getRandomList(compElements.size());
        /*if(list[0]==1)
            secondPart = new TableRowBox(configRow2v1, 0, 0);
        else*/
        secondPart = new TableRowBox(configRow2v2, 0, 0);
        for(int i =0; i < compElements.size(); i++)
            secondPart.addElement(compElements.get(i+1), false);
        this.compContactAvailable = -1;
        this.ciDAvailable = -1;

        // table Third part
        thirdPart =  new TableRowBox(configRow1v1,0,0);
        SalaryBox salaryTable = new SalaryBox(0, 0, model.getSalaryTable(),fonts[2], fonts[1], fontSize);


        CompanyInfoBox employeeInfotry = new CompanyInfoBox(companyInfoBox.concatContainersVertically
                (new ElementBox[]{emptyBox, emptyBox, salaryTable }));

        thirdPart.addElement(employeeInfotry,false);

        payslipPage.addElement(firstPart);
        payslipPage.addElement(secondPart);
        payslipPage.addElement(thirdPart);

        fourthPart =  new TableRowBox(configRow2v1,0,0);
        /////

       SumUpSalaryPayslipBox iSumUpr = new SumUpSalaryPayslipBox(sumUpSalaryPayslipBox.concatContainersVertically(
               new ElementBox[]{new SumUpSalaryPayslipBox(sumUpSalaryPayslipBox.getNetImposabelBlock()),
                       new SumUpSalaryPayslipBox(sumUpSalaryPayslipBox.getNetAPayerBlock())}));


        Map<Integer, ElementBox> sumUpElements = new HashMap<>();
        {
           // sumUpElements.put(1, emptyBox); // title
            sumUpElements.put(2, iSumUpr); //companyAddIDCont);
        }
        if (LeaveInfosAvailable != -1){
            sumUpElements.put(1,new LeaveInfoPayslipBox (leaveInfoPayslipBox.getLeaveInformationTable1()));
        }else {
            sumUpElements.put(1, emptyBox);
        }
        //fourthPart.addElement(emptyBox,false);
        for(int i =0; i < sumUpElements.size(); i++)
            fourthPart.addElement(sumUpElements.get(i+1), false);
        payslipPage.addElement(fourthPart);

       /* payslipPage.addElement(fifthPart);*/

        ///
        payslipPage.translate(30,785);
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
