package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;
import com.mifmif.common.regex.Generex;

import java.util.*;
import java.util.stream.Collectors;

public class InvoiceNumber {

    private String label;
    private String value;
    private String labelCommand;
    private String valueCommand;

    public InvoiceNumber(String label, String value, String labelCommand, String valueCommand) {
        this.label = label;
        this.value = value;
        this.labelCommand = labelCommand;
        this.valueCommand = valueCommand;
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

    public String getLabelCommand() {
        return labelCommand;
    }

    public void setLabelCommand(String labelCommand) {
        this.labelCommand = labelCommand;
    }

    public String getValueCommand() {
        return valueCommand;
    }

    public void setValueCommand(String valueCommand) {
        this.valueCommand = valueCommand;
    }

    @Override
    public String toString() {
        return "InvoiceNumber{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                ", labelCommand='" + labelCommand + '\'' +
                ", valueCommand='" + valueCommand + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<InvoiceNumber> {

        private static final List<String> formatsInvoice = new ArrayList<>();
        private static final List<String> formatsCommand = new ArrayList<>();
        private static final Map<String, String> labelsInvoice = new LinkedHashMap<>();
        private static final Map<String, String> labelsCommand = new LinkedHashMap<>();
        {
            formatsInvoice.add("[A-D][H-N]-[A-Z]{2}-[0-9]{6}-[0-9]{2}");
            formatsInvoice.add("[0-9]{3}-[0-9]{5}-1[0-9]{4}");
            formatsInvoice.add("1[0-9]{6}");
            formatsInvoice.add("[3-7][0-9]{7}");
            formatsInvoice.add("[4-9][0-9]{9}");
            formatsInvoice.add("FV201[0-7]00[0-9]{4}");
            formatsInvoice.add("00[0-9]{5}");
            //formatsInvoice.add("#FA00[0-9]{4}"); Does not produce anything in Java, cause of no facture number error
            formatsInvoice.add("FC500[0-9]{3}");
            formatsInvoice.add("INV-[0-9]{4}");
            formatsInvoice.add("[0-9]{4}-7[0-9]{5}");
        }
        {
            formatsCommand.add("[A-D][H-N]-[A-Z]{1}-[0-9]{3}-[0-9]{2}");
            formatsCommand.add("[0-9]{3}-[0-9]{3}-1[0-9]{2}");
            formatsCommand.add("2[0-9]{3}");
            formatsCommand.add("[1-2][0-9]{3}");
            formatsCommand.add("[4-9][0-9]{3}");
            formatsCommand.add("CD201[0-7]00[0-9]{2}");
            formatsCommand.add("99[0-9]{4}");
           // formatsCommand.add("#CO00[0-9]{4}");
            formatsCommand.add("CM500[0-9]{3}");
            formatsCommand.add("COM-[0-9]{4}");
            formatsCommand.add("[0-9]{3}-7[0-9]{4}");
        }
        {
            labelsInvoice.put("Invoice Number", "en");
            labelsInvoice.put("Invoice No.", "en");
            labelsInvoice.put("Invoice ID", "en");
            labelsInvoice.put("Invoice Reference", "en");
            labelsInvoice.put("Numéro de facture", "fr");
            labelsInvoice.put("N° facture", "fr");
            labelsInvoice.put("N°", "fr");
            labelsInvoice.put("N° de facture", "fr");
            labelsInvoice.put("FACTURE N°", "fr");
            labelsInvoice.put("Facture n°", "fr");
            labelsInvoice.put("Facture-n°", "fr");
            labelsInvoice.put("FACTURE No", "fr");
            labelsInvoice.put("Référence de la facture", "fr");
        }
        {
            labelsCommand.put("Order Number", "en");
            labelsCommand.put("Order No.", "en");
            labelsCommand.put("Order ID", "en");
            labelsCommand.put("Order Reference", "en");
            labelsCommand.put("Numéro de commande", "fr");
            labelsCommand.put("N° commande", "fr");
            labelsCommand.put("N°", "fr");
            labelsCommand.put("N° de commande", "fr");
            labelsCommand.put("COMMANDE N°", "fr");
            labelsCommand.put("Commande n°", "fr");
            labelsCommand.put("Commande-n°", "fr");
            labelsCommand.put("COMMAND No", "fr");
            labelsCommand.put("Référence de la facture", "fr");
        }

        @Override
        public InvoiceNumber generate(GenerationContext ctx) {
            int idxF = ctx.getRandom().nextInt(formatsInvoice.size());
            Generex generex = new Generex(formatsInvoice.get(idxF));
            String generated = generex.random();
            generex = new Generex(formatsCommand.get(idxF));
            String generatedCommand = generex.random();

            List<String> localizedLabels = labelsInvoice.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedLabelsCommand = labelsCommand.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxL = ctx.getRandom().nextInt(localizedLabels.size());
            return new InvoiceNumber(localizedLabels.get(idxL), generated, localizedLabelsCommand.get(idxL), generatedCommand);
        }
    }
}
