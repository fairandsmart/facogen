package com.fairandsmart.invoices;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.data.model.InvoiceNumber;
import com.fairandsmart.invoices.layout.amazon.AmazonLayout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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

        InvoiceModel model = new InvoiceModel();

        for ( int i=0; i<25; i++ ) {
            GenerationContext ctx = GenerationContext.generate();
            InvoiceNumber nb = new InvoiceNumber.Generator().generate(ctx);
            System.out.println(nb);
        }

        InvoiceGenerator.getInstance().generateInvoice(new AmazonLayout(), model, pdf, xml, img);
    }

}
