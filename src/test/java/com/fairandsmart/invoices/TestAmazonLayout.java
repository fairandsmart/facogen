package com.fairandsmart.invoices;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import com.fairandsmart.invoices.layout.amazon.AmazonLayout;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestAmazonLayout {

    @Test
    public void test() throws Exception {
        String ts = "" + System.currentTimeMillis();
        Path pdf = Paths.get("target/amazon-"+ ts + ".pdf");
        Path xml = Paths.get("target/amazon-"+ ts + ".xml");
        Path img = Paths.get("target/amazon-"+ ts + ".tiff");

        GenerationContext ctx = GenerationContext.generate();
        InvoiceModel model = new InvoiceModel.Generator().generate(ctx);
        InvoiceLayout layout = new AmazonLayout();
        InvoiceGenerator.getInstance().generateInvoice(layout, model, pdf, xml, img);
    }

}
