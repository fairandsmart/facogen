package com.fairandsmart.invoices.layout.head;

import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.element.BoundingBox;
import com.fairandsmart.invoices.element.ElementBox;
import com.fairandsmart.invoices.element.container.HorizontalContainer;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import javax.xml.stream.XMLStreamWriter;

public class HeadBox extends ElementBox {

    private PDFont font;
    private PDFont fontBold;
    private PDFont fontItalic;
    private float fontSize;
    private InvoiceModel model;
    private VerticalContainer container;
    private PDDocument document;

    public HeadBox(PDFont font, PDFont fontBold, PDFont fontItalic, float fontSize, InvoiceModel model, PDDocument document) throws Exception {
        this.font = font;
        this.fontBold = fontBold;
        this.fontItalic = fontItalic;
        this.fontSize = fontSize;
        this.model = model;
        this.document = document;
        this.init();
    }

    private void init() throws Exception {
        container = new VerticalContainer(0,0, 0);
        HorizontalContainer top = new HorizontalContainer(0,0);
        CompanyInfoBox companyInfoBox = new CompanyInfoBox(font, fontBold, 11, model, document);
        companyInfoBox.setWidth(250);
        CompanyInfoBox companyInfoBox2 = new CompanyInfoBox(font, fontBold, 11, model, document);
        companyInfoBox2.setWidth(250);
        top.addElement(companyInfoBox);
        top.addElement(companyInfoBox2);
        container.addElement(top);
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
        container.translate(offsetX, offsetY);
    }

    @Override
    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        container.build(stream, writer);
    }
}
