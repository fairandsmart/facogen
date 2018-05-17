package com.fairandsmart.invoices.layout.product;

import com.fairandsmart.invoices.data.model.Product;
import com.fairandsmart.invoices.data.model.ProductContainer;
import com.fairandsmart.invoices.element.BoundingBox;
import com.fairandsmart.invoices.element.ElementBox;
import com.fairandsmart.invoices.element.HAlign;
import com.fairandsmart.invoices.element.VAlign;
import com.fairandsmart.invoices.element.border.BorderBox;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import com.fairandsmart.invoices.element.table.TableRowBox;
import com.fairandsmart.invoices.element.textbox.SimpleTextBox;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.util.logging.Logger;

public class ProductBox extends ElementBox {

    private static final Logger LOGGER = Logger.getLogger(VerticalContainer.class.getName());

    private Color headBackgroundColor;
    private Color bodyBackgroundColor;
    private VerticalContainer container;
    private ProductContainer productContainer;


    public ProductBox(float posX, float posY, ProductContainer productContainer) throws Exception {
        container = new VerticalContainer(posX, posY, 0);
        this.productContainer = productContainer;
        this.init();
    }

    private void init() throws Exception {
        float[] configRow = {20f, 180f, 50f, 50f, 50f, 50f, 50f, 50f};
        TableRowBox head = new TableRowBox(configRow, 0, 0, VAlign.BOTTOM);
        head.setBackgroundColor(Color.BLACK);
        head.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "QTY", Color.WHITE, null, HAlign.CENTER));
        head.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "DESCRIPTION", Color.WHITE, null, HAlign.CENTER));
        head.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "UNIT PRICE", Color.WHITE, null, HAlign.CENTER));
        head.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "DISCOUNT", Color.WHITE, null, HAlign.CENTER));
        head.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "TOTAL WITHOUT TAX", Color.WHITE, null, HAlign.CENTER));
        head.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "TAX TYPE", Color.WHITE, null, HAlign.CENTER));
        head.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "TAX RATE", Color.WHITE, null, HAlign.CENTER));
        head.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, "TAX AMOUNT", Color.WHITE, null, HAlign.CENTER));
        container.addElement(head);

        for(Product product : productContainer.getProducts() ) {
            TableRowBox productLine = new TableRowBox(configRow, 0, 0);
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, Float.toString(product.getQuantity()), Color.BLACK, null, HAlign.CENTER));
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA_BOLD, 8, 0, 0, product.getName(), Color.BLACK, null, HAlign.CENTER));
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, product.getFormatedPriceWithoutTax(), Color.BLACK, null, HAlign.CENTER));
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, "", Color.BLACK, null, HAlign.CENTER));
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, product.getFormatedTotalPriceWithoutTax(), Color.BLACK, null, HAlign.CENTER));
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, "", Color.BLACK, null, HAlign.CENTER));
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, Float.toString(product.getTaxRate() * 100)+"%", Color.BLACK, null, HAlign.CENTER));
            productLine.addElement(new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, product.getFormatedTotalTax(), Color.BLACK, null, HAlign.CENTER));

            container.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 2, 5));
            container.addElement(productLine);
            container.addElement(new BorderBox(Color.WHITE,Color.WHITE, 0,0, 0, 2, 5));
        }
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