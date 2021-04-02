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
import com.mifmif.common.regex.Generex;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class NewSSDModel extends Model {
    private ReceiptDate date;
    private String applicableLaw;
    private ProductReceiptContainer productReceiptContainer;
    private String headTitle;
    private InvoiceNumber reference;


    public NewSSDModel() {}

    public String getHeadTitle() {
        return headTitle;
    }

    public void setHeadTitle(String headTitle) {
        this.headTitle = headTitle;
    }

    /**public PayElements getPayElements() {
        return payElements;
    }

    public void setPayElements(PayElements payElements) {
        this.payElements = payElements;
    }**/


    public String getApplicableLaw() {
        return applicableLaw;
    }

    public void setApplicableLaw(String applicableLaw) {
        this.applicableLaw = applicableLaw;
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
    public String toString() {
        return "ReceiptModel{" +
                "date=" + getDate() +
                "reference"+getReference()+
                ", company=" + getCompany() +
                ", applicableLaw='" + getApplicableLaw() + '\'' +
                ", headTitle='" + headTitle + '\'' +
                '}';
    }


    public static class Generator implements ModelGenerator<NewSSDModel> {
        private static final Map<String, String> headerLabels = new HashMap<>();

        {
            headerLabels.put("CASH BILL", "en");
            headerLabels.put("Receipt", "fr");
            headerLabels.put("Invoice", "fr");
            headerLabels.put("CASH RECEIPT", "fr");
        }

        @Override
        public NewSSDModel generate(GenerationContext ctx) {
            NewSSDModel model = new NewSSDModel();
            model.setDate(new ReceiptDate.Generator().generate(ctx));
            model.setLang(ctx.getLanguage());
            model.setCompany(new Company.Generator().generate(ctx));
            model.setProductReceiptContainer(new ProductReceiptContainer.Generator().generate(ctx));
            model.setReference(new InvoiceNumber.Generator().generate(ctx));

            List<String> localizedHeaderLabel = headerLabels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxvL = new Random().nextInt(localizedHeaderLabel.size());
            Generex generex = new Generex(localizedHeaderLabel.get(idxvL));
            model.setHeadTitle(generex.random());
            return model;
        }
    }
}
