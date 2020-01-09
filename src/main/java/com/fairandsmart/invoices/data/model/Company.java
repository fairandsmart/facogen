package com.fairandsmart.invoices.data.model;

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

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Company {

    private static final Logger LOGGER = Logger.getLogger(Company.class.getName());

    private Logo logo;
    private IDNumbers idNumbers;
    private String name;
    private Address address;
    private ContactNumber contact;
    // TODO Company email and website needed, should correspond to company name
    private String email;
    private String website;

    public Company() {
    }

    public Logo getLogo() {
        return logo;
    }

    public void setLogo(Logo logo) {
        this.logo = logo;
    }

    public IDNumbers getIdNumbers() { return idNumbers; }

    public void setIdNumbers(IDNumbers idNumbers) {
        this.idNumbers = idNumbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactNumber getContact() {
        return contact;
    }

    public void setContact(ContactNumber contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Company{" +
                "logo=" + logo +
                ", idNumbers=" + idNumbers +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", contact=" + contact +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<Company> {

        private static final String companiesFileFR = "company/companies_fr.csv";
        private static Map<Company, String> companies = new HashMap<>();
        {
            try {
                Reader in = new InputStreamReader(Logo.class.getClassLoader().getResourceAsStream(companiesFileFR));
                Iterable<CSVRecord> records = CSVFormat.newFormat(';').withQuote('"').withFirstRecordAsHeader().parse(in);
                for (CSVRecord record : records) {
                    String name = record.get("ENSEIGNE");
                    String adresseL1 = record.get("L4_NORMALISEE");
                    String adresseL2 = record.get("L5_NORMALISEE");
                    String cp = record.get("CODPOS");
                    String ville = record.get("LIBCOM");
                    String pays = record.get("L7_NORMALISEE");
                    if ( name.length() > 3 ) {
                        Company comp = new Company();
                        comp.setName(name);
                        Address companyAddress = new Address(adresseL1, adresseL2, "", cp, ville, pays);
                        comp.setAddress(companyAddress);
                        companies.put(comp, "FR");
                    }
                }
            } catch ( Exception e ) {
                LOGGER.log(Level.SEVERE, "unable to parse csv source: " + companiesFileFR, e);
            }
        }


        @Override
        public Company generate(GenerationContext ctx) {
            List<Company> goodCompanies = companies.entrySet().stream().filter(comp -> comp.getValue().matches(ctx.getCountry())).map(comp -> comp.getKey()).collect(Collectors.toList());
            Company company = goodCompanies.get(ctx.getRandom().nextInt(goodCompanies.size()));
            company.setLogo(new Logo.Generator().generate(ctx));
            company.setIdNumbers(new IDNumbers.Generator().generate(ctx));
            company.setContact(new ContactNumber.Generator().generate(ctx));
            return company;
        }
    }

}
