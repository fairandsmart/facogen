package com.fairandsmart.invoices.layout.fairandsmart;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.datatable.DataTable;
import com.fairandsmart.invoices.data.model.InvoiceModel;
import com.fairandsmart.invoices.layout.InvoiceLayout;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FairAndSmartLayout implements InvoiceLayout {

    @Override
    public void builtInvoice(InvoiceModel model, PDDocument document, XMLStreamWriter writer) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        //Global values
        float margin = 10;
        float bottomMargin = 0;

        PDFont font = PDType1Font.HELVETICA_BOLD;
        PDPageContentStream contentStream = new PDPageContentStream(document, page);


        //Logo
        String logo = this.getClass().getClassLoader().getResource("parts/fairandsmart/logo.png").getFile();
        PDImageXObject pdImage = PDImageXObject.createFromFile(logo, document);
        contentStream.drawImage(pdImage, margin, 715, pdImage.getWidth() / 4, pdImage.getHeight() / 4);

        //Company address
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.newLineAtOffset(margin, 700);
        contentStream.showText("Fair and Smart SAS");
        contentStream.newLineAtOffset(0, -16);
        contentStream.showText("11 Rempart St Thiébault");
        contentStream.newLineAtOffset(0, -16);
        contentStream.showText("57000 Metz - France");
        contentStream.endText();

        //Invoice Number
        contentStream.beginText();
        contentStream.setFont(font, 20);
        contentStream.newLineAtOffset(400, 750);
        contentStream.showText("INVOICE N°1276551");
        contentStream.endText();

        //Invoice content
        //Initialize table
        float tableWidth = page.getMediaBox().getWidth() - (2 * margin);
        float yStartNewPage = page.getMediaBox().getHeight() - (2 * margin);
        float yStart = 450;
        List<List> data = new ArrayList();
        data.add(new ArrayList<>( Arrays.asList("Column One", "Column Two", "Column Three", "Column Four", "Column Five")));
        for (int i = 1; i <= 10; i++) {
            data.add(new ArrayList<>( Arrays.asList("Row " + i + " Col One", "Row " + i + " Col Two", "Row " + i + " Col Three", "Row " + i + " Col Four", "Row " + i + " Col Five")));
        }
        BaseTable dataTable = new BaseTable(yStart, yStartNewPage, bottomMargin, tableWidth, margin, document, page, true, true);
        DataTable t = new DataTable(dataTable, page);
        t.addListToTable(data, DataTable.HASHEADER);
        dataTable.draw();

        contentStream.close();
    }
}
