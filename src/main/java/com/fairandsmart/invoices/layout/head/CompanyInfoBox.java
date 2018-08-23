package com.fairandsmart.invoices.layout.head;

import com.fairandsmart.invoices.data.model.IDNumbers;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.element.BoundingBox;
import com.fairandsmart.invoices.element.ElementBox;
import com.fairandsmart.invoices.element.HAlign;
import com.fairandsmart.invoices.element.container.HorizontalContainer;
import com.fairandsmart.invoices.element.container.VerticalContainer;
import com.fairandsmart.invoices.element.image.ImageBox;
import com.fairandsmart.invoices.element.textbox.SimpleTextBox;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyInfoBox extends ElementBox {

    private PDFont font;
    private PDFont fontBold;
    private float fontSize;
    private InvoiceModel model;
    private VerticalContainer container;
    private HorizontalContainer hcontainer;
    private PDDocument document;
    private String logoFile;
    private PDImageXObject logo;
    private IDNumbers idnumObj;
    private String idNames[];
    private static final List<String[]> idNumbersOrder = new ArrayList<>();
    {
       // idNumbersOrder.add(new String[] {"Siret", "Toa", "Vat"} );

        idNumbersOrder.add(new String[] {"Cid", "Vat"} );
        idNumbersOrder.add(new String[] {"Vat"} );
        idNumbersOrder.add(new String[] {"Rcs","Vat"} );
        idNumbersOrder.add(new String[] {"Rcs", "Siret", "Vat"} );
        idNumbersOrder.add(new String[] {"Toa", "Siret", "Vat"} );
        idNumbersOrder.add(new String[] {"Toa", "Cid", "Vat"} );
        idNumbersOrder.add(new String[] {"Toa", "Rcs", "Siret", "Vat"} );
    }

    public CompanyInfoBox( HorizontalContainer hcontainer){this.hcontainer = hcontainer;}
    public CompanyInfoBox( VerticalContainer container){this.container = container;}

    public CompanyInfoBox(PDFont font, PDFont fontBold, float fontSize, InvoiceModel model, PDDocument document) throws Exception {
        this.font = font;
        this.fontBold = fontBold;
        this.fontSize = fontSize;
        this.model = model;
        this.document = document;
        this.container = new VerticalContainer(0, 0, 0);
        this.hcontainer = new HorizontalContainer(0, 0);
        this.idnumObj = model.getCompany().getIdNumbers();
        this.logoFile = this.getClass().getClassLoader().getResource("logo/" + model.getCompany().getLogo().getFullPath()).getFile();
        this.logo = PDImageXObject.createFromFile(logoFile, document);
        this.idNames = idNumbersOrder.get(model.getRandom().nextInt(idNumbersOrder.size()));
        this.init();
    }

    private void init() throws Exception
    {
        container.addElement(concatContainersVertically(new ElementBox[] {getLogoBox(40,Color.GRAY),
                getCompanyAddressBlock(), getCompanyContactBlock(), getCompanyIdBlock()} ) );
    }

    public ImageBox getLogoBox(float height, Color color){
        ImageBox logoBox = new ImageBox(logo, 0, 0, "logo");
        logoBox.setHeight(height);
        logoBox.setBackgroundColor(color);
        return logoBox;
    }

    public VerticalContainer getCompanyAddressBlock() throws Exception
    {
        VerticalContainer addContainer = new VerticalContainer(0,0,0);
        SimpleTextBox name = new SimpleTextBox(fontBold, fontSize, 0, 0, model.getCompany().getName(), "SN");
        //name.setEntityName("SN");
        addContainer.addElement(name);
        SimpleTextBox adresse1 = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getAddress().getLine1(), "SA");
        //adresse1.setEntityName("SA");
        addContainer.addElement(adresse1);

        if (model.getCompany().getAddress().getLine2() != null && model.getCompany().getAddress().getLine2().length() > 0) {
            SimpleTextBox adresse2 = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getAddress().getLine2(),"SA");
            //adresse2.setEntityName("SA");
            addContainer.addElement(adresse2);
        }

        if (model.getCompany().getAddress().getLine3() != null && model.getCompany().getAddress().getLine3().length() > 0) {
            SimpleTextBox adresse3 = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getAddress().getLine3(),"SA");
            //adresse3.setEntityName("SA");
            addContainer.addElement(adresse3);
        }

        HorizontalContainer cityContainer = new HorizontalContainer(0, 0);
        SimpleTextBox zip = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getAddress().getZip(),"SA");
        zip.setPadding(0, 0, 5, 0);
        //zip.setEntityName("SA");
        cityContainer.addElement(zip);
        SimpleTextBox city = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getAddress().getCity(),"SA");
        //city.setEntityName("SA");
        cityContainer.addElement(city);
        addContainer.addElement(cityContainer);

        if (model.getCompany().getAddress().getCountry() != null && model.getCompany().getAddress().getCountry().length() > 0) {
            SimpleTextBox country = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getAddress().getCountry(),"SA");
            //country.setEntityName("SA");
            addContainer.addElement(country);
        }
        return addContainer;
    }

    public HorizontalContainer getCompanyNameLine() throws Exception
    {
        HorizontalContainer companyNameContainer = new HorizontalContainer(0, 0);
        SimpleTextBox name = new SimpleTextBox(fontBold, fontSize, 0, 0, model.getCompany().getName(),"SN");
        //name.setEntityName("SN");
        companyNameContainer.addElement(name);
        return companyNameContainer;
    }

    public HorizontalContainer getCompanyAddLine() throws Exception {
        String separators[] = {", " , "", " - "};
        String separator = separators[model.getRandom().nextInt(separators.length)];
        HorizontalContainer address = new HorizontalContainer(0, 0);
        SimpleTextBox adresse1 = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getAddress().getLine1(),"SA");
        //adresse1.setEntityName("SA");
        adresse1.setPadding(0, 0, 1, 0);
        address.addElement(adresse1);

        if (model.getCompany().getAddress().getLine2() != null && model.getCompany().getAddress().getLine2().length() > 0) {
            SimpleTextBox adresse2 = new SimpleTextBox(font, fontSize, 0, 0, separator + model.getCompany().getAddress().getLine2(),"SA");
            //adresse2.setEntityName("SA");
            adresse2.setPadding(0, 0, 2, 0);
            address.addElement(adresse2);
        }

        if (model.getCompany().getAddress().getLine3() != null && model.getCompany().getAddress().getLine3().length() > 0) {
            SimpleTextBox adresse3 = new SimpleTextBox(font, fontSize, 0, 0, separator + model.getCompany().getAddress().getLine3(),"SA");
            //adresse3.setEntityName("SA");
            adresse3.setPadding(0, 0, 2, 0);
            address.addElement(adresse3);
        }

        SimpleTextBox zip = new SimpleTextBox(font, fontSize, 0, 0, separator + model.getCompany().getAddress().getZip(),"SA");
        zip.setPadding(0, 0, 1, 0);
        //zip.setEntityName("SA");
        address.addElement(zip);

        SimpleTextBox city = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getAddress().getCity(),"SA");
        //city.setEntityName("SA");
        city.setPadding(0, 0, 1, 0);
        address.addElement(city);

        if (model.getCompany().getAddress().getCountry() != null && model.getCompany().getAddress().getCountry().length() > 0) {
            SimpleTextBox country = new SimpleTextBox(font, fontSize, 0, 0, separator + model.getCompany().getAddress().getCountry(),"SA");
            //country.setEntityName("SA");
            country.setPadding(0, 0, 1, 0);
            address.addElement(country);
        }
        return address;
    }

    public VerticalContainer getCompanyAddressLine() throws Exception
    {
        VerticalContainer addContainer = new VerticalContainer(0,0,0);
        addContainer.addElement(getCompanyNameLine());
        addContainer.addElement(getCompanyAddLine());
        return addContainer;
    }


    public VerticalContainer getCompanyContactBlock() throws Exception
    {
        VerticalContainer contactContainer = new VerticalContainer(0, 0, 0);

        if (model.getCompany().getContact().getphoneValue() != null && model.getCompany().getContact().getphoneValue().length() > 0) {
            HorizontalContainer phoneContainer = new HorizontalContainer(0, 0);
            SimpleTextBox phoneLabel = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getContact().getphoneLabel());
            phoneLabel.setPadding(0, 0, 5, 0);
            phoneContainer.addElement(phoneLabel);
            SimpleTextBox phoneValue = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getContact().getphoneValue(),"SCN");
            phoneValue.setPadding(5, 0, 0, 0);
            //phoneValue.setEntityName("SCN");
            phoneContainer.addElement(phoneValue);
            //phoneContainer.setBackgroundColor(Color.PINK);
            contactContainer.addElement(phoneContainer);
        }

        if (model.getCompany().getContact().getfaxValue() != null && model.getCompany().getContact().getfaxValue().length() > 0) {
            HorizontalContainer faxContainer = new HorizontalContainer(0, 0);
            SimpleTextBox faxLabel = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getContact().getfaxLabel());
            faxLabel.setPadding(0, 0, 5, 0);
            faxContainer.addElement(faxLabel);
            SimpleTextBox faxValue = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getContact().getfaxValue(),"SFAX");
            faxValue.setPadding(5, 0, 0, 0);
            //faxValue.setEntityName("SFAX");
            faxContainer.addElement(faxValue);
            contactContainer.addElement(faxContainer);
        }

        if (model.getCompany().getEmail() != null && model.getCompany().getEmail().length() > 0) {
            HorizontalContainer emailContainer = new HorizontalContainer(0, 0);
            SimpleTextBox emailLabel = new SimpleTextBox(font, fontSize, 0, 0, "Email");
            emailLabel.setPadding(0, 0, 10, 0);
            emailContainer.addElement(emailLabel);
            SimpleTextBox emailValue = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getEmail(),"SEMAIL");
            emailValue.setPadding(5, 0, 0, 0);
            //emailValue.setEntityName("SEMAIL");
            emailContainer.addElement(emailValue);
            contactContainer.addElement(emailContainer);
        }

        if (model.getCompany().getWebsite() != null && model.getCompany().getWebsite().length() > 0) {
            HorizontalContainer websiteContainer = new HorizontalContainer(0, 0);
            SimpleTextBox websiteLabel = new SimpleTextBox(font, fontSize, 0, 0, "Website");
            websiteLabel.setPadding(0, 0, 10, 0);
            websiteContainer.addElement(websiteLabel);
            SimpleTextBox websiteValue = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getWebsite(),"SWEB");
            websiteValue.setPadding(5, 0, 0, 0);
            //websiteValue.setEntityName("SWEB");
            websiteContainer.addElement(websiteValue);
            contactContainer.addElement(websiteContainer);
        }

        return contactContainer;
    }

    public HorizontalContainer getCompanyContactLine() throws Exception
    {
        HorizontalContainer contactContainer = new HorizontalContainer( 0, 0);

        if (model.getCompany().getContact().getphoneValue() != null && model.getCompany().getContact().getphoneValue().length() > 0) {
            SimpleTextBox phoneLabel = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getContact().getphoneLabel());
            phoneLabel.setPadding(0, 0, 2, 0);
            contactContainer.addElement(phoneLabel);
            SimpleTextBox phoneValue = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getContact().getphoneValue(),"SCN");
            phoneValue.setPadding(2, 0, 4, 0);
            //phoneValue.setEntityName("SCN");
            contactContainer.addElement(phoneValue);
        }

        if (model.getCompany().getContact().getfaxValue() != null && model.getCompany().getContact().getfaxValue().length() > 0) {
            SimpleTextBox faxLabel = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getContact().getfaxLabel());
            faxLabel.setPadding(0, 0, 2, 0);
            contactContainer.addElement(faxLabel);
            SimpleTextBox faxValue = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getContact().getfaxValue(),"SFAX");
            faxValue.setPadding(2, 0, 0, 0);
            //faxValue.setEntityName("SFAX");
            contactContainer.addElement(faxValue);
        }

        if (model.getCompany().getEmail() != null && model.getCompany().getEmail().length() > 0) {
            SimpleTextBox emailLabel = new SimpleTextBox(font, fontSize, 0, 0, "Email");
            emailLabel.setPadding(0, 0, 10, 0);
            contactContainer.addElement(emailLabel);
            SimpleTextBox emailValue = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getEmail(),"SEMAIL");
            emailValue.setPadding(5, 0, 0, 0);
            //emailValue.setEntityName("SEMAIL");
            contactContainer.addElement(emailValue);
        }

        if (model.getCompany().getWebsite() != null && model.getCompany().getWebsite().length() > 0) {
            SimpleTextBox websiteLabel = new SimpleTextBox(font, fontSize, 0, 0, "Website");
            websiteLabel.setPadding(0, 0, 10, 0);
            contactContainer.addElement(websiteLabel);
            SimpleTextBox websiteValue = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getWebsite(), "SWEB");
            websiteValue.setPadding(5, 0, 0, 0);
            //websiteValue.setEntityName("SWEB");
            contactContainer.addElement(websiteValue);
        }

        return contactContainer;
    }

    public VerticalContainer getCompanyIdBlock() throws Exception
    {
        VerticalContainer idContainer = new VerticalContainer(0,0,0);
        this.idNames = idNumbersOrder.get(model.getRandom().nextInt(idNumbersOrder.size()));

        for (String idName: idNames)
        {
            HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);
            if (idName.equals("Rcs")) // Special because not available in IDNumbers
            {
                SimpleTextBox RCSLabel = new SimpleTextBox(font, fontSize, 0, 0, "RCS ");
                RCSLabel.setPadding(0, 0, 2, 0);
                companyIDContainer.addElement(RCSLabel);
                SimpleTextBox RCSValue1 = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getAddress().getCity() ,"SCPOR");
                RCSValue1.setPadding(0, 0, 2, 0);
                companyIDContainer.addElement(RCSValue1);
                SimpleTextBox RCSValue2 = new SimpleTextBox(font, fontSize, 0, 0, idnumObj.getCidValue() ,"SCID");
                RCSValue2.setPadding(0, 0, 2, 0);
                companyIDContainer.addElement(RCSValue2);
            }
            else
            {
                String labelName = "get" + idName + "Label";
                String valueName = "get" + idName + "Value";
                SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.callviaName(idnumObj, labelName).toString());
                Label.setPadding(0, 0, 2, 0);
                companyIDContainer.addElement(Label);
                SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.callviaName(idnumObj, valueName).toString(),"S" + idName.toUpperCase());
               // Value.setEntityName("S" + idName.toUpperCase());
                Value.setPadding(0, 0, 3, 0);
                companyIDContainer.addElement(Value);
            }

            idContainer.addElement(companyIDContainer);
        }

        return idContainer;
    }

    public HorizontalContainer getCompanyIdLine(int size) throws Exception
    {
        if(size==0)
            this.idNames = idNumbersOrder.get(model.getRandom().nextInt(6-3+1)+3);
        else
            this.idNames = idNumbersOrder.get(model.getRandom().nextInt(size)); // not more than 3 fields
        HorizontalContainer companyIDContainer = new HorizontalContainer(0, 0);
        for (int i = 0; i < idNames.length; i++)
        {
            if (idNames[i].equals("Rcs")) // Special because not available in IDNumbers
            {
                SimpleTextBox RCSLabel = new SimpleTextBox(font, fontSize, 0, 0, "RCS ");
                RCSLabel.setPadding(0, 0, 2, 0);
                companyIDContainer.addElement(RCSLabel);
                SimpleTextBox RCSValue1 = new SimpleTextBox(font, fontSize, 0, 0, model.getCompany().getAddress().getCity(), "SCPOR");
                RCSValue1.setPadding(0, 0, 2, 0);
                companyIDContainer.addElement(RCSValue1);
                SimpleTextBox RCSValue = new SimpleTextBox(font, fontSize, 0, 0, idnumObj.getCidValue(),"SCID");
                RCSValue.setPadding(0, 0, 2, 0);
                companyIDContainer.addElement(RCSValue);
            }
            else
            {
                String labelName = "get" + idNames[i] + "Label";
                String valueName = "get" + idNames[i] + "Value";
                SimpleTextBox Label = new SimpleTextBox(font, fontSize, 0, 0, model.callviaName(idnumObj, labelName).toString());
                Label.setPadding(0, 0, 2, 0);
                companyIDContainer.addElement(Label);
                SimpleTextBox Value = new SimpleTextBox(font, fontSize, 0, 0, model.callviaName(idnumObj, valueName).toString(),"S" + idNames[i].toUpperCase());
               // Value.setEntityName("S" + idNames[i].toUpperCase());
                Value.setPadding(0, 0, 3, 0);
                companyIDContainer.addElement(Value);
            }
        }

        return companyIDContainer;
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
