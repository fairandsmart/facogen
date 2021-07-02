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
 * Djedjiga Belhadj <djedjiga.belhadj@gmail.com> / Loria
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


import com.fairandsmart.generator.documents.data.generator.GenerationContext;
import com.fairandsmart.generator.documents.data.generator.ModelGenerator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import java.text.DecimalFormat;

public class ProductReceiptContainer {

    private List<Product> products = new ArrayList<Product>();
    private float totalWithTax;
    private float totalTaxRate;
    private int totalItems;
    private int totalQty;
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
    private String roundedHead;
    private String roundingHead;
    private String cashHead;
    private String changeHead;
    private String qtyTotalHead;
    private String itemsTotalHead;
    private String GSTHead;
    private Boolean roundAvailable;
    private Boolean discountAvailable;
    private Boolean totaltaxAvailable;
    //Added
    private float totalEcoParticipation;
    private String totalDiscount;
    private String totalRounded;
    private String totalRounding;
    private String cash;
    private String change;
    private float totalDeliveryCost;

    public ProductReceiptContainer(String currency, String descHead, String qtyHead, String unitPriceHead, String taxRateHead,
                                   String taxHead, String lineTotalHead, String withoutTaxTotalHead, String taxTotalHead, String withTaxTotalHead, String snHead,
                                   String discountHead, String roundedHead, String roundingHead, String cashHead, String changeHead,
                                   String qtyTotalHead,String itemsTotalHead,String GSTHead) {
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
        this.discountHead = discountHead;
        this.roundedHead = roundedHead;
        this.roundingHead = roundingHead;
        this.cashHead = cashHead;
        this.changeHead = changeHead;
        this.qtyTotalHead = qtyTotalHead;
        this.itemsTotalHead = itemsTotalHead;
        this.GSTHead = GSTHead;
    }

