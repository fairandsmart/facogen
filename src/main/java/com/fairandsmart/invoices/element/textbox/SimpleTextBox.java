package com.fairandsmart.invoices.element.textbox;

import com.fairandsmart.invoices.element.BoundingBox;
import com.fairandsmart.invoices.element.ElementBox;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import javax.xml.stream.XMLStreamWriter;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SimpleTextBox extends ElementBox {

    private static final Logger LOGGER = Logger.getLogger(SimpleTextBox.class.getName());

    private PDFont font;
    private float fontSize;
    private BoundingBox box;
    private float underline;
    private float overline;
    private String text;
    private List<String> lines;

    public SimpleTextBox(PDFont font, float fontSize, float posX, float posY, String text) throws Exception {
        this.font = font;
        this.fontSize = fontSize;
        this.text = text;
        this.lines = new ArrayList<>();
        this.lines.add(text);
        this.underline = font.getFontDescriptor().getFontBoundingBox().getLowerLeftY() / 1000 * fontSize;
        this.overline = font.getFontDescriptor().getFontBoundingBox().getUpperRightY() / 1000 * fontSize;
        this.box = new BoundingBox(posX, posY, fontSize * font.getStringWidth(text) / 1000, (overline - underline));
    }

    @Override
    public BoundingBox getBoundingBox() {
        return box;
    }

    @Override
    public void setWidth(float width) throws IOException {
        //TODO we need to count the number of spaces between word to include the good posX in the offset
        //TODO Maybe throw en exception when a single word is longer that the maxwidth (unseccable)
        String[] words = text.split(" ");
        this.lines = new ArrayList<>();
        String currentLine = "";
        for ( String word : words ) {
            if ( (this.fontSize * this.font.getStringWidth(currentLine + (currentLine.isEmpty()?"":" ") + word) / 1000) > width ) {
                //End of line
                this.lines.add(currentLine);
                currentLine = word;
            } else {
                currentLine += (currentLine.isEmpty() ? "" : " ") + word;
            }
        }
        this.lines.add(currentLine);
        this.box.setHeight(this.lines.size() * (overline - underline));
    }

    @Override
    public void setHeight(float height) throws Exception {
        throw new Exception("Not allowed");
    }

    @Override
    public void translate(float offsetX, float offsetY) {
        this.getBoundingBox().translate(offsetX, offsetY);
    }

    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        stream.beginText();
        stream.setNonStrokingColor(Color.BLACK);
        stream.setFont(font, fontSize);
        stream.newLineAtOffset(box.getPosX(), box.getPosY() - underline);
        for (int i=0; i<this.lines.size(); i++) {
            if ( i > 0 ) {
                stream.newLineAtOffset(0, underline - overline);
            }
            stream.showText(this.lines.get(i));
        }
        stream.endText();

        float offsetY = 0;
        for (int i=0; i<this.lines.size(); i++) {
            offsetY = i * (overline - underline);
            float offsetX = 0;
            String[] words = lines.get(i).split(" ");
            for (String word : words) {
                //TODO we need to count the number of spaces between word to include the good posX in the offset
                float wordWidth = fontSize * font.getStringWidth(word) / 1000;
                BoundingBox wordBox = new BoundingBox(box.getPosX() + offsetX, box.getPosY() - offsetY, wordWidth, overline - underline);
                writeXMLZone(writer, "word", word, wordBox);
                offsetX = offsetX + wordWidth + (fontSize * font.getSpaceWidth() / 1000);
            }
            float lineWidth = fontSize * font.getStringWidth(lines.get(i)) / 1000;
            BoundingBox lineBox = new BoundingBox(box.getPosX(), box.getPosY() - offsetY, lineWidth, overline - underline);
            writeXMLZone(writer, "line", text, lineBox);
        }
        //BoxBoundary zoneBox = new BoxBoundary(box.getPosX(), box.getPosY() + underline, box.getWidth(), overline - underline);
        //writeXMLZone(writer, "zone", text, zoneBox);
    }

}
