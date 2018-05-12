package com.fairandsmart.invoices;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import com.fairandsmart.invoices.layout.macomp.MACOMPLayout;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestMACOMPLayout {

    @Test
    public void test() throws Exception {
        //String ts = "" + System.currentTimeMillis();
        for ( int i=0; i<40; i++) {
        Path pdf = Paths.get("target/macomp-"+ i + ".pdf");
        Path xml = Paths.get("target/macomp-"+ i + ".xml");
        Path img = Paths.get("target/macomp-"+ i + ".tiff");
        GenerationContext ctx = GenerationContext.generate();
        InvoiceModel model = new InvoiceModel.Generator().generate(ctx);
        InvoiceLayout layout = new MACOMPLayout();
        InvoiceGenerator.getInstance().generateInvoice(layout, model, pdf, xml, img);
        System.out.println("current: " + i);

        }
    }

}
