package com.fairandsmart.invoices.data.model;

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

public class Product {


    private String description;
    private String taxType;

    private String name;
    private String ean;
    private String sku;
    private String brand;
    private float priceWithTax;
    private float priceWithoutTax;
    private int quantity;
    private float taxRate;
    private String currency;

    //Added
    private float discount;
    private float ecoParticipationWithoutTax;
    private int taxReference;
    private String deliveryType;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public float getPriceWithTax() {
        return priceWithTax;
    }

    public void setPriceWithTax(float priceWithTax) {
        this.priceWithTax = priceWithTax;
    }

    public float getPriceWithoutTax() {
        return priceWithoutTax;
    }

    public String getFormatedPriceWithoutTax() {
        return String.format("%.2f", this.getPriceWithoutTax()) + " " + currency;
    }

    public void setPriceWithoutTax(float priceWithoutTax) {
        this.priceWithoutTax = priceWithoutTax;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(float taxRate) {
        this.taxRate = taxRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getTotalPriceWithTax() {
        return priceWithTax * quantity;
    }

    public String getFormatedTotalPriceWithTax() {

        return String.format("%.2f", this.getTotalPriceWithTax()) + " " + currency;
    }


    public float getTotalPriceWithoutTax() {
        return priceWithoutTax * quantity;
    }

    public String getFormatedTotalPriceWithoutTax() {

        return String.format("%.2f", this.getTotalPriceWithoutTax()) + " " + currency;
    }

    public float getTotalTax() {
        return this.getTotalPriceWithTax() - this.getTotalPriceWithoutTax();
    }

    public String getFormatedTotalTax() {
        return String.format("%.2f", this.getTotalTax()) + " " + currency;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public float getEcoParticipationWithoutTax() {
        return ecoParticipationWithoutTax;
    }

    public void setEcoParticipationWithoutTax(float ecoParticipationWithoutTax) {
        this.ecoParticipationWithoutTax = ecoParticipationWithoutTax;
    }

    public int getTaxReference() {
        return taxReference;
    }

    public void setTaxReference(int taxReference) {
        this.taxReference = taxReference;
    }

    @Override
    public String toString() {
        return "Product{" +
                "description='" + description + '\'' +
                ", taxType='" + taxType + '\'' +
                ", name='" + name + '\'' +
                ", ean='" + ean + '\'' +
                ", sku='" + sku + '\'' +
                ", brand='" + brand + '\'' +
                ", priceWithTax=" + priceWithTax +
                ", priceWithoutTax=" + priceWithoutTax +
                ", quantity=" + quantity +
                ", taxRate=" + taxRate +
                ", currency='" + currency + '\'' +
                ", discount=" + discount +
                ", ecoParticipationWithoutTax=" + ecoParticipationWithoutTax +
                ", taxReference=" + taxReference +
                ", deliveryType='" + deliveryType + '\'' +
                '}';
    }

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
