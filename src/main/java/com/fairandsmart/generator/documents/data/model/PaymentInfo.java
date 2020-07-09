package com.fairandsmart.generator.documents.data.model;

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

import com.fairandsmart.generator.documents.data.generator.ModelGenerator;
import com.fairandsmart.generator.documents.data.generator.GenerationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PaymentInfo {

    private String labelType;
    private String valueType;
    private String labelTransaction;
    private String valueTransaction;
    private String labelFiscalZone;
    private String valueFiscalZone;

    public PaymentInfo(String labelType, String valueType, String labelTransaction, String valueTransaction, String labelFiscalZone, String valueFiscalZone) {
        this.labelType = labelType;
        this.valueType = valueType;
        this.labelTransaction = labelTransaction;
        this.valueTransaction = valueTransaction;
        this.labelFiscalZone = labelFiscalZone;
        this.valueFiscalZone = valueFiscalZone;
    }

    public String getLabelType() {
        return labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getLabelTransaction() {
        return labelTransaction;
    }

    public void setLabelTransaction(String labelTransaction) {
        this.labelTransaction = labelTransaction;
    }

    public String getValueTransaction() {
        return valueTransaction;
    }

    public void setValueTransaction(String valueTransaction) {
        this.valueTransaction = valueTransaction;
    }

    public String getLabelFiscalZone() {
        return labelFiscalZone;
    }

    public void setLabelFiscalZone(String labelFiscalZone) {
        this.labelFiscalZone = labelFiscalZone;
    }

    public String getValueFiscalZone() {
        return valueFiscalZone;
    }

    public void setValueFiscalZone(String valueFiscalZone) {
        this.valueFiscalZone = valueFiscalZone;
    }

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "labelType='" + labelType + '\'' +
                ", valueType='" + valueType + '\'' +
                ", labelTransaction='" + labelTransaction + '\'' +
                ", valueTransaction='" + valueTransaction + '\'' +
                ", labelFiscalZone='" + labelFiscalZone + '\'' +
                ", valueFiscalZone='" + valueFiscalZone + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<PaymentInfo> {

        private static final Map<String, String> labelsType = new HashMap<>();
        private static final Map<String, String> valuesType = new HashMap<>();
        private static final Map<String, String> labelsTransaction = new HashMap<>();
        private static final Map<String, String> valuesTransaction = new HashMap<>();
        private static final Map<String, String> labelsFiscalZone = new HashMap<>();
        private static final Map<String, String> valuesFiscalZone = new HashMap<>();

        {
            labelsType.put("Payment type", "en");
            labelsType.put("Payment means", "en");
            labelsType.put("Payed through", "en");
            labelsType.put("Type de paiement", "fr");
            labelsType.put("Moyen de paiement", "fr");
            labelsType.put("Mode de règlement", "fr");
        }
        {
            valuesType.put("Paypal", "en");
            valuesType.put("Credit card", "en");
            valuesType.put("Bank Transfer", "en");
            valuesType.put("Cheque", "en");
            valuesType.put("Paypal", "fr");
            valuesType.put("CB", "fr");
            valuesType.put("Virement", "fr");
            valuesType.put("Chèque", "fr");
        }
        {
            labelsTransaction.put("Transaction number", "en");
            labelsTransaction.put("Transaction numéro", "fr");
        }
        {
            labelsFiscalZone.put("Fiscal zone", "en");
            labelsFiscalZone.put("Zone fiscale", "fr");
        }


        @Override
        public PaymentInfo generate(GenerationContext ctx) {
            List<String> localizedLabelsTypes = labelsType.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idx1 = ctx.getRandom().nextInt(localizedLabelsTypes.size());

            List<String> localizedValuesTypes = valuesType.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idx2 = ctx.getRandom().nextInt(localizedValuesTypes.size());

            return new PaymentInfo(localizedLabelsTypes.get(idx1), localizedValuesTypes.get(idx2),
                    localizedLabelsTypes.get(idx1), localizedValuesTypes.get(idx2),
                    localizedLabelsTypes.get(idx1), localizedValuesTypes.get(idx2));
        }

    }
}
