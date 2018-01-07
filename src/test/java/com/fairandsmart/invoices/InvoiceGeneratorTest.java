package com.fairandsmart.invoices;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class InvoiceGeneratorTest {

    @Test
    public void testBasicTemplateGeneration() throws IOException {
        String ts = "" + System.currentTimeMillis();
        Path output = Paths.get("target/basic"+ ts + ".pdf");
        InvoiceGenerator.getInstance().generateInvoice(InvoiceGenerator.Model.BASIC, output);
    }

}
