package com.fairandsmart.invoices.data.model;

import com.fairandsmart.invoices.data.generator.GenerationContext;
import com.fairandsmart.invoices.data.generator.ModelGenerator;
import com.mifmif.common.regex.Generex;

import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IDNumbers {

    private String cidLabel;                  // Company ID (like Siren Number)
    private String cidValue;
    private String siretLabel;               // Particular to french invoice
    private String siretValue;               // 14 digits unique company center id Siren + Nic(5 digits)
    private String toaLabel;                 // Type of activity eg: APE code / NAF Code for an enterprise
    private String toaValue;
    private String vatLabel;
    private String vatValue;

    public IDNumbers(String vatValue, String vatLabel, String cidValue, String cidLabel, String siretValue, String siretLabel, String toaValue, String toaLabel) {
        this.vatValue = vatValue;
        this.vatLabel = vatLabel;
        this.cidLabel = cidLabel;
        this.cidValue = cidValue;
        this.siretLabel = siretLabel;
        this.siretValue = siretValue;
        this.toaLabel = toaLabel;
        this.toaValue =  toaValue;
    }

    public String getVatLabel() { return vatLabel; }

    public void setLabel(String vatLabel) {
        this.vatLabel = vatLabel;
    }

    public String getVatValue() { return vatValue;  }

    public void setVatValue(String vatValue) {
        this.vatValue = vatValue;
    }

    public String getCidValue() { return cidValue; }
    public String getCidLabel() { return cidLabel; }

    public String getSiretValue() { return siretValue; }
    public String getSiretLabel() { return siretLabel; }

    public String getToaValue() { return toaValue; }
    public String getToaLabel() { return toaLabel; }


    @Override
    public String toString() {
        return "IDNumbers{" +
                "vatValue='" + vatValue + '\'' +
                ", vatLabel='" + vatLabel + '\'' +
                " cidValue='" + cidValue + '\'' +
                ", cidLabel='" + cidLabel + '\'' +
                " siretValue='" + siretValue + '\'' +
                ", siretLabel='" + siretLabel + '\'' +
                ", toaValue='" + toaValue + '\'' +
                ", toaLabel='" + toaLabel + '\'' +
                '}';
    }

    public static class Generator implements ModelGenerator<IDNumbers> {

        private static final Map<String, String> vatValues = new HashMap<>();
        private static final Map<String, String> vatLabels = new HashMap<>();
        private static final Map<String, String> cidLabels = new HashMap<>();
        private static final Map<String, String> siretLabels = new HashMap<>();
        private static final Map<String, String> toaLabels = new HashMap<>();

        {
            siretLabels.put("Siret", "fr");
            siretLabels.put("N° Siret", "fr");
            siretLabels.put("Siret", "en");
        }

        {
            toaLabels.put("Code APE", "fr");
            toaLabels.put("NAF", "fr");
            toaLabels.put("N.A.F.", "fr");
            toaLabels.put("APE", "en");
            toaLabels.put("NAF", "en");
        }


        {
            cidLabels.put("Siren", "fr");
            cidLabels.put("N° Siren", "fr");
            cidLabels.put("Siren", "en");
        }

        {
            vatLabels.put("Numéro de TVA", "fr");
            vatLabels.put("N° TVA Intracommunautaire", "fr");
            vatLabels.put("TVA intracommunautaire", "fr");
            vatLabels.put("N° intracommunautaire", "fr");
            vatLabels.put("TVA Intracomm.", "fr");
            vatLabels.put("No identif. Intracomm.", "fr");
            vatLabels.put("N° Identification TVA", "fr");
            vatLabels.put("TVA numéro ", "fr");
            vatLabels.put("N° TVA Intracom", "fr");
            vatLabels.put("N° TVA", "fr");
            vatLabels.put("VAT Number:", "en");
            vatLabels.put("VAT", "en");
            vatLabels.put("VAT/TIN Number:", "en");

        }

        {
            vatValues.put("LU[0-9]{8}", "LU");
            // vatValues.put("GB[0-9]{9}", "UK");
            vatValues.put("GB[0-9]{3} [0-9]{4} [0-9]{2}", "UK");
            vatValues.put("GB[0-9]{9} [0-9]{3}", "UK");
            vatValues.put("BE[0-9]{10}", "BE");

        }


        @Override
        public IDNumbers generate(GenerationContext ctx) {

            String cidValue="", siretValue="", vatValue="", toaValue="";
            if("FR" != ctx.getCountry().intern()){
                List<String> localizedRegex = vatValues.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getCountry())).map(Map.Entry::getKey).collect(Collectors.toList());
                int idxR = ctx.getRandom().nextInt(localizedRegex.size());
                Generex generex1 = new Generex(localizedRegex.get(idxR));
                vatValue = generex1.random();
            }
            else{
                // French Specific System

                Generex gensiren = new Generex("[0-9]{9}"); // For Siren Number
                cidValue = gensiren.random();

                Generex gennic = new Generex("[0-9]{5}");
                siretValue = cidValue + gennic.random();

                int siren = Integer.parseInt(cidValue);
                int key = (12 + 3 * (siren % 97))% 97; // To calculate key for TVA from SIREN
                vatValue = "FR"+ String.valueOf(key) + cidValue; // TVA number

                try {
                    toaValue = getToacode();
                }
                catch (IOException e){
                    System.out.println("File Reading Exception");
                }
            }


            List<String> localizedvatLabel = vatLabels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxvL = ctx.getRandom().nextInt(localizedvatLabel.size());
            Generex generex2 = new Generex(localizedvatLabel.get(idxvL));
            String vatLabel = generex2.random();

            List<String> localizedcidLabel = cidLabels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxcL = ctx.getRandom().nextInt(localizedcidLabel.size());
            Generex generex3 = new Generex(localizedcidLabel.get(idxcL));
            String cidLabel = generex3.random();

            List<String> localizedsiretLabel = siretLabels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxsL = ctx.getRandom().nextInt(localizedsiretLabel.size());
            Generex generex4 = new Generex(localizedsiretLabel.get(idxsL));
            String siretLabel = generex4.random();

            List<String> localizedtoaLabel = toaLabels.entrySet().stream().filter(entry -> entry.getValue().equals(ctx.getLanguage())).map(Map.Entry::getKey).collect(Collectors.toList());
            int idxtL = ctx.getRandom().nextInt(localizedtoaLabel.size());
            Generex generex5 = new Generex(localizedtoaLabel.get(idxtL));
            String toaLabel = generex5.random();


            return new IDNumbers(vatValue, vatLabel, cidValue, cidLabel, siretValue, siretLabel,toaValue,toaLabel);
        }

        private String getToacode() throws IOException {
            FileReader fileReader = null;
            BufferedReader reader = null;
            String lineIn = null;
            Random random = new Random();
            int random_line = random.nextInt(1267 - 1 + 1) + 1;     // 1267 lines in file, each containg an ape code
            String cwdPath = new File("").getAbsolutePath();       // current working directory
            try
            {
                fileReader = new FileReader(cwdPath+ "/src/main/resources/apecodes.txt" );
                reader = new BufferedReader(fileReader);
                int i = 1;
                while (((lineIn = reader.readLine()) !=null) && i!=random_line) {
                    i++;
                }

            }
            catch(Exception e){
                System.out.println("Cannot read from file");}
            finally{
                if(lineIn != null)
                    reader.close();
            }

            return lineIn;
        }
    }
}
