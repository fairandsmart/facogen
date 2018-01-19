package com.fairandsmart.invoices.data.model;

public class Product {


    private String description;
    private String taxType;

    private String name;
    private String ean;
    private String sku;
    private String brand;
    private float priceWithTax;
    private float priceWithTaxDisplay;
    private float priceWithoutTax;
    private float priceWithoutTaxDisplay;
    private int quantity;
    private float taxRate;
    private float totalPriceWithTax;
    private float totalPriceWithoutTax;
    private float totalTax;

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

    public float getPriceWithTaxDisplay() {
        return priceWithTaxDisplay;
    }

    public void setPriceWithTaxDisplay(float priceWithTaxDisplay) {
        this.priceWithTaxDisplay = priceWithTaxDisplay;
    }

    public float getPriceWithoutTax() {
        return priceWithoutTax;
    }

    public void setPriceWithoutTax(float priceWithoutTax) {
        this.priceWithoutTax = priceWithoutTax;
    }

    public float getPriceWithoutTaxDisplay() {
        return priceWithoutTaxDisplay;
    }

    public void setPriceWithoutTaxDisplay(float priceWithoutTaxDisplay) {
        this.priceWithoutTaxDisplay = priceWithoutTaxDisplay;
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

    public float getTotalPriceWithTax() {
        return priceWithTax * quantity;
    }

    public float getTotalPriceWithoutTax() {
        return priceWithoutTax * quantity;
    }

    public float getTotalTax() {
        return this.getTotalPriceWithTax() - this.getTotalPriceWithoutTax();
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
                ", priceWithTaxDisplay=" + priceWithTaxDisplay +
                ", priceWithoutTax=" + priceWithoutTax +
                ", priceWithoutTaxDisplay=" + priceWithoutTaxDisplay +
                ", quantity=" + quantity +
                ", taxRate=" + taxRate +
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
