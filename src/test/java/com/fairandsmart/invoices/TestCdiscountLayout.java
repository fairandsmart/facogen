package com.fairandsmart.invoices;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import com.fairandsmart.invoices.layout.cdiscount.CdiscountLayout;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestCdiscountLayout {

    @Test
    public void test() throws Exception {
        String ts = "" + System.currentTimeMillis();

        for ( int i=0; i<100; i++) {

            Path pdf = Paths.get("target/cdiscount/cdiscount" + ts + ".pdf");
            Path xml = Paths.get("target/cdiscount/cdiscount" + ts + ".xml");
            Path img = Paths.get("target/cdiscount/cdiscount" + ts + ".tiff");

            GenerationContext ctx = GenerationContext.generate();
            ctx.setCountry("FR");
            ctx.setLanguage("fr");
            InvoiceModel model = new InvoiceModel.Generator().generate(ctx);
            InvoiceLayout layout = new CdiscountLayout();
            InvoiceGenerator.getInstance().generateInvoice(layout, model, pdf, xml, img);
        }
    }

}
