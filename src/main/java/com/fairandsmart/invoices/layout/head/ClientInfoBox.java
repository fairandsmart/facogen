package com.fairandsmart.invoices.layout.head;

import com.fairandsmart.invoices.data.model.Client;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.element.BoundingBox;
import com.fairandsmart.invoices.element.ElementBox;
import com.fairandsmart.invoices.element.container.HorizontalContainer;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import com.fairandsmart.invoices.element.image.ImageBox;
import com.fairandsmart.invoices.element.textbox.SimpleTextBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import java.util.Random;
import java.lang.reflect.*;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClientInfoBox extends ElementBox {

    private PDFont font;
    private PDFont fontBold;
    private float fontSize;
    private InvoiceModel model;
    private VerticalContainer container;
    private PDDocument document;
    private String addressType;

    public ClientInfoBox(PDFont font, PDFont fontBold, float fontSize, InvoiceModel model, PDDocument document, String addressType) throws Exception {
        this.font = font;
        this.fontBold = fontBold;
        this.fontSize = fontSize;
        this.model = model;
        this.document = document;
        this.addressType = addressType;
        this.container = new VerticalContainer(0, 0, 0);
        this.init();
    }

    private void init() throws Exception {

        String entityPrefix = "", addLine2 = "", addLine3 = "", acountry = "";
        Client client_obj = model.getClient();
        if(addressType.equals("Billing"))
            entityPrefix = "B";
        else if(addressType.equals("Shipping"))
            entityPrefix = "SH";

        SimpleTextBox heading = new SimpleTextBox(fontBold, fontSize+2, 0,0, model.callviaName(client_obj,"get"+addressType+"Head").toString());
        container.addElement(heading);

        SimpleTextBox name = new SimpleTextBox(fontBold, fontSize, 0,0, model.callviaName(client_obj,"get"+addressType+"Name").toString());
        name.setEntityName(entityPrefix+"N");
        container.addElement(name);

        SimpleTextBox adresse1 = new SimpleTextBox(font, fontSize, 0,0, model.callviaName(model.callviaName(client_obj,"get"+addressType+"Address"),"getLine1").toString());
        adresse1.setEntityName(entityPrefix+"A");
        container.addElement(adresse1);

        addLine2 = model.callviaName(model.callviaName(client_obj,"get"+addressType+"Address"),"getLine2").toString();
        if ( addLine2 != null &&  addLine2.length() > 0 ) {
            SimpleTextBox adresse2 = new SimpleTextBox(font, fontSize, 0, 0, addLine2);
            adresse2.setEntityName(entityPrefix+"A");
            container.addElement(adresse2);
        }

        addLine3 = model.callviaName(model.callviaName(client_obj,"get"+addressType+"Address"),"getLine3").toString();
        if ( addLine3!= null &&  addLine3.length() > 0 ) {
            SimpleTextBox adresse3 = new SimpleTextBox(font, fontSize, 0, 0, addLine3);
            adresse3.setEntityName(entityPrefix+"A");
            container.addElement(adresse3);
        }

        HorizontalContainer cityContainer = new HorizontalContainer(0,0);

        SimpleTextBox zip = new SimpleTextBox(font, fontSize, 0, 0, model.callviaName(model.callviaName(client_obj,"get"+addressType+"Address"),"getZip").toString());
        zip.setPadding(0, 0, 5, 0);
        zip.setEntityName(entityPrefix+"A");
        cityContainer.addElement(zip);

        SimpleTextBox city = new SimpleTextBox(font, fontSize, 0, 0, model.callviaName(model.callviaName(client_obj,"get"+addressType+"Address"),"getCity").toString());
        city.setEntityName(entityPrefix+"A");
        cityContainer.addElement(city);
        container.addElement(cityContainer);

        acountry = model.callviaName(model.callviaName(client_obj,"get"+addressType+"Address"),"getCountry").toString();
        if ( acountry != null &&  acountry.length() > 0 ) {
            SimpleTextBox country = new SimpleTextBox(font, fontSize, 0, 0, acountry);
            country.setEntityName(entityPrefix+"A");
            container.addElement(country);
        }

    }

    @Override
    public BoundingBox getBoundingBox() {
        return container.getBoundingBox();
    }

    @Override
    public void setWidth(float width) throws Exception {
        container.getBoundingBox().setWidth(width);
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
