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
 * Djedjiga Belhadj <djedjiga.belhadj@gmail.com> / Loria
 * %%
 * Copyright (C) 2019 - 2020 Fair And Smart
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
            Faker faker = Faker.instance(Locale.forLanguageTag(ctx.getLanguagePayslip()));
            Random rand =new Random();
            Date date1 = new Date();
            double mnt = rand.nextDouble();
            int[] maxes = {34 , 30, 20};
            int max = maxes[rand.nextInt(maxes.length-1)];
            int[] acq = {rand.nextInt(max),rand.nextInt(max)};
            int[] pris = {0, 0};

            if (acq[0] != 0) pris[0] = rand.nextInt(acq[0]);
            if (acq[1] != 0) pris[1] = rand.nextInt(acq[1]);
            int[] cpNN = {acq[0],pris[0],acq[0]-pris[0]} ; //+ min
            int[] cpN = {acq[1],pris[1],acq[1]-pris[1]} ; //+ min
            return new LeaveInformation(mnt, date1,cpNN,cpN);
        }
    }
}
