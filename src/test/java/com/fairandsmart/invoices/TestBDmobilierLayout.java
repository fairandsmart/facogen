package com.fairandsmart.invoices;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import com.fairandsmart.invoices.layout.bdmobilier.BDmobilierLayout;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestBDmobilierLayout {

    @Test
    public void test() throws Exception {
        String ts = "" + System.currentTimeMillis();

        for ( int i=0; i<100; i++) {

            Path pdf = Paths.get("target/bdmobilier/bdmobilier" + ts + ".pdf");
            Path xml = Paths.get("target/bdmobilier/bdmobilier" + ts + ".xml");
            Path img = Paths.get("target/bdmobilier/bdmobilier" + ts + ".tiff");

            GenerationContext ctx = GenerationContext.generate();
            ctx.setCountry("FR");
            ctx.setLanguage("fr");
            InvoiceModel model = new InvoiceModel.Generator().generate(ctx);
            InvoiceLayout layout = new BDmobilierLayout();
            InvoiceGenerator.getInstance().generateInvoice(layout, model, pdf, xml, img);
        }
    }
}
