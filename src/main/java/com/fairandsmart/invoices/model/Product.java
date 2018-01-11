package com.fairandsmart.invoices.model;


/*

        hmap.put("qty", "1");
        hmap.put("description", "Microsoft Xbox 360 Controller for windows");
        hmap.put("priceByUnitWithoutVAT", "2390");
        hmap.put("discount", "0");
        hmap.put("priceByUnitWithTax", "2390");
        hmap.put("taxType", "CST");
        hmap.put("taxRate", "12.5");
        hmap.put("taxAmount", "265.56");
 */

public class Product {

    private float qty;
    private String description;
    private float priceByUnitWithoutVAT;
    private float priceByUnitWithTax;
    private String taxType;
    private float taxRate;


    public Product(float qty, String description, float priceByUnitWithoutVAT, float priceByUnitWithTax, String taxType, float taxRate) {
        this.qty = qty;
        this.description = description;
        this.priceByUnitWithoutVAT = priceByUnitWithoutVAT;
        this.priceByUnitWithTax = priceByUnitWithTax;
        this.taxType = taxType;
        this.taxRate = taxRate;
    }

    public float getTaxAmount() {

        return this.taxRate * this.priceByUnitWithoutVAT;

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