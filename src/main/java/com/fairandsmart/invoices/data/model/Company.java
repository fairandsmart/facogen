package com.fairandsmart.invoices.data.model;

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
    private VATNumber vatNumber;
    private String name;
    private Address address;
    private FaxNumber fax;
    private PhoneNumber phone;
    private String email;
    private String website;
    private String siren;
    private String nic;
    private String siret;

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

    public FaxNumber getFax() {
        return fax;
    }

    public void setFax(FaxNumber fax) {
        this.fax = fax;
    }

    public PhoneNumber getPhone() {
        return phone;
    }

    public void setPhone(PhoneNumber phone) {
        this.phone = phone;
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

    public String getSiren() {
        return siren;
    }

    public void setSiren(String siren) {
        this.siren = siren;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }

    @Override
    public String toString() {
        return "Company{" +
                "logo=" + logo +
                ", vatNumber=" + vatNumber +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", fax=" + fax +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                ", siren='" + siren + '\'' +
                ", nic='" + nic + '\'' +
                ", siret='" + siret + '\'' +
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
                    String siren = record.get("SIREN");
                    String nic = record.get("NIC");
                    String name = record.get("ENSEIGNE");
                    String adresseL1 = record.get("L4_NORMALISEE");
                    String adresseL2 = record.get("L5_NORMALISEE");
                    String cp = record.get("CODPOS");
                    String ville = record.get("LIBCOM");
                    String pays = record.get("L7_NORMALISEE");
                    if ( name.length() > 3 ) {
                        Company comp = new Company();
                        comp.setName(name);
                        comp.setSiren(siren);
                        comp.setNic(nic);
                        comp.setSiret(siren + nic);
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
            company.setVatNumber(new VATNumber.Generator().generate(ctx));
            company.setPhone(new PhoneNumber.Generator().generate(ctx));
            company.setFax(new FaxNumber.Generator().generate(ctx));
            return company;
        }
    }

}
