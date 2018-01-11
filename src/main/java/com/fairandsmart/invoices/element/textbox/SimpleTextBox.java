package com.fairandsmart.invoices.element.textbox;

import com.fairandsmart.invoices.element.BoxBoundary;
import com.fairandsmart.invoices.element.ElementBox;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import javax.xml.stream.XMLStreamWriter;
import java.awt.*;
import java.util.logging.Logger;

public class SingleLineTextBox extends ElementBox {

    private static final Logger LOGGER = Logger.getLogger(SingleLineTextBox.class.getName());

    private PDFont font;
    private float fontSize;
    private BoxBoundary box;
    private float underline;
    private float overline;
    private String text;

    public SingleLineTextBox(PDFont font, float fontSize, float posX, float posY, String text) throws Exception {
        this.font = font;
        this.fontSize = fontSize;
        this.text = text;
        this.box = new BoxBoundary(posX, posY, fontSize * font.getStringWidth(text) / 1000, fontSize);
        this.underline = font.getFontDescriptor().getFontBoundingBox().getLowerLeftY() / 1000 * fontSize;
        this.overline = font.getFontDescriptor().getFontBoundingBox().getUpperRightY() / 1000 * fontSize;
    }

    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        stream.beginText();
        stream.setNonStrokingColor(Color.BLACK);
        stream.setFont(font, fontSize);
        stream.newLineAtOffset(box.getPosX(), box.getPosY());
        stream.showText(text);
        stream.endText();

        String[] words = text.split(" ");
        float offsetX = 0;
        for (String word : words) {
            //TODO we need to count the number of spaces between word to include the good posX in the offset
            float wordWidth = fontSize * font.getStringWidth(word) / 1000;
            BoxBoundary wordBox = new BoxBoundary(box.getPosX() + offsetX, box.getPosY() + underline, box.getWidth(), overline - underline);
            writeXMLZone(writer, "word", word, wordBox);
            offsetX = offsetX + wordWidth + (fontSize * font.getSpaceWidth() / 1000);
        }

        BoxBoundary lineBox = new BoxBoundary(box.getPosX(), box.getPosY() + underline, box.getWidth(), overline - underline);
        writeXMLZone(writer, "line", text, lineBox);
    }

    @Override
    public BoxBoundary getBoxBoundary() {
        return box;
    }

}
