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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mifmif.common.regex.Generex;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;


public class ProductContainer {

    private List<Product> products = new ArrayList<Product>();
    private float totalWithTax;
    private float totalWithoutTax;
    private String currency;
    private String descHead;
    private String qtyHead;
    private String unitPriceHead;
    private String taxRateHead;
    private String taxHead;
    private String lineTotalHead;
    private String withoutTaxTotalHead;
    private String taxTotalHead;
    private String withTaxTotalHead;
    private String snHead;

    //Added
    private float totalEcoParticipation;
    private float totalDiscount;
    private float totalDeliveryCost;

    public ProductContainer(String currency, String descHead, String qtyHead, String unitPriceHead, String taxRateHead,
                            String taxHead, String lineTotalHead, String withoutTaxTotalHead, String taxTotalHead, String withTaxTotalHead, String snHead) {
        this.setCurrency(currency);
        this.descHead = descHead;
        this.qtyHead = qtyHead;
        this.unitPriceHead = unitPriceHead;
        this. taxRateHead = taxRateHead;
        this.taxHead = taxHead;
        this.lineTotalHead = lineTotalHead;
        this.withoutTaxTotalHead = withoutTaxTotalHead;
        this.taxTotalHead = taxTotalHead;
        this.withTaxTotalHead = withTaxTotalHead;
        this.snHead = snHead;
    }

