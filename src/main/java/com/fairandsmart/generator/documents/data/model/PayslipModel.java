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

import java.util.*;
import java.util.stream.Collectors;

public class PayslipModel extends Model {
    private PayslipDate date;
    private Company employer;
    private String applicableLaw;
    private Employee employee;
    //private PayElements payElements;
    private String headTitle;

    public PayslipModel() {}

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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getApplicableLaw() {
        return applicableLaw;
    }

    public void setApplicableLaw(String applicableLaw) {
        this.applicableLaw = applicableLaw;
    }


    public PayslipDate getDate() {
        return date;
    }

    public void setDate(PayslipDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "PayslipModel{" +
                " date=" + getDate() +
                ", lang=" + getLang() +
                ", paymentInfo=" + getPaymentInfo() +
                ", employer=" + getCompany() +
                ", employee=" + getEmployee() +
                ", payElements=" + getProductContainer() +
                '}';
    }




    public static class Generator implements ModelGenerator<PayslipModel> {
        private static final Map<String, String> headerLabels = new HashMap<>();

        {
            headerLabels.put("Payslip", "en");
            headerLabels.put("Bulletin de paie", "fr");
            headerLabels.put("Fiche de paie", "fr");
            headerLabels.put("Bulletin de salaire", "fr");
        }

        @Override
        public PayslipModel generate(GenerationContext ctx) {
            PayslipModel model = new PayslipModel();
            model.setDate(new PayslipDate.Generator().generate(ctx));
            model.setLang(ctx.getLanguage());
            model.setPaymentInfo(new PaymentInfo.Generator().generate(ctx));
            model.setCompany(new Company.Generator().generate(ctx));
            model.setEmployee(new Employee.Generator().generate(ctx));
            //model.setPayElements(new PayElements.Generator().generate(ctx));

            List<String> localizedHeaderLabel = headerLabels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxvL = new Random().nextInt(localizedHeaderLabel.size());
            Generex generex = new Generex(localizedHeaderLabel.get(idxvL));
            model.setHeadTitle(generex.random());
            return model;
        }
    }
}
