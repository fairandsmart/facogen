package com.fairandsmart.generator.invoices.data.model;

/*-
 * #%L
 * FacoGen / A tool for annotated GEDI based invoice generation.
 * 
 * Authors:
 * 
 * Xavier Lefevre <xavier.lefevre@fairandsmart.com> / FairAndSmart
 * Nicolas Rueff <nicolas.rueff@fairandsmart.com> / FairAndSmart
 * Alan Balbo <alan.balbo@fairandsmart.com> / FairAndSmart
 * Frederic Pierre <frederic.pierre@fairansmart.com> / FairAndSmart
 * Victor Guillaume <victor.guillaume@fairandsmart.com> / FairAndSmart
 * Jérôme Blanchard <jerome.blanchard@fairandsmart.com> / FairAndSmart
 * Aurore Hubert <aurore.hubert@fairandsmart.com> / FairAndSmart
 * Kevin Meszczynski <kevin.meszczynski@fairandsmart.com> / FairAndSmart
 * %%
 * Copyright (C) 2019 Fair And Smart
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.fairandsmart.generator.invoices.data.generator.ModelGenerator;
import com.fairandsmart.generator.invoices.data.generator.GenerationContext;
import com.mifmif.common.regex.Generex;

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
