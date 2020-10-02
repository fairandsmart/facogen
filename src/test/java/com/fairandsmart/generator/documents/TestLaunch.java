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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestLaunch {
    static int layout;
    static int number;

    private static void launch() throws Exception {
        switch(layout){
            case 1:
                TestAmazonLayout.test(number);
                break;
            case 2:
                TestBDmobilierLayout.test(number);
                break;
            case 3:
                TestCdiscountLayout.test(number);
                break;
            case 4:
                TestDartyLayout.test(number);
                break;
            case 5:
                TestFairandsmartLayout.test(number);
                break;
            case 6:
                TestLDLCLayout.test(number);
                break;
            case 7:
                TestLoriaLayout.test(number);
                break;
            case 8:
                TestMACOMPLayout.test(number);
                break;
            case 9:
                TestMaterielnetLayout.test(number);
                break;
            case 10:
                TestNatureDecouvertesLayout.test(number);
                break;
            case 11:
                TestgenericLayout.test(number);
                break;
            case 12:
                TestGenericPayslipLayout.test(number);
                break;
            default:
                System.out.println("Invalid data");
        }

    }

    public static void main(String args[]){

        JFrame frame = new JFrame("Invoice génération");

        JPanel saisie1 = new JPanel(new GridBagLayout());
        //decoration du champ
        saisie1.setPreferredSize(new Dimension(800,30));
        //texte explicatif
        JLabel texte1 = new JLabel("Which layout do you want to generate ? ",JLabel.CENTER);
        texte1.setFont(new java.awt.Font("Helvetica",Font.PLAIN,15));
        saisie1.add(texte1);

        DefaultListCellRenderer centr = new DefaultListCellRenderer();
        centr.setHorizontalAlignment(JLabel.CENTER);
        JComboBox layoutChoice = new JComboBox(new String[] {" ",
                "Amazon",
                "Bdmobilier",
                "Cdiscount",
                "Darty",
                "Fairandsmart",
                "Ldlc",
                "Loria",
                "Macomp",
                "Materielnet",
                "Nature&Découvertes",
                "Random",
                "Payslip"});
        layoutChoice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                layout = layoutChoice.getSelectedIndex();
            }
        });
        layoutChoice.setRenderer(centr);
        saisie1.add(layoutChoice);

        JPanel saisie2 = new JPanel(new GridBagLayout());
        //decoration du champ
        saisie1.setPreferredSize(new Dimension(500,30));
        //texte explicatif
        JLabel texte2 = new JLabel("How many invoices do you want ? ",JLabel.CENTER);
        texte2.setFont(new java.awt.Font("Helvetica",Font.PLAIN,15));
        saisie2.add(texte2);
        JSpinner spinner = new JSpinner();
        JSpinner.NumberEditor spinnerEditor = new JSpinner.NumberEditor(spinner,"###,##0");
        spinner.setEditor(spinnerEditor);
        spinnerEditor.getModel().setValue(1);
        spinnerEditor.getModel().setStepSize(1);
        spinnerEditor.getModel().setMinimum(1);
        spinner.setPreferredSize(new Dimension(100,30));
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                number = (Integer)spinnerEditor.getModel().getNumber();;
            }
        });
        saisie2.add(spinner);

        JPanel saisie3 = new JPanel();
        JButton generate = new JButton("Generate");
        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    launch();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        saisie3.add(generate);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(saisie1,BorderLayout.NORTH);
        frame.getContentPane().add(saisie2,BorderLayout.CENTER);
        frame.getContentPane().add(saisie3,BorderLayout.SOUTH);
        frame.setSize(new Dimension(500,700));
        frame.pack();
        frame.setVisible(true);

    }
}
