package com.fairandsmart.generator.invoices;

/*-
 * #%L
 * FacoGen / A tool for annotated GEDI based invoice generation.
 * 
 * Authors:
 * 
 * Xavier Lefevre <xavier.lefevre@fairandsmart.com> / FairAndSmart
 * Nicolas Rueff <nicolas.rueff@fairandsmart.com> / FairAndSmart
 * Alan Balbo <alan.balbo@fairandsmart.com> / FairAndSmart
 * Frederic Pierre <frederic.pierre@fairansmart.com> / FairAndSmart
 * Victor Guillaume <victor.guillaume@fairandsmart.com> / FairAndSmart
 * Jérôme Blanchard <jerome.blanchard@fairandsmart.com> / FairAndSmart
 * Aurore Hubert <aurore.hubert@fairandsmart.com> / FairAndSmart
 * Kevin Meszczynski <kevin.meszczynski@fairandsmart.com> / FairAndSmart
 * %%
 * Copyright (C) 2019 Fair And Smart
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.fairandsmart.generator.invoices.layout.InvoiceLayout;
import com.fairandsmart.generator.invoices.layout.ngeneric.NGenericLayout;
import com.fairandsmart.generator.invoices.data.generator.GenerationContext;
import com.fairandsmart.generator.invoices.data.model.InvoiceModel;
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
            InvoiceLayout layout = new NGenericLayout();
            InvoiceGenerator.getInstance().generateInvoice(layout, model, pdf, xml, img);
            System.out.println("current: " + i);
            }
            catch (Exception e){}
     }
    }

}
