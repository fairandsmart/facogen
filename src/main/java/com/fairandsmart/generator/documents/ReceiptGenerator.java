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

import com.fairandsmart.generator.documents.data.generator.GenerationContext;
import com.fairandsmart.generator.documents.data.model.ReceiptModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import javax.imageio.ImageIO;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReceiptGenerator {
    private static final String DATE_FORMAT = "mm/dd/yyyy hh:mm";

    private static class PayslipGeneratorHolder {
        private final static ReceiptGenerator instance = new ReceiptGenerator();
    }

    public static ReceiptGenerator getInstance() {
        return PayslipGeneratorHolder.instance;
    }

    private ReceiptGenerator() {
    }

    public void generateReceipt(com.fairandsmart.generator.documents.layout.receipt.GenericReceiptLayout layout, ReceiptModel model, Path pdf, Path xml, Path img, Path xmlForEvaluation) throws Exception {

        Boolean modeEval= true;
        if (xmlForEvaluation == null) modeEval = false;
        OutputStream xmlos = Files.newOutputStream(xml);
        XMLStreamWriter xmlout = XMLOutputFactory.newInstance().createXMLStreamWriter(new OutputStreamWriter(xmlos, "utf-8"));
        xmlout.writeStartDocument();
        xmlout.writeStartElement("", "GEDI", "http://lamp.cfar.umd.edu/media/projects/GEDI/");
        xmlout.writeAttribute("GEDI_version", "2.4");
        xmlout.writeAttribute("GEDI_date", "07/29/2013");
        xmlout.writeStartElement("USER");
        xmlout.writeAttribute("name", "FairAndSmartGenerator");
        xmlout.writeAttribute("date", new SimpleDateFormat(DATE_FORMAT).format(new Date()));
        xmlout.writeAttribute("dateFormat", DATE_FORMAT);
        xmlout.writeEndElement();
        xmlout.writeStartElement("DL_DOCUMENT");
        xmlout.writeAttribute("src", img.getFileName().toString());
        xmlout.writeAttribute("NrOfPages", "1");
        xmlout.writeAttribute("docTag", "xml");

        ///
        OutputStream xmlosEval ;
        XMLStreamWriter xmloutEval = null;
        if (modeEval) {
            xmlosEval = Files.newOutputStream(xmlForEvaluation);
            xmloutEval = XMLOutputFactory.newInstance().createXMLStreamWriter(new OutputStreamWriter(xmlosEval, "utf-8"));
            xmloutEval.writeStartDocument();
            xmloutEval.writeStartElement("", "GEDI", "http://lamp.cfar.umd.edu/media/projects/GEDI/");
            xmloutEval.writeAttribute("GEDI_version", "2.4");
            xmloutEval.writeAttribute("GEDI_date", "07/29/2013");
            xmloutEval.writeStartElement("USER");
            xmloutEval.writeAttribute("name", "FairAndSmartGenerator");
            xmloutEval.writeAttribute("date", new SimpleDateFormat(DATE_FORMAT).format(new Date()));
            xmloutEval.writeAttribute("dateFormat", DATE_FORMAT);
            xmloutEval.writeEndElement();
            xmloutEval.writeStartElement("DL_DOCUMENT");
            xmloutEval.writeAttribute("src", img.getFileName().toString());
            xmloutEval.writeAttribute("NrOfPages", "1");
            xmloutEval.writeAttribute("docTag", "xml");
        }

        PDDocument document = new PDDocument();
        layout.builtSSD(model, document, xmlout,xmloutEval);
        document.save(pdf.toFile());

        //Export as TIFF
        //PDFRenderer pdfRenderer = new PDFRenderer(document);
        //BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
        /////

        PDPage page = document.getPage(0);
        page.setCropBox(new PDRectangle(0f, 100f, 400f, 1000f)); // Here you draw a rectangle around the area you want to specify
        PDFRenderer renderer = new PDFRenderer(document);
        BufferedImage image = renderer.renderImageWithDPI(0, 150);
        ImageIO.write(image, "TIFF", new File(img.toString()));
        document.close();

        /////
        //ImageIOUtil.writeImage(bim, img.toString(), 300);
        //document.close();

        xmlout.writeEndElement();
        xmlout.writeEndElement();
        xmlout.writeEndDocument();
        xmlout.close();

        //////
        if (modeEval) {
            xmloutEval.writeEndElement();
            xmloutEval.writeEndElement();
            xmloutEval.writeEndDocument();
            xmloutEval.close();
        }
    }

    public static void main(String args[]) throws Exception {


        Path generated = Paths.get("target/generated/" + args[0]);
        if ( !Files.exists(generated) ) {
            Files.createDirectories(generated);
        }

        int start = Integer.parseInt(args[1]);
        int stop = Integer.parseInt(args[2]);
        for ( int i=start; i<stop; i++) {
            //String ts = "" + System.currentTimeMillis();

            Path pdf = Paths.get("target/generated/" + args[0] + "/basic-"+ i + ".pdf");
            Path xml = Paths.get("target/generated/" + args[0] + "/basic-"+ i + ".xml");
            Path xmlForEval = Paths.get("target/generated/" + args[0] + "/basic-2"+ i + ".xml");
            Path img = Paths.get("target/generated/" + args[0] + "/basic-"+ i + ".tiff");
            GenerationContext ctx = GenerationContext.generate();
            ReceiptModel model = new ReceiptModel.Generator().generate(ctx);
            ReceiptGenerator.getInstance().generateReceipt(new com.fairandsmart.generator.documents.layout.receipt.GenericReceiptLayout(), model, pdf, xml, img,xmlForEval);
            System.out.println("current: " + i);
        }
    }
}
