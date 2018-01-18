package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;

public class Company {


    private Logo logo;

    private VATNumber vatNumber;


    public Company() {
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }


    public VATNumber getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(VATNumber vatNumber) {
        this.vatNumber = vatNumber;
    }


    @Override
    public String toString() {
        return "Company{" +
                "logo=" + logo +
                ", vatNumber=" + vatNumber +
                '}';
    }

    public static class Generator implements ModelGenerator<Company> {

        @Override
        public Company generate(GenerationContext ctx) {
            Company model = new Company();
            model.setLogo(new Logo.Generator().generate(ctx));
            model.setVatNumber(new VATNumber.Generator().generate(ctx));
            return model;
        }
    }

/*
    private String name;
    private Address address;
    private String fax;
    private String phone;
    private String email;
    private String website;
    private VATNumber vat;
    */



}
