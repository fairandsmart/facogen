package com.fairandsmart.invoices.layout.product;

import com.fairandsmart.invoices.data.model.Product;
import com.fairandsmart.invoices.data.model.ProductContainer;
import com.fairandsmart.invoices.element.BoundingBox;
import com.fairandsmart.invoices.element.ElementBox;
import com.fairandsmart.invoices.element.HAlign;
import com.fairandsmart.invoices.element.VAlign;
import com.fairandsmart.invoices.element.border.BorderBox;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import com.fairandsmart.invoices.element.line.HorizontalLineBox;
import com.fairandsmart.invoices.element.table.TableRowBox;
import com.fairandsmart.invoices.element.textbox.SimpleTextBox;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
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


    public ProductBox(float posX, float posY, ProductContainer productContainer, PDFont font, PDFont fontBold, float fontSize) throws Exception {
        container = new VerticalContainer(posX, posY, 0);
        this.productContainer = productContainer;
        this.font = font;
        this.fontBold = fontBold;
        this.fontSize = fontSize;
        this.init();
    }

    private void init() throws Exception {
        //float[] configRow = {20f, 180f, 50f, 50f, 50f, 50f, 50f, 50f};
        float[] configRow = {200f, 60f, 80f, 80f, 80f};
        TableRowBox head = new TableRowBox(configRow, 0, 0, VAlign.BOTTOM);
        head.setBackgroundColor(Color.BLACK);
        head.addElement(new SimpleTextBox(fontBold, fontSize+1, 0, 0, productContainer.getDescHead(), Color.WHITE, null, HAlign.CENTER));
        head.addElement(new SimpleTextBox(fontBold, fontSize+1, 0, 0, productContainer.getQtyHead(), Color.WHITE, null, HAlign.CENTER));
        head.addElement(new SimpleTextBox(fontBold, fontSize+1, 0, 0, productContainer.getUPHead(), Color.WHITE, null, HAlign.CENTER));
        head.addElement(new SimpleTextBox(fontBold, fontSize+1, 0, 0, productContainer.getTaxRateHead(), Color.WHITE, null, HAlign.CENTER));
        head.addElement(new SimpleTextBox(fontBold, fontSize+1, 0, 0, productContainer.getLineTotalHead(), Color.WHITE, null, HAlign.CENTER));
//        head.addElement(new SimpleTextBox(fontBold, fontSize, 0, 0, "TAX TYPE", Color.WHITE, null, HAlign.CENTER));
//        head.addElement(new SimpleTextBox(fontBold, fontSize, 0, 0, "TAX RATE", Color.WHITE, null, HAlign.CENTER));
//        head.addElement(new SimpleTextBox(fontBold, fontSize, 0, 0, "TAX AMOUNT", Color.WHITE, null, HAlign.CENTER));
        container.addElement(head);

        for(Product product : productContainer.getProducts() ) {
            TableRowBox productLine = new TableRowBox(configRow, 0, 0);
            productLine.addElement(new SimpleTextBox(fontBold, fontSize, 0, 0, product.getName(), Color.BLACK, null, HAlign.LEFT));
            productLine.addElement(new SimpleTextBox(font, fontSize, 0, 0, Integer.toString(product.getQuantity()), Color.BLACK, null, HAlign.CENTER));
            productLine.addElement(new SimpleTextBox(font, fontSize, 0, 0, product.getFormatedPriceWithoutTax(), Color.BLACK, null, HAlign.CENTER));
            //productLine.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER));
            //productLine.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER));
            productLine.addElement(new SimpleTextBox(font, fontSize, 0, 0, Float.toString(product.getTaxRate() * 100)+"%", Color.BLACK, null, HAlign.CENTER));
            //productLine.addElement(new SimpleTextBox(font, fontSize, 0, 0, product.getFormatedTotalTax(), Color.BLACK, null, HAlign.CENTER));
            productLine.addElement(new SimpleTextBox(font, fontSize, 0, 0, product.getFormatedTotalPriceWithoutTax(), Color.BLACK, null, HAlign.CENTER));

            container.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
            container.addElement(productLine);
            container.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        }

        container.addElement(new HorizontalLineBox(0,0, head.getBoundingBox().getWidth()+head.getBoundingBox().getPosX(), 0));
        container.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        container.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        container.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 0, 5));
        TableRowBox totalHT = new TableRowBox(configRow, 0, 0);
        totalHT.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER));
        totalHT.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER));
        totalHT.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER));
        totalHT.addElement(new SimpleTextBox(fontBold, fontSize+1, 0, 0, productContainer.getTotalWithoutTaxHead(), Color.BLACK, null, HAlign.LEFT));
        totalHT.addElement(new SimpleTextBox(font, fontSize, 0, 0, productContainer.getFormatedTotalWithoutTax(), Color.BLACK, null, HAlign.CENTER ));

        container.addElement(totalHT);

        TableRowBox totalTax = new TableRowBox(configRow, 0, 0);
        totalTax.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER));
        totalTax.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER));
        totalTax.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER));
        totalTax.addElement(new SimpleTextBox(fontBold, fontSize+1, 0, 0, productContainer.getTotalTaxHead(), Color.BLACK, null, HAlign.LEFT));
        totalTax.addElement(new SimpleTextBox(font, fontSize, 0, 0, productContainer.getFormatedTotalTax(), Color.BLACK, null, HAlign.CENTER ));

        container.addElement(totalTax);

        TableRowBox totalTTC = new TableRowBox(configRow, 0, 0);
        totalTTC.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.RIGHT));
        totalTTC.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER));
        totalTTC.addElement(new SimpleTextBox(font, fontSize, 0, 0, "", Color.BLACK, null, HAlign.CENTER));
        totalTTC.addElement(new SimpleTextBox(fontBold, fontSize+1, 0, 0, productContainer.getTotalAmountHead(), Color.BLACK, null, HAlign.LEFT));
        totalTTC.addElement(new SimpleTextBox(font, fontSize, 0, 0, productContainer.getFormatedTotalWithTax(), Color.BLACK, null, HAlign.CENTER ));

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

}