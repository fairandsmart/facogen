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
 * Copyright (C) 2019 - 2020 Fair And Smart
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
import com.fairandsmart.generator.documents.data.model.PayslipModel;
import com.fairandsmart.generator.documents.layout.payslip.GenericPayslipLayout;
import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestGenericPayslipLayout {
    @Test
    public void test() throws Exception {
        Path amazon = Paths.get("target/payslip");
        if ( !Files.exists(amazon) ) {
            Files.createDirectory(amazon);
        }
        for(int i=1; i<=6; i++){
            Path pdf = Paths.get("target/payslip/payslip-"+ i + ".pdf");
            Path xml = Paths.get("target/payslip/payslip-"+ i + ".xml");
            Path img = Paths.get("target/payslip/payslip-"+ i + ".tiff");

            GenerationContext ctx = GenerationContext.generate();
            PayslipModel model = new PayslipModel.Generator().generate(ctx);
            System.out.println(model);
            GenericPayslipLayout layout = new GenericPayslipLayout();
            PayslipGenerator.getInstance().generatePayslip(layout, model, pdf, xml, img);
        }
    }
}
