package com.fairandsmart.generator.documents.data.model;

import com.fairandsmart.generator.documents.data.generator.GenerationContext;
import com.fairandsmart.generator.documents.data.generator.ModelGenerator;
import com.github.javafaker.Faker;

import java.util.*;
import java.util.logging.Logger;

public class LeaveInformation {

    private static final Logger LOGGER = Logger.getLogger(LeaveInformation.class.getName());
    private Random random= new Random();
    private double amount;
    private Date date;

    public LeaveInformation(double amount, Date date) {

        this.amount = amount;
        this.date = date;
    }


    public String getMntLabel() {
        List<String> labels = new ArrayList<String>(Arrays.asList("Montant :","Taux : "));
        return labels.get(this.random.nextInt(labels.size()));
    }

    public String getDateLabel() {
        List<String> labels = new ArrayList<String>(Arrays.asList("Date :","période : "));
        return labels.get(this.random.nextInt(labels.size()));
    }



    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static class Generator implements ModelGenerator<LeaveInformation> {
//TODO LISTS DE HEADS ET DE CONTENU

        @Override
        public LeaveInformation generate(GenerationContext ctx) {
            Faker faker = Faker.instance(Locale.forLanguageTag(ctx.getLanguage()));
            Random rand =new Random();


            Date date1 = new Date();
            double mnt = rand.nextDouble();

            return new LeaveInformation(mnt, date1);
        }
    }
}
