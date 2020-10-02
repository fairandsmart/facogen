package com.fairandsmart.generator.documents.data.model;

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
 * Copyright (C) 2019 - 2020 Fair And Smart
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

import com.fairandsmart.generator.documents.data.generator.ModelGenerator;
import com.fairandsmart.generator.documents.data.generator.GenerationContext;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Employer {

    private static final Logger LOGGER = Logger.getLogger(Employer.class.getName());

    private Logo logo;
    private IDNumbers idNumbers;
    private String name;
    private Address address;
    private ContactNumber contact;
    // TODO Company email and website needed, should correspond to company name
    private String email;
    private String website;

    public Employer() {
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
        return "Employer{" +
                "logo=" + logo +
                ", idNumbers=" + idNumbers +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", contact=" + contact +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<Employer> {

        private static final String employersFileFR = "common/company/companies_fr.csv";
        private static Map<Employer, String> employers = new HashMap<>();
        {
            try {
                Reader in = new InputStreamReader(Logo.class.getClassLoader().getResourceAsStream(employersFileFR));
                Iterable<CSVRecord> records = CSVFormat.newFormat(';').withQuote('"').withFirstRecordAsHeader().parse(in);
                for (CSVRecord record : records) {
                    String name = record.get("ENSEIGNE");
                    String adresseL1 = record.get("L4_NORMALISEE");
                    String adresseL2 = record.get("L5_NORMALISEE");
                    String cp = record.get("CODPOS");
                    String ville = record.get("LIBCOM");
                    String pays = record.get("L7_NORMALISEE");
                    if ( name.length() > 3 ) {
                        Employer emp = new Employer();
                        emp.setName(name);
                        Address companyAddress = new Address(adresseL1, adresseL2, "", cp, ville, pays);
                        emp.setAddress(companyAddress);
                        employers.put(emp, "FR");
                    }
                }
            } catch ( Exception e ) {
                LOGGER.log(Level.SEVERE, "unable to parse csv source: " + employersFileFR, e);
            }
        }


        @Override
        public Employer generate(GenerationContext ctx) {
            List<Employer> goodEmployers = employers.entrySet().stream().filter(comp -> comp.getValue().matches(ctx.getCountry())).map(comp -> comp.getKey()).collect(Collectors.toList());
            Employer employer = goodEmployers.get(ctx.getRandom().nextInt(goodEmployers.size()));
            employer.setLogo(new Logo.Generator().generate(ctx));
            employer.setIdNumbers(new IDNumbers.Generator().generate(ctx));
            employer.setContact(new ContactNumber.Generator().generate(ctx));
            return employer;
        }
    }

}
