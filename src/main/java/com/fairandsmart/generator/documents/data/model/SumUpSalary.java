package com.fairandsmart.generator.documents.data.model;

import com.fairandsmart.generator.documents.data.generator.GenerationContext;
import com.fairandsmart.generator.documents.data.generator.ModelGenerator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SumUpSalary {

    private static final Logger LOGGER = Logger.getLogger(SumUpSalary.class.getName());
    private Random random= new Random();
    private double netApayer;
    private double netImposable;
    private double brut;


    public SumUpSalary(double netApayer, double netImposable) {
        this.netApayer = netApayer;
        this.netImposable = netImposable;
    }

    public double getNetApayer() {
        return netApayer;
    }

    public double getNetImposable() {
        return netImposable;
    }

    public void setNetApayer(double netApayer) {
        this.netApayer = netApayer;
    }

    public void setNetImposable(double netImposable) {
        this.netImposable = netImposable;
    }

    public double getBrut() {
        return brut;
    }

    public void setBrut(double brut) {
        this.brut = brut;
    }

    public String getNetImposabletLabel() {
        List<String> labels = new ArrayList<String>(Arrays.asList("Net à payer avant impôt sur le revenue :", "Net imposable : "));
        return labels.get(this.random.nextInt(labels.size()));
    }

    public String getNetApayerLabel() {
        List<String> labels = new ArrayList<String>(Arrays.asList("Net à payer :","Salaire Net à payer"));
        return labels.get(this.random.nextInt(labels.size()));
    }

    @Override
    public String toString() {
        return "SumUpSalary{" +
                "netApayer=" + netApayer +
                ", netImposable=" + netImposable +
                '}';
    }

    public static class Generator implements ModelGenerator<SumUpSalary> {


        @Override
        public SumUpSalary generate(GenerationContext ctx) {
            Random rand =new Random();

            double netApayer = rand.nextDouble();
            double netImpo = rand.nextDouble();

            return new SumUpSalary(netApayer, netImpo);
        }
    }

}
