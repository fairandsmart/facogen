package com.fairandsmart.generator.documents.element.salary;

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

import com.fairandsmart.generator.documents.data.model.Product;
import com.fairandsmart.generator.documents.data.model.ProductContainer;
import com.fairandsmart.generator.documents.data.model.SalaryCotisationTable;
import com.fairandsmart.generator.documents.data.model.SalaryLine;
import com.fairandsmart.generator.documents.element.BoundingBox;
import com.fairandsmart.generator.documents.element.ElementBox;
import com.fairandsmart.generator.documents.element.HAlign;
import com.fairandsmart.generator.documents.element.VAlign;
import com.fairandsmart.generator.documents.element.border.BorderBox;
import com.fairandsmart.generator.documents.element.container.VerticalContainer;
import com.fairandsmart.generator.documents.element.line.HorizontalLineBox;
import com.fairandsmart.generator.documents.element.table.TableRowBox;
import com.fairandsmart.generator.documents.element.textbox.SimpleTextBox;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.logging.Logger;
import java.text.DecimalFormat;


public class SalaryBox extends ElementBox {

    private static DecimalFormat df = new DecimalFormat("0.00");
    private static final Logger LOGGER = Logger.getLogger(VerticalContainer.class.getName());
    private PDFont font;
    private PDFont fontBold;
    private float fontSize;
    private Color headBackgroundColor;
    private Color bodyBackgroundColor;
    private VerticalContainer container;
    private SalaryCotisationTable salaryContainer;
    private String [] chosenFormatForEval;
    private static Random rnd = new Random();

    private static final List<String[]> tableFormat = new ArrayList<>();
    {
        tableFormat.clear();
        tableFormat.add(new String[] {"Rubrique", "Base", "TxSalarial", "CtSalarial", "TxPatrnl", "CotPatrnl"} );
        tableFormat.add(new String[] {"Rubrique", "Base", "TxSalarial", "CtSalarial", "CotPatrnl"} );
        tableFormat.add(new String[] {"Element", "Rubrique", "Base", "TxSalarial", "CtSalarial", "TxPatrnl", "CotPatrnl"} );
    }

    private static final List<float[]> tableConfig = new ArrayList<>();
    {
        tableConfig.add(new float[] {190f, 40f, 70f, 70f, 70f, 70f} );
        tableConfig.add(new float[] {190f,  90f, 90f, 70f, 70f} );
        tableConfig.add(new float[] {40f, 190f, 40f, 70f,  70f, 70f, 70f} );
    }

    public Random getRandom() {
        return rnd;
    }

    public SalaryBox(float posX, float posY, SalaryCotisationTable salaryContainer, PDFont font, PDFont fontBold, float fontSize) throws Exception {
        container = new VerticalContainer(posX, posY, 0);
        this.salaryContainer = salaryContainer;
        this.font = font;
        this.fontBold = fontBold;
        this.fontSize = fontSize;
        this.init();
    }

    private String getProductElement(SalaryLine salaryLine, String colName){
        String salaryElement;
        switch (colName){
            case "Element":
                if (salaryLine.getCodeElement()==0) salaryElement ="";
                else {
                    salaryElement = ""+salaryLine.getCodeElement();
                }
                break;
            case "Rubrique":  salaryElement = salaryLine.getHeading();
                break;
            case "Base":
                if (salaryLine.getBase()==0) salaryElement ="";
                else {
                    salaryElement = df.format(salaryLine.getBase());
                }
                break;
            case "TxSalarial":
                if (salaryLine.getSalaryRate()==0) salaryElement ="";
                else {
                    salaryElement = df.format(salaryLine.getSalaryRate());
                }
                break;
            case "CtSalarial":
                if (salaryLine.getEmployeeContributions()==0) salaryElement ="";
                else {
                    salaryElement = df.format(salaryLine.getEmployeeContributions());
                }
                break;
            case "TxPatrnl":
                if (salaryLine.getEmployerRate()==0 ) salaryElement ="";
                else {
                    salaryElement = df.format(salaryLine.getEmployerRate());
                }
                break;
            case "CotPatrnl":
                if (salaryLine.getEmployerContributions()==0 ) salaryElement ="";
                else {
                    salaryElement = df.format(salaryLine.getEmployerContributions());
                }
                break;

            default: return "Invalid Product Name";
        }
        return  salaryElement;
    }

    private void init() throws Exception {
        final Map<String, String> headLabels = new HashMap<>();
        {
            headLabels.put("Rubrique", salaryContainer.getHeadingHead());
            headLabels.put("Element", salaryContainer.getCodeElementHead());
            headLabels.put("Base", salaryContainer.getBaseHead());
            headLabels.put("TxSalarial", salaryContainer.getSalaryRateHead());
            headLabels.put("CtSalarial", salaryContainer.getEmployeeContrHead());
            headLabels.put("TxPatrnl", salaryContainer.getEmployerRateHead());
            headLabels.put("CotPatrnl", salaryContainer.getEmployerContrHead());
        }

        int chosenFormatIndex = getRandom().nextInt(tableFormat.size());
        float[] configRow = tableConfig.get(chosenFormatIndex);
        String [] chosenFormat = tableFormat.get(chosenFormatIndex);
        chosenFormatForEval =chosenFormat;
        TableRowBox head = new TableRowBox(configRow, 0, 0, VAlign.BOTTOM);
        head.setBackgroundColor(Color.LIGHT_GRAY);

        for(String colname: chosenFormat)
        {
            head.addElement(new SimpleTextBox(fontBold, fontSize+1, 0, 0, headLabels.get(colname), Color.black, null, HAlign.CENTER), false);

        }
        container.addElement(head);

        int salarylineNum= 0;
        for(SalaryLine salaryLine : salaryContainer.getSalaryTableLines() ) {
            TableRowBox productLine = new TableRowBox(configRow, 0, 0);
            HAlign halign;
            salarylineNum++;
            String salaryElement;
            for(String colname: chosenFormat)
            {
                salaryElement = getProductElement(salaryLine, colname);
                halign = HAlign.LEFT;
               // productLine.addElement(new SimpleTextBox(fontBold, fontSize, 0, 0, salaryElement, Color.BLACK, null, halign, colname), false);
                productLine.addElement(new SimpleTextBox(fontBold, fontSize, 0, 0, salaryElement, Color.BLACK, null, halign), false);

            }
           // container.addElement(new HorizontalLineBox(0,0, head.getBoundingBox().getWidth()+30, 0));
            container.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
            container.addElement(productLine);
           // container.addElement(new BorderBox(Color.BLACK,Color.BLACK, 0,0, 0, 0, 5));
        }

        container.addElement(new HorizontalLineBox(0,0, head.getBoundingBox().getWidth()+30, 0));
        container.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));

    }

    @Override
    public BoundingBox getBoundingBox() {
        return container.getBoundingBox();
    }

    @Override
    public void setWidth(float width) throws Exception {
        throw new Exception("Not allowed");
    }

    @Override
    public void setHeight(float height) throws Exception {
        throw new Exception("Not allowed");
    }

    @Override
    public void translate(float offsetX, float offsetY) {
        this.container.translate(offsetX, offsetY);
    }

    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        this.container.build(stream, writer);
    }

    public String[] getChosenFormatForEval() {
        return chosenFormatForEval;
    }
}
