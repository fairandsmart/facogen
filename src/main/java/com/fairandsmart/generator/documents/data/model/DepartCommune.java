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

public class DepartCommune {

    private String code_comm;
    private String nom_dept;
    private String code_dept;
    private String nom_comm;

    public DepartCommune(String code_comm, String nom_dept, String code_dept, String nom_comm) {
        this.code_comm = code_comm;
        this.nom_dept = nom_dept;
        this.code_dept = code_dept;
        this.nom_comm = nom_comm;
    }

    public DepartCommune() {
    }

    public String getCode_comm() {
        return code_comm;
    }

    public String getNom_dept() {
        return nom_dept;
    }

    public String getCode_dept() {
        return code_dept;
    }

    public String getNom_comm() {
        return nom_comm;
    }

    public void setCode_comm(String code_comm) {
        this.code_comm = code_comm;
    }

    public void setNom_dept(String nom_dept) {
        this.nom_dept = nom_dept;
    }

    public void setCode_dept(String code_dept) {
        this.code_dept = code_dept;
    }

    public void setNom_comm(String nom_comm) {
        this.nom_comm = nom_comm;
    }

    @Override
    public String toString() {
        return "DepartCommune{" +
                "code_comm='" + code_comm + '\'' +
                ", nom_dept='" + nom_dept + '\'' +
                ", code_dept='" + code_dept + '\'' +
                ", nom_comm='" + nom_comm + '\'' +
                '}';
    }
}
