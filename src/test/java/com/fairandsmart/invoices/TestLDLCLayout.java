package com.fairandsmart.invoices;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import com.fairandsmart.invoices.layout.ldlc.LDLCLayout;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestLDLCLayout {

    @Test
    public void test() throws Exception {
        String ts = "" + System.currentTimeMillis();
        Path pdf = Paths.get("target/ldlc-"+ ts + ".pdf");
        Path xml = Paths.get("target/ldlc-"+ ts + ".xml");
        Path img = Paths.get("target/ldlc-"+ ts + ".tiff");

        GenerationContext ctx = GenerationContext.generate();
        InvoiceModel model = new InvoiceModel.Generator().generate(ctx);
        InvoiceLayout layout = new LDLCLayout();
        InvoiceGenerator.getInstance().generateInvoice(layout, model, pdf, xml, img);
    }

}
