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

public class SalaryLine {

    //News
    private int codeElement;
    private String heading;
    private float base;
    private float salaryRate; // taux salarial
    private float employeeContributions; // cotisations salariales
    private float employerRate; // taux patronal
    private float employerContributions; // cotisations patronales

    public int getCodeElement() {
        return codeElement;
    }

    public String getHeading() {
        return heading;
    }

    public float getBase() {
        return base;
    }

    public float getSalaryRate() {
        return salaryRate;
    }

    public float getEmployeeContributions() {
        return employeeContributions;
    }

    public float getEmployerRate() {
        return employerRate;
    }

    public float getEmployerContributions() {
        return employerContributions;
    }

    public void setCodeElement(int codeElement) {
        this.codeElement = codeElement;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public void setBase(float base) {
        this.base = base;
    }

    public void setSalaryRate(float salaryRate) {
        this.salaryRate = salaryRate;
    }

    public void setEmployeeContributions(float employeeContributions) {
        this.employeeContributions = employeeContributions;
    }

    public void setEmployerRate(float employerRate) {
        this.employerRate = employerRate;
    }

    public void setEmployerContributions(float employerContributions) {
        this.employerContributions = employerContributions;
    }

    public SalaryLine(int codeElement, String heading, float base, float salaryRate, float employeeContributions, float employerRate, float employerContributions) {
        this.codeElement = codeElement;
        this.heading = heading;
        this.base = base;
        this.salaryRate = salaryRate;
        this.employeeContributions = employeeContributions;
        this.employerRate = employerRate;
        this.employerContributions = employerContributions;
    }

    public SalaryLine() {
    }

    @Override
    public String toString() {
        return "SalaryLine{" +
                "codeElement=" + codeElement +
                ", heading='" + heading + '\'' +
                ", base=" + base +
                ", salaryRate=" + salaryRate +
                ", employeeContributions=" + employeeContributions +
                ", employerRate=" + employerRate +
                ", employerContributions=" + employerContributions +
                '}';
    }


/*
    public String getFormatedPriceWithoutTax() {
        return String.format("%.2f", this.getPriceWithoutTax()) + " " + currency;
    }*/



    /*
      "price": "479.0",
              "ean": "8806088499048",
              "name": "Lave linge hublot Samsung ADD WASH WW90K4437YW",
              "brand": "Samsung",
              "sku": "000000000001079579",
              "categories": [
              "Gros électroménager",
              "Lave-linge",
              "Lave-linge hublot"
              ]

              */

}
