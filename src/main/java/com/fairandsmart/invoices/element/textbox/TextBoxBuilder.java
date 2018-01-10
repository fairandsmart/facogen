package com.fairandsmart.invoices.element.textbox;

import com.fairandsmart.invoices.element.ElementBuilder;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;

public class TextBoxBuilder extends ElementBuilder {

    private PDFont font;
    private float fontSize;
    private float posX;
    private float posY;
    private float width;
    private float height;
    private String text;

    public TextBoxBuilder(PDFont font, float fontSize, float posX, float posY, float maxWidth, String text) throws Exception {
        this.font = font;
        this.fontSize = fontSize;
        this.posX = posX;
        this.posY = posY;
        this.text = text;
        float realWidth = fontSize * font.getStringWidth(text) / 1000;
        if ( realWidth > maxWidth ) {
            throw new Exception("maxWidth exceeded, multiple line split not implemented yet");
        } else {
            this.width = realWidth;
            this.height = 1.5f * fontSize;
        }
    }

    public TextBoxBuilder(PDFont font, float fontSize, float posX, float posY, String text) throws Exception {
        this.font = font;
        this.fontSize = fontSize;
        this.posX = posX;
        this.posY = posY;
        this.text = text;
        this.width = fontSize * font.getStringWidth(text) / 1000;
        this.height = 1.5f * fontSize;
    }

    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        stream.beginText();
        stream.setNonStrokingColor(Color.BLACK);
        stream.setFont(font, fontSize);
        stream.newLineAtOffset(posX, posY-height);
        stream.showText(text);
        stream.endText();

        float[] convPos = convertZone(posX, posY, width, height);
        writer.writeStartElement("DL_ZONE");
        writer.writeAttribute("gedi_type", "ocr_line");
        writer.writeAttribute("id", "line_1_" + nextElementNumber());
        writer.writeAttribute("col", "" + (int) convPos[0]);
        writer.writeAttribute("row", "" + (int) convPos[1]);
        writer.writeAttribute("width", "" + (int) convPos[2]);
        writer.writeAttribute("height", "" + (int) convPos[3]);
        writer.writeAttribute("contents", text);
        writer.writeEndElement();
    }

}
