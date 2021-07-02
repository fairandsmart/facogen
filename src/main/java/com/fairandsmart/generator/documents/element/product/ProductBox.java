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
import com.fairandsmart.generator.documents.element.BoundingBox;
import com.fairandsmart.generator.documents.element.ElementBox;
import com.fairandsmart.generator.documents.element.border.BorderBox;
import com.fairandsmart.generator.documents.element.container.VerticalContainer;
import com.fairandsmart.generator.documents.element.textbox.SimpleTextBox;
import com.fairandsmart.generator.documents.element.HAlign;
import com.fairandsmart.generator.documents.element.VAlign;
import com.fairandsmart.generator.documents.element.line.HorizontalLineBox;
import com.fairandsmart.generator.documents.element.table.TableRowBox;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Logger;

public class ProductBox extends ElementBox {

    private static final Logger LOGGER = Logger.getLogger(VerticalContainer.class.getName());
    private PDFont font;
    private PDFont fontBold;
    private float fontSize;
    private Color headBackgroundColor;
    private Color bodyBackgroundColor;
    private VerticalContainer container;
    private ProductContainer productContainer;
    private String [] chosenFormatForEval;
    private static Random rnd = new Random();

    private static final List<String[]> tableFormat = new ArrayList<>();
    {
        tableFormat.add(new String[] {"PD", "QTY", "UP", "TXR", "PTWTX"} );
        tableFormat.add(new String[] {"QTY", "PD", "UP", "TXR", "PTWTX"} );
        tableFormat.add(new String[] {"SNO", "PD", "QTY", "UP", "TXR", "PTWTX"} );
        tableFormat.add(new String[] {"SNO", "QTY", "PD", "UP", "TXR", "PTWTX"} );
    }

    private static final List<float[]> tableConfig = new ArrayList<>();
    {
        tableConfig.add(new float[] {210f, 60f, 80f, 80f, 80f} );
        tableConfig.add(new float[] {60f, 210f, 80f, 80f, 80f} );
        tableConfig.add(new float[] {40f, 190f, 70f, 70f, 70f, 70f} );
        tableConfig.add(new float[] {40f, 70f, 190f, 70f, 70f, 70f} );
    }

    public Random getRandom() {
        return rnd;
    }

    public ProductBox(float posX, float posY, ProductContainer productContainer, PDFont font, PDFont fontBold, float fontSize) throws Exception {
        container = new VerticalContainer(posX, posY, 0);
        this.productContainer = productContainer;
        this.font = font;
        this.fontBold = fontBold;
        this.fontSize = fontSize;
        this.init();
    }

    private String getProductElement(Product product, String colName){
        String productElement;
        switch (colName){
            case "PD":  productElement = product.getName();
                break;
            case "QTY":  productElement = Integer.toString(product.getQuantity());
                break;
            case "UP":  productElement = product.getFormatedPriceWithoutTax();
                break;
            case "TXR":  productElement = Float.toString(product.getTaxRate() * 100)+"%";
                break;
            case "PTWTX":  productElement = product.getFormatedTotalPriceWithoutTax();
                break;
//            case "sn":  productElement = product.getFormatedTotalPriceWithoutTax();
//                break;

            default: return "Invalid Product Name";
        }
        return  productElement;
    }


    private void init() throws Exception {
        final Map<String, String> headLabels = new HashMap<>();
        {
            headLabels.put("PD", productContainer.getDescHead());
            headLabels.put("QTY", productContainer.getQtyHead());
            headLabels.put("UP", productContainer.getUPHead());
            headLabels.put("TXR", productContainer.getTaxRateHead());
            headLabels.put("PTWTX", productContainer.getLineTotalHead());
            headLabels.put("SNO", productContainer.getsnHead());
        }


        int chosenFormatIndex = getRandom().nextInt(tableFormat.size());
        float[] configRow = tableConfig.get(chosenFormatIndex);
        String [] chosenFormat = tableFormat.get(chosenFormatIndex);
        chosenFormatForEval = chosenFormat;
        TableRowBox head = new TableRowBox(configRow, 0, 0, VAlign.BOTTOM);
        head.setBackgroundColor(Color.BLACK);

        for(String colname: chosenFormat)
        {
            head.addElement(new SimpleTextBox(fontBold, fontSize+1, 0, 0, headLabels.get(colname), Color.WHITE, null, HAlign.CENTER), false);
        }
        container.addElement(head);

        int productNum= 0;
        for(Product product : productContainer.getProducts() ) {
            TableRowBox productLine = new TableRowBox(configRow, 0, 0);
            HAlign halign;
            productNum++;
            String productElement;
            for(String colname: chosenFormat)
            {
                productElement = getProductElement(product, colname);
                if(colname.equals("SNO"))
                    productElement = Integer.toString(productNum);
                halign = HAlign.CENTER;
                if(colname.equals("PD"))
                    halign = HAlign.LEFT;
                //System.out.println(colname + productElement);
                productLine.addElement(new SimpleTextBox(fontBold, fontSize, 0, 0, productElement, Color.BLACK, null, halign), false);//, colname), false);
            }
            container.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
            container.addElement(productLine);
            container.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        }

        container.addElement(new HorizontalLineBox(0,0, head.getBoundingBox().getWidth()+30, 0));
        container.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 15));

        TableRowBox totalHT = new TableRowBox(configRow, 0, 0);
        totalHT.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER), false);
        totalHT.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER), false);
        totalHT.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER), false);
        totalHT.addElement(new SimpleTextBox(fontBold, fontSize+1, 0, 0, productContainer.getTotalWithoutTaxHead(), Color.BLACK, null, HAlign.LEFT), false);
        totalHT.addElement(new SimpleTextBox(font, fontSize, 0, 0, productContainer.getFormatedTotalWithoutTax(), Color.BLACK, null, HAlign.CENTER , "TWTX"), false);

        container.addElement(totalHT);

        TableRowBox totalTax = new TableRowBox(configRow, 0, 0);
        totalTax.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER), false);
        totalTax.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER), false);
        totalTax.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER), false);
        totalTax.addElement(new SimpleTextBox(fontBold, fontSize+1, 0, 0, productContainer.getTotalTaxHead(), Color.BLACK, null, HAlign.LEFT), false);
        totalTax.addElement(new SimpleTextBox(font, fontSize, 0, 0, productContainer.getFormatedTotalTax(), Color.BLACK, null, HAlign.CENTER , "TTX"), false);

        container.addElement(totalTax);

        TableRowBox totalTTC = new TableRowBox(configRow, 0, 0);
        totalTTC.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.RIGHT), false);
        totalTTC.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER), false);
        totalTTC.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER), false);
        totalTTC.addElement(new SimpleTextBox(fontBold, fontSize+1, 0, 0, productContainer.getTotalAmountHead(), Color.BLACK, null, HAlign.LEFT), false);
        totalTTC.addElement(new SimpleTextBox(font, fontSize, 0, 0, productContainer.getFormatedTotalWithTax(), Color.BLACK, null, HAlign.CENTER,"TA" ), false);

        container.addElement(totalTTC);
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
