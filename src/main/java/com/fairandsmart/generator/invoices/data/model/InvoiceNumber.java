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

import java.util.*;
import java.util.stream.Collectors;

public class InvoiceNumber {

    private String label;
    private String value;
    private String labelCommand;
    private String valueCommand;
    private String labelClient;
    private String valueClient;

    public InvoiceNumber(String label, String value, String labelCommand, String valueCommand, String labelClient, String valueClient) {
        this.label = label;
        this.value = value;
        this.labelCommand = labelCommand;
        this.valueCommand = valueCommand;
        this.labelClient = labelClient;
        this.valueClient = valueClient;
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

    public String getLabelClient() {
        return labelClient;
    }

    public void setLabelClient(String labelClient) {
        this.labelClient = labelClient;
    }

    public String getValueClient() {
        return valueClient;
    }

    public void setValueClient(String valueClient) {
        this.valueClient = valueClient;
    }

    @Override
    public String toString() {
        return "InvoiceNumber{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                ", labelCommand='" + labelCommand + '\'' +
                ", valueCommand='" + valueCommand + '\'' +
                ", labelClient='" + labelClient + '\'' +
                ", valueClient='" + valueClient + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<InvoiceNumber> {

        private static final List<String> formatsInvoice = new ArrayList<>();
        private static final List<String> formatsCommand = new ArrayList<>();
        private static final List<String> formatsClient = new ArrayList<>();
        private static final Map<String, String> labelsInvoice = new LinkedHashMap<>();
        private static final Map<String, String> labelsCommand = new LinkedHashMap<>();
        private static final Map<String, String> labelsClient = new LinkedHashMap<>();
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
            formatsCommand.add("[A-D][H-N]-[A-Z]{1}-[0-9]{1}-[0-9]{1}");
            formatsCommand.add("[0-9]{3}-[0-9]{3}");
            formatsCommand.add("2[0-9]{3}");
            formatsCommand.add("[1-2][0-9]{3}");
            formatsCommand.add("[4-9][0-9]{3}");
            formatsCommand.add("CD201[0-7]00[0-9]{1}");
            formatsCommand.add("99[0-9]{3}");
           // formatsCommand.add("#CO00[0-9]{4}");
            formatsCommand.add("CM5[0-9]{3}");
            formatsCommand.add("COM-[0-9]{3}");
            formatsCommand.add("[0-9]{3}-9[0-9]{2}");
        }
        {
            formatsClient.add("[A-D][H-N]-[A-Z]{2}-[0-9]{3}-[0-9]{2}");
            formatsClient.add("[0-9]{3}-[0-9]{3}-1[0-9]{2}");
            formatsClient.add("7[0-9]{4}");
            formatsClient.add("[1-2][0-9]{4}");
            formatsClient.add("[4-9][0-9]{4}");
            formatsClient.add("CL10[0-7]00[0-9]{2}");
            formatsClient.add("00[0-9]{4}");
            // formatsClient.add("#CO00[0-9]{4}");
            formatsClient.add("CL55[0-9]{3}");
            formatsClient.add("CL-[0-9]{4}");
            formatsClient.add("[0-9]{3}-0[0-9]{3}");
        }

        {
            labelsInvoice.put("Invoice Number", "en");
            labelsInvoice.put("Invoice No.", "en");
            labelsInvoice.put("Invoice ID", "en");
            labelsInvoice.put("Invoice Reference", "en");
            labelsInvoice.put("Numéro de facture", "fr");
            labelsInvoice.put("N° facture", "fr");
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
            labelsCommand.put("N° de commande", "fr");
            labelsCommand.put("COMMANDE N°", "fr");
            labelsCommand.put("Commande n°", "fr");
            labelsCommand.put("Commande-n°", "fr");
            labelsCommand.put("COMMANDE No", "fr");
            labelsCommand.put("Référence de la commande", "fr");
        }
        {
            labelsClient.put("Client Number", "en");
            labelsClient.put("Client No.", "en");
            labelsClient.put("Client ID", "en");
            labelsClient.put("Client Reference", "en");
            labelsClient.put("Numéro de client", "fr");
            labelsClient.put("N° client", "fr");
            labelsClient.put("N° de client", "fr");
            labelsClient.put("CLIENT N°", "fr");
            labelsClient.put("Client n°", "fr");
            labelsClient.put("Client-n°", "fr");
            labelsClient.put("CLIENT No", "fr");
            labelsClient.put("Référence de la client", "fr");
        }

        @Override
        public InvoiceNumber generate(GenerationContext ctx) {
            int idxF = ctx.getRandom().nextInt(formatsInvoice.size());

            Generex generex = new Generex(formatsInvoice.get(idxF));
            String generated = generex.random();

            generex = new Generex(formatsCommand.get(idxF));
            String generatedCommand = generex.random();

            generex = new Generex(formatsClient.get(idxF));
            String generatedClient = generex.random();

            List<String> localizedLabels = labelsInvoice.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedLabelsCommand = labelsCommand.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedLabelsClient = labelsClient.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxL = ctx.getRandom().nextInt(localizedLabels.size());
            return new InvoiceNumber(localizedLabels.get(idxL), generated, localizedLabelsCommand.get(idxL), generatedCommand, localizedLabelsClient.get(idxL), generatedClient);
        }
    }
}
