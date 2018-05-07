package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BillingType {

    private String label;
    private String value;

    public BillingType(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BillingType{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<BillingType> {

        private static final Map<String, String> values = new HashMap<>();
        private static final Map<String, String> labels = new HashMap<>();
        {
            values.put("à domicile par Ducros", "fr");
        }
        {
            labels.put("Méthode de livraison", "fr");
        }

        @Override
        public BillingType generate(GenerationContext ctx) {
            List<String> localizedValues = values.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxF = ctx.getRandom().nextInt(localizedValues.size());

            List<String> localizedLabels = labels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxL = ctx.getRandom().nextInt(localizedLabels.size());
            return new BillingType(localizedLabels.get(idxL), localizedValues.get(idxF));
        }
    }

}
