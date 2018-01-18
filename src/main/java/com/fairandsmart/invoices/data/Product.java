package com.fairandsmart.invoices.data;

public class Product {

    private float qty;
    private String description;
    private float priceByUnitWithoutVAT;
    private float priceByUnitWithTax;
    private String taxType;
    private float taxRate;
    private float discount;


    public float getDiscount() {
        return discount;
    }

    public Product(float qty, String description, float priceByUnitWithoutVAT, float priceByUnitWithTax, String taxType, float taxRate, float discount) {
        this.qty = qty;
        this.description = description;
        this.priceByUnitWithoutVAT = priceByUnitWithoutVAT;
        this.priceByUnitWithTax = priceByUnitWithTax;
        this.taxType = taxType;
        this.taxRate = taxRate;
        this.discount = discount;
    }

    public float getTaxAmount() {

        return this.taxRate * this.priceByUnitWithoutVAT;

    }

    public float getNetAmount() {

        return this.qty * this.priceByUnitWithoutVAT;
    }

    public float getQty() {
        return qty;
    }

    public String getDescription() {
        return description;
    }

    public float getPriceByUnitWithoutVAT() {
        return priceByUnitWithoutVAT;
    }

    public float getPriceByUnitWithTax() {
        return priceByUnitWithTax;
    }

    public String getTaxType() {
        return taxType;
    }

    public float getTaxRate() {
        return taxRate;
    }
}