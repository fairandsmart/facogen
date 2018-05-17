package com.fairandsmart.invoices.element;

import com.fairandsmart.invoices.InvoiceGenerator;
import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.layout.product.ProductBox;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.xml.stream.XMLStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(JUnit4.class)
public class TestProductBox implements InvoiceLayout {

    @Override
    public void builtInvoice(InvoiceModel model, PDDocument document, XMLStreamWriter writer) throws Exception {

        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        writer.writeStartElement("DL_PAGE");
        writer.writeAttribute("gedi_type", "DL_PAGE");
        writer.writeAttribute("pageID", "1");
        writer.writeAttribute("width", "2480");
        writer.writeAttribute("height", "3508");

        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        PDFont font = PDType1Font.HELVETICA_BOLD;

        ProductBox products = new ProductBox(25, 750, model.getProductContainer());
        products.build(contentStream, writer);

        contentStream.close();

        writer.writeEndElement();
    }

    @Test
    public void test() throws Exception {
        String ts = "" + System.currentTimeMillis();
        Path pdf = Paths.get("target/productbox-"+ ts + ".pdf");
        Path xml = Paths.get("target/productbox-"+ ts + ".xml");
        Path img = Paths.get("target/productbox-"+ ts + ".tiff");

        GenerationContext ctx = GenerationContext.generate();
        InvoiceModel model = new InvoiceModel.Generator().generate(ctx);
        InvoiceGenerator.getInstance().generateInvoice(this, model, pdf, xml, img);
    }
}

