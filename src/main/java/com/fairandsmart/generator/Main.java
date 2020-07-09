package com.fairandsmart.generator;

import com.fairandsmart.generator.documents.InvoiceGenerator;
import com.fairandsmart.generator.documents.data.generator.GenerationContext;
import com.fairandsmart.generator.documents.data.model.InvoiceModel;
import com.fairandsmart.generator.documents.layout.loria.LoriaLayout;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws Exception {
        Path pdf = Paths.get("/tmp/file1.pdf");
        Path xml = Paths.get("/tmp/file1.xml");
        Path img = Paths.get("/tmp/file1.tiff");

        GenerationContext ctx = GenerationContext.generate();
        InvoiceModel model = new InvoiceModel.Generator().generate(ctx);
        InvoiceGenerator.getInstance().generateInvoice(new LoriaLayout(), model, pdf, xml, img);
    }
}
