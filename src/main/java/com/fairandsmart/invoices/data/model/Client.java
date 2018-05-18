package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;
import com.github.javafaker.Faker;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Client {

    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    private String billingHead;
    private String billingName;
    private Address billingAddress;
    private String shippingHead;
    private String shippingName;
    private Address shippingAddress;

    public Client(String billingHead, String billingName, Address billingAddress, String shippingHead,String shippingName, Address shippingAddress) {
        this.billingHead = billingHead;
        this.billingName = billingName;
        this.billingAddress = billingAddress;
        this.shippingHead = shippingHead;
        this.shippingName = shippingName;
        this.shippingAddress = shippingAddress;
    }

    public String getBillingHead() {
        return billingHead;
    }

    public void setBillingHead(String billingHead) {
        this.billingHead = billingHead;
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

    public String getShippingHead() {
        return shippingHead;
    }

    public void setShippingHead(String shippingHead) {
        this.shippingHead = shippingHead;
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
                "billingHead='" + billingHead + '\'' +
                ", billingName=" + billingName +
                ", billingAddress=" + billingAddress +
                "shippingHead='" + shippingHead + '\'' +
                ", shippingName=" + shippingName +
                ", shippingAddress=" + shippingAddress +
                '}';
    }

    public static class Generator implements ModelGenerator<Client> {

        private static final Map<String, String> billingHeads = new LinkedHashMap<>();
        private static final Map<String, String> shippingHeads = new LinkedHashMap<>();

        {
            billingHeads.put("Destinataire", "fr");
            billingHeads.put("Adresse Facturation", "fr");
            billingHeads.put("Adresse de Facturation", "fr");
            billingHeads.put("Invoice To", "en");
            billingHeads.put("Invoiced To", "en");
            billingHeads.put("Invoice Address", "en");
            billingHeads.put("Bill To", "en");
            billingHeads.put("Billed To", "en");
            billingHeads.put("Billing Address", "en");
            billingHeads.put("Sold To", "en");

        }

        {
            shippingHeads.put("Livraison à", "fr");
            shippingHeads.put("Adresse Livraison", "fr");
            shippingHeads.put("Adresse de Livraison", "fr");
            shippingHeads.put("Delivery To", "en");
            shippingHeads.put("Deliver To", "en");
            shippingHeads.put("Delivery Address", "en");
            shippingHeads.put("Ship To", "en");
            shippingHeads.put("Shipped To", "en");
            shippingHeads.put("Shipping Address", "en");
            shippingHeads.put("Send To", "en");

        }

        @Override
        public Client generate(GenerationContext ctx) {
            Faker faker = Faker.instance(Locale.forLanguageTag(ctx.getLanguage()));
            String billingName = faker.name().fullName();
            String shippingName = billingName;
            Address billingAddress = new Address.Generator().generate(ctx);
            Address shippingAddress = billingAddress;

            int idxOtherAddress = ctx.getRandom().nextInt(25);
            if ( idxOtherAddress > 20 ) {
                shippingAddress = new Address.Generator().generate(ctx);
            }

            int idxOtherName = ctx.getRandom().nextInt(50);
            if ( idxOtherName > 48 ) {
                shippingName = faker.name().fullName();
            }

            // For Address Heads
            List<String> localizedBillHeads = billingHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedShipHeads = shippingHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxA = ctx.getRandom().nextInt(localizedBillHeads.size()); // Note: Only one index for both shipping & billing, to retrieve similar format heads!

            return new Client(localizedBillHeads.get(idxA), billingName, billingAddress,
                    localizedShipHeads.get(idxA), shippingName, shippingAddress);
        }
    }
}
