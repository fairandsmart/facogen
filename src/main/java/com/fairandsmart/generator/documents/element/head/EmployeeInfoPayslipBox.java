package com.fairandsmart.generator.documents.element.head;

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

import com.fairandsmart.generator.documents.data.model.IDNumbers;
import com.fairandsmart.generator.documents.data.model.Model;
import com.fairandsmart.generator.documents.data.model.PayslipModel;
import com.fairandsmart.generator.documents.element.BoundingBox;
import com.fairandsmart.generator.documents.element.ElementBox;
import com.fairandsmart.generator.documents.element.HAlign;
import com.fairandsmart.generator.documents.element.container.HorizontalContainer;
import com.fairandsmart.generator.documents.element.container.VerticalContainer;
import com.fairandsmart.generator.documents.element.image.ImageBox;
import com.fairandsmart.generator.documents.element.textbox.SimpleTextBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmployeeInfoPayslipBox extends ElementBox {

    private PDFont font;
    private PDFont fontBold;
    private float fontSize;
    private PayslipModel model;
    private VerticalContainer container;
    private HorizontalContainer hcontainer;
    private PDDocument document;
    private IDNumbers idnumObj;
    private String idNames[];
    private static final List<String[]> idNumbersOrder = new ArrayList<>();
    {
       // idNumbersOrder.add(new String[] {"Siret", "Toa", "Vat"} );

        idNumbersOrder.add(new String[] {"Cid", "Vat"} );
        idNumbersOrder.add(new String[] {"Vat"} );
        idNumbersOrder.add(new String[] {"Rcs","Vat"} );
        idNumbersOrder.add(new String[] {"Rcs", "Siret", "Vat"} );
        idNumbersOrder.add(new String[] {"Toa", "Siret", "Vat"} );
        idNumbersOrder.add(new String[] {"Toa", "Cid", "Vat"} );
        idNumbersOrder.add(new String[] {"Toa", "Rcs", "Siret", "Vat"} );
    }

    public EmployeeInfoPayslipBox(HorizontalContainer hcontainer){this.hcontainer = hcontainer;}
    public EmployeeInfoPayslipBox(VerticalContainer container){this.container = container;}

    public EmployeeInfoPayslipBox(PDFont font, PDFont fontBold, float fontSize, PayslipModel model, PDDocument document) throws Exception {
        this.font = font;
        this.fontBold = fontBold;
        this.fontSize = fontSize;
        this.model = model;
        this.document = document;
        this.container = new VerticalContainer(0, 0, 0);
        this.hcontainer = new HorizontalContainer(0, 0);
        this.idnumObj = model.getCompany().getIdNumbers();

        this.idNames = idNumbersOrder.get(model.getRandom().nextInt(idNumbersOrder.size()));
        this.init();
    }

    private void init() throws Exception
    {
        container.addElement(concatContainersVertically(new ElementBox[] {
                getEmployeeSucurityNumberBlock(), getEmployeeRegNumberBlock(), getEmployeeCodeBlock()} ) );
    }



    public VerticalContainer getEmployeeCodeBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getEmployeCodeLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getEmployeCode());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getEmployeeRegNumberBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getRegistartionNumberLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getRegistrationNumber());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getEmployeeSucurityNumberBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getSecurityNumberLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getSocialSecurityNumber());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getEmployeeDateStartBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getDateStartLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getArrivalDate());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getEmploiBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getEmploymentLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getEmployment());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getQualifBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getQualifLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getAssignment());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getReleaseDateBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getReleaseDateLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getReleaseDate());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getClassificationBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getClassificationLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getClassification());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getCoeffBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getCoeffLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getCoef());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getSocialSecurityCeilingBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getSocialSecurityCeilingLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getSocialSecurityCeiling());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getHourlyRateBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getHourlyRateLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getHourlyRate());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getDateSeniorityBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getDateSeniorityLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getDateSeniority());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getMonthlyPayRefBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getMonthlyRefPayLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getMonthlyPayRef());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getMonthlyPayBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getMonthlyPayLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getMonthlyPay());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getTimeTableBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getTimeTableLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getTimetable());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getEchelonBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getEchelonLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getEchelon());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getContratBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getContratLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getContratType());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getAssignementBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getAssignementLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getAssignment());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getCoefMinBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getMinCoeffLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getMincoef());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getPeriodBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getPaymentPeriodLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        String pattern = "MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String dateAsString = df.format(model.getEmployeeInformation().getPeriode());
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, dateAsString);
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getPaymentDateBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getPaymentDateLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getPaymentDate());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }
    public VerticalContainer getPaymentPeriodeDatesBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getPaymentPeriodDatesLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);

        Calendar cal = Calendar.getInstance();
        cal.setTime(model.getEmployeeInformation().getPeriode());
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        String pattern = "MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        String date1AsString = df.format(cal.getTime());

        SimpleTextBox Label1 = new SimpleTextBox(font, fontSize, 0, 0, "Du" );
        Label1.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label1);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, date1AsString);
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        SimpleTextBox Label2 = new SimpleTextBox(font, fontSize, 0, 0, "à" );
        Label2.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label2);

        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));

        String date2AsString = df.format(cal.getTime());
        SimpleTextBox Value1 = new SimpleTextBox(font, fontSize, 0, 0, date2AsString);
        // Value.setEntityName("S" + idName.toUpperCase());
        Value1.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value1);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getConvCollectBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getEmployeeInformation().getConv());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getEmployeeInformationTable1() throws  Exception
    {
        float[] configRow = {70f, 70f,70f};
        SimpleTextBox emptyBox= new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER);

        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer titleContainer = new HorizontalContainer(0, 0);
        HorizontalContainer ligne1 = new HorizontalContainer(0, 0);
        HorizontalContainer ligne2 = new HorizontalContainer(0, 0);
        HorizontalContainer ligne3 = new HorizontalContainer(0, 0);
        HorizontalContainer ligne4 = new HorizontalContainer(0, 0);

        ligne1.addElement(getEmployeeCodeBlock());
        emptyBox.setWidth(configRow[0]);
        ligne1.addElement(emptyBox);
        ligne1.addElement(getSocialSecurityCeilingBlock());
        idContainer.addElement(ligne1);

        ligne2.addElement(getEmploiBlock());
        emptyBox.setWidth(configRow[0]);
        ligne2.addElement(emptyBox);
        ligne2.addElement(getTimeTableBlock());
        idContainer.addElement(ligne2);

        ligne3.addElement(getQualifBlock());
        emptyBox.setWidth(configRow[0]);
        ligne3.addElement(emptyBox);
        ligne3.addElement(getHourlyRateBlock());
        idContainer.addElement(ligne3);

        ligne4.addElement(getEchelonBlock());
        emptyBox.setWidth(configRow[0]);
        ligne4.addElement(emptyBox);
        ligne4.addElement(getCoeffBlock());
        idContainer.addElement(ligne4);

        return idContainer;
    }


    public VerticalContainer concatContainersVertically(ElementBox parts[]) throws  Exception
    {   int x = 1;
        VerticalContainer result = new VerticalContainer(0,0,0);;
        for (ElementBox part: parts)
        {
            result.addElement(part);
        }
        return result;
    }

    @Override
    public BoundingBox getBoundingBox() {
        if(container!=null)
        return container.getBoundingBox();
        return hcontainer.getBoundingBox();
    }

    @Override
    public void setWidth(float width) throws Exception {
        if(container==null)
            hcontainer.getBoundingBox().setWidth(width);
        else
            container.getBoundingBox().setWidth(width);
    }

    @Override
    public void setHeight(float height) throws Exception {
        throw new Exception("Not allowed");
    }

    @Override
    public void translate(float offsetX, float offsetY) {
        if(container==null)
            hcontainer.translate(offsetX,offsetY);
        else
        container.translate(offsetX, offsetY);
    }

    @Override
    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        if(container==null)
            hcontainer.build(stream,writer);
        else
        container.build(stream, writer);
    }
}
