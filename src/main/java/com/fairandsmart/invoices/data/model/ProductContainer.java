package com.fairandsmart.invoices.data.model;



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

    public ProductContainer(String currency) {
        this.setCurrency(currency);
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

    @Override
    public String toString() {
        return "ProductContainer{" +
                "products=" + products +
                ", totalWithTax=" + totalWithTax +
                ", totalWithoutTax=" + totalWithoutTax +
                '}';
    }

    public static class Generator implements ModelGenerator<ProductContainer> {

        private static final List<String> formats = new ArrayList<>();
        private static final Map<String, String> labels = new HashMap<>();
        {
            formats.add("[A-D][H-N]-[A-Z]{4}-[0-9]{9}-[0-9]{2}");
            formats.add("[0-9]{3}-[0-9]{7}-1[0-9]{6}");
            formats.add("[3-7][0-9]{7}");
            formats.add("1[0-9]{7}");
            formats.add("[4-9][0-9]{9}");
            formats.add("FV201[0-7]00[0-9]{6}");
            formats.add("00[0-9]{5}");
            formats.add("#FA00[0-9]{4}");
            formats.add("FC500[0-9]{3}");
            formats.add("INV-[0-9]{4}");
            formats.add("[0-9]{6}-7[0-9]{5}");
        }
        {
            labels.put("Invoice Number", "en");
            labels.put("Invoice ID", "en");
            labels.put("Numéro de facture", "fr");
            labels.put("N° facture", "fr");
            labels.put("N°", "fr");
            labels.put("N° de facture", "fr");
            labels.put("FACTURE N°", "fr");
            labels.put("Facture n°", "fr");
            labels.put("Facture-n°", "fr");
            labels.put("FACTURE No", "fr");
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

            int maxProduct = 4;
            ProductContainer productContainer = new ProductContainer(ctx.getCurrency());
            for (int i = 0; i < ctx.getRandom().nextInt(maxProduct -1)+1; i++) {
                int maxQuantity = 5;
                Product electibleProduct = products.get(ctx.getRandom().nextInt(products.size()));
                electibleProduct.setQuantity(ctx.getRandom().nextInt(maxQuantity -1) +1);
                electibleProduct.setCurrency(ctx.getCurrency());
                productContainer.addProduct(electibleProduct);
            }
            return productContainer;
        }

    }

}
