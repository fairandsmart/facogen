package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;
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

public class Address {

    private static final Logger LOGGER = Logger.getLogger(Address.class.getName());

    private String line1;
    private String line2;
    private String line3;
    private String zip;
    private String city;
    private String country;

    public Address() {
    }

    public Address(String line1, String line2, String line3, String zip, String city, String country) {
        this.line1 = line1;
        this.line2 = line2;
        this.line3 = line3;
        this.zip = zip;
        this.city = city;
        this.country = country;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                ", line3='" + line3 + '\'' +
                ", zip='" + zip + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<Address> {

        private static final String[] addressFiles = new String[] { "address/france.csv", "address/luxembourg.csv", "address/belgium.csv", "address/germany.csv" };
        private static Map<Address, String> addresses = new HashMap<>();
        {
            for ( String addressFile : addressFiles ) {
                try {
                    Reader in = new InputStreamReader(Logo.class.getClassLoader().getResourceAsStream(addressFile));
                    Iterable<CSVRecord> records = CSVFormat.newFormat(',').withFirstRecordAsHeader().parse(in);
                    for (CSVRecord record : records) {
                        String number = record.get("NUMBER");
                        String street = record.get("STREET");
                        String city = record.get("CITY");
                        String postcode = record.get("POSTCODE");
                        Address address = new Address(number + ", " + street, "", "", postcode, city, "France");
                        switch ( addressFile.substring(8) ) {
                            case "france.csv" :
                                addresses.put(address, "FR");
                                break;
                            case "luxembourg.csv" :
                                addresses.put(address, "LU");
                                break;
                            case "belgium.csv" :
                                addresses.put(address, "BE");
                                break;
                            case "germany.csv" :
                                addresses.put(address, "DE");
                                break;
                        }
                    }
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "unable to parse csv source: " + addressFile, e);
                }
            }
        }


        @Override
        public Address generate(GenerationContext ctx) {
            List<Address> goodAddresses = addresses.entrySet().stream().filter(comp -> comp.getValue().matches(ctx.getCountry())).map(comp -> comp.getKey()).collect(Collectors.toList());
            Address address = goodAddresses.get(ctx.getRandom().nextInt(goodAddresses.size()));
            //TODO include a random line 3 with app n°2, etage 3...
            return address;
        }
    }
}
