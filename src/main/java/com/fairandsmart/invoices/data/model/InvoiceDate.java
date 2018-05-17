package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;
import com.mifmif.common.regex.Generex;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.spi.CalendarDataProvider;
import java.util.stream.Collectors;

public class InvoiceDate {

    private String label;
    private String value;
    private String labelCommand;
    private String valueCommand;
    private String labelExpedition;
    private String valueExpedition;
    private String labelPayment;
    private String valuePayment;


    public InvoiceDate(String label, String value
                      // String labelCommand, String valueCommand, String labelExpedition, String valueExpedition, String labelPayment, String valuePayment
    ) {
        this.label = label;
        this.value = value;
//        this.labelCommand = labelCommand;
//        this.valueCommand = valueCommand;
//        this.labelExpedition = labelExpedition;
//        this.valueExpedition = valueExpedition;
//        this.labelPayment = labelPayment;
//        this.valuePayment = valuePayment;
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

    public String getLabelExpedition() {
        return labelExpedition;
    }

    public void setLabelExpedition(String labelExpedition) {
        this.labelExpedition = labelExpedition;
    }

    public String getValueExpedition() {
        return valueExpedition;
    }

    public void setValueExpedition(String valueExpedition) {
        this.valueExpedition = valueExpedition;
    }

    public String getLabelPayment() {
        return labelPayment;
    }

    public void setLabelPayment(String labelPayment) {
        this.labelPayment = labelPayment;
    }

    public String getValuePayment() {
        return valuePayment;
    }

    public void setValuePayment(String valuePayment) {
        this.valuePayment = valuePayment;
    }

    @Override
    public String toString() {
        return "InvoiceDate{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                ", labelCommand='" + labelCommand + '\'' +
                ", valueCommand='" + valueCommand + '\'' +
                ", labelExpedition='" + labelExpedition + '\'' +
                ", valueExpedition='" + valueExpedition + '\'' +
                ", labelPayment='" + labelPayment + '\'' +
                ", valuePayment='" + valuePayment + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<InvoiceDate> {

        private static final long from = 252493200;
        private static final long to = System.currentTimeMillis() / 1000;
        private static final Map<SimpleDateFormat, String> formats = new LinkedHashMap<>();
        private static final Map<String, String> labels = new LinkedHashMap<>();
        private static final Map<String, String> labelsCommand = new LinkedHashMap<>();
        private static final Map<String, String> labelsExpedition = new LinkedHashMap<>();
        private static final Map<String, String> labelsPayment = new LinkedHashMap<>();
        {
            formats.put(new SimpleDateFormat("MMM d, YYYY"), "en");
            formats.put(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"), "en");
            formats.put(new SimpleDateFormat("dd/MM/YY"), "fr");
            formats.put(new SimpleDateFormat("d MMM, YYYY"), "en");
            formats.put(new SimpleDateFormat("d MMM YYYY"), "fr");
        }
        {
            labels.put("Invoice Date", "en");
            labels.put("Date", "en");
            labels.put("Du", "fr");
            labels.put("Dated", "en");
            labels.put("Date de la facture", "fr");
        }
        {
            labelsCommand.put("Command date", "en");
            labelsCommand.put("Commandé le", "fr");
            labelsCommand.put("Date de commande", "fr");
        }
        {
            labelsExpedition.put("Expedition date", "en");
            labelsExpedition.put("Expédié le", "fr");
            labelsExpedition.put("Date d'expédition", "fr");
        }
        {
            labelsPayment.put("Purchase date", "en");
            labelsPayment.put("Payé le", "fr");
            labelsPayment.put("Date de paiement", "fr");
        }


        @Override
        public InvoiceDate generate(GenerationContext ctx) {
            long date = (ctx.getRandom().nextInt((int)(to-from)) + from) * 1000;
            ctx.setDate(date);
            List<SimpleDateFormat> localizedFormats = formats.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxF = ctx.getRandom().nextInt(localizedFormats.size());

            List<String> localizedLabels = labels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedLabelsCommand = labelsCommand.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedLabelsExpedition = labelsExpedition.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedLabelsPayment = labelsPayment.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxL = ctx.getRandom().nextInt(localizedLabels.size());

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date);
            Date invoiceDate = calendar.getTime();
            Date expeditionDate = calendar.getTime();
            calendar.add(Calendar.DAY_OF_WEEK, -4);
            Date commandDate = calendar.getTime();
            Date paymentDate = calendar.getTime();
            return new InvoiceDate(localizedLabels.get(idxL), localizedFormats.get(idxF).format(invoiceDate)
//                    localizedLabelsCommand.get(idxL), localizedFormats.get(idxF).format(commandDate),
//                    localizedLabelsExpedition.get(idxL), localizedFormats.get(idxF).format(expeditionDate),
//                    localizedLabelsPayment.get(idxL), localizedFormats.get(idxF).format(paymentDate)
            );
        }

    }

}
