package com.fairandsmart.generator.documents.data.generator;

/*-
 * #%L
 * FacoGen / A tool for annotated GEDI based invoice generation.
 * 
 * Authors:
 * 
 * Xavier Lefevre <xavier.lefevre@fairandsmart.com> / FairAndSmart
 * Nicolas Rueff <nicolas.rueff@fairandsmart.com> / FairAndSmart
 * Alan Balbo <alan.balbo@fairandsmart.com> / FairAndSmart
 * Frederic Pierre <frederic.pierre@fairansmart.com> / FairAndSmart
 * Victor Guillaume <victor.guillaume@fairandsmart.com> / FairAndSmart
 * Jérôme Blanchard <jerome.blanchard@fairandsmart.com> / FairAndSmart
 * Aurore Hubert <aurore.hubert@fairandsmart.com> / FairAndSmart
 * Kevin Meszczynski <kevin.meszczynski@fairandsmart.com> / FairAndSmart
 * %%
 * Copyright (C) 2019 Fair And Smart
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

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
        //languages.add("en");
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

    @Override
    public String toString() {
        return "GenerationContext{" +
                "country='" + country + '\'' +
                ", language='" + language + '\'' +
                ", brandName='" + brandName + '\'' +
                ", currency='" + currency + '\'' +
                ", date=" + date +
                '}';
    }

    public static GenerationContext generate() {
        GenerationContext ctx = new GenerationContext();
        ctx.setCountry(countries.get(rnd.nextInt(countries.size())));
        ctx.setLanguage(languages.get(rnd.nextInt(languages.size())));
        ctx.setCurrency(currencies.get(rnd.nextInt(currencies.size())));
        return ctx;
    }
}
