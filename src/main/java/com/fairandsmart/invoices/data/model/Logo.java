package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class Logo {

    private String fullPath;
    private String name;

    public Logo() {
    }

    public Logo(String fullPath, String name) {
        this.fullPath = fullPath;
        this.name = name;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Logo{" +
                "fullPath='" + fullPath + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<Logo> {

        private static final String brandsFile = "logo/result.json";
        private static List<Logo> logos;
        {
            Reader jsonReader = new InputStreamReader(Logo.class.getClassLoader().getResourceAsStream(brandsFile));
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<Logo>>(){}.getType();
            logos = gson.fromJson(jsonReader, collectionType);
        }

        @Override
        public Logo generate(GenerationContext ctx) {
            Logo electibleLogo;
            List<Logo> electibleLogos = logos.stream().filter(logo -> logo.name.matches(ctx.getBrandName())).collect(Collectors.toList());
            if ( electibleLogos.size() == 0 ) {
                electibleLogo = electibleLogos.get(ctx.getRandom().nextInt(electibleLogos.size()));
            } else {
                electibleLogo = logos.get(ctx.getRandom().nextInt(logos.size()));
            }
            return electibleLogo;
        }
    }

}
