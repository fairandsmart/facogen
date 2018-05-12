package com.fairandsmart.invoices;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.layout.amazon.AmazonLayout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class InvoiceGeneratorTest {

    @Test
    public void testBasicTemplateGeneration() throws Exception {

        Path generated = Paths.get("target/generated");
        if ( !Files.exists(generated) ) {
            Files.createDirectory(generated);
        }

        for ( int i=0; i<40; i++) {
            //String ts = "" + System.currentTimeMillis();
            Path pdf = Paths.get("target/generated/basic-"+ i + ".pdf");
            Path xml = Paths.get("target/generated/basic-"+ i + ".xml");
            Path img = Paths.get("target/generated/basic-"+ i + ".tiff");
            GenerationContext ctx = GenerationContext.generate();
            InvoiceModel model = new InvoiceModel.Generator().generate(ctx);
            InvoiceGenerator.getInstance().generateInvoice(new AmazonLayout(), model, pdf, xml, img);
            System.out.println("current: " + i);

        }
    }

}
