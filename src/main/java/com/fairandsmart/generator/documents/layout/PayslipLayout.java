package com.fairandsmart.generator.documents.layout;

import com.fairandsmart.generator.documents.data.model.PayslipModel;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.xml.stream.XMLStreamWriter;

public interface PayslipLayout {
    String name();

    void builtPayslip(PayslipModel model, PDDocument document, XMLStreamWriter writer) throws Exception;

}
