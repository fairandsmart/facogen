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

public class SumUpSalaryPayslipBox extends ElementBox {

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
                getNetImposabelBlock(), getNetAPayerBlock()} ) );
    }

    public VerticalContainer getNetImposabelBlock() throws Exception
    {
        float[] configRow = {240f, 90f};
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer container = new HorizontalContainer(0, 0);
        SimpleTextBox emptyBox= new SimpleTextBox(PDType1Font.HELVETICA, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER);


        idContainer.addElement(new HorizontalLineBoxV2(0,0, configRow[0]+configRow[1]+15, 0));
        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getSumUpSalary().getNetImposabletLabel());
        Label.setPadding(0, 0, 2, 0);
        Label.setWidth(configRow[0]);
        container.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, Double.toString(model.getSalaryTable().getNetImposabel()));
        // Value.setEntityName("S" + idName.toUpperCase());
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

    public VerticalContainer getNetAPayerBlock() throws Exception
    {
        float[] configRow = {240f, 90f};
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        HorizontalContainer container = new HorizontalContainer(0, 0);
        SimpleTextBox emptyBox= new SimpleTextBox(PDType1Font.HELVETICA, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER);

        idContainer.addElement(new HorizontalLineBoxV2(0,0, configRow[0]+configRow[1]+15, 0));

        SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.getSumUpSalary().getNetApayerLabel());
        Label.setPadding(0, 0, 2, 0);
        Label.setWidth(configRow[0]);
        container.addElement(Label);
        SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, Double.toString(model.getSumUpSalary().getNetApayer()));
        // Value.setEntityName("S" + idName.toUpperCase());
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
