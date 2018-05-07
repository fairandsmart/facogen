package com.fairandsmart.invoices;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import com.fairandsmart.invoices.layout.generic.GenericLayout;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestGenericLayout {

    @Test
    public void test() throws Exception {
        String ts = "" + System.currentTimeMillis();
        Path pdf = Paths.get("target/generic-"+ ts + ".pdf");
        Path xml = Paths.get("target/generic-"+ ts + ".xml");
        Path img = Paths.get("target/generic-"+ ts + ".tiff");

        GenerationContext ctx = GenerationContext.generate();
        InvoiceModel model = new InvoiceModel.Generator().generate(ctx);
        InvoiceLayout layout = new GenericLayout();
        InvoiceGenerator.getInstance().generateInvoice(layout, model, pdf, xml, img);
    }

}
