package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;
import com.mifmif.common.regex.Generex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PhoneNumber {

    private String label;
    private String value;

    public PhoneNumber(String label, String value) {
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
        return "PhoneNumber{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<PhoneNumber> {

        private static final Map<String, String> formats = new HashMap<>();
        private static final Map<String, String> labels = new HashMap<>();
        {
            formats.put("0[0-9]{1}[.][0-9]{2}[.][0-9]{2}[.][0-9]{2}[.][0-9]{2}", "FR");
            formats.put("0[0-9]{1}[-][0-9]{2}[-][0-9]{2}[-][0-9]{2}[-][0-9]{2}", "FR");
            formats.put("+33 (0) [0-9]{3} [0-9]{3} [0-9]{3}", "FR");
            formats.put("+33 [(]0[)] [0-9]{1} [0-9]{2} [0-9]{2} [0-9]{2} [0-9]{2}", "FR");
        }
        {
            labels.put("Tel", "fr");
            labels.put("Téléphone", "fr");
            labels.put("Phone", "en");
        }

        @Override
        public PhoneNumber generate(GenerationContext ctx) {
            List<String> localizedFormats = formats.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getCountry())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxF = ctx.getRandom().nextInt(localizedFormats.size());
            Generex generex = new Generex(localizedFormats.get(idxF));
            String generated = generex.random();

            List<String> localizedLabels = labels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxL = ctx.getRandom().nextInt(localizedLabels.size());
            return new PhoneNumber(localizedLabels.get(idxL), generated);
        }
    }

}
