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

import com.fairandsmart.generator.documents.data.generator.GenerationContext;
import com.fairandsmart.generator.documents.data.generator.ModelGenerator;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ReceiptDate {

    private String label;
    private String value;
    private String time;
    private String timeLabel;
    private String printedDateLabel;

    public ReceiptDate(String label,String timeLabel, String printedDateLabel, String value, String time ) {
        this.label = label;
        this.value = value;
        this.time = time;
        this.timeLabel = timeLabel;
        this.printedDateLabel = printedDateLabel;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeLabel() {
        return timeLabel;
    }

    public String getprintedDateLabel() {
        return printedDateLabel;
    }

    @Override
    public String toString() {
        return "ReceiptDate{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<ReceiptDate> {

        private static final long from = 252493200;
        private static final long to = System.currentTimeMillis() / 1000;
        private static final Map<SimpleDateFormat, String> formatsDate = new LinkedHashMap<>();
        private static final Map<SimpleDateFormat, String> formatsTime = new LinkedHashMap<>();
        private static final Map<String, String> labels = new LinkedHashMap<>();
        private static final Map<String, String> labelsCommand = new LinkedHashMap<>();
        private static final Map<String, String> labelsExpedition = new LinkedHashMap<>();
        private static final Map<String, String> labelsPayment = new LinkedHashMap<>();
        private static final Map<String, String> labelsTime = new LinkedHashMap<>();
        private static final Map<String, String> labelsPrintedDate = new LinkedHashMap<>();
        {
            formatsDate.put(new SimpleDateFormat("MMM d, YYYY "), "en");
            formatsDate.put(new SimpleDateFormat("YYYY-MM-dd"), "en");
            formatsDate.put(new SimpleDateFormat("dd/MM/YY"), "fr");
            formatsDate.put(new SimpleDateFormat("d MMM, YYYY "), "en");
            formatsDate.put(new SimpleDateFormat("d MMM YYYY "), "fr");
        }
        {
            formatsTime.put(new SimpleDateFormat("hh:mm:ss aa"), "en");
            formatsTime.put(new SimpleDateFormat("HH:mm:ss "), "en");
            formatsTime.put(new SimpleDateFormat("HH:mm:ss"), "fr");
            formatsTime.put(new SimpleDateFormat("HH:mm"), "fr");
        }
        {
            labels.put("Receipt Date", "en");
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
        {
            labelsTime.put("Time", "en");
            labelsTime.put("Heure", "fr");
        }
        {
            labelsPrintedDate.put("Printed Date", "en");
            labelsPrintedDate.put("PRN ON", "en");
            labelsPrintedDate.put("Imprimé le", "fr");
            labelsPrintedDate.put("Printed on", "en");
        }

        @Override
        public ReceiptDate generate(GenerationContext ctx) {
            long date = (ctx.getRandom().nextInt((int)(to-from)) + from) * 1000;
            ctx.setDate(date);
            List<SimpleDateFormat> localizedFormats = formatsDate.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxF = ctx.getRandom().nextInt(localizedFormats.size());

            List<SimpleDateFormat> localizedFormatsTime = formatsTime.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxT = ctx.getRandom().nextInt(localizedFormatsTime.size());


            List<String> localizedLabels = labels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedLabelsCommand = labelsCommand.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedLabelsExpedition = labelsExpedition.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedLabelsPayment = labelsPayment.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedLabelsTime = labelsTime.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedLabelPrintedDate = labelsPrintedDate.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());

            int idxL = ctx.getRandom().nextInt(localizedLabels.size());
            int idxLC = ctx.getRandom().nextInt(localizedLabelsCommand.size());
            int idxLE = ctx.getRandom().nextInt(localizedLabelsExpedition.size());
            int idxLP = ctx.getRandom().nextInt(localizedLabelsPayment.size());
            int idxLT = ctx.getRandom().nextInt(localizedLabelsTime.size());
            int idxLPR = ctx.getRandom().nextInt(localizedLabelPrintedDate.size());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date);
            Date invoiceDate = calendar.getTime();

            calendar.add(Calendar.DAY_OF_WEEK, -4);
            return new ReceiptDate(localizedLabels.get(idxL),localizedLabelsTime.get(idxLT),localizedLabelPrintedDate.get(idxLPR), localizedFormats.get(idxF).format(invoiceDate),localizedFormatsTime.get(idxT).format(invoiceDate)
            );
        }

    }

}
