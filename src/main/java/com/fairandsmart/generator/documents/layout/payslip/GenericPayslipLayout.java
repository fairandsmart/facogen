package com.fairandsmart.generator.documents.layout.payslip;

import com.fairandsmart.generator.documents.data.model.PayslipModel;
import com.fairandsmart.generator.documents.element.container.HorizontalContainer;
import com.fairandsmart.generator.documents.element.container.VerticalContainer;
import com.fairandsmart.generator.documents.element.head.CompanyInfoBox;
import com.fairandsmart.generator.documents.element.head.EmployeeInfoBox;
import com.fairandsmart.generator.documents.element.image.ImageBox;
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
    private int dateAvailable;

    // 1 available, 0 not available, -1 used

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

        //generation des booleens de placement
        boolean titleCenter = model.getRandom().nextBoolean();
        boolean titleDate = model.getRandom().nextBoolean();
        boolean periodMonth = model.getRandom().nextBoolean();
        boolean logo = model.getRandom().nextBoolean();

        //payslip parts
        VerticalContainer firstPart = null,secondPart = null, thirdPart = null;

        //companyInfo
        CompanyInfoBox companyInfoBox = new CompanyInfoBox(fonts[2], fonts[1], fontSize, model, document);
        CompanyInfoBox companyAddress = new CompanyInfoBox(companyInfoBox.getCompanyAddressBlock());
        CompanyInfoBox companyContact = new CompanyInfoBox(companyInfoBox.getCompanyContactBlock());
        CompanyInfoBox companyId = new CompanyInfoBox(companyInfoBox.getCompanyIdBlock());

        EmployeeInfoBox employeeInfoBox = new EmployeeInfoBox(fonts[2], fonts[1], fontSize, model, document);
        EmployeeInfoBox employeeAddress = new EmployeeInfoBox(employeeInfoBox.getEmployeeAddressBlock());
        ImageBox companyLogo =  companyInfoBox.getLogoBox(42, Color.WHITE);


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
                contentStream.drawImage(imgLogo,55,page.getMediaBox().getHeight()-50,42,ratioImage*42);
                companyLogo.build(contentStream,writer);
            }
            secondPart.addElement(companyAddress);
            secondPart.addElement(companyContact);
            secondPart.addElement(companyId);


            //bloc employee
            thirdPart = new VerticalContainer(1299*ratioPage,page.getMediaBox().getHeight()-200*ratioPage,0);
            thirdPart.addElement(employeeAddress);


            //bloc info salaire
            contentStream.setNonStrokingColor(156,163,131);
            float cursorX = 64 * ratioPage;
            float cursorY = page.getMediaBox().getHeight()-2856*ratioPage;
            contentStream.fillRect(cursorX,cursorY,2328*ratioPage, 2004 * ratioPage);
            contentStream.setNonStrokingColor(0, 0, 0); //black text
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.moveTextPositionByAmount(cursorX, cursorY);
            contentStream.drawString("BLOC INFOS SALAIRE");
            contentStream.endText();

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
                //contentStream.setNonStrokingColor(246, 162, 29);
                float cursorX = 1444 * ratioPage;
                float cursorY = page.getMediaBox().getHeight()-380*ratioPage;
                //bloc society
                contentStream.setNonStrokingColor(155,175,181);
                cursorX = 97 * ratioPage;
                cursorY = page.getMediaBox().getHeight()-377*ratioPage;
                contentStream.fillRect(cursorX,cursorY,1329*ratioPage, 344 * ratioPage);
                contentStream.setNonStrokingColor(0, 0, 0); //black text
                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.moveTextPositionByAmount(cursorX, cursorY);
                contentStream.drawString("BLOC SOCIETE");
                contentStream.endText();

                //bloc employee
                contentStream.setNonStrokingColor(201,103,49);
                cursorX = 87 * ratioPage;
                cursorY = page.getMediaBox().getHeight()- 909*ratioPage;
                contentStream.fillRect(cursorX,cursorY,2376*ratioPage, 521 * ratioPage);
                contentStream.setNonStrokingColor(0, 0, 0); //black text
                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.moveTextPositionByAmount(cursorX, cursorY);
                contentStream.drawString("BLOC EMPLOYE");
                contentStream.endText();

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
        boolean societyInfo = model.getRandom().nextBoolean();

        if(societyInfo){
            //bloc infos supplementaires
            contentStream.setNonStrokingColor(135,121,93);
            float cursorX = 64 * ratioPage;
            float cursorY = page.getMediaBox().getHeight()-3488*ratioPage;
            contentStream.fillRect(cursorX,cursorY,2392*ratioPage, 120 * ratioPage);
            contentStream.setNonStrokingColor(0, 0, 0); //black text
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.moveTextPositionByAmount(cursorX, cursorY);
            contentStream.drawString("BLOC INFOS SOCIETE");
            contentStream.endText();
        }else{
            boolean conditions = model.getRandom().nextBoolean();
            if(conditions){
                contentStream.setNonStrokingColor(160,152,140);
                float cursorX = 64 * ratioPage;
                float cursorY = page.getMediaBox().getHeight()-3488*ratioPage;
                contentStream.fillRect(cursorX,cursorY,2392*ratioPage, 120 * ratioPage);
                contentStream.setNonStrokingColor(0, 0, 0); //black text
                contentStream.beginText();
                contentStream.setFont(font, 12);
                contentStream.moveTextPositionByAmount(cursorX, cursorY);
                contentStream.drawString("BLOC CONDITIONS");
                contentStream.endText();
            }
        }
        firstPart.build(contentStream,writer);

        secondPart.build(contentStream,writer);
        thirdPart.build(contentStream,writer);

        contentStream.close();
        writer.writeEndElement();
        /**this.invoiceNumAvailable = model.getRandom().nextInt(2);
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
         float fontSize = 10;
         int compContactAvailable = model.getRandom().nextInt(2);
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


         VerticalContainer payslipPage = new VerticalContainer(0, 0, 0);
         CompanyInfoBox companyInfoBox = new CompanyInfoBox(fonts[2], fonts[1], this.fontSize, model, document);
         ImageBox companyLogo = companyInfoBox.getLogoBox(40, Color.WHITE);
         TableRowBox firstPart = null, secondPart = null, thirdPart = null, fourthPart = null, fifthPart = null;

         SimpleTextBox payslipHeader = new SimpleTextBox(fonts[1],this.fontSize+6,0,0,getHeaderLabel(model.getLang()).toUpperCase());
         Boolean logo = model.getRandom().nextBoolean();
         if(logo) {
         firstPart = new TableRowBox(configRow3, 0, 0);
         }else{
         firstPart = new TableRowBox(configRow2,0,0);
         }
         CompanyInfoBox companyId = new CompanyInfoBox(companyInfoBox.getCompanyAdressIdBlock());
         Map<Integer, ElementBox> compElements = new HashMap<>();
         {
         compElements.put(2, payslipHeader);
         compElements.put(1, companyId);
         if(logo){ compElements.put(3, companyLogo);}
         }
         int list[] = getRandomList(compElements.size());
         for(int i =0; i < list.length; i++) {
         firstPart.addElement(compElements.get(list[i]), false);
         }
         this.compContactAvailable = -1;
         payslipPage.addElement(firstPart);

         payslipPage.translate(30,800);
         payslipPage.build(contentStream, writer);
         contentStream.close();
         writer.writeEndElement();
         */
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
