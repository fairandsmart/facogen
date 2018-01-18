package com.fairandsmart.invoices;

import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import com.fairandsmart.invoices.layout.amazon.AmazonLayout;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InvoiceGenerator {

    private static final String DATE_FORMAT = "mm/dd/yyyy hh:mm";

    private static class InvoiceGeneratorHolder {
        private final static InvoiceGenerator instance = new InvoiceGenerator();
    }

    public static InvoiceGenerator getInstance() {
        return InvoiceGeneratorHolder.instance;
    }

    private InvoiceGenerator() {
    }

    public void generateInvoice(InvoiceLayout layout, InvoiceModel model,  Path pdf, Path xml, Path img) throws Exception {

        OutputStream xmlos = Files.newOutputStream(xml);
        XMLStreamWriter xmlout = XMLOutputFactory.newInstance().createXMLStreamWriter(new OutputStreamWriter(xmlos, "utf-8"));
        xmlout.writeStartDocument();
        xmlout.writeStartElement("", "GEID", "http://lamp.cfar.umd.edu/media/projects/GEDI/");
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

        PDDocument document = new PDDocument();
        layout.builtInvoice(model, document, xmlout);
        document.save(pdf.toFile());

        //Export as TIFF
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        BufferedImage bim = pdfRenderer.renderImageWithDPI(0, 300, ImageType.RGB);
        ImageIOUtil.writeImage(bim, img.toString(), 300);

        document.close();

        xmlout.writeEndElement();
        xmlout.writeEndElement();
        xmlout.writeEndDocument();
        xmlout.close();
    }

}
