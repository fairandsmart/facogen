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



import com.fairandsmart.generator.documents.data.generator.ModelGenerator;
import com.fairandsmart.generator.documents.data.generator.GenerationContext;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
    private String discountHead;
    private Boolean discountAvailable;
    private Boolean taxRateAvailable;
    private float totalDiscount;
    private String totalDiscountFomated;
    //Added
    private float totalEcoParticipation;
    //private float totalDiscount;
    private float totalDeliveryCost;

    public ProductContainer(String currency, String descHead, String qtyHead, String unitPriceHead, String taxRateHead,
                            String taxHead, String lineTotalHead, String withoutTaxTotalHead, String taxTotalHead, String withTaxTotalHead, String snHead, String discountHead) {
        this.setCurrency(currency);
        this.descHead = descHead;
        this.qtyHead = qtyHead;
        this.unitPriceHead = unitPriceHead;
        this.taxRateHead = taxRateHead;
        this.taxHead = taxHead;
        this.lineTotalHead = lineTotalHead;
        this.withoutTaxTotalHead = withoutTaxTotalHead;
        this.taxTotalHead = taxTotalHead;
        this.withTaxTotalHead = withTaxTotalHead;
        this.snHead = snHead;
        this.discountHead =discountHead;
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

    public void setDiscountAvailable(Boolean discountAvailable) {
        this.discountAvailable = discountAvailable;
    }

    public Boolean getDiscountAvailable() {
        return discountAvailable;
    }

    public float getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(float totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getTotalDiscountFomated() {
        return String.format("%.2f", this.getTotalDiscount()) + " " + currency;
    }

    public void setTotalDiscountFomated(String totalDiscountFomated) {
        this.totalDiscountFomated = totalDiscountFomated;
    }

    public String getDiscountHead() {
        return discountHead;
    }

    public String getTaxTotalHead() {
        return taxTotalHead;
    }

    public Boolean getTaxRateAvailable() {
        return taxRateAvailable;
    }

    public void setTaxRateAvailable(Boolean taxRateAvailable) {
        this.taxRateAvailable = taxRateAvailable;
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
        private static final Map<String, String> discountHeads = new LinkedHashMap<>();

        {
            descHeads.put("Désignation", "fr");
            descHeads.put("Description", "fr");
            descHeads.put("Désignation du Produit", "fr");
            descHeads.put("Descriptions", "en");
            descHeads.put("Product Description", "en");
        }

        {
            snHeads.put("Non.", "fr");
            snHeads.put("S.Non.", "fr");
            snHeads.put("Numéro de série", "fr");
            snHeads.put("No.", "en");
            snHeads.put("S.No.", "en");
            snHeads.put("Serial Number", "en");
        }

        {
            qtyHeads.put("Qté", "fr");
            qtyHeads.put("Quantité", "fr");
            qtyHeads.put("Qty", "en");
            qtyHeads.put("Quantity", "en");
        }

        {
            unitPriceHeads.put("UP", "fr");
            unitPriceHeads.put("Prix Unitaire", "fr");
            unitPriceHeads.put("P.U. HT", "fr");
            unitPriceHeads.put("P.U.", "fr");
            unitPriceHeads.put("U.P.", "en");
            unitPriceHeads.put("Unit Price", "en");
            unitPriceHeads.put("Price per unit", "en");
        }

        {
            taxRateHeads.put("TVA", "fr");
            taxRateHeads.put("Taux de TVA", "fr");
            taxRateHeads.put("VAT/TVA", "en");
            taxRateHeads.put("TVA Rate", "en");
        }

        {
            taxHeads.put("TVA", "fr");
            taxHeads.put("Montant TVA", "fr");
            taxHeads.put("VAT/TVA", "en");
            taxHeads.put("TVA Amount", "en");
        }

        {
            lineTotalHeads.put("Montant H.T.", "fr");
            lineTotalHeads.put("Montant HT", "fr");
            lineTotalHeads.put("Amount", "en");
            lineTotalHeads.put("Total", "en");
        }

        {
            withoutTaxTotalHeads.put("Montant H.T.", "fr");
            withoutTaxTotalHeads.put("Montant HT", "fr");
            withoutTaxTotalHeads.put("Amount", "en");
            withoutTaxTotalHeads.put("Total", "en");
            withoutTaxTotalHeads.put("Total without tax", "en");
        }

        {
            taxTotalHeads.put("Montant TVA", "fr");
            taxTotalHeads.put("TVA", "fr");
            taxTotalHeads.put("TVA Amount", "en");
            taxTotalHeads.put("Tax Amount", "en");
            taxTotalHeads.put("Total Tax", "en");
        }

        {
            withTaxTotalHeads.put("Montant TTC", "fr");
            withTaxTotalHeads.put("Total TTC", "fr");
            withTaxTotalHeads.put("Net à payer", "fr");
            withTaxTotalHeads.put("Total Amount", "en");
            withTaxTotalHeads.put("Amount to pay", "en");
            withTaxTotalHeads.put("Total Net", "en");
        }
        {
            discountHeads.put("@DISC", "en");
            discountHeads.put("Discount", "en");
            discountHeads.put("DISC", "en");
            discountHeads.put("TOTAL REMISE IMMEDIATE", "fr");
        }

        private List<Product> products;
        private static final String productsFile = "invoices/product/householdandmedia.json";
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
            List<String> localdiscountHeads = discountHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());

            int idxL = ctx.getRandom().nextInt(localqtyHeads.size());
            int idxD = ctx.getRandom().nextInt(localdiscountHeads.size());


            int maxProduct = 6;
            ProductContainer productContainer = new ProductContainer(ctx.getCurrency(), localdescHeads.get(idxL), localqtyHeads.get(idxL),
                                                localUPHeads.get(idxL), localtaxRateHeads.get(idxL), localtaxHeads.get(idxL), locallineTotalHeads.get(idxL),
                                                localwithoutTaxTotalHeads.get(idxL), localTaxTotalHeads.get(idxL), localwithTaxTotalHeads.get(idxL), localSNHeads.get(idxL),localdiscountHeads.get(idxD));
            for (int i = 0; i < ctx.getRandom().nextInt(maxProduct -1)+1; i++) {
                int maxQuantity = 5;
                Product electibleProduct = products.get(ctx.getRandom().nextInt(products.size()));
                electibleProduct.setQuantity(ctx.getRandom().nextInt(maxQuantity -1) +1);
                electibleProduct.setCurrency(ctx.getCurrency());

                productContainer.addProduct(electibleProduct);
            }
            Boolean discountAvailable = ctx.getRandom().nextBoolean();
            productContainer.setDiscountAvailable(discountAvailable);
            Float total = productContainer.getTotalWithTax();
            if(discountAvailable){
                /// discount
                Float discout =0.0f;
                /*if(ctx.getRandom().nextBoolean()){
                    discout = ctx.getRandom().nextFloat()*0.4f*total;
                }*/
                String discountS = String.format("%.2f", discout);
                productContainer.setTotalDiscount(discout);
                total = total - discout;
                productContainer.setTotalWithTax(total);
            }
            Boolean taxRateAvailable = ctx.getRandom().nextBoolean();
            productContainer.setTaxRateAvailable(taxRateAvailable);

            return productContainer;
        }

    }

}
