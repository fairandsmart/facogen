package com.fairandsmart.invoices.element.textbox;

import com.fairandsmart.invoices.element.BoundingBox;
import com.fairandsmart.invoices.element.ElementBox;
import com.fairandsmart.invoices.element.HAlign;
import com.fairandsmart.invoices.element.Padding;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import javax.xml.stream.XMLStreamWriter;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleTextBox extends ElementBox {

    private static final Logger LOGGER = Logger.getLogger(SimpleTextBox.class.getName());

    private Color textColor;
    private Color backgroundColor;
    private PDFont font;
    private float fontSize;
    private BoundingBox box;
    private Padding padding;
    private float lineHeight;
    private float underline;
    private float overline;
    private String text;
    private List<String> lines;
    private String entityName;
    private HAlign halign;

    public SimpleTextBox(PDFont font, float fontSize, float posX, float posY, String text) throws Exception {
        this(font, fontSize, posX, posY, text, Color.BLACK, null);
    }

    public SimpleTextBox(PDFont font, float fontSize, float posX, float posY, String text, Color textColor, Color backgroundColor) throws Exception {
        this(font, fontSize, posX, posY, text, textColor, backgroundColor, HAlign.LEFT);
    }

    public SimpleTextBox(PDFont font, float fontSize, float posX, float posY, String text, Color textColor, Color backgroundColor, HAlign halign) throws Exception {
        this.padding = new Padding();
        this.font = font;
        this.fontSize = fontSize;
        this.text = text;
        this.lines = new ArrayList<>();
        this.lines.add(text);
        this.underline = font.getFontDescriptor().getFontBoundingBox().getLowerLeftY() / 1000 * fontSize;
        this.overline = font.getFontDescriptor().getFontBoundingBox().getUpperRightY() / 1000 * fontSize;
        this.lineHeight = overline - underline;
        this.box = new BoundingBox(posX, posY, fontSize * font.getStringWidth(text) / 1000, lineHeight);
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.halign = halign;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return box;
    }

    @Override
    public void setWidth(float width) throws IOException {
        //TODO we need to count the number of spaces between word to include the good posX in the offset
        //TODO Maybe throw en exception when a single word is longer that the maxwidth (unseccable) or cut the word
        float contentWidth = width - this.padding.getHorizontalPadding();
        if ( contentWidth <= 0 ) {
            throw new IOException("unable to fit content in the desired width");
        }
        String[] words = text.split(" ");
        this.lines = new ArrayList<>();
        String currentLine = "";
        for ( String word : words ) {
            float lineWidth = (this.fontSize * this.font.getStringWidth(currentLine + (currentLine.isEmpty()?"":" ") + word) / 1000);
            //If line is empty, word is too long to fit in the width, we put it anyway and it will be outside of the box...
            if ( !currentLine.isEmpty() && lineWidth > contentWidth ) {
                this.lines.add(currentLine);
                currentLine = word;
            } else {
                currentLine += (currentLine.isEmpty() ? "" : " ") + word;
            }
        }
        this.lines.add(currentLine);
        this.box.setHeight((this.lines.size() * lineHeight) + padding.getVerticalPadding());
        this.box.setWidth(width + padding.getHorizontalPadding());
    }

    @Override
    public void setHeight(float height) throws Exception {
        throw new Exception("Not allowed");
    }

    public void setPadding(float left, float top, float right, float bottom) throws IOException {
        this.padding = new Padding(left, top, right, bottom);
        this.setWidth(this.getBoundingBox().getWidth() + padding.getHorizontalPadding());
        //this.box = new BoundingBox(box.getPosX(), box.getPosY(), box.getWidth() + padding.getHorizontalPadding(), box.getHeight() + padding.getVerticalPadding());
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    public void setTextColor(Color color) {
        this.textColor = color;
    }

    public void setHalign(HAlign halign) {
        this.halign = halign;
    }

    @Override
    public void translate(float offsetX, float offsetY) {
        this.getBoundingBox().translate(offsetX, offsetY);
    }

    public void build(PDPageContentStream stream, XMLStreamWriter writer) throws Exception {
        if ( backgroundColor != null ) {
            stream.setNonStrokingColor(backgroundColor);
            stream.addRect(box.getPosX(), box.getPosY()-box.getHeight(), box.getWidth(), box.getHeight());
            stream.fill();
        }

        stream.beginText();
        stream.setNonStrokingColor(textColor);
        stream.setFont(font, fontSize);
        float lineOffsetX = 0;
        stream.newLineAtOffset(box.getPosX(), box.getPosY() - underline - padding.getTop());
        for (int i=0; i<this.lines.size(); i++) {
            float lineWidth = this.fontSize * this.font.getStringWidth(this.lines.get(i)) / 1000;
            switch ( halign ) {
                case LEFT :
                    lineOffsetX = padding.getLeft(); break;
                case RIGHT:
                    lineOffsetX = box.getWidth() - lineWidth - padding.getRight(); break;
                case CENTER:
                    lineOffsetX = padding.getLeft() + ((box.getWidth() - lineWidth - padding.getHorizontalPadding()) / 2); break;
            }
            stream.newLineAtOffset(lineOffsetX, 0-lineHeight);
            stream.showText(this.lines.get(i));
            stream.newLineAtOffset(0-lineOffsetX, 0);
        }
        stream.endText();

        float offsetY = 0 - padding.getTop() - lineHeight;
        for (int i=0; i<this.lines.size(); i++) {
            float offsetX = padding.getLeft();
            if ( lines.get(i).length() > 0 ) {
                String[] words = lines.get(i).split(" ");
                List<String> wordIds = new ArrayList<>();
                for (String word : words) {
                    //TODO we need to count the number of spaces between word to include the good posX in the offset
                    float wordWidth = fontSize * font.getStringWidth(word) / 1000;
                    BoundingBox wordBox = new BoundingBox(box.getPosX() + offsetX, box.getPosY() + offsetY, wordWidth, lineHeight);
                    wordIds.add(writeXMLZone(writer, "ocr_word", word, wordBox));
                    offsetX = offsetX + wordWidth + (fontSize * font.getSpaceWidth() / 1000);
                }
                if ( entityName != null && entityName.length() > 0 ) {
                    offsetX = padding.getLeft();
                    float lineWidth = fontSize * font.getStringWidth(lines.get(i)) / 1000;
                    BoundingBox entityBox = new BoundingBox(box.getPosX() + offsetX, box.getPosY() + offsetY, lineWidth, lineHeight);
                    writeXMLZone(writer, entityName, this.lines.get(i), entityBox, wordIds);
                }
            }
            offsetY = offsetY - lineHeight;
        }
    }

}
