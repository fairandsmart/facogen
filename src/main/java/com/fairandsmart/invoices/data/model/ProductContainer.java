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

    public ProductContainer() {
    }

    public void generateRandomProduct() throws Exception {


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
            Product electibleLogo = products.get(ctx.getRandom().nextInt(products.size()));
            return new ProductContainer();
        }

    }

}
