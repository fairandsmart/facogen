package com.fairandsmart.invoices;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import com.fairandsmart.invoices.layout.darty.DartyLayout;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestDartyLayout {

    @Test
    public void test() throws Exception {
        String ts = "" + System.currentTimeMillis();

        for ( int i=0; i<100; i++) {

            Path pdf = Paths.get("target/darty/darty" + ts + ".pdf");
            Path xml = Paths.get("target/darty/darty" + ts + ".xml");
            Path img = Paths.get("target/darty/darty" + ts + ".tiff");

            GenerationContext ctx = GenerationContext.generate();
            InvoiceModel model = new InvoiceModel.Generator().generate(ctx);
            ctx.setCountry("FR");
            ctx.setLanguage("fr");
            InvoiceLayout layout = new DartyLayout();
            InvoiceGenerator.getInstance().generateInvoice(layout, model, pdf, xml, img);
        }
    }

}
