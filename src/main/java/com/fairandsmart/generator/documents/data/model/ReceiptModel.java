package com.fairandsmart.generator.documents.data.model;

/*-
 * #%L
 * FacoGen / A tool for annotated GEDI based invoice generation.
 * 
 * Authors:
 * 
 * Xavier Lefevre <xavier.lefevre@fairandsmart.com> / FairAndSmart
 * Nicolas Rueff <nicolas.rueff@fairandsmart.com> / FairAndSmart
 * Alan Balbo <alan.balbo@fairandsmart.com> / FairAndSmart
 * Frederic Pierre <frederic.pierre@fairansmart.com> / FairAndSmart
 * Victor Guillaume <victor.guillaume@fairandsmart.com> / FairAndSmart
 * Jérôme Blanchard <jerome.blanchard@fairandsmart.com> / FairAndSmart
 * Aurore Hubert <aurore.hubert@fairandsmart.com> / FairAndSmart
 * Kevin Meszczynski <kevin.meszczynski@fairandsmart.com> / FairAndSmart
 * Djedjiga Belhadj <djedjiga.belhadj@gmail.com> / Loria
 * %%
 * Copyright (C) 2019 - 2020 Fair And Smart
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.fairandsmart.generator.documents.data.generator.GenerationContext;
import com.fairandsmart.generator.documents.data.generator.ModelGenerator;
import com.fairandsmart.generator.documents.element.product.ReceiptProductBox;
import com.mifmif.common.regex.Generex;

import java.util.*;
import java.util.stream.Collectors;

public class ReceiptModel extends Model {
    private ReceiptDate date;
    private ProductReceiptContainer productReceiptContainer;
    private String headTitle;
    private InvoiceNumber reference;
    private Client client;
    private String cashierLabel;
    private List<String> footnotes;


    public ReceiptModel() {}

    public String getHeadTitle() {
        return headTitle;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
    }

    public ReceiptDate getDate() {
        return date;
    }

    public void setDate(ReceiptDate date) {
        this.date = date;
    }


    public ProductReceiptContainer getProductReceiptContainer() {
        return productReceiptContainer;
    }

    public void setProductReceiptContainer(ProductReceiptContainer productReceiptContainer) {
        this.productReceiptContainer = productReceiptContainer;
    }

    public void setReference(InvoiceNumber reference) {
        this.reference = reference;
    }

    public InvoiceNumber getReference() {
        return reference;
    }

    @Override
    public Client getClient() {
        return client;
    }

    @Override
    public void setClient(Client client) {
        this.client = client;
    }

    public String getCashierLabel() {
        return cashierLabel;
    }

    public void setCashierLabel(String cashierLabel) {
        this.cashierLabel = cashierLabel;
    }

    public List<String> getFootnotes() {
        return footnotes;
    }

    public void setFootnotes(List<String> footnotes) {
        this.footnotes = footnotes;
    }

    @Override
    public String toString() {
        return "ReceiptModel{" +
                "date=" + getDate() +
                "reference"+getReference()+
                ", company=" + getCompany() +
                ", headTitle='" + headTitle + '\'' +
                '}';
    }


    public static class Generator implements ModelGenerator<ReceiptModel> {
        private static final Map<String, String> headerLabels = new HashMap<>();
        {
            headerLabels.put("CASH BILL", "en");
            headerLabels.put("Receipt", "fr");
            headerLabels.put("Invoice", "fr");
            headerLabels.put("CASH RECEIPT", "fr");
        }
        private static final Map<String, String> cashierLabels = new HashMap<>();
        {
            cashierLabels.put("Cashier", "en");
            cashierLabels.put("Caisse", "fr");
            cashierLabels.put("CAISSE", "fr");
        }
        private static final Map<List<String>, String> footnotesLabels = new HashMap<>();
        {
            footnotesLabels.put(Arrays.asList("Thank You ! Please Come Again ","Goods Sold are not Returnable",
                    "Dealing In Wholesale And Retail"), "en");
            footnotesLabels.put(Arrays.asList("GOODS SOLD ARE NOT RETURNABLE ARE NOT RETURNABLE OR EXCHANGEABLE",
                    "THANK YOU","PLEASE COME AGAIN"), "en");
            footnotesLabels.put(Arrays.asList("EXCHANGE ARE ALLOWED WITHIN 7 DAYS WITH RECEIPT.",
                    "STRICTLY NO CASH REFUND."), "en");
            footnotesLabels.put(Arrays.asList("THANKS YOUR SUPPORT"), "en");
            footnotesLabels.put(Arrays.asList("********* THANK YOU **********",
                    "ANY GOODS RETURN PLEASE DO WITHIN 7 DAYS WITH ORIGINAL RECEIPT TQ^^"), "en");
            footnotesLabels.put(Arrays.asList("THANK YOU FOR SHOPPING","GOODS SOLD ARE NOT RETURNABLE."), "en");
            footnotesLabels.put(Arrays.asList("THANK YOU! PLEASE COME AGAIN!","\"GOODS ARE NOT RETURNABLE",
                    "DEALING IN WHOLESALE AND RETAIL"), "en");
            footnotesLabels.put(Arrays.asList("Merci de votre visite"), "fr");
            footnotesLabels.put(Arrays.asList("TICKET CLIENT","A CONSERVER","MERCI ET A BIENTOT"), "fr");
        }


        @Override
        public ReceiptModel generate(GenerationContext ctx) {
            ReceiptModel model = new ReceiptModel();
            model.setDate(new ReceiptDate.Generator().generate(ctx));
            model.setLang(ctx.getLanguage());
            model.setCompany(new Company.Generator().generate(ctx));
            model.setProductReceiptContainer(new ProductReceiptContainer.Generator().generate(ctx));
            model.setReference(new InvoiceNumber.Generator().generate(ctx));
            model.setClient(new Client.Generator().generate(ctx));
            List<String> localizedHeaderLabel = headerLabels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxvL = new Random().nextInt(localizedHeaderLabel.size());
            Generex generex = new Generex(localizedHeaderLabel.get(idxvL));
            model.setHeadTitle(generex.random());
            List<String> localizedCashierLabels = cashierLabels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxCL = new Random().nextInt(localizedCashierLabels.size());
            Generex generex1 = new Generex(localizedCashierLabels.get(idxCL));
            model.setCashierLabel(generex1.random());
            List<List<String>> localizedFootNotes = footnotesLabels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxFL = new Random().nextInt(localizedFootNotes.size());
            model.setFootnotes(localizedFootNotes.get(idxFL));
            return model;
        }
    }
}
