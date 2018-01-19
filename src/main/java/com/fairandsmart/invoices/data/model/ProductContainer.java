package com.fairandsmart.invoices.data.model;



import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mifmif.common.regex.Generex;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ProductContainer {

    private List<Product> products = new ArrayList<Product>();
    private float totalWithTax;
    private float totalWithoutTax;

    public ProductContainer() {

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

    public void setTotalWithTax(float totalWithTax) {
        this.totalWithTax = totalWithTax;
    }

    public float getTotalWithoutTax() {
        return totalWithoutTax;
    }

    public void setTotalWithoutTax(float totalWithoutTax) {
        this.totalWithoutTax = totalWithoutTax;
    }

    public float getTotalTax() {
        return totalWithTax - totalWithoutTax;
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
            ProductContainer productContainer = new ProductContainer();
            for (int i = 0; i < ctx.getRandom().nextInt(maxProduct -1)+1; i++) {
                int maxQuantity = 5;
                Product electibleProduct = products.get(ctx.getRandom().nextInt(products.size()));
                electibleProduct.setQuantity(ctx.getRandom().nextInt(maxQuantity -1) +1);
                productContainer.addProduct(electibleProduct);
            }
            return productContainer;
        }

    }

}
