package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;
import com.mifmif.common.regex.Generex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VATNumber {

    private String value;
    private String label;

    public VATNumber(String value, String label) {
        this.value = value;
        this.label = label;
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
        return "VATNumber{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<VATNumber> {

        private static final Map<String, String> formats = new HashMap<>();
        private static final Map<String, String> labels = new HashMap<>();

        {
            formats.put("FR[0-9]{11}", "FR");
            formats.put("LU[0-9]{8}", "LU");
            // formats.put("GB[0-9]{9}", "UK");
            formats.put("GB[0-9]{3} [0-9]{4} [0-9]{2}", "UK");
            formats.put("GB[0-9]{9} [0-9]{3}", "UK");
            formats.put("BE[0-9]{10}", "BE");


        }

        {
            labels.put("VAT Number:", "en");
            labels.put("VAT", "en");
            labels.put("VAT/TIN Number:", "en");
            labels.put("Numéro de TVA", "fr");
            labels.put("TVA intracommunautaire", "fr");
            labels.put("TVA numéro ", "fr");
        }

        @Override
        public VATNumber generate(GenerationContext ctx) {

            List<String> localizedRegex = formats.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getCountry())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxR = ctx.getRandom().nextInt(localizedRegex.size());
            Generex generex = new Generex(localizedRegex.get(idxR));
            String generated = generex.random();

            List<String> localizedLabel = labels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxL = ctx.getRandom().nextInt(localizedLabel.size());
            Generex generex2 = new Generex(localizedLabel.get(idxL));
            String generatedLabel = generex2.random();
            return new VATNumber(generated, generatedLabel);
        }
    }
}
