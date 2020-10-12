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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class SalaryCotisationTable {

    private Random random= new Random();
    private List<SalaryLine> salaryTableLines = new ArrayList<SalaryLine>();
    private float baseSalary =1539.42f;
    private float brutSalary = 1539.42f;
    private float netSalary =0 ;
    private float netImposabel =0;
    private float tauxHorr = 10.15f;

    // News
    private String codeElementHead;
    private String headingHead;
    private String baseHead;
    private String salaryRateHead;
    private String employeeContrHead;
    private String employerRateHead;
    private String employerContrHead;


    public SalaryCotisationTable(String codeElementHead, String headingHead, String baseHead, String salaryRateHead, String employeeContrHead,
                                 String employerRateHead, String employerContrHead) {
        this.codeElementHead = codeElementHead;
        this.headingHead = headingHead;
        this.baseHead = baseHead;
        this.salaryRateHead = salaryRateHead;
        this.employeeContrHead = employeeContrHead;
        this.employerRateHead = employerRateHead;
        this.employerContrHead = employerContrHead;
    }

    public void addLineSalary(SalaryLine sLine) {
        salaryTableLines.add(sLine);
        // TODO ADD THE NECESSARY CALCULATIONS
            }

    public List<Float> getTotalCotisations(List<SalaryLine> listCot) {
        float sumEmployee = 0;
        float sumEmployer = 0;
        List<Float> list = new ArrayList<>() ;

        for (int i = 0; i < listCot.size(); i++) {
            sumEmployee += listCot.get(i).getEmployeeContributions();
            sumEmployer += listCot.get(i).getEmployerContributions();
        }
        list.add(sumEmployee);
        list.add(sumEmployer);
        return list;
    }

    public List<SalaryLine> getTotalCotisationslist(List<Float> listCot){
        List<SalaryLine> totalCot = new ArrayList<SalaryLine>();
        int rnd= random.nextInt(2);
        switch (rnd){
            case 0 :
                SalaryLine s = new SalaryLine(0,"Total des retenues",0, 0,listCot.get(0), 0, listCot.get(1) );
                totalCot.add(s);
                break;
            case 1:
                SalaryLine s1 = new SalaryLine(0,"TOTAL CONTRIBUTIONS ET COTISATIONS SALARIALES",0, 0,listCot.get(0), 0, 0 );
                totalCot.add(s1);
            SalaryLine s2 = new SalaryLine(0,"TOTAL CONTRIBUTIONS ET COTISATIONS PATRONALES",0, 0,0, 0, listCot.get(1) );
            totalCot.add(s2);
            break;
    }
    return totalCot;
    }
    public List<SalaryLine> getSalaryTableLines() {
        return salaryTableLines;
    }

    public String getCodeElementHead() {
        return codeElementHead;
    }

    public String getHeadingHead() {
        return headingHead;
    }

    public String getBaseHead() {
        return baseHead;
    }

    public String getSalaryRateHead() {
        return salaryRateHead;
    }

    public String getEmployeeContrHead() {
        return employeeContrHead;
    }

    public String getEmployerRateHead() {
        return employerRateHead;
    }

    public String getEmployerContrHead() {
        return employerContrHead;
    }

    public void setSalaryTableLines(List<SalaryLine> salaryTableLines) {
        this.salaryTableLines = salaryTableLines;
    }

    public void setCodeElementHead(String codeElementHead) {
        this.codeElementHead = codeElementHead;
    }

    public void setHeadingHead(String headingHead) {
        this.headingHead = headingHead;
    }

    public void setBaseHead(String baseHead) {
        this.baseHead = baseHead;
    }

    public void setSalaryRateHead(String salaryRateHead) {
        this.salaryRateHead = salaryRateHead;
    }

    public void setEmployeeContrHead(String employeeContrHead) {
        this.employeeContrHead = employeeContrHead;
    }

    public void setEmployerRateHead(String employerRateHead) {
        this.employerRateHead = employerRateHead;
    }

    public void setEmployerContrHead(String employerContrHead) {
        this.employerContrHead = employerContrHead;
    }

    public float getBaseSalary() {
        return baseSalary;
    }

    public float getBrutSalary() {
        return brutSalary;
    }

    public float getNetSalary() {
        return netSalary;
    }

    public void setBaseSalary(float baseSalary) {
        this.baseSalary = baseSalary;
    }

    public void setBrutSalary(float brutSalary) {
        this.brutSalary = brutSalary;
    }

    public void setNetSalary(float netSalary) {
        this.netSalary = netSalary;
    }

    public double getTauxHorr() {
        return tauxHorr;
    }

    public void setTauxHorr(float tauxHorr) {
        this.tauxHorr = tauxHorr;
    }

    public float getNetImposabel() {
        return netImposabel;
    }

    public void setNetImposabel(float netImposabel) {
        this.netImposabel = netImposabel;
    }

    @Override
    public String toString() {
        return "SalaryCotisationTable{" +
                "salaryTableLines=" + salaryTableLines +
                '}';
    }

    public static class Generator {//implements ModelGenerator<SalaryCotisationTable> {

        private static final Map<String, String> codeElementHeads = new LinkedHashMap<>();
        private static final Map<String, String> headingHeads = new LinkedHashMap<>();
        private static final Map<String, String> baseHeads = new LinkedHashMap<>();
        private static final Map<String, String> salaryRateHeads = new LinkedHashMap<>();
        private static final Map<String, String> employeeContrHeads = new LinkedHashMap<>();
        private static final Map<String, String> employerRateHeads = new LinkedHashMap<>();
        private static final Map<String, String> employerContrHeads = new LinkedHashMap<>();

        {
            codeElementHeads.put("Élément", "fr");

        }

        {
            headingHeads.put("Désignation", "fr");
            headingHeads.put("Rubriques", "fr");
            headingHeads.put("libellé", "fr");

        }

        {
            baseHeads.put("Base", "fr");
            baseHeads.put("Nombre ou base", "fr");
            baseHeads.put("Quantité ou base", "fr");

        }

        {
            salaryRateHeads.put("Taux salarial", "fr");
            salaryRateHeads.put("Taux ou %", "fr");
            salaryRateHeads.put("Tx. SAL", "fr");
            salaryRateHeads.put("TAUX", "fr");

        }

        {
            employeeContrHeads.put("Cot. Salariales", "fr");
            employeeContrHeads.put("Montant", "fr");
            employeeContrHeads.put("Mt. SAL", "fr");
            employeeContrHeads.put("Montant salarial", "fr");
            employeeContrHeads.put("À déduire", "fr");

        }

        {
            employerRateHeads.put("Taux patronal", "fr");
            employerRateHeads.put("Tx. PAT", "fr");
            employerRateHeads.put("Taux", "fr");

        }

        {
            employerContrHeads.put("Cot. Patronales", "fr");
            employerContrHeads.put("Montant", "fr");
            employerContrHeads.put("Mt. PAT", "fr");
            employerContrHeads.put("Mt. Patronal", "fr");

        }

        // cotisations santé
        private List<List<SalaryLine>> santeCotisations;// products;
        private static final String cotisationFileSante = "payslips/salary/cotisations_santé.json";
        {
            Reader jsonReader = new InputStreamReader(SalaryCotisationTable.class.getClassLoader().getResourceAsStream(cotisationFileSante));
            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<Collection<SalaryLine>>>(){}.getType();
            santeCotisations = gson.fromJson(jsonReader, collectionType);
        }
        // cotisations accidents de travail
        private List<List<SalaryLine>> accidentCotisation;// products;
        private static final String cotisationFileAccident = "payslips/salary/cotisations_accidents.json";
        {
            Reader jsonReader = new InputStreamReader(SalaryCotisationTable.class.getClassLoader().getResourceAsStream(cotisationFileAccident));
            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<Collection<SalaryLine>>>(){}.getType();
            accidentCotisation = gson.fromJson(jsonReader, collectionType);
        }
        // cotisation retraite
        private List<List<SalaryLine>> retaraiteCotisation;// products;
        private static final String cotisationFileRetraite = "payslips/salary/cotisations_retraite.json";
        {
            Reader jsonReader = new InputStreamReader(SalaryCotisationTable.class.getClassLoader().getResourceAsStream(cotisationFileRetraite));
            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<Collection<SalaryLine>>>(){}.getType();
            retaraiteCotisation = gson.fromJson(jsonReader, collectionType);
        }
        // cotisations prestations familiales
        private List<List<SalaryLine>> prestationsCotisation;// products;
        private static final String cotisationFilePrest = "payslips/salary/cotisations_prestation_fam.json";
        {
            Reader jsonReader = new InputStreamReader(SalaryCotisationTable.class.getClassLoader().getResourceAsStream(cotisationFilePrest));
            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<Collection<SalaryLine>>>(){}.getType();
            prestationsCotisation = gson.fromJson(jsonReader, collectionType);
        }
        // cotisations chomage
        private List<List<SalaryLine>> chomageCotisation;// products;
        private static final String cotisationFileChomage = "payslips/salary/cotisations_chomage.json";
        {
            Reader jsonReader = new InputStreamReader(SalaryCotisationTable.class.getClassLoader().getResourceAsStream(cotisationFileChomage));
            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<Collection<SalaryLine>>>(){}.getType();
            chomageCotisation = gson.fromJson(jsonReader, collectionType);
        }

        // autres cotisations
        private List<List<SalaryLine>> othercotisations;// products;
        private static final String othercotisationsFile = "payslips/salary/cotisations_autres.json";
        {
            Reader jsonReader = new InputStreamReader(SalaryCotisationTable.class.getClassLoader().getResourceAsStream(othercotisationsFile));
            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<Collection<SalaryLine>>>(){}.getType();
            othercotisations = gson.fromJson(jsonReader, collectionType);
        }

        // csg deductible
        private List<List<SalaryLine>> csgcotisations;// products;
        private static final String csgcotisationsFile = "payslips/salary/cotisations_csg_nondeduct.json";
        {
            Reader jsonReader = new InputStreamReader(SalaryCotisationTable.class.getClassLoader().getResourceAsStream(csgcotisationsFile));
            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<Collection<SalaryLine>>>(){}.getType();
            csgcotisations = gson.fromJson(jsonReader, collectionType);
        }

        // composantes remuneration
        private List<List<SalaryLine>> composantesRenum;// products;
        private static final String composantesRenumFile = "payslips/salary/composantes_remunération.json";
        {
            Reader jsonReader = new InputStreamReader(SalaryCotisationTable.class.getClassLoader().getResourceAsStream(composantesRenumFile));
            Gson gson = new Gson();
            Type collectionType = new TypeToken<Collection<Collection<SalaryLine>>>(){}.getType();
            composantesRenum = gson.fromJson(jsonReader, collectionType);
        }


        //@Override
        public SalaryCotisationTable generate(GenerationContext ctx, String brutSal) {

            float brut;
            float tauxHorr;
            float base;
            List<SalaryLine> listCotisations = new ArrayList<SalaryLine>();

            List<String> localcodeElementHead = codeElementHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguagePayslip())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localheadingHead = headingHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguagePayslip())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localbaseHead = baseHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguagePayslip())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localsalaryRateHead = salaryRateHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguagePayslip())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localemployeeContrHead = employeeContrHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguagePayslip())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localemployerRateHead = employerRateHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguagePayslip())).map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> localemployerContrHead = employerContrHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguagePayslip())).map(Map.Entry::getKey).collect(Collectors.toList());

            SalaryCotisationTable salaryTableContainer = new SalaryCotisationTable(localcodeElementHead.get(ctx.getRandom().nextInt(localcodeElementHead.size())),
                    localheadingHead.get(ctx.getRandom().nextInt(localheadingHead.size())),
                    localbaseHead.get(ctx.getRandom().nextInt(localbaseHead.size())),
                    localsalaryRateHead.get(ctx.getRandom().nextInt(localsalaryRateHead.size())),
                    localemployeeContrHead.get(ctx.getRandom().nextInt(localemployeeContrHead.size())),
                    localemployerRateHead.get(ctx.getRandom().nextInt(localemployerRateHead.size())),
                    localemployerContrHead.get(ctx.getRandom().nextInt(localemployerContrHead.size())));

            // composantes remun
            List<SalaryLine> composantesRemunLines = composantesRenum.get(ctx.getRandom().nextInt(composantesRenum.size()));

            if(composantesRemunLines.size() == 1){

               // **
                brut= Float.parseFloat(brutSal.replace(',','.'));
                //brut= (float)(salaryTableContainer.getBrutSalary()+salaryTableContainer.random.nextDouble()*100);
                salaryTableContainer.setBrutSalary(brut);
                SalaryLine s = composantesRemunLines.get(0);
                s.setEmployeeContributions(brut);
                salaryTableContainer.addLineSalary(s);
            }else {
                tauxHorr = (float) salaryTableContainer.getTauxHorr()+ salaryTableContainer.random.nextFloat()*10;
                salaryTableContainer.setTauxHorr(tauxHorr);

                // la base
                brut= Float.parseFloat(brutSal.replace(',','.'));
                SalaryLine s = composantesRemunLines.get(0);
                s.setBase(brut/tauxHorr);
                s.setSalaryRate(tauxHorr);
                base = brut;//s.getBase()*tauxHorr;
                s.setEmployeeContributions(base);
                salaryTableContainer.addLineSalary(s);


                for (int i = 1; i < composantesRemunLines.size()-1; i++) {
                    SalaryLine s1 = composantesRemunLines.get(i);
                    // base
                    if (s1.getBase() == 1)s1.setBase(base);
                    else if (s1.getBase() == 2) s1.setBase(salaryTableContainer.random.nextFloat()*200);
                    // taux
                    if (s1.getSalaryRate()==2)s1.setSalaryRate(tauxHorr+salaryTableContainer.random.nextFloat()*10);
                    // cotisations salarié
                    if( s1.getBase()!=0 && s1.getSalaryRate()!=0) s1.setEmployeeContributions(s1.getSalaryRate()*s1.getBase()/100);
                    else if(s1.getBase()!=0 && s1.getSalaryRate()==0) s1.setEmployeeContributions(s1.getBase());
                    salaryTableContainer.addLineSalary(s1);
                    brut += s1.getEmployeeContributions();
                }
                salaryTableContainer.setBrutSalary(brut);

                SalaryLine s2 = composantesRemunLines.get(composantesRemunLines.size()-1);
                s2.setEmployeeContributions(brut);
                salaryTableContainer.addLineSalary(s2);
            }
            // santé
            List<SalaryLine> santeLines = santeCotisations.get(ctx.getRandom().nextInt(santeCotisations.size()));
            for (int i= 0; i<santeLines.size(); i++){
                SalaryLine s = santeLines.get(i);
                if (s.getBase() == 1)s.setBase(brut);
                if (s.getSalaryRate() != 0) s.setEmployeeContributions(s.getBase()*s.getSalaryRate()/100);
                if (s.getEmployerRate() != 0) s.setEmployerContributions(s.getBase()*s.getEmployerRate()/100);
                salaryTableContainer.addLineSalary(s);
                listCotisations.add(s);
            }
            // accident de travail
            List<SalaryLine> accidentLines = accidentCotisation.get(ctx.getRandom().nextInt(accidentCotisation.size()));
            for (int i= 0; i<accidentLines.size(); i++){
                SalaryLine s = accidentLines.get(i);
                if (s.getBase() == 1)s.setBase(brut);
                if (s.getSalaryRate() != 0) s.setEmployeeContributions(s.getBase()*s.getSalaryRate()/100);
                if (s.getEmployerRate() != 0) s.setEmployerContributions(s.getBase()*s.getEmployerRate()/100);
                salaryTableContainer.addLineSalary(s);
                listCotisations.add(s);
            }
            // Retraite
            List<SalaryLine> retraiteLines = retaraiteCotisation.get(ctx.getRandom().nextInt(retaraiteCotisation.size()));
            for (int i= 0; i<retraiteLines.size(); i++){
                SalaryLine s = retraiteLines.get(i);
                if (s.getBase() == 1)s.setBase(brut);
                if (s.getSalaryRate() != 0) s.setEmployeeContributions(s.getBase()*s.getSalaryRate()/100);
                if (s.getEmployerRate() != 0) s.setEmployerContributions(s.getBase()*s.getEmployerRate()/100);
                salaryTableContainer.addLineSalary(s);
                listCotisations.add(s);
            }
            // prestations familiales
            List<SalaryLine> prestationLines = prestationsCotisation.get(ctx.getRandom().nextInt(prestationsCotisation.size()));
            for (int i= 0; i<prestationLines.size(); i++){
                SalaryLine s = prestationLines.get(i);
                if (s.getBase() == 1)s.setBase(brut);
                if (s.getSalaryRate() != 0) s.setEmployeeContributions(s.getBase()*s.getSalaryRate()/100);
                if (s.getEmployerRate() != 0) s.setEmployerContributions(s.getBase()*s.getEmployerRate()/100);
                salaryTableContainer.addLineSalary(s);
                listCotisations.add(s);
            }
            // chomage
            List<SalaryLine> chomageLines = chomageCotisation.get(ctx.getRandom().nextInt(chomageCotisation.size()));
            for (int i= 0; i<chomageLines.size(); i++){
                SalaryLine s = chomageLines.get(i);
                if (s.getBase() == 1)s.setBase(brut);
                if (s.getSalaryRate() != 0) s.setEmployeeContributions(s.getBase()*s.getSalaryRate()/100);
                if (s.getEmployerRate() != 0) s.setEmployerContributions(s.getBase()*s.getEmployerRate()/100);
                salaryTableContainer.addLineSalary(s);
                listCotisations.add(s);
            }
            // autres contributions due à l'emplyeur
            List<SalaryLine> otherCotLines = othercotisations.get(ctx.getRandom().nextInt(othercotisations.size()));
            for (int i= 0; i<otherCotLines.size(); i++){
                SalaryLine s = otherCotLines.get(i);
                if (s.getBase() == 1)s.setBase(brut);
                if (s.getSalaryRate() != 0) s.setEmployeeContributions(s.getBase()*s.getSalaryRate()/100);
                if (s.getEmployerRate() != 0) s.setEmployerContributions(s.getBase()*s.getEmployerRate()/100);
                salaryTableContainer.addLineSalary(s);
                listCotisations.add(s);
            }
            // Total des retenues
            List<Float> totalRetenue = salaryTableContainer.getTotalCotisations(listCotisations);

            List<SalaryLine> totalCotisations = salaryTableContainer.getTotalCotisationslist(totalRetenue);
            for (int i= 0; i<totalCotisations.size(); i++){
               salaryTableContainer.addLineSalary(totalCotisations.get(i));
            }
            // Total imposable
            float netImpos = brut-totalRetenue.get(0);
            salaryTableContainer.setNetImposabel(netImpos);
            SalaryLine netimposable = new SalaryLine(0,"Net Imposable ",0,0,netImpos,0,0);
            salaryTableContainer.addLineSalary(netimposable);

            // csg non deductible
            List<SalaryLine> csgCotLines = csgcotisations.get(ctx.getRandom().nextInt(csgcotisations.size()));
            for (int i= 0; i<csgCotLines.size(); i++){
                SalaryLine s = csgCotLines.get(i);
                if (s.getBase() == 1)s.setBase(brut);
                if (s.getSalaryRate() != 0) s.setEmployeeContributions(s.getBase()*s.getSalaryRate()/100);
                if (s.getEmployerRate() != 0) s.setEmployerContributions(s.getBase()*s.getEmployerRate()/100);
                csgCotLines.set(i,s);
                salaryTableContainer.addLineSalary(s);
            }
            float adeduire=0;
            // a deduite du net imoosable
            for (int i=0;i<csgCotLines.size();i++){
                adeduire+= csgCotLines.get(i).getEmployeeContributions();
            }
            float net=netimposable.getEmployeeContributions()-adeduire;

            salaryTableContainer.setNetSalary(net);
            //net à payer avnt impot sur le revenue
            SalaryLine s1 = new SalaryLine(0,"NET À PAYER AVANT IMPOT SUR LE REVENU",0,0,net,0,0);
            salaryTableContainer.addLineSalary(s1);
            //
            return salaryTableContainer;
        }
    }
}
