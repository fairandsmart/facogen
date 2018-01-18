package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.Product;

import java.util.List;

public class InvoiceModel {

    private InvoiceNumber reference;
    private Company seller;
    private Client client;
    private List<Product> products;
    private List<Artefact> artefacts;

}