    public void addProduct(Product product) {
        products.add(product);
        totalWithTax = totalWithTax + ( product.getQuantity() * product.getPriceWithTax());
        totalWithoutTax = totalWithoutTax + ( product.getQuantity() * product.getPriceWithoutTax());
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public float getTotalWithTax() {
        return totalWithTax;
    }

    public String getFormatedTotalWithTax() {
        return String.format("%.2f", this.getTotalWithTax()) + " " + currency;
    }

    public void setTotalWithTax(float totalWithTax) {
        this.totalWithTax = totalWithTax;
    }

    public float getTotalWithoutTax() {
        return totalWithoutTax;
    }

    public String getFormatedTotalWithoutTax() {
        return String.format("%.2f", this.getTotalWithoutTax()) + " " + currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setTotalWithoutTax(float totalWithoutTax) {
        this.totalWithoutTax = totalWithoutTax;
    }

    public float getTotalTax() {
        return totalWithTax - totalWithoutTax;
    }

    public String getFormatedTotalTax() {
        return String.format("%.2f", this.getTotalTax()) + " " + currency;
    }

    public String getDescHead() {
        return descHead;
    }

    public String getQtyHead() {
        return qtyHead;
    }

    public String getUPHead() {
        return unitPriceHead;
    }

    public String getTaxRateHead() {
        return taxRateHead;
    }

    public String getTaxHead() {
        return taxHead;
    }

    public String getsnHead() {
        return snHead;
    }

    public String getLineTotalHead() {
        return lineTotalHead;
    }

    public String getTotalWithoutTaxHead() {
        return withoutTaxTotalHead;
    }

    public String getTotalTaxHead() {
        return taxTotalHead;
    }

    public String getTotalAmountHead() {
        return withTaxTotalHead;
    }

    @Override
    public String toString() {
        return "ProductContainer{" +
                "products=" + products +
                ", totalWithTax=" + totalWithTax +
                ", totalWithoutTax=" + totalWithoutTax +
                '}';
    }

    public static class Generator implements ModelGenerator<ProductContainer> {

        private static final Map<String, String> descHeads = new LinkedHashMap<>();
        private static final Map<String, String> qtyHeads = new LinkedHashMap<>();
        private static final Map<String, String> unitPriceHeads = new LinkedHashMap<>();
        private static final Map<String, String> taxRateHeads = new LinkedHashMap<>();
        private static final Map<String, String> taxHeads = new LinkedHashMap<>();
        private static final Map<String, String> lineTotalHeads = new LinkedHashMap<>();
        private static final Map<String, String> snHeads = new LinkedHashMap<>();
        private static final Map<String, String> withoutTaxTotalHeads = new LinkedHashMap<>();
        private static final Map<String, String> taxTotalHeads = new LinkedHashMap<>();
        private static final Map<String, String> withTaxTotalHeads = new LinkedHashMap<>();

        {
            descHeads.put("Désignation", "fr");
            descHeads.put("Description", "fr");
            descHeads.put("Désignation du Produit", "fr");
            descHeads.put("Descriptions", "en");
            descHeads.put("Product Description", "en");
//            descHeads.put("Invoice Address", "en");
//            descHeads.put("Bill To", "en");
//            descHeads.put("Billed To", "en");
//            descHeads.put("Billing Address", "en");
//            descHeads.put("Sold To", "en");
        }

        {
            snHeads.put("Non.", "fr");
//            snHeads.put("Nombre", "fr");
            snHeads.put("S.Non.", "fr");
            snHeads.put("Numéro de série", "fr");
            snHeads.put("No.", "en");
//            snHeads.put("Number", "en");
            snHeads.put("S.No.", "en");
            snHeads.put("Serial Number", "en");
        }

        {
            qtyHeads.put("Qté", "fr");
            qtyHeads.put("Quantité", "fr");
          //  qtyHeads.put("Adresse de Livraison", "fr");
            qtyHeads.put("Qty", "en");
            qtyHeads.put("Quantity", "en");
//            qtyHeads.put("Delivery Address", "en");
//            qtyHeads.put("Ship To", "en");
//            qtyHeads.put("Shipped To", "en");
//            qtyHeads.put("Shipping Address", "en");
//            qtyHeads.put("Send To", "en");
        }

        {
            unitPriceHeads.put("PU", "fr");
            unitPriceHeads.put("Prix Unitaire", "fr");
            unitPriceHeads.put("P.U. HT", "fr");
            unitPriceHeads.put("P.U.", "fr");
            unitPriceHeads.put("U.P.", "en");
            unitPriceHeads.put("Unit Price", "en");
            unitPriceHeads.put("Price per unit", "en");
//            unitPriceHeads.put("Ship To", "en");
//            unitPriceHeads.put("Shipped To", "en");
//            unitPriceHeads.put("Shipping Address", "en");
//            unitPriceHeads.put("Send To", "en");
        }

        {
            taxRateHeads.put("TVA", "fr");
            taxRateHeads.put("Taux de TVA", "fr");
//            taxRateHeads.put("Adresse Livraison", "fr");
//            taxRateHeads.put("Adresse de Livraison", "fr");
            taxRateHeads.put("VAT/TVA", "en");
            taxRateHeads.put("TVA Rate", "en");
//            taxRateHeads.put("Delivery Address", "en");
//            taxRateHeads.put("Ship To", "en");
//            taxRateHeads.put("Shipped To", "en");
//            taxRateHeads.put("Shipping Address", "en");
//            taxRateHeads.put("Send To", "en");
        }

        {
            taxHeads.put("TVA", "fr");
            taxHeads.put("Montant TVA", "fr");
            //taxHeads.put("Adresse de Livraison", "fr");
            taxHeads.put("VAT/TVA", "en");
            taxHeads.put("TVA Amount", "en");
//            taxHeads.put("Delivery Address", "en");
//            taxHeads.put("Ship To", "en");
//            taxHeads.put("Shipped To", "en");
//            taxHeads.put("Shipping Address", "en");
//            taxHeads.put("Send To", "en");
        }

        {
            lineTotalHeads.put("Montant H.T.", "fr");
            lineTotalHeads.put("Montant HT", "fr");
         //  lineTotalHeads.put("Adresse de Livraison", "fr");
            lineTotalHeads.put("Amount", "en");
            lineTotalHeads.put("Total", "en");
//            lineTotalHeads.put("Delivery Address", "en");
//            lineTotalHeads.put("Ship To", "en");
//            lineTotalHeads.put("Shipped To", "en");
//            lineTotalHeads.put("Shipping Address", "en");
//            lineTotalHeads.put("Send To", "en");
        }

        {
            withoutTaxTotalHeads.put("Montant H.T.", "fr");
            withoutTaxTotalHeads.put("Montant HT", "fr");
          //  withoutTaxTotalHeads.put("Adresse de Livraison", "fr");
            withoutTaxTotalHeads.put("Amount", "en");
            withoutTaxTotalHeads.put("Total", "en");
            withoutTaxTotalHeads.put("Total without tax", "en");
//            withoutTaxTotalHeads.put("Ship To", "en");
//            withoutTaxTotalHeads.put("Shipped To", "en");
//            withoutTaxTotalHeads.put("Shipping Address", "en");
//            withoutTaxTotalHeads.put("Send To", "en");
        }

        {
            taxTotalHeads.put("Montant TVA", "fr");
            taxTotalHeads.put("TVA", "fr");
      //      taxTotalHeads.put("Adresse de Livraison", "fr");
            taxTotalHeads.put("TVA Amount", "en");
            taxTotalHeads.put("Tax Amount", "en");
            taxTotalHeads.put("Total Tax", "en");
//            taxTotalHeads.put("Ship To", "en");
//            taxTotalHeads.put("Shipped To", "en");
//            taxTotalHeads.put("Shipping Address", "en");
//            taxTotalHeads.put("Send To", "en");
        }

        {
            withTaxTotalHeads.put("Montant TTC", "fr");
            withTaxTotalHeads.put("Total TTC", "fr");
            withTaxTotalHeads.put("Net à payer", "fr");
            withTaxTotalHeads.put("Total Amount", "en");
            withTaxTotalHeads.put("Amount to pay", "en");
            withTaxTotalHeads.put("Total Net", "en");
//            withTaxTotalHeads.put("Ship To", "en");
//            withTaxTotalHeads.put("Shipped To", "en");
//            withTaxTotalHeads.put("Shipping Address", "en");
//            withTaxTotalHeads.put("Send To", "en");
        }

        private List<Product> products;
        private static final String productsFile = "product/boulangerProductsFlat.json";
        {
            Reader jsonReader = new InputStreamReader(ProductContainer.class.getClassLoader().getResourceAsStream(productsFile));
            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<Product>>(){}.getType();
            products = gson.fromJson(jsonReader, collectionType);
        }

        @Override
        public ProductContainer generate(GenerationContext ctx) {

            List<String> localdescHeads = descHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localqtyHeads = qtyHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localUPHeads = unitPriceHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localtaxRateHeads = taxRateHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localtaxHeads = taxHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> locallineTotalHeads = lineTotalHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localwithoutTaxTotalHeads = withoutTaxTotalHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localTaxTotalHeads = taxTotalHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localwithTaxTotalHeads = withTaxTotalHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localSNHeads = snHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxL = ctx.getRandom().nextInt(localqtyHeads.size());


            int maxProduct = 6;
            ProductContainer productContainer = new ProductContainer(ctx.getCurrency(), localdescHeads.get(idxL), localqtyHeads.get(idxL),
                                                localUPHeads.get(idxL), localtaxRateHeads.get(idxL), localtaxHeads.get(idxL), locallineTotalHeads.get(idxL),
                                                localwithoutTaxTotalHeads.get(idxL), localTaxTotalHeads.get(idxL), localwithTaxTotalHeads.get(idxL), localSNHeads.get(idxL));
            for (int i = 0; i < ctx.getRandom().nextInt(maxProduct -1)+1; i++) {
                int maxQuantity = 5;
                Product electibleProduct = products.get(ctx.getRandom().nextInt(products.size()));
                electibleProduct.setQuantity(ctx.getRandom().nextInt(maxQuantity -1) +1);
                electibleProduct.setCurrency(ctx.getCurrency());

                productContainer.addProduct(electibleProduct);
            }
           // System.out.println(productContainer);
            return productContainer;
        }

    }

}
