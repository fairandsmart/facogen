package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;

import java.util.Random;

public class InvoiceModel {

    private InvoiceNumber reference;
    private InvoiceDate date;
    private String lang;
    private PaymentInfo paymentInfo;
    private Company company;
    private Client client;
    private ProductContainer productContainer;
    private static Random rnd = new Random();

    public InvoiceModel() {
    }

    public InvoiceNumber getReference() {
        return reference;
    }

    public void setReference(InvoiceNumber reference) {
        this.reference = reference;
    }

    public InvoiceDate getDate() {
        return date;
    }

    public void setDate(InvoiceDate date) {
        this.date = date;
    }

    public Random getRandom() {
        return rnd;
    }

    public Object callviaName(Object c, String methodName) throws Exception
    {
        // Calls a method with its name as a string
        return c.getClass().getMethod(methodName).invoke(c);
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ProductContainer getProductContainer() {
        return productContainer;
    }

    public void setProductContainer(ProductContainer productContainer) {
        this.productContainer = productContainer;
    }

    @Override
    public String toString() {
        return "InvoiceModel{" +
                "reference=" + reference +
                ", date=" + date +
                ", lang=" + lang +
                ", paymentInfo=" + paymentInfo +
                ", company=" + company +
                ", client=" + client +
                ", productContainer=" + productContainer +
                '}';
    }

    public static class Generator implements ModelGenerator<InvoiceModel> {

        @Override
        public InvoiceModel generate(GenerationContext ctx) {
            InvoiceModel model = new InvoiceModel();
            model.setReference(new InvoiceNumber.Generator().generate(ctx));
            model.setDate(new InvoiceDate.Generator().generate(ctx));
            model.setLang(ctx.getLanguage());
            model.setPaymentInfo(new PaymentInfo.Generator().generate(ctx));
            model.setCompany(new Company.Generator().generate(ctx));
            model.setClient(new Client.Generator().generate(ctx));
            model.setProductContainer(new ProductContainer.Generator().generate(ctx));
            return model;
        }
    }
}
