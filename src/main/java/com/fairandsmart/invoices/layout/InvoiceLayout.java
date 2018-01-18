package com.fairandsmart.invoices.layout;

import com.fairandsmart.invoices.data.model.InvoiceModel;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.IOException;

public interface InvoiceLayout {

    void builtInvoice(InvoiceModel model, PDDocument document, XMLStreamWriter writer) throws Exception;

}
