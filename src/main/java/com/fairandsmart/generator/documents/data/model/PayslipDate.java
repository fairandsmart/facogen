package com.fairandsmart.generator.documents.data.model;

import com.fairandsmart.generator.documents.data.generator.GenerationContext;
import com.fairandsmart.generator.documents.data.generator.ModelGenerator;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class PayslipDate {
    private String label;
    private String value;
    private String labelStart;
    private String valueStart;
    private String labelEnd;
    private String valueEnd;
    private String labelPayment;
    private String valuePayment;

    public PayslipDate(String label, String value, String labelStart, String valueStart, String labelEnd, String valueEnd, String labelPayment, String valuePayment){
        this.label = label;
        this.value = value;
        this.labelStart = labelStart;
        this.valueStart = valueStart;
        this.labelEnd = labelEnd;
        this.valueEnd = valueEnd;
        this.labelPayment = labelPayment;
        this.valuePayment = valuePayment;
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

    public String getLabelStart() {
        return labelStart;
    }

    public void setLabelStart(String labelStart) {
        this.labelStart = labelStart;
    }

    public String getValueStart() {
        return valueStart;
    }

    public void setValueStart(String valueStart) {
        this.valueStart = valueStart;
    }

    public String getLabelEnd() {
        return labelEnd;
    }

    public void setLabelEnd(String labelEnd) {
        this.labelEnd = labelEnd;
    }

    public String getValueEnd() {
        return valueEnd;
    }

    public void setValueEnd(String valueEnd) {
        this.valueEnd = valueEnd;
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
        return "PayslipDate{" +
                "label='" + label + '\'' +
                ", value='" + value + '\'' +
                ", labelStart='" + labelStart + '\'' +
                ", valueStart='" + valueStart + '\'' +
                ", labelEnd='" + labelEnd + '\'' +
                ", valueEnd='" + valueEnd + '\'' +
                ", labelPayment='" + labelPayment + '\'' +
                ", valuePayment='" + valuePayment + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<PayslipDate> {

        private static final long from = 252493200;
        private static final long to = System.currentTimeMillis() / 1000;
        private static final SimpleDateFormat formatsPeriod = new SimpleDateFormat("MMM YYYY");
        private static final Map<SimpleDateFormat,String> formatsDate = new LinkedHashMap<>();
        private static final Map<String, String> labels = new LinkedHashMap<>();
        private static final Map<String, String> labelsStart = new LinkedHashMap<>();
        private static final Map<String, String> labelsEnd = new LinkedHashMap<>();
        private static final Map<String, String> labelsPayment = new LinkedHashMap<>();
        {
            formatsDate.put(new SimpleDateFormat("MMM d, YYYY"), "en");
            //  formats.put(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss"), "en");
            formatsDate.put(new SimpleDateFormat("dd/MM/YY"), "fr");
            formatsDate.put(new SimpleDateFormat("d MMM, YYYY"), "en");
            formatsDate.put(new SimpleDateFormat("d MMM YYYY"), "fr");
        }
        {
            labels.put("Period", "en");
            labels.put("Période", "fr");
        }
        {
            labelsStart.put("Start date", "en");
            labelsStart.put("Du", "fr");
            labelsStart.put("Début de période", "fr");
        }
        {
            labelsEnd.put("End date", "en");
            labelsEnd.put("Au", "fr");
            labelsEnd.put("Fin de période", "fr");
        }
        {
            labelsPayment.put("Payment date", "en");
            labelsPayment.put("Date de paiement", "fr");
            labelsPayment.put("Paiement le", "fr");
        }


        @Override
        public PayslipDate generate(GenerationContext ctx) {
            long date = (ctx.getRandom().nextInt((int)(to-from)) + from) * 1000;
            ctx.setDate(date);
            List<SimpleDateFormat> localizedFormats = formatsDate.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxF = ctx.getRandom().nextInt(localizedFormats.size());

            List<String> localizedLabels = labels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedLabelsStart = labelsStart.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedLabelsEnd = labelsEnd.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localizedLabelsPayment = labelsPayment.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxL = ctx.getRandom().nextInt(localizedLabels.size());
            int idxLS = ctx.getRandom().nextInt(localizedLabelsStart.size());
            int idxLP = ctx.getRandom().nextInt(localizedLabelsPayment.size());

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date);
            Date payslipDate = calendar.getTime();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            Date startDate = calendar.getTime();
            calendar.set(Calendar.DATE,calendar.getActualMaximum(Calendar.DATE));
            Date endDate =calendar.getTime();

            return new PayslipDate(localizedLabels.get(idxL), formatsPeriod.format(payslipDate),
                    localizedLabelsStart.get(idxLS), localizedFormats.get(idxF).format(startDate),
                    localizedLabelsEnd.get(idxLS), localizedFormats.get(idxF).format(endDate),
                    localizedLabelsPayment.get(idxLP), localizedFormats.get(idxF).format(endDate)
            );
        }

    }
}