    public void addProduct(Product product) {
        products.add(product);
        totalWithTax = totalWithTax + ( product.getQuantity() * product.getPriceWithTax());
        totalWithoutTax = totalWithoutTax + ( product.getQuantity() * product.getPriceWithoutTax());
        totalItems +=1;
        totalQty += product.getQuantity();
        totalTaxRate = (totalTaxRate+product.getTaxRate())/2;
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

    public String getDiscountHead() {
        return discountHead;
    }

    public String  getTotalDiscount() {
        return totalDiscount;
    }

    public void setDiscountHead(String discountHead) {
        this.discountHead = discountHead;
    }

    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getRoundedHead() {
        return roundedHead;
    }

    public String getTotalRounded() {
        return totalRounded;
    }

    public void setRoundedHead(String roundedHead) {
        this.roundedHead = roundedHead;
    }

    public void setTotalRounded(String totalRounded) {
        this.totalRounded = totalRounded;
    }

    public String getRoundingHead() {
        return roundingHead;
    }

    public String getTotalRounding() {
        return totalRounding;
    }

    public void setRoundingHead(String roundingHead) {
        this.roundingHead = roundingHead;
    }

    public void setTotalRounding(String totalRounding) {
        this.totalRounding = totalRounding;
    }

    public String getCashHead() {
        return cashHead;
    }

    public String getChangeHead() {
        return changeHead;
    }

    public String getCash() {
        return cash;
    }

    public String getChange() {
        return change;
    }

    public void setCashHead(String cashHead) {
        this.cashHead = cashHead;
    }

    public void setChangeHead(String changeHead) {
        this.changeHead = changeHead;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public float getTotalTaxRate() {
        return totalTaxRate;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalTaxRate(float totalTaxRate) {
        this.totalTaxRate = totalTaxRate;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public String getQtyTotalHead() {
        return qtyTotalHead;
    }

    public String getItemsTotalHead() {
        return itemsTotalHead;
    }

    public String getGSTHead() {
        return GSTHead;
    }

    public void setRoundAvailable(Boolean roundAvailable) {
        this.roundAvailable = roundAvailable;
    }

    public void setDiscountAvailable(Boolean discountAvailable) {
        this.discountAvailable = discountAvailable;
    }

    public void setTotaltaxAvailable(Boolean totaltaxAvailable) {
        this.totaltaxAvailable = totaltaxAvailable;
    }

    public Boolean getRoundAvailable() {
        return roundAvailable;
    }

    public Boolean getDiscountAvailable() {
        return discountAvailable;
    }

    public Boolean getTotaltaxAvailable() {
        return totaltaxAvailable;
    }

    @Override
    public String toString() {
        return "ProductContainer{" +
                "products=" + products +
                ", totalWithTax=" + totalWithTax +
                ", totalWithoutTax=" + totalWithoutTax +
                '}';
    }

    public static class Generator implements ModelGenerator<ProductReceiptContainer> {

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
        ////
        private static final Map<String, String> discountHeads = new LinkedHashMap<>();
        private static final Map<String, String> roundingHeads = new LinkedHashMap<>();
        private static final Map<String, String> roundedHeads = new LinkedHashMap<>();
        private static final Map<String, String> cashHeads = new LinkedHashMap<>();
        private static final Map<String, String> changeHeads = new LinkedHashMap<>();
        private static final Map<String, String> qtyTotalHeads = new LinkedHashMap<>();
        private static final Map<String, String> itemsTotalHeads = new LinkedHashMap<>();
        private static final Map<String, String> withoutGSTtotalHeads = new LinkedHashMap<>();
        private static final Map<String, String> withGSTtotalHeads = new LinkedHashMap<>();
        private static final Map<String, String> GSTHeads = new LinkedHashMap<>();


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
            taxRateHeads.put("Taux", "fr");
            taxRateHeads.put("%", "fr");
            taxRateHeads.put("Taux de TVA", "fr");
            taxRateHeads.put("VAT/TVA", "en");
            taxRateHeads.put("%", "en");
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
            withTaxTotalHeads.put("Total Sales", "en");
            withTaxTotalHeads.put("Total", "en");
            withTaxTotalHeads.put("TOTAL AMT", "en");
            withTaxTotalHeads.put("GRAND TOTAL", "en");

        }

        {
            discountHeads.put("@DISC", "en");
            discountHeads.put("Discount", "en");
            discountHeads.put("DISC", "en");
            discountHeads.put("TOTAL REMISE IMMEDIATE", "fr");
        }

        {
            roundingHeads.put("Rounding adj", "en");
            roundingHeads.put("Rounding Adjustment", "en");
            roundingHeads.put("Rounding", "en");
            roundingHeads.put("Arrondi", "fr");
        }

        {
            roundedHeads.put("Rounded total", "en");
            roundedHeads.put("Total Rounded", "en");
            roundedHeads.put("Total", "en");
            roundedHeads.put("Total Sales", "en");
            roundedHeads.put("Total", "fr");
        }

        {
            cashHeads.put("Cash", "en");
            cashHeads.put("Cash Received", "en");
            cashHeads.put("Cash Tendered", "en");
            cashHeads.put("ESPECES", "fr");
            cashHeads.put("Reçu", "fr");
        }

        {
            changeHeads.put("CHANGE", "en");
            changeHeads.put("Change", "en");
            changeHeads.put("Votre monnaie", "fr");
            changeHeads.put("Rendu", "fr");
        }

        {
            qtyTotalHeads.put("Total Qty", "en");
            qtyTotalHeads.put("Qty(s)", "en");
            qtyTotalHeads.put("Qty(s)", "fr");
        }

        {
            itemsTotalHeads.put("Total Items", "en");
            itemsTotalHeads.put("Item(s)", "en");
            itemsTotalHeads.put("Article(s)", "fr");
        }

        {
            withoutGSTtotalHeads.put("Total Sales", "en");
            withoutGSTtotalHeads.put("SUB TOTAL", "en");
            withoutGSTtotalHeads.put("Total Sales(Excluding GST)", "en");
            withoutGSTtotalHeads.put("SUB TOTAL", "fr");
        }

        {
            withGSTtotalHeads.put("Total (incl GST)", "en");
            withGSTtotalHeads.put("Total Sales(Inclusive of GST)", "en");
            withGSTtotalHeads.put("Total Sales(Including GST)", "en");
            withGSTtotalHeads.put("Total", "en");
            withGSTtotalHeads.put("Total Sales(Including GST)", "fr");
        }

        {
            GSTHeads.put("Total GST", "en");
            GSTHeads.put("GST Summary", "en");
            GSTHeads.put("GST", "en");
            GSTHeads.put("Code TVA", "fr");
            GSTHeads.put("TVA", "fr");
        }

        private List<Product> products;
        private static final String productsFile = "invoices/product/householdandmedia.json";
        {
            Reader jsonReader = new InputStreamReader(ProductReceiptContainer.class.getClassLoader().getResourceAsStream(productsFile));
            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<Product>>(){}.getType();
            products = gson.fromJson(jsonReader, collectionType);
        }

        @Override
        public ProductReceiptContainer generate(GenerationContext ctx) {

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
            List<String> localroundedHeads = roundedHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localroundingHeads = roundingHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localCashHeads = cashHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localChangeHeads = changeHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());

            List<String> localqtyTotalHeads = qtyTotalHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localitemsTotalHeads = itemsTotalHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localGSTHeads = GSTHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());


            int idxL = ctx.getRandom().nextInt(localqtyHeads.size());
            int idxD = ctx.getRandom().nextInt(localdiscountHeads.size());
            int idxRed = ctx.getRandom().nextInt(localroundedHeads.size());
            int idxRing = ctx.getRandom().nextInt(localroundingHeads.size());
            int idxCsh = ctx.getRandom().nextInt(localCashHeads.size());
            int idxCh = ctx.getRandom().nextInt(localChangeHeads.size());

            int idxTQty = ctx.getRandom().nextInt(localqtyTotalHeads.size());
            int idxTItm = ctx.getRandom().nextInt(localitemsTotalHeads.size());
            int idxGST = ctx.getRandom().nextInt(localGSTHeads.size());


            int maxProduct = 6;
            ProductReceiptContainer productContainer = new ProductReceiptContainer(ctx.getCurrency(), localdescHeads.get(idxL), localqtyHeads.get(idxL),
                                                localUPHeads.get(idxL), localtaxRateHeads.get(idxL), localtaxHeads.get(idxL), locallineTotalHeads.get(idxL),
                                                localwithoutTaxTotalHeads.get(idxL), localTaxTotalHeads.get(idxL), localwithTaxTotalHeads.get(idxL), localSNHeads.get(idxL), localdiscountHeads.get(idxD),
                                                localroundedHeads.get(idxRed),localroundingHeads.get(idxRing),localCashHeads.get(idxCsh), localChangeHeads.get(idxCh),
                                                localqtyTotalHeads.get(idxTQty),localitemsTotalHeads.get(idxTItm),localGSTHeads.get(idxGST));
            for (int i = 0; i < ctx.getRandom().nextInt(maxProduct -1)+1; i++) {
                int maxQuantity = 5;
                Product electibleProduct = products.get(ctx.getRandom().nextInt(products.size()));
                electibleProduct.setQuantity(ctx.getRandom().nextInt(maxQuantity -1) +1);
                electibleProduct.setCurrency(ctx.getCurrency());

                productContainer.addProduct(electibleProduct);
            }
            /// Bollans Availables
            Boolean discountAvailable = ctx.getRandom().nextBoolean();
            Boolean roundAvailable = ctx.getRandom().nextBoolean();
            Boolean totalTaxAvailable = ctx.getRandom().nextBoolean();
            productContainer.setDiscountAvailable(discountAvailable);
            productContainer.setRoundAvailable(roundAvailable);
            productContainer.setTotaltaxAvailable(totalTaxAvailable);

            /// get Total
            Float total = productContainer.getTotalWithTax();
            Float totalCp = total;
            /// Rounded
            if(discountAvailable){
                /// discount
                Float discout =0.0f;
                if(ctx.getRandom().nextBoolean()){
                    discout = ctx.getRandom().nextFloat()*0.4f*total;
                }
                String discountS = String.format("%.2f", discout);
                productContainer.setTotalDiscount(discountS);
                total = total - discout;
            }

            /// Rounded
            if(roundAvailable) {
                if(ctx.getRandom().nextBoolean()) {
                    BigDecimal bigDecimal = new BigDecimal(Float.toString(total));
                    bigDecimal = bigDecimal.setScale(1, RoundingMode.DOWN);
                    total = bigDecimal.floatValue();
                }
                String roundedS = String.format("%.2f", total);
                productContainer.setTotalRounded(roundedS);

                // Rounding
                Float roundingF = totalCp - total;
                String roundingS = String.format("%.2f", roundingF);
                productContainer.setTotalRounding(roundingS);
            }
            // Cash
            BigDecimal bigDecimal1 = new BigDecimal(total);
            bigDecimal1 = bigDecimal1.setScale(0, RoundingMode.UP);
            float cashF = bigDecimal1.floatValue();
            String cashS = String.format("%.2f", cashF);
            productContainer.setCash(cashS);

            // Change
            Float changeF = cashF - total;
            String changeS = String.format("%.2f", changeF);
            productContainer.setChange(changeS);

            return productContainer;
        }

    }

}
