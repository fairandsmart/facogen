package com.fairandsmart.generator.documents.element.footer;

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
import com.fairandsmart.generator.documents.element.HAlign;
import com.fairandsmart.generator.documents.element.container.HorizontalContainer;
import com.fairandsmart.generator.documents.element.container.VerticalContainer;
import com.fairandsmart.generator.documents.element.line.HorizontalLineBoxV2;
import com.fairandsmart.generator.documents.element.line.VerticalLineBox;
import com.fairandsmart.generator.documents.element.textbox.SimpleTextBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.text.DecimalFormat;

public class SumUpSalaryPayslipBox extends ElementBox {

    private static DecimalFormat df = new DecimalFormat("0.00");
    private PDFont font;
    private PDFont fontBold;
    private float fontSize;
    private PayslipModel model;
    private VerticalContainer container;
    private HorizontalContainer hcontainer;
    private PDDocument document;
    private IDNumbers idnumObj;
    private String idNames[];

    public SumUpSalaryPayslipBox(HorizontalContainer hcontainer){this.hcontainer = hcontainer;}
    public SumUpSalaryPayslipBox(VerticalContainer container){this.container = container;}

    public SumUpSalaryPayslipBox(PDFont font, PDFont fontBold, float fontSize, PayslipModel model, PDDocument document) throws Exception {
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
                getNetImposabelBlock(), getNetAPayerBlock(0)} ) );
    }

    public VerticalContainer getNetImposabelBlock() throws Exception
    {
        float[] configRow = {240f, 90f};
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer container = new HorizontalContainer(0, 0);
        SimpleTextBox emptyBox= new SimpleTextBox(PDType1Font.HELVETICA, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER);


        idContainer.addElement(new HorizontalLineBoxV2(0,0, configRow[0]+configRow[1]+15, 0));
        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getSumUpSalary().getNetImposabletLabel());
        //Label.setEntityName("NSIH");
        Label.setPadding(0, 0, 2, 0);
        Label.setWidth(configRow[0]);
        container.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, df.format(model.getSalaryTable().getNetImposabel()));
        Value.setEntityName("NSI");
        Value.setPadding(0, 0, 3, 0);
        Value.setWidth(configRow[1]);
        container.addElement(Value);

        idContainer.addElement(container);
        idContainer.addElement(emptyBox);
        idContainer.addElement(new HorizontalLineBoxV2(0,0, configRow[0]+configRow[1]+15, 0));
        idContainer.addElement(new VerticalLineBox(0,0, 0, idContainer.getBoundingBox().getHeight())); //  sumUp.getBoundingBox().getPosY()
        idContainer.addElement(new VerticalLineBox(0,0,  configRow[0], idContainer.getBoundingBox().getHeight())); //  sumUp.getBoundingBox().getPosY()
        idContainer.addElement(new VerticalLineBox(0,0,  configRow[0]+configRow[1]+15, idContainer.getBoundingBox().getHeight())); //  sumUp.getBoundingBox().getPosY()

        return idContainer;
    }

    public VerticalContainer getNetAvantImpotBlock() throws Exception
    {
        float[] configRow = {240f, 90f};
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer container = new HorizontalContainer(0, 0);
        SimpleTextBox emptyBox= new SimpleTextBox(PDType1Font.HELVETICA, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER);


        idContainer.addElement(new HorizontalLineBoxV2(0,0, configRow[0]+configRow[1]+15, 0));
        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getSumUpSalary().getNetAvantImpotLabel());
        //Label.setEntityName("NSBH");
        Label.setPadding(0, 0, 2, 0);
        Label.setWidth(configRow[0]);
        container.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, df.format(model.getSalaryTable().getNetSalary()));
        Value.setEntityName("NSB");
        Value.setPadding(0, 0, 3, 0);
        Value.setWidth(configRow[1]);
        container.addElement(Value);

        idContainer.addElement(container);
        idContainer.addElement(emptyBox);
        idContainer.addElement(new HorizontalLineBoxV2(0,0, configRow[0]+configRow[1]+15, 0));
        idContainer.addElement(new VerticalLineBox(0,0, 0, idContainer.getBoundingBox().getHeight())); //  sumUp.getBoundingBox().getPosY()
        idContainer.addElement(new VerticalLineBox(0,0,  configRow[0], idContainer.getBoundingBox().getHeight())); //  sumUp.getBoundingBox().getPosY()
      //  idContainer.addElement(new VerticalLineBox(0,0,  configRow[0]+configRow[1]+15, idContainer.getBoundingBox().getHeight())); //  sumUp.getBoundingBox().getPosY()

        return idContainer;
    }

    public VerticalContainer getNetAPayerBlock(float impot) throws Exception
    {
        float[] configRow = {240f, 90f};
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer container = new HorizontalContainer(0, 0);
        SimpleTextBox emptyBox= new SimpleTextBox(PDType1Font.HELVETICA, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER);

        idContainer.addElement(new HorizontalLineBoxV2(0,0, configRow[0]+configRow[1]+15, 0));

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getSumUpSalary().getNetApayerLabel());
        //Label.setEntityName("NSH");
        Label.setPadding(0, 0, 2, 0);
        Label.setWidth(configRow[0]);
        container.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, df.format(model.getSalaryTable().getNetSalary()-impot));
        Value.setEntityName("NS");
        Value.setPadding(0, 0, 3, 0);
        Value.setWidth(configRow[1]);
        container.addElement(Value);
        idContainer.addElement(container);
        idContainer.addElement(emptyBox);
        idContainer.addElement(new HorizontalLineBoxV2(0,0, configRow[0]+configRow[1]+15, 0));
        idContainer.addElement(new VerticalLineBox(0,0, 0, idContainer.getBoundingBox().getHeight())); //  sumUp.getBoundingBox().getPosY()
        idContainer.addElement(new VerticalLineBox(0,0,  configRow[0], idContainer.getBoundingBox().getHeight())); //  sumUp.getBoundingBox().getPosY()
       // idContainer.addElement(new VerticalLineBox(0,0,  configRow[0]+configRow[1]+15, idContainer.getBoundingBox().getHeight())); //  sumUp.getBoundingBox().getPosY()

        return idContainer;
    }


    public VerticalContainer getSumUpSalaryTable1() throws  Exception
    {
        float[] configRow = {90f, 80f, 80f, 80f};
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer titleContainer = new HorizontalContainer(0, 0);
        HorizontalContainer acquis = new HorizontalContainer(0, 0);

        idContainer.addElement(new HorizontalLineBoxV2(0,0, getNetAvantImpotBlock().getBoundingBox().getWidth()+10, 0));

        idContainer.addElement(getNetAvantImpotBlock());

        idContainer.addElement(new HorizontalLineBoxV2(0,0, getNetAvantImpotBlock().getBoundingBox().getWidth()+10, 0));

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, "Impot sur le revenu");
        Label.setPadding(0, 0, 2, 0);
        Label.setWidth(configRow[0]);
        titleContainer.addElement(Label);

        SimpleTextBox Label1 = new SimpleTextBox(font, fontSize, 0, 0, "Revenu");
        Label1.setPadding(0, 0, 2, 0);
        Label1.setWidth(configRow[1]);
        titleContainer.addElement(Label1);

        SimpleTextBox Label2 = new SimpleTextBox(font, fontSize, 0, 0, "Taux non personalisé");
        Label2.setPadding(0, 0, 2, 0);
        Label2.setWidth(configRow[2]);
        titleContainer.addElement(Label2);

        SimpleTextBox Label3 = new SimpleTextBox(font, fontSize, 0, 0, "Montant");
        Label3.setPadding(0, 0, 2, 0);
        Label3.setWidth(configRow[3]);
        titleContainer.addElement(Label3);

        idContainer.addElement(titleContainer);

        idContainer.addElement(new HorizontalLineBoxV2(0,0, getNetAvantImpotBlock().getBoundingBox().getWidth()+10, 0));

        // Ecquis
        SimpleTextBox Label4 = new SimpleTextBox(font, fontSize, 0, 0, "Impot sur le revenu prevelvé à la source");
        Label4.setPadding(0, 0, 2, 0);
        Label4.setWidth(configRow[0]);
        acquis.addElement(Label4);

        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, df.format(model.getSalaryTable().getNetImposabel()));
        Value.setEntityName("NSI");
        Value.setPadding(0, 0, 3, 0);
        Value.setWidth(configRow[1]);
        acquis.addElement(Value);

        float taux = 10+model.getRandom().nextFloat()*10;
        SimpleTextBox Value1 = new SimpleTextBox(font, fontSize, 0, 0, df.format(taux));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value1.setPadding(0, 0, 3, 0);
        Value1.setWidth(configRow[2]);
        acquis.addElement(Value1);

        float impot = model.getSalaryTable().getNetImposabel()*taux/100;
        SimpleTextBox Value2 = new SimpleTextBox(font, fontSize, 0, 0, df.format(impot));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value2.setPadding(0, 0, 3, 0);
        Value2.setWidth(configRow[3]);
        acquis.addElement(Value2);

        idContainer.addElement(acquis);

        idContainer.addElement(new HorizontalLineBoxV2(0,0, getNetAvantImpotBlock().getBoundingBox().getWidth()+10, 0));


        idContainer.addElement(getNetAPayerBlock(impot));

        idContainer.addElement(new HorizontalLineBoxV2(0,0, getNetAvantImpotBlock().getBoundingBox().getWidth()+10, 0));
        idContainer.addElement(new VerticalLineBox(0,0, 0, idContainer.getBoundingBox().getHeight())); //  sumUp.getBoundingBox().getPosY()
        idContainer.addElement(new VerticalLineBox(0,0,  getNetAvantImpotBlock().getBoundingBox().getWidth()+10, idContainer.getBoundingBox().getHeight())); //  sumUp.getBoundingBox().getPosY()

        return idContainer;
    }

    public VerticalContainer getSumUpSalaryTable2() throws  Exception
    {
        float[] configRow = {170f, 110f, 110f, 110f};
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer titleContainer = new HorizontalContainer(0, 0);
        HorizontalContainer encours = new HorizontalContainer(0, 0);
        HorizontalContainer encours1 = new HorizontalContainer(0, 0);
        HorizontalContainer acquis = new HorizontalContainer(0, 0);

        idContainer.addElement(new HorizontalLineBoxV2(0,0, 500, 0));

        idContainer.addElement(getNetAvantImpotBlock());

        idContainer.addElement(new HorizontalLineBoxV2(0,0, 500, 0));

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, "Impot sur le revenu");
        Label.setPadding(0, 0, 2, 0);
        Label.setWidth(configRow[0]);
        titleContainer.addElement(Label);

        SimpleTextBox Label1 = new SimpleTextBox(font, fontSize, 0, 0, "Revenu");
        Label1.setPadding(0, 0, 2, 0);
        Label1.setWidth(configRow[1]);
        titleContainer.addElement(Label1);

        SimpleTextBox Label2 = new SimpleTextBox(font, fontSize, 0, 0, "Taux non personalisé");
        Label2.setPadding(0, 0, 2, 0);
        Label2.setWidth(configRow[2]);
        titleContainer.addElement(Label2);

        SimpleTextBox Label3 = new SimpleTextBox(font, fontSize, 0, 0, "Montant");
        Label3.setPadding(0, 0, 2, 0);
        Label3.setWidth(configRow[3]);
        titleContainer.addElement(Label3);

        idContainer.addElement(titleContainer);

        idContainer.addElement(new HorizontalLineBoxV2(0,0, 500, 0));

        // Ecquis
        SimpleTextBox Label4 = new SimpleTextBox(font, fontSize, 0, 0, "Impot sur le revenu prevelvé à la source");
        Label4.setPadding(0, 0, 2, 0);
        Label4.setWidth(configRow[0]);
        acquis.addElement(Label4);

        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, df.format(model.getSalaryTable().getNetImposabel()));
        Value.setEntityName("NSI");
        Value.setPadding(0, 0, 3, 0);
        Value.setWidth(configRow[1]);
        acquis.addElement(Value);

        float taux = 10+model.getRandom().nextFloat()*10;
        SimpleTextBox Value1 = new SimpleTextBox(font, fontSize, 0, 0, df.format(taux));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value1.setPadding(0, 0, 3, 0);
        Value1.setWidth(configRow[2]);
        acquis.addElement(Value1);

        float impot = model.getSalaryTable().getNetImposabel()*taux/100;
        SimpleTextBox Value2 = new SimpleTextBox(font, fontSize, 0, 0, df.format(impot));
        // Value.setEntityName("S" + idName.toUpperCase());
        Value2.setPadding(0, 0, 3, 0);
        Value2.setWidth(configRow[3]);
        acquis.addElement(Value2);

        idContainer.addElement(acquis);

        idContainer.addElement(new HorizontalLineBoxV2(0,0, 500, 0));


        idContainer.addElement(getNetAPayerBlock(impot));

        idContainer.addElement(new HorizontalLineBoxV2(0,0, 500, 0));
        idContainer.addElement(new VerticalLineBox(0,0, 0, idContainer.getBoundingBox().getHeight())); //  sumUp.getBoundingBox().getPosY()
        idContainer.addElement(new VerticalLineBox(0,0,  500, idContainer.getBoundingBox().getHeight())); //  sumUp.getBoundingBox().getPosY()

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
