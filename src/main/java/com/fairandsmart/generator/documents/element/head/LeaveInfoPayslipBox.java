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
 * Djedjiga Belhadj <djedjiga.belhadj@gmail.com> / Loria
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
import com.fairandsmart.generator.documents.data.model.PayslipModel;
import com.fairandsmart.generator.documents.element.BoundingBox;
import com.fairandsmart.generator.documents.element.ElementBox;
import com.fairandsmart.generator.documents.element.container.HorizontalContainer;
import com.fairandsmart.generator.documents.element.container.VerticalContainer;
import com.fairandsmart.generator.documents.element.line.HorizontalLineBoxV2;
import com.fairandsmart.generator.documents.element.line.VerticalLineBox;
import com.fairandsmart.generator.documents.element.textbox.SimpleTextBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import javax.xml.stream.XMLStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class LeaveInfoPayslipBox extends ElementBox {

    private PDFont font;
    private PDFont fontBold;
    private float fontSize;
    private PayslipModel model;
    private VerticalContainer container;
    private HorizontalContainer hcontainer;
    private PDDocument document;
    private IDNumbers idnumObj;
    private String idNames[];

    public LeaveInfoPayslipBox(HorizontalContainer hcontainer){this.hcontainer = hcontainer;}
    public LeaveInfoPayslipBox(VerticalContainer container){this.container = container;}

    public LeaveInfoPayslipBox(PDFont font, PDFont fontBold, float fontSize, PayslipModel model, PDDocument document) throws Exception {
        this.font = font;
        this.fontBold = fontBold;
        this.fontSize = fontSize;
        this.model = model;
        this.document = document;
        this.container = new VerticalContainer(0, 0, 0);
        this.hcontainer = new HorizontalContainer(0, 0);
        this.idnumObj = model.getCompany().getIdNumbers();

        this.init();
    }

    private void init() throws Exception
    {
        container.addElement(concatContainersVertically(new ElementBox[] {
                getAMountLeaveBlock(), getLeaveDateBlock()} ) );
    }

    public VerticalContainer getLeaveDateBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getLeaveInformation().getDateLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.getLeaveInformation().getDate().toString());
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

        return idContainer;
    }

    public VerticalContainer getAMountLeaveBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getLeaveInformation().getMntLabel());
        Label.setPadding(0, 0, 2, 0);
        companyIDContainer.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, Double.toString(model.getLeaveInformation().getAmount()));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value.setPadding(0, 0, 3, 0);
        companyIDContainer.addElement(Value);

        idContainer.addElement(companyIDContainer);

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

    public VerticalContainer getLeaveInformationTable1() throws  Exception
    {
        float[] configRow = {70f, 70f};
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer titleContainer = new HorizontalContainer(0, 0);
        HorizontalContainer encours = new HorizontalContainer(0, 0);
        HorizontalContainer acquis = new HorizontalContainer(0, 0);
        HorizontalContainer pris = new HorizontalContainer(0, 0);
        HorizontalContainer solde = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, "Congés payés");
        Label.setPadding(0, 0, 2, 0);
        titleContainer.addElement(Label);
        //Label.setWidth(configRow[0]);
        idContainer.addElement(titleContainer);

        // encours
        SimpleTextBox Label0 = new SimpleTextBox(font, fontSize, 0, 0, "En cours");
        Label0.setPadding(0, 0, 2, 0);
        Label0.setWidth(configRow[0]);
        encours.addElement(Label0);
        SimpleTextBox Value0 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpN()[0]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value0.setPadding(0, 0, 3, 0);
        Value0.setWidth(configRow[1]);
        encours.addElement(Value0);
        idContainer.addElement(encours);

        // Ecquis
        SimpleTextBox Label1 = new SimpleTextBox(font, fontSize, 0, 0, model.getLeaveInformation().getAquisLabel());
        //Label1.setEntityName("LDH");
        Label1.setPadding(0, 0, 2, 0);
        Label1.setWidth(configRow[0]);
        acquis.addElement(Label1);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpNMinus1()[0]));
        Value.setEntityName("LD");
        Value.setPadding(0, 0, 3, 0);
        Value.setWidth(configRow[1]);
        acquis.addElement(Value);
        idContainer.addElement(acquis);

        // pris
        SimpleTextBox Label2 = new SimpleTextBox(font, fontSize, 0, 0, model.getLeaveInformation().getPrisLabel());
        Label2.setPadding(0, 0, 2, 0);
        Label2.setWidth(configRow[0]);
        pris.addElement(Label2);
        SimpleTextBox Value1 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpNMinus1()[1]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value1.setPadding(0, 0, 3, 0);
        Value1.setWidth(configRow[1]);
        pris.addElement(Value1);
        idContainer.addElement(pris);

        // solde
        SimpleTextBox Label3 = new SimpleTextBox(font, fontSize, 0, 0, model.getLeaveInformation().getSoldeLabel());
        Label3.setPadding(0, 0, 2, 0);
        Label3.setWidth(configRow[0]);
        solde.addElement(Label3);
        SimpleTextBox Value2 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpNMinus1()[2]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value2.setPadding(0, 0, 3, 0);
        Value2.setWidth(configRow[1]);
        solde.addElement(Value2);
        idContainer.addElement(solde);

        return idContainer;
    }

    public VerticalContainer getLeaveInformationTable2() throws  Exception
    {
        float[] configRow = {50f, 50f, 50f};
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer titleContainer = new HorizontalContainer(0, 0);
        HorizontalContainer encours = new HorizontalContainer(0, 0);
        HorizontalContainer acquis = new HorizontalContainer(0, 0);
        HorizontalContainer pris = new HorizontalContainer(0, 0);
        HorizontalContainer solde = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, " ");
        Label.setPadding(0, 0, 2, 0);
        Label.setWidth(configRow[0]);
        titleContainer.addElement(Label);


        SimpleTextBox Label1 = new SimpleTextBox(font, fontSize, 0, 0, "CP N-1");
        //Label1.setEntityName("LDH");
        Label1.setPadding(0, 0, 2, 0);
        Label1.setWidth(configRow[1]);
        titleContainer.addElement(Label1);

        SimpleTextBox Label2 = new SimpleTextBox(font, fontSize, 0, 0, "CP N");
        Label2.setPadding(0, 0, 2, 0);
        Label2.setWidth(configRow[2]);
        titleContainer.addElement(Label2);

        idContainer.addElement(titleContainer);


        // Ecquis
        SimpleTextBox Label3 = new SimpleTextBox(font, fontSize, 0, 0, model.getLeaveInformation().getAquisLabel());
        Label3.setPadding(0, 0, 2, 0);
        Label3.setWidth(configRow[0]);
        acquis.addElement(Label3);

        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpNMinus1()[0]));
        Value.setEntityName("LD");
        Value.setPadding(0, 0, 3, 0);
        Value.setWidth(configRow[1]);
        acquis.addElement(Value);

        SimpleTextBox Value1 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpN()[0]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value1.setPadding(0, 0, 3, 0);
        Value1.setWidth(configRow[2]);
        acquis.addElement(Value1);

        idContainer.addElement(acquis);

        // pris
        SimpleTextBox Label4 = new SimpleTextBox(font, fontSize, 0, 0, model.getLeaveInformation().getPrisLabel());
        Label4.setPadding(0, 0, 2, 0);
        Label4.setWidth(configRow[0]);
        pris.addElement(Label4);

        SimpleTextBox Value2 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpNMinus1()[1]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value2.setPadding(0, 0, 3, 0);
        Value2.setWidth(configRow[1]);
        pris.addElement(Value2);

        SimpleTextBox Value3 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpN()[1]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value3.setPadding(0, 0, 3, 0);
        Value3.setWidth(configRow[2]);
        pris.addElement(Value3);

        idContainer.addElement(pris);

        // solde
        SimpleTextBox Label5 = new SimpleTextBox(font, fontSize, 0, 0, model.getLeaveInformation().getSoldeLabel());
        Label5.setPadding(0, 0, 2, 0);
        Label5.setWidth(configRow[0]);
        solde.addElement(Label5);

        SimpleTextBox Value4 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpNMinus1()[2]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value4.setPadding(0, 0, 3, 0);
        Value4.setWidth(configRow[1]);
        solde.addElement(Value4);

        SimpleTextBox Value5 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpN()[2]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value5.setPadding(0, 0, 3, 0);
        Value5.setWidth(configRow[2]);
        solde.addElement(Value5);

        idContainer.addElement(solde);

        return idContainer;
    }

    public VerticalContainer getLeaveInformationTable3() throws  Exception
    {
        float[] configRow = {50f, 30f, 30f, 30f};
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer titleContainer = new HorizontalContainer(0, 0);
        HorizontalContainer encours = new HorizontalContainer(0, 0);
        HorizontalContainer acquis = new HorizontalContainer(0, 0);

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, "Congé payés");
        Label.setPadding(0, 0, 2, 0);
        Label.setWidth(configRow[0]);
        titleContainer.addElement(Label);


        SimpleTextBox Label1 = new SimpleTextBox(font, fontSize, 0, 0, model.getLeaveInformation().getAquisLabel());
        Label1.setPadding(0, 0, 2, 0);
        Label1.setWidth(configRow[1]);
        titleContainer.addElement(Label1);

        SimpleTextBox Label2 = new SimpleTextBox(font, fontSize, 0, 0, model.getLeaveInformation().getPrisLabel());
        Label2.setPadding(0, 0, 2, 0);
        Label2.setWidth(configRow[2]);
        titleContainer.addElement(Label2);

        SimpleTextBox Label3 = new SimpleTextBox(font, fontSize, 0, 0, model.getLeaveInformation().getSoldeLabel());
        Label3.setPadding(0, 0, 2, 0);
        Label3.setWidth(configRow[3]);
        titleContainer.addElement(Label3);

        idContainer.addElement(titleContainer);


        // Ecquis
        SimpleTextBox Label4 = new SimpleTextBox(font, fontSize, 0, 0, "ACQUIS");
        //Label4.setEntityName("LDH");
        Label4.setPadding(0, 0, 2, 0);
        Label4.setWidth(configRow[0]);
        acquis.addElement(Label4);

        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpNMinus1()[0]));
        Value.setEntityName("LD");
        Value.setPadding(0, 0, 3, 0);
        Value.setWidth(configRow[1]);
        acquis.addElement(Value);

        SimpleTextBox Value1 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpNMinus1()[1]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value1.setPadding(0, 0, 3, 0);
        Value1.setWidth(configRow[2]);
        acquis.addElement(Value1);

        SimpleTextBox Value2 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpNMinus1()[2]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value2.setPadding(0, 0, 3, 0);
        Value2.setWidth(configRow[3]);
        acquis.addElement(Value2);

        idContainer.addElement(acquis);

        // Encours
        SimpleTextBox Label5 = new SimpleTextBox(font, fontSize, 0, 0, "EN COURS");
        Label5.setPadding(0, 0, 2, 0);
        Label5.setWidth(configRow[0]);
        encours.addElement(Label5);

        SimpleTextBox Value3 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpN()[0]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value3.setPadding(0, 0, 3, 0);
        Value3.setWidth(configRow[1]);
        encours.addElement(Value3);

        SimpleTextBox Value4 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpN()[1]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value4.setPadding(0, 0, 3, 0);
        Value4.setWidth(configRow[2]);
        encours.addElement(Value4);

        SimpleTextBox Value5 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpN()[2]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value5.setPadding(0, 0, 3, 0);
        Value5.setWidth(configRow[3]);
        encours.addElement(Value5);

        idContainer.addElement(encours);
        return idContainer;
    }

    public VerticalContainer getLeaveInformationTable4() throws  Exception
    {
        float[] configRow = {50f, 30f, 30f, 30f};
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer titleContainer = new HorizontalContainer(0, 0);
        HorizontalContainer encours = new HorizontalContainer(0, 0);
        HorizontalContainer acquis = new HorizontalContainer(0, 0);


        idContainer.addElement(new HorizontalLineBoxV2(0,0, 155f, 0));

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, "Congé payés");
        Label.setPadding(0, 0, 2, 0);
        Label.setWidth(configRow[0]);
        titleContainer.addElement(Label);


        SimpleTextBox Label1 = new SimpleTextBox(font, fontSize, 0, 0, model.getLeaveInformation().getAquisLabel());
        //Label1.setEntityName("LDH");
        Label1.setPadding(0, 0, 2, 0);
        Label1.setWidth(configRow[1]);
        titleContainer.addElement(Label1);

        SimpleTextBox Label2 = new SimpleTextBox(font, fontSize, 0, 0, model.getLeaveInformation().getPrisLabel());
        Label2.setPadding(0, 0, 2, 0);
        Label2.setWidth(configRow[2]);
        titleContainer.addElement(Label2);

        SimpleTextBox Label3 = new SimpleTextBox(font, fontSize, 0, 0, model.getLeaveInformation().getSoldeLabel());
        Label3.setPadding(0, 0, 2, 0);
        Label3.setWidth(configRow[3]);
        titleContainer.addElement(Label3);

        idContainer.addElement(titleContainer);


        // Ecquis
        SimpleTextBox Label4 = new SimpleTextBox(font, fontSize, 0, 0, "ACQUIS");
        //Label4.setEntityName("LDH");
        Label4.setPadding(0, 0, 2, 0);
        Label4.setWidth(configRow[0]);
        acquis.addElement(Label4);

        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpNMinus1()[0]));
        Value.setEntityName("LD");
        Value.setPadding(0, 0, 3, 0);
        Value.setWidth(configRow[1]);
        acquis.addElement(Value);

        SimpleTextBox Value1 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpNMinus1()[1]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value1.setPadding(0, 0, 3, 0);
        Value1.setWidth(configRow[2]);
        acquis.addElement(Value1);

        SimpleTextBox Value2 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpNMinus1()[2]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value2.setPadding(0, 0, 3, 0);
        Value2.setWidth(configRow[3]);
        acquis.addElement(Value2);

        idContainer.addElement(acquis);

        // Encours
        SimpleTextBox Label5 = new SimpleTextBox(font, fontSize, 0, 0, "EN COURS");
        Label5.setPadding(0, 0, 2, 0);
        Label5.setWidth(configRow[0]);
        encours.addElement(Label5);

        SimpleTextBox Value3 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpN()[0]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value3.setPadding(0, 0, 3, 0);
        Value3.setWidth(configRow[1]);
        encours.addElement(Value3);

        SimpleTextBox Value4 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpN()[1]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value4.setPadding(0, 0, 3, 0);
        Value4.setWidth(configRow[2]);
        encours.addElement(Value4);

        SimpleTextBox Value5 = new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(model.getLeaveInformation().getCpN()[2]));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value5.setPadding(0, 0, 3, 0);
        Value5.setWidth(configRow[3]);
        encours.addElement(Value5);

        idContainer.addElement(encours);
        idContainer.addElement(new HorizontalLineBoxV2(0,0, 155f, 0));
        idContainer.addElement(new VerticalLineBox(0,0, 0, idContainer.getBoundingBox().getHeight())); //  sumUp.getBoundingBox().getPosY()
        idContainer.addElement(new VerticalLineBox(0,0,  155f, idContainer.getBoundingBox().getHeight())); //  sumUp.getBoundingBox().getPosY()

        return idContainer;
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
