package com.fairandsmart.invoices.element.image;

import com.fairandsmart.invoices.element.ElementBuilder;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.xml.stream.XMLStreamWriter;

public class ImageBuilder extends ElementBuilder {

    private PDImageXObject image;
    private String text;
    private float posX = 0;
    private float posY = 0;
    private float scale;

    public ImageBuilder(PDImageXObject image, float posX, float posY, float scale, String text) {
        this.image = image;
        this.text = text;
        this.posX = posX;
        this.posY = posY;
        this.scale = scale;
    }

    public ImageBuilder(PDImageXObject image, float posX, float posY, float scale) {
        this.image = image;
        this.posX = posX;
        this.posY = posY;
        this.scale = scale;
    }

    @Override
    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        stream.drawImage(image, posX, posY, image.getWidth() * scale, image.getHeight() * scale);

        //<DL_ZONE gedi_type="ocr_carea" id="block_1_29" col="84" row="928" width="2188" height="2425" contents="Relation : Telephone : 0044/2079632525 Fax : 0044/2079329404 KBC Aartselaar Terms of payment and general sales conditions see reverse side. Payment in EURO : IBAN NR : BEOS 4118 0689 2175 - Swift Code : KREDBEBB Payment conditions : 30 Dagen factuurdatum Reference : 14015 Teak Coffee table 2 levels 130/80/42h 1,00 258,27 10% 232,44 V.A.T. Subtotal 232,44 Client number VAT number Document date Due Date Invoice # 2233 GB-259976879 28/06/2006 28/07/2006 06143212 Article Description Quantity U.P. Price V.A.T, Vrij van BTW ig. Art. 39 bis van het BTW Wetboek GROSS WEIGHT : 43,100 INTR. : 94036010 NET WEIGHT : 43,100 INTR. : 94036010 Intrastat B.O. Ref. : 32477,501,1529,Mrs I DEIGHAN Delivery 00031148 - 28/06/2006 Total GBP 232,44 V.A.T. 0,00"> </DL_ZONE>
        writer.writeStartElement("DL_ZONE");
        writer.writeAttribute("gedi_type", "ocr_carea");
        writer.writeAttribute("id", "block_1_" + nextElementNumber());
        writer.writeAttribute("col", "" + (int) posX);
        writer.writeAttribute("row", "" + (int) posY);
        writer.writeAttribute("width", "" + (int) (image.getWidth() * scale));
        writer.writeAttribute("height", "" + (int) (image.getHeight() * scale));
        writer.writeAttribute("contents", text);
        writer.writeEndElement();
    }

}
