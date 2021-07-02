package com.fairandsmart.generator.documents.element.product;

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
import com.fairandsmart.generator.documents.data.model.ProductReceiptContainer;
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

public class ReceiptGSTSammury extends ElementBox {

    private static final Logger LOGGER = Logger.getLogger(VerticalContainer.class.getName());
    private PDFont font;
    private PDFont fontBold;
    private float fontSize;
    private Color headBackgroundColor;
    private Color bodyBackgroundColor;
    private VerticalContainer container;
    private ProductReceiptContainer productContainer;
    private static Random rnd = new Random();
    private String[] chosenFormatHeaders;

    private static final List<String[]> tableFormat = new ArrayList<>();
    {
        tableFormat.add(new String[] {"CODE", "TAUX", "TTC","TVA", "HT"} );
        tableFormat.add(new String[] {"CODE", "TAUX", "HT","TVA", "TTC"} );
        tableFormat.add(new String[] {"CODE", "TAUX", "HT","TVA"} );
        tableFormat.add(new String[] {"CODE", "HT","TVA"} );

    }

    private static final List<float[]> tableConfig = new ArrayList<>();
    {
        tableConfig.add(new float[] {70f,70f, 70f, 70f,  70f} );
        tableConfig.add(new float[] {50f,60f, 80f, 80f,  80f} );
        tableConfig.add(new float[] {120f, 60f, 80f,  80f} );
        tableConfig.add(new float[] {140f, 90f, 90f} );

    }

    public Random getRandom() {
        return rnd;
    }

    public ReceiptGSTSammury(float posX, float posY, ProductReceiptContainer productContainer, PDFont font, PDFont fontBold, float fontSize) throws Exception {
        container = new VerticalContainer(posX, posY, 0);
        this.productContainer = productContainer;
        this.font = font;
        this.fontBold = fontBold;
        this.fontSize = fontSize;
        this.init();
    }


    private String getTableElement(String colName){
        String tableElement;

        switch (colName){
            case "CODE":  tableElement = "(1)";
                break;
            case "TAUX":  tableElement = String.format("%.2f",productContainer.getTotalTaxRate() * 100)+"%";
                break;
            case "TTC":  tableElement = productContainer.getFormatedTotalWithTax();
                break;
            case "TVA":  tableElement = productContainer.getFormatedTotalTax();
                break;
            case "HT":  tableElement = productContainer.getFormatedTotalWithoutTax();
                break;
            default: return "Invalid element name Name";
        }
        return  tableElement;
    }

    private void init() throws Exception {
        final Map<String, String> headLabels = new HashMap<>();
        {
            headLabels.put("CODE", productContainer.getGSTHead());
            headLabels.put("TAUX", productContainer.getTaxRateHead());
            headLabels.put("TTC", productContainer.getTotalAmountHead());
            headLabels.put("TVA", productContainer.getTotalTaxHead());
            headLabels.put("HT", productContainer.getTotalWithoutTaxHead());
        }


        int chosenFormatIndex = getRandom().nextInt(tableFormat.size());
        float[] configRow = tableConfig.get(chosenFormatIndex);
        String [] chosenFormat = tableFormat.get(chosenFormatIndex);
        this.chosenFormatHeaders =chosenFormat;
        TableRowBox head = new TableRowBox(configRow, 0, 0, VAlign.BOTTOM);
        head.setBackgroundColor(Color.WHITE);

        for(String colname: chosenFormat)
        {
            head.addElement(new SimpleTextBox(fontBold, fontSize+1, 0, 0, headLabels.get(colname), Color.BLACK, null, HAlign.CENTER), false);
        }
        container.addElement(head);

        TableRowBox productLine = new TableRowBox(configRow, 0, 0);
        HAlign halign;
        String tabelElement;

        for(String colname: chosenFormat)
        {
            tabelElement = getTableElement(colname);
            halign = HAlign.CENTER;
            if(colname.equals("CODE"))
                halign = HAlign.LEFT;
            productLine.addElement(new SimpleTextBox(font, fontSize, 0, 0, tabelElement, Color.BLACK, null, halign),false);// colname), false);
        }
        container.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        container.addElement(productLine);
        container.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));

        container.addElement(new HorizontalLineBox(0,0, head.getBoundingBox().getWidth()+30, 0));
        container.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 15));

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

    public String[] getChosenFormatHeaderss() {
        return chosenFormatHeaders;
    }
}
