package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.Product;
import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;
import com.mifmif.common.regex.Generex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InvoiceModel {

    private InvoiceNumber reference;
    private InvoiceDate date;
    private Company company;

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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "InvoiceModel{" +
                "reference=" + reference +
                ", date=" + date +
                ", company=" + company +
                '}';
    }

    public static class Generator implements ModelGenerator<InvoiceModel> {

        @Override
        public InvoiceModel generate(GenerationContext ctx) {
            InvoiceModel model = new InvoiceModel();
            model.setReference(new InvoiceNumber.Generator().generate(ctx));
            model.setDate(new InvoiceDate.Generator().generate(ctx));
            return model;
        }
    }
}