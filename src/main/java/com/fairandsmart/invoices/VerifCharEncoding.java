package com.fairandsmart.invoices;

import com.fairandsmart.invoices.element.textbox.SimpleTextBox;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.encoding.WinAnsiEncoding;

public class VerifCharEncoding extends WinAnsiEncoding {

    public static String remove(String test) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < test.length(); i++) {
            if(test.charAt(i)=='\u00A0'){
                b.append(' ');
            }
            else if (WinAnsiEncoding.INSTANCE.contains(test.charAt(i))) {
                b.append(test.charAt(i));
            }
        }
        return b.toString();
    }

    public static void main(String[] args) throws Exception {
        String  text = "abc"+"\u00A0"+"cde";
        //System.out.println(remove(text));
        new SimpleTextBox(PDType1Font.HELVETICA, 8, 0, 0, text);
        // prints abccde
    }

}