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

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

public class Employee{

    private static final Logger LOGGER = Logger.getLogger(Employee.class.getName());

    private String name;
    private String arrivalDate;
    private String employment;
    private String position;
    private String email;
    private Address address;
    private String employeeId;
    private String socialSecurityNumber;
    private String department;
    private String bankDetail;
    private String contratType;
    private String civilStatus;

    public  Employee(String name,String arrivalDate, String employment, String position, String email,
                     Address address, String employeeId, String socialSecurityNumber, String department,
                     String bankDetail, String contratType, String civilStatus)
    {
        this.name = name;
        this.arrivalDate = arrivalDate;
        this.employment = employment;
        this.position = position;
        this.email = email;
        this.address = address;
        this.employeeId = employeeId;
        this.socialSecurityNumber = socialSecurityNumber;
        this.department = department;
        this.bankDetail = bankDetail;
        this.contratType = contratType;
        this.civilStatus = civilStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBankDetail() {
        return bankDetail;
    }

    public void setBankDetail(String bankDetail) {
        this.bankDetail = bankDetail;
    }

    public String getContratType() {
        return contratType;
    }

    public void setContratType(String contratType) {
        this.contratType = contratType;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    @Override
   public String toString() {
        return "Employee{" +
                "name='" + name +
                ", employment=" + employment +
                ", position=" + position +
                ", department=" + department +
                ", contratType=" + contratType +
                ", email=" + email +
                ", address=" + address +
                ", arrivalDate=" + arrivalDate +
                ", employeeId=" + employeeId +
                ", socialSecurityNumber=" + socialSecurityNumber +
                ", bankDetail=" + bankDetail +
                ", civilStatus=" + civilStatus +
                '}';
    }

    public static class Generator implements ModelGenerator<Employee> {
//TODO LISTS DE HEADS ET DE CONTENU
        private static final Map<String, String> billingHeads = new LinkedHashMap<>();
        private static final Map<String, String> shippingHeads = new LinkedHashMap<>();

        {
            billingHeads.put("Destinataire", "fr");
            billingHeads.put("Adresse Facturation", "fr");
            billingHeads.put("Adresse de Facturation", "fr");
            billingHeads.put("Invoice To", "en");
            billingHeads.put("Invoiced To", "en");
            billingHeads.put("Invoice Address", "en");
            billingHeads.put("Bill To", "en");
            billingHeads.put("Billed To", "en");
            billingHeads.put("Billing Address", "en");
            billingHeads.put("Sold To", "en");

        }

        {
            shippingHeads.put("Livraison à", "fr");
            shippingHeads.put("Adresse Livraison", "fr");
            shippingHeads.put("Adresse de Livraison", "fr");
            shippingHeads.put("Delivery To", "en");
            shippingHeads.put("Deliver To", "en");
            shippingHeads.put("Delivery Address", "en");
            shippingHeads.put("Ship To", "en");
            shippingHeads.put("Shipped To", "en");
            shippingHeads.put("Shipping Address", "en");
            shippingHeads.put("Send To", "en");

        }

        @Override
        public Employee generate(GenerationContext ctx) {
            Faker faker = Faker.instance(Locale.forLanguageTag(ctx.getLanguage()));
            String name = faker.name().fullName();
            Address address = new Address.Generator().generate(ctx);

            // For Address Heads
            //List<String> localizedBillHeads = billingHeads.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
           //int idxA = ctx.getRandom().nextInt(localizedBillHeads.size()); // Note: Only one index for both shipping & billing, to retrieve similar format heads!


            String arrivalDate = null;
            String employment = null;
            String position = null;
            String email = null;
            String employeeId = null;
            String socialSecurityNumber = null;
            String department = null;
            String bankDetail = null;
            String contratType = null;
            String civilStatus = null;

            return new Employee(name, arrivalDate, employment, position, email,
                    address, employeeId, socialSecurityNumber, department, bankDetail,
                    contratType, civilStatus);
        }
    }
}
