package com.fairandsmart.generator.documents;

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

import com.fairandsmart.generator.documents.data.generator.GenerationContext;
import com.fairandsmart.generator.documents.data.model.InvoiceModel;
import com.fairandsmart.generator.documents.layout.InvoiceLayout;
import com.fairandsmart.generator.documents.layout.amazon.AmazonLayout;
import com.fairandsmart.generator.documents.layout.bdmobilier.BDmobilierLayout;
import com.fairandsmart.generator.documents.layout.cdiscount.CdiscountLayout;
import com.fairandsmart.generator.documents.layout.darty.DartyLayout;
import com.fairandsmart.generator.documents.layout.ldlc.LDLCLayout;
import com.fairandsmart.generator.documents.layout.macomp.MACOMPLayout;
import com.fairandsmart.generator.documents.layout.materielnet.MaterielnetLayout;
import com.fairandsmart.generator.documents.layout.ngeneric.NGenericLayout;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class InvoiceGeneratorTest {

    @Test
    public void generateUsingRoundRobinOnAllLayout() throws Exception {

        List<InvoiceLayout> availablesLayout = new ArrayList<>();
        availablesLayout.add(new AmazonLayout());
        availablesLayout.add(new BDmobilierLayout());
        availablesLayout.add(new CdiscountLayout());
        availablesLayout.add(new DartyLayout());
        //availablesLayout.add(new GenericLayout());
        availablesLayout.add(new LDLCLayout());
        availablesLayout.add(new MACOMPLayout());
        availablesLayout.add(new MaterielnetLayout());
        availablesLayout.add(new NGenericLayout());

        Path generated = Paths.get("target/generated");
        if ( !Files.exists(generated) ) {
            Files.createDirectory(generated);
        }

        for ( int i=0; i<40; i++) {
            //String ts = "" + System.currentTimeMillis();
            Path pdf = Paths.get("target/generated/basic-"+ i + ".pdf");
            Path xml = Paths.get("target/generated/basic-"+ i + ".xml");
            Path img = Paths.get("target/generated/basic-"+ i + ".tiff");
            GenerationContext ctx = GenerationContext.generate();
            InvoiceModel model = new InvoiceModel.Generator().generate(ctx);
            InvoiceGenerator.getInstance().generateInvoice(availablesLayout.get(i % availablesLayout.size()), model, pdf, xml, img);
            System.out.println("current: " + i);
        }

    }

}
