package com.fairandsmart.invoices;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import com.fairandsmart.invoices.layout.ngeneric.genericLayout;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestgenericLayout {

    @Test
    public void test() throws Exception {
      //  String i = "" + System.currentTimeMillis();
        for ( int i=17448; i<19000; i++) {
//        Path pdf = Paths.get("target/pdf/gen-"+ i + ".pdf");
//        Path xml = Paths.get("target/xml/gen-"+ i + ".xml");
//        Path img = Paths.get("target/tiff/gen-"+ i + ".tiff");
        Path pdf = Paths.get("target/new/pdf1/gen-"+ i + ".pdf");
        Path xml = Paths.get("target/new/xml1/gen-"+ i + ".xml");
        Path img = Paths.get("target/new/tiff1/gen-"+ i + ".tiff");
        try {
            GenerationContext ctx = GenerationContext.generate();
            InvoiceModel model = new InvoiceModel.Generator().generate(ctx);
            InvoiceLayout layout = new genericLayout();
            InvoiceGenerator.getInstance().generateInvoice(layout, model, pdf, xml, img);
            System.out.println("current: " + i);
            }
            catch (Exception e){}
     }
    }

}
