package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;
import com.mifmif.common.regex.Generex;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class InvoiceDate {

    private String label;
    private String value;

    public InvoiceDate(String label, String value) {
        this.label = label;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "InvoiceDate{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<InvoiceDate> {

        private static final long from = 252493200;
        private static final long to = System.currentTimeMillis() / 1000;
        private static final Map<SimpleDateFormat, String> formats = new HashMap<>();
        private static final Map<String, String> labels = new HashMap<>();
        {
            formats.put(new SimpleDateFormat("MMM d, YYYY"), "en");
            formats.put(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"), "en");
            formats.put(new SimpleDateFormat("dd/MM/YY"), "fr");
            formats.put(new SimpleDateFormat("d MMM, YYYY"), "en");
            formats.put(new SimpleDateFormat("d MMM YYYY"), "fr");
        }
        {
            labels.put("Purchase date", "en");
            labels.put("Du", "fr");
            labels.put("Date", "fr");
        }

        @Override
        public InvoiceDate generate(GenerationContext ctx) {
            long date = (ctx.getRandom().nextInt((int)(to-from)) + from) * 1000;
            List<SimpleDateFormat> localizedFormats = formats.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxF = ctx.getRandom().nextInt(localizedFormats.size());
            List<String> localizedLabels = labels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxL = ctx.getRandom().nextInt(localizedLabels.size());
            return new InvoiceDate(localizedLabels.get(idxL), localizedFormats.get(idxF).format(new Date(date)));
        }

    }

}
