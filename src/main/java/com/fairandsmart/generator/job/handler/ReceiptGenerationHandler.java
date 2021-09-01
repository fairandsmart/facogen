package com.fairandsmart.generator.job.handler;

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
 * Djedjiga Belhadj <djedjiga.belhadj@gmail.com> / Loria
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

import com.fairandsmart.generator.documents.ReceiptGenerator;
import com.fairandsmart.generator.documents.data.generator.GenerationContext;
import com.fairandsmart.generator.documents.data.model.ReceiptModel;
import com.fairandsmart.generator.documents.layout.ReceiptLayout;
import com.fairandsmart.generator.documents.layout.payslip.GenericPayslipLayout;
import com.fairandsmart.generator.documents.layout.receipt.GenericReceiptLayout;
import com.fairandsmart.generator.job.JobManager;
import com.fairandsmart.generator.job.JobNotFoundException;
import com.fairandsmart.generator.job.entity.Job;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Dependent
public class ReceiptGenerationHandler implements JobHandler {

    private static final Logger LOGGER = Logger.getLogger(ReceiptGenerationHandler.class.getName());
    public static final String[] SUPPORTED_TYPES = {"receipt.generate"};
    public static final String PARAM_QTY = "qty";
    public static final String PARAM_START_IDX = "start-idx";
    public static final String PARAM_OUTPUT = "output";

    private String root;
    private Long jobId;
    private Map<String, String> params;

    @Inject
    @Any
    Instance<GenericReceiptLayout> layouts;


    @Inject
    JobManager manager;

    public ReceiptGenerationHandler() {
        LOGGER.log(Level.INFO, "ReceiptGenerationHandler instanciated");
    }

    @Override
    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @Override
    public void setJobRoot(String root) {
        this.root = root;
    }

    @Override
    public void setJobParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public boolean canHandle(Job job) {
        return Arrays.stream(SUPPORTED_TYPES).anyMatch(type -> type.equals(job.type));
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "Starting Generation Handler thread");
        StringBuffer report = new StringBuffer();

        try {
            try {
                LOGGER.log(Level.INFO, "ReceiptGeneration started");
                if ( !params.containsKey(PARAM_QTY) ) {
                    report.append("Missing parameters: " + PARAM_QTY);
                    manager.fail(jobId, report.toString());
                    return;
                }
                manager.start(jobId);
                LOGGER.log(Level.INFO, "Starting generation of receipts");
                report.append("Starting generation of receipts \r\n");
                int qty = Integer.parseInt(params.get(PARAM_QTY));
                int start = Integer.parseInt(params.getOrDefault(PARAM_START_IDX, "1"));
                int stop = start + qty;
                //TODO Filter layouts according to param
                LOGGER.log(Level.INFO, "layouts");
                List<GenericReceiptLayout> availableLayouts = layouts.stream().collect(Collectors.toList());

                LOGGER.log(Level.INFO, "availableLayouts.size() = "+availableLayouts.size());

                if ( availableLayouts.size() == 0 ) {
                    report.append("Unable to find available layouts for this job.");
                    LOGGER.log(Level.INFO, "Unable to find available layouts for this job");
                    manager.fail(jobId, report.toString());
                    return;
                }
                LOGGER.log(Level.INFO, "After generating layout");
                for ( int i=start; i<stop; i++) {
                    Path pdf = Paths.get(root, params.getOrDefault(PARAM_OUTPUT, "receipt") + "-" + i + ".pdf");
                    Path xml = Paths.get(root, params.getOrDefault(PARAM_OUTPUT, "receipt") + "-" + i + ".xml");
                    //Path xmlEval = Paths.get(root, params.getOrDefault(PARAM_OUTPUT, "receiptEval") + "-" + i + ".xml");
                    Path xmlEval = null;
                    Path img = Paths.get(root, params.getOrDefault(PARAM_OUTPUT, "receipt") + "-" + i + ".tiff");
                    //TODO configure context according to config
                    GenerationContext ctx = GenerationContext.generate();
                    ReceiptModel model = new ReceiptModel.Generator().generate(ctx);
                    ReceiptGenerator.getInstance().generateReceipt(new GenericReceiptLayout(), model, pdf, xml, img,xmlEval);
                    manager.progress(jobId, (long)((i-start)*100)/qty);
                }
                report.append("All receipts generated");
                LOGGER.log(Level.INFO, "All receipts generated");
                manager.complete(jobId, report.toString());
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error while executing job",  e);
                report.append("Error occurred during job: " + e.getMessage());
                manager.fail(jobId, report.toString());
            }
        } catch (JobNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Unable to find a job for id: " + jobId, e);
        }
        LOGGER.log(Level.INFO, "Generation Handler thread finished");

    }

}
