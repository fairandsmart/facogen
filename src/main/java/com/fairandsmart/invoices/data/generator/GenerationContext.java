package com.fairandsmart.invoices.data.generator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerationContext {

    private static List<String> countries = new ArrayList<>();
    private static List<String> languages = new ArrayList<>();
    private static List<String> currencies = new ArrayList<>();
    private static Random rnd = new Random();
    {
        countries.add("FR");
        //countries.add("LU");
        //countries.add("BE");
        //countries.add("UK");
    }
    {
        languages.add("fr");
        languages.add("en");
    }
    {
        currencies.add("EUR");
        currencies.add("€");
        currencies.add("USD");
        currencies.add("$");
        //currencies.add("Rs.");
        //currencies.add("¥");
        //currencies.add("Yuan");
    }

    private String country;
    private String language;
    private String brandName;
    private String currency;
    private long date;

    public GenerationContext() {
        brandName = ".*";
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Random getRandom() {
        return rnd;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public static GenerationContext generate() {
        GenerationContext ctx = new GenerationContext();
        ctx.setCountry(countries.get(rnd.nextInt(countries.size())));
        ctx.setLanguage(languages.get(rnd.nextInt(languages.size())));
        ctx.setCurrency(currencies.get(rnd.nextInt(currencies.size())));
        return ctx;
    }
}
