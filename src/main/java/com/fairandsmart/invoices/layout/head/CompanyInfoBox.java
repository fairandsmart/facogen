package com.fairandsmart.invoices.layout.head;

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

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;

public class CompanyInfoBox extends ElementBox {

    private PDFont font;
    private PDFont fontBold;
    private float fontSize;
    private InvoiceModel model;
    private VerticalContainer container;
    private PDDocument document;

    public CompanyInfoBox(PDFont font, PDFont fontBold, float fontSize, InvoiceModel model, PDDocument document) throws Exception {
        this.font = font;
        this.fontBold = fontBold;
        this.fontSize = fontSize;
        this.model = model;
        this.document = document;
        this.container = new VerticalContainer(0, 0, 0);
        this.init();
    }

    private void init() throws Exception {
        String logoFile = this.getClass().getClassLoader().getResource("logo/"+model.getCompany().getLogo().getFullPath()).getFile();
        PDImageXObject logo = PDImageXObject.createFromFile(logoFile, document);
        ImageBox logoBox = new ImageBox(logo,  0,0, "logo");
        logoBox.setHeight(40);
        logoBox.setBackgroundColor(Color.GRAY);
        container.addElement(logoBox);

        SimpleTextBox name = new SimpleTextBox(fontBold, fontSize, 0,0, model.getCompany().getName());
        name.setEntityName("SN");
        container.addElement(name);
        SimpleTextBox adresse1 = new SimpleTextBox(font, fontSize, 0,0, model.getCompany().getAddress().getLine1());
        adresse1.setEntityName("SA");
        container.addElement(adresse1);
        if ( model.getCompany().getAddress().getLine2() != null &&  model.getCompany().getAddress().getLine2().length() > 0 ) {
            SimpleTextBox adresse2 = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getAddress().getLine2());
            adresse2.setEntityName("SA");
            container.addElement(adresse2);
        }
        if ( model.getCompany().getAddress().getLine3() != null &&  model.getCompany().getAddress().getLine3().length() > 0 ) {
            SimpleTextBox adresse3 = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getAddress().getLine3());
            adresse3.setEntityName("SA");
            container.addElement(adresse3);
        }

        HorizontalContainer cityContainer = new HorizontalContainer(0,0);
        SimpleTextBox zip = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getAddress().getZip());
        zip.setPadding(0, 0, 5, 0);
        zip.setEntityName("SA");
        cityContainer.addElement(zip);
        SimpleTextBox city = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getAddress().getCity());
        city.setEntityName("SA");
        cityContainer.addElement(city);
        container.addElement(cityContainer);

        if ( model.getCompany().getAddress().getCountry() != null &&  model.getCompany().getAddress().getCountry().length() > 0 ) {
            SimpleTextBox country = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getAddress().getCountry());
            country.setEntityName("SA");
            container.addElement(country);
        }

        if ( model.getCompany().getContact().getphoneValue() != null &&  model.getCompany().getContact().getphoneValue().length() > 0 ) {
            HorizontalContainer phoneContainer = new HorizontalContainer(0,0);
            SimpleTextBox phoneLabel = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getContact().getphoneLabel());
            phoneLabel.setPadding(0, 0, 10, 0);
            phoneContainer.addElement(phoneLabel);
            SimpleTextBox phoneValue = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getContact().getphoneValue());
            phoneValue.setPadding(5, 0, 0, 0);
            phoneValue.setEntityName("SCN");
            phoneContainer.addElement(phoneValue);
            container.addElement(phoneContainer);
        }

        if ( model.getCompany().getContact().getfaxValue() != null &&  model.getCompany().getContact().getfaxValue().length() > 0 ) {
            HorizontalContainer faxContainer = new HorizontalContainer(0,0);
            SimpleTextBox faxLabel = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getContact().getfaxLabel());
            faxLabel.setPadding(0, 0, 10, 0);
            faxContainer.addElement(faxLabel);
            SimpleTextBox faxValue = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getContact().getfaxValue());
            faxValue.setPadding(5, 0, 0, 0);
            faxValue.setEntityName("SFAX");
            faxContainer.addElement(faxValue);
            container.addElement(faxContainer);
        }

        if ( model.getCompany().getEmail() != null && model.getCompany().getEmail().length() > 0 ) {
            HorizontalContainer emailContainer = new HorizontalContainer(0,0);
            SimpleTextBox emailLabel = new SimpleTextBox(font, fontSize, 0, 0, "Email");
            emailLabel.setPadding(0, 0, 10, 0);
            emailContainer.addElement(emailLabel);
            SimpleTextBox emailValue = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getEmail());
            emailValue.setPadding(5, 0, 0, 0);
            emailValue.setEntityName("SEMAIL");
            emailContainer.addElement(emailValue);
            container.addElement(emailContainer);
        }

        if ( model.getCompany().getWebsite() != null && model.getCompany().getWebsite().length() > 0 ) {
            HorizontalContainer websiteContainer = new HorizontalContainer(0,0);
            SimpleTextBox websiteLabel = new SimpleTextBox(font, fontSize, 0, 0, "Email");
            websiteLabel.setPadding(0, 0, 10, 0);
            websiteContainer.addElement(websiteLabel);
            SimpleTextBox websiteValue = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getWebsite());
            websiteValue.setPadding(5, 0, 0, 0);
            websiteValue.setEntityName("SWEB");
            websiteContainer.addElement(websiteValue);
            container.addElement(websiteContainer);
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
