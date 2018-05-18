package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;
import com.mifmif.common.regex.Generex;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ContactNumber {

    private String phoneLabel;
    private String phoneValue;
    private String faxLabel;
    private String faxValue;

    public ContactNumber(String phoneLabel, String phoneValue, String faxLabel, String faxValue) {
        this.phoneLabel = phoneLabel;
        this.phoneValue = phoneValue;
        this.faxLabel = faxLabel;
        this.faxValue = faxValue;
    }

    public String getphoneLabel() {
        return phoneLabel;
    }

    public void setphoneLabel(String phoneLabel) {
        this.phoneLabel = phoneLabel;
    }

    public String getphoneValue() {
        return phoneValue;
    }

    public void setphoneValue(String phoneValue) {
        this.phoneValue = phoneValue;
    }

    public String getfaxLabel() {
        return faxLabel;
    }

    public void setfaxLabel(String faxLabel) {
        this.faxLabel = faxLabel;
    }

    public String getfaxValue() {
        return faxValue;
    }

    public void setfaxValue(String faxValue) {
        this.faxValue = faxValue;
    }

    @Override
    public String toString() {
        return "ContactNumber{" +
                "phoneLabel='" + phoneLabel + '\'' +
                ", phoneValue='" + phoneValue + '\'' +
                "faxLabel='" + faxLabel + '\'' +
                ", faxValue='" + faxValue + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<ContactNumber> {

        private static final Map<String, String> formats_part1 = new LinkedHashMap<>();
        private static final Map<String, String> formats_part2 = new LinkedHashMap<>();
        private static final Map<String, String> phoneLabels = new LinkedHashMap<>();
        private static final Map<String, String> faxLabels = new LinkedHashMap<>();

        {
            formats_part1.put("0[0-9]{1}[.][0-9]{2}[.][0-9]{2}[.]", "FR");
            formats_part1.put("0[0-9]{1}[-][0-9]{2}[-][0-9]{2}[-]", "FR");
            formats_part1.put("+33 (0) [0-9]{3} [0-9]{3} ", "FR");
            formats_part1.put("+33 [(]0[)] [0-9]{1} [0-9]{2} [0-9]{2} ", "FR");
        }
        {
            formats_part2.put("[0-9]{2}[.][0-9]{2}", "FR");
            formats_part2.put("[0-9]{2}[-][0-9]{2}", "FR");
            formats_part2.put("[0-9]{3}", "FR");
            formats_part2.put("[0-9]{2} [0-9]{2}", "FR");
        }
        {
            phoneLabels.put("Tel:", "fr");
            phoneLabels.put("Téléphone", "fr");
            phoneLabels.put("Numéro de Tel", "fr");
            phoneLabels.put("Phone", "en");
        }

        {
            faxLabels.put("Fax:", "fr");
            faxLabels.put("Télécopie", "fr");
            faxLabels.put("Numéro de Fax", "fr");
            faxLabels.put("Fax", "en");
        }

        @Override
        public ContactNumber generate(GenerationContext ctx) {
            List<String> localizedFormats1 = formats_part1.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getCountry())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedFormats2 = formats_part2.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getCountry())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxF = ctx.getRandom().nextInt(localizedFormats1.size());

            Generex generex1 = new Generex(localizedFormats1.get(idxF));
            String samePrefix = generex1.random();

            Generex generex2 = new Generex(localizedFormats2.get(idxF));
            String phoneNumber = samePrefix + generex2.random();
            String faxNumber = samePrefix + generex2.random();

            List<String> localizedPLabels = phoneLabels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedFLabels = faxLabels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxL = ctx.getRandom().nextInt(localizedPLabels.size());
            System.out.println(idxL);
            return new ContactNumber(localizedPLabels.get(idxL), phoneNumber, localizedFLabels.get(idxL), faxNumber);
        }
    }

}
