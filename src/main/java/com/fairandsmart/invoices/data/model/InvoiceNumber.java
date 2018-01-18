package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;
import com.mifmif.common.regex.Generex;

import java.util.*;
import java.util.stream.Collectors;

public class InvoiceNumber {

    private String label;
    private String value;

    public InvoiceNumber(String label, String value) {
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
        return "InvoiceNumber{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<InvoiceNumber> {

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

        @Override
        public InvoiceNumber generate(GenerationContext ctx) {
            int idxF = ctx.getRandom().nextInt(formats.size());
            Generex generex = new Generex(formats.get(idxF));
            String generated = generex.random();

            List<String> localizedLabels = labels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxL = ctx.getRandom().nextInt(localizedLabels.size());
            return new InvoiceNumber(localizedLabels.get(idxL), generated);
        }
    }
}
