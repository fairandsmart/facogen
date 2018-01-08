package com.fairandsmart.invoices.layout;

import org.apache.pdfbox.pdmodel.PDDocument;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;

public interface InvoiceLayout {

    void builtInvoice(PDDocument document, XMLStreamWriter writer) throws IOException, XMLStreamException, Exception;

}
