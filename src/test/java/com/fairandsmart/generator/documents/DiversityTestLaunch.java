package com.fairandsmart.generator.documents;

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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DiversityTestLaunch {
    static int docType;
    static int testDiversity;
    static String path;

    private static void launch() throws Exception {
        String result="";
        switch (docType){
            case 1:
                path= "target/SSDInvoice/";
                break;
            case 2:
                path = "target/SSDPayslip/";
                break;
            case 3:
                path = "target/SSDReceipt/";
                break;
            default:
                System.out.println("Invalid path");
        }
        switch(testDiversity){
            case 1:
                result= "Alignement score is :"+TestSSDDiversityAlign.test(path);
                break;
            case 2:
                result= "Overlapping score is :"+TestSSDDiversityOverlapping.test(path);
                break;
            case 3:
                result= "SELF BLEU score is :"+TestSSDDiversitySELF_BLEU.test(path);
                break;
            case 4:
                path=path+"xmlEval/";
                result= "SCR score is :"+ TestSSDDiversitySCR.test(path);
                break;
            default:
                System.out.println("Invalid test");
        }
        JOptionPane.showMessageDialog(null, result, "Diversity evaluation scores", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String args[]){

        JFrame frame = new JFrame("Diversity Evaluation");

        JPanel saisie1 = new JPanel(new GridBagLayout());
        //decoration du champ
        saisie1.setPreferredSize(new Dimension(800,30));
        //texte explicatif
        JLabel texte1 = new JLabel("Choose the document type ",JLabel.CENTER);
        texte1.setFont(new Font("Helvetica",Font.PLAIN,15));
        saisie1.add(texte1);

        DefaultListCellRenderer centr = new DefaultListCellRenderer();
        centr.setHorizontalAlignment(JLabel.CENTER);
        JComboBox docTypeChoice = new JComboBox(new String[] {" ",
                "Invoices",
                "Payslips",
                "Receipts"
              });
        docTypeChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                docType = docTypeChoice.getSelectedIndex();
            }
        });
        docTypeChoice.setRenderer(centr);
        saisie1.add(docTypeChoice);

        JPanel saisie2 = new JPanel(new GridBagLayout());
        //decoration du champ
        saisie1.setPreferredSize(new Dimension(500,30));
        //texte explicatif
        JLabel texte2 = new JLabel("Choose the diversity test",JLabel.CENTER);
        texte2.setFont(new Font("Helvetica",Font.PLAIN,15));
        saisie2.add(texte2);

        DefaultListCellRenderer divTest = new DefaultListCellRenderer();
        divTest.setHorizontalAlignment(JLabel.CENTER);
        JComboBox divTestChoice = new JComboBox(new String[] {" ",
                "Alignement",
                "Overlapping",
                "SELF-BLEU",
                "SRC"
        });
        divTestChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                testDiversity = divTestChoice.getSelectedIndex();
            }
        });
        divTestChoice.setRenderer(divTest);
        saisie2.add(divTestChoice);



        //////
        JPanel saisie3 = new JPanel(new GridBagLayout());//JPanel();
        JButton evaluate = new JButton("Evaluate");
        evaluate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    launch();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        saisie3.add(evaluate);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(saisie1,BorderLayout.NORTH);
        frame.getContentPane().add(saisie2,BorderLayout.CENTER);
        frame.getContentPane().add(saisie3,BorderLayout.SOUTH);
        frame.setSize(new Dimension(500,700));
        frame.pack();
        frame.setVisible(true);

    }
}
