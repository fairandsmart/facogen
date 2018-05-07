package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;
import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.logging.Logger;

public class Client {

    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    private String billingName;
    private Address billingAddress;
    private String shippingName;
    private Address shippingAddress;

    //Added
    private String clientNumber;
    private String clientReference;

    public Client(String billingName, Address billingAddress, String shippingName, Address shippingAddress) {
        this.billingName = billingName;
        this.billingAddress = billingAddress;
        this.shippingName = shippingName;
        this.shippingAddress = shippingAddress;
    }

    public Client() {
    }

    public String getBillingName() {
        return billingName;
    }

    public void setBillingName(String billingName) {
        this.billingName = billingName;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getShippingName() {
        return shippingName;
    }

    public void setShippingName(String shippingName) {
        this.shippingName = shippingName;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    @Override
    public String toString() {
        return "Client{" +
                "billingName='" + billingName + '\'' +
                ", billingAddress=" + billingAddress +
                ", shippingName='" + shippingName + '\'' +
                ", shippingAddress=" + shippingAddress +
                '}';
    }

    public static class Generator implements ModelGenerator<Client> {

        @Override
        public Client generate(GenerationContext ctx) {
            Faker faker = Faker.instance(Locale.forLanguageTag(ctx.getLanguage()));
            String name = faker.name().fullName();
            Address address = new Address.Generator().generate(ctx);
            Client client = new Client();
            client.setBillingName(name);
            client.setBillingAddress(address);
            client.setShippingName(name);
            client.setShippingAddress(address);

            int idxOtherAddress = ctx.getRandom().nextInt(25);
            if ( idxOtherAddress > 20 ) {
                Address shipAddress = new Address.Generator().generate(ctx);
                client.setShippingAddress(shipAddress);
            }

            int idxOtherName = ctx.getRandom().nextInt(50);
            if ( idxOtherName > 48 ) {
                String shipName = faker.name().fullName();
                client.setShippingName(shipName);
            }

            return client;
        }
    }
}
