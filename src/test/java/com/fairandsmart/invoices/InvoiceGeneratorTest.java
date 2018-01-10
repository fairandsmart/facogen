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
    public void testBasicTemplateGeneration() throws Exception {
        String ts = "" + System.currentTimeMillis();
        Path pdf = Paths.get("target/basic"+ ts + ".pdf");
        Path xml = Paths.get("target/basic"+ ts + ".xml");
        Path img = Paths.get("target/basic"+ ts + ".tiff");
        InvoiceGenerator.getInstance().generateInvoice(InvoiceGenerator.Model.BASIC, pdf, xml, img);
    }

}
