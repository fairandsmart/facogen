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
    private int[] cpNMinus1;
    private int[] cpN;


    public LeaveInformation(double amount, Date date) {

        this.amount = amount;
        this.date = date;
    }

    public LeaveInformation(double amount, Date date, int[] cpNMinus1, int[] cpN) {
        this.amount = amount;
        this.date = date;
        this.cpNMinus1 = cpNMinus1;
        this.cpN = cpN;
    }

    public String getMntLabel() {
        List<String> labels = new ArrayList<String>(Arrays.asList("Montant :","Taux : "));
        return labels.get(this.random.nextInt(labels.size()));
    }

    public String getDateLabel() {
        List<String> labels = new ArrayList<String>(Arrays.asList("Date :","période : "));
        return labels.get(this.random.nextInt(labels.size()));
    }

    public String getAquisLabel() {
        List<String> labels = new ArrayList<String>(Arrays.asList("ACQUIS","Dû","Acquis"));
        return labels.get(this.random.nextInt(labels.size()));
    }

    public String getPrisLabel() {
        List<String> labels = new ArrayList<String>(Arrays.asList("PRIS","Pris", "Total pris"));
        return labels.get(this.random.nextInt(labels.size()));
    }

    public String getSoldeLabel() {
        List<String> labels = new ArrayList<String>(Arrays.asList("SOLDE","Resté"));
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

    public int[] getCpNMinus1() {
        return cpNMinus1;
    }

    public int[] getCpN() {
        return cpN;
    }

    public void setCpNMinus1(int[] cpNMinus1) {
        this.cpNMinus1 = cpNMinus1;
    }

    public void setCpN(int[] cpN) {
        this.cpN = cpN;
    }

    public static class Generator implements ModelGenerator<LeaveInformation> {
//TODO LISTS DE HEADS ET DE CONTENU

        @Override
        public LeaveInformation generate(GenerationContext ctx) {
            Faker faker = Faker.instance(Locale.forLanguageTag(ctx.getLanguage()));
            Random rand =new Random();
            Date date1 = new Date();
            double mnt = rand.nextDouble();
            int[] maxes = {34 , 30, 20};
            int max = maxes[rand.nextInt(maxes.length-1)];
            int[] acq = {rand.nextInt(max),rand.nextInt(max)};
            int[] pris = {0, rand.nextInt(acq[1])};
            if (acq[0] != 0) pris[0] = rand.nextInt(acq[0]);
            if (acq[1] != 0) pris[1] = rand.nextInt(acq[1]);
            int[] cpNN = {acq[0],pris[0],acq[0]-pris[0]} ; //+ min
            int[] cpN = {acq[1],pris[1],acq[1]-pris[1]} ; //+ min
            return new LeaveInformation(mnt, date1,cpNN,cpN);
        }
    }
}
