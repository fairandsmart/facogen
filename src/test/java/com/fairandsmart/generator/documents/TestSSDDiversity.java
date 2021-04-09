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

import com.fairandsmart.generator.evaluation.CompleteInformation;
import com.fairandsmart.generator.evaluation.ElementaryInfo;
import com.fairandsmart.generator.evaluation.InfoMap;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.xml.bind.Marshaller;


public class TestSSDDiversity {
    public static int Dmh(int x1, int y1, int x2, int y2){
        return (Math.abs(x2-x1)+Math.abs(y2-y1));
    }
    public static int Align(CompleteInformation b1, CompleteInformation b2){
        return (Dmh(b1.getP1x(),b1.getP1y(),b2.getP1x(),b2.getP1y())+Dmh(b1.getP2x(),b1.getP2y(),b2.getP2x(),b2.getP2y()));
    }
    public static double OverlapArea(CompleteInformation b1, CompleteInformation b2){
        int areaB1 = Math.abs(b1.getP1x() - b1.getP2x()) *Math.abs(b1.getP1y() - b1.getP2y());
        int areaB2 = Math.abs(b2.getP1x() - b2.getP2x()) * Math.abs(b2.getP1y() - b2.getP2y());
        int x_dist = Math.min(b1.getP2x(), b2.getP2x()) - Math.max(b1.getP1x(), b2.getP1x());
        int y_dist = (Math.min(b1.getP2y(), b2.getP2y()) - Math.max(b1.getP1y(), b2.getP1y()));
        int areaI = 0;
        if( x_dist > 0 && y_dist > 0 )
        {
            areaI = x_dist * y_dist;
        }
        return (1-((2*areaI)/(areaB1 + areaB2)) );
     }
    public static void main(String args[]) throws JAXBException {

        String dirPath = "target/receipts/xml";
        String dirPath2 = "target/receipts/xml2/";
        File fileName = new File(dirPath);
        File[] fileList = fileName.listFiles();

        for (File file: fileList) {
            //System.out.println(file);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
                dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(file);
                doc.getDocumentElement().normalize();
                System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
                System.out.println("------");
                NodeList list = doc.getElementsByTagName("DL_PAGE");
                Node node_DL_PAGE = list.item(0);

                ////
                Hashtable<String, CompleteInformation> information = new Hashtable<String, CompleteInformation>();

                if (node_DL_PAGE.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node_DL_PAGE;
                    NodeList liste = eElement.getElementsByTagName("DL_ZONE");
                    for (int j = 0; j < liste.getLength(); j++) {
                        Node node = liste.item(j);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement1 = (Element) node;
                            if (!eElement1.getAttribute("correctclass").equals("undefined")) {

                                int x1= Integer.parseInt(eElement1.getAttribute("col"));
                                int y1 = Integer.parseInt(eElement1.getAttribute("row"));
                                int x2 = x1 + Integer.parseInt(eElement1.getAttribute("width"));
                                int y2 = y1 + Integer.parseInt(eElement1.getAttribute("height"));
                                ElementaryInfo elInf = new ElementaryInfo(x1, y1, eElement1.getAttribute("contents"));
                                CompleteInformation info = information.get(eElement1.getAttribute("correctclass"));
                                if (info != null) {
                                    CompleteInformation inf = information.get(eElement1.getAttribute("correctclass"));
                                    inf.UpdateInformation(elInf,x1,y1,x2,y2);
                                    information.replace(eElement1.getAttribute("correctclass"), inf);
                                } else {
                                    information.put(eElement1.getAttribute("correctclass"), new CompleteInformation(eElement1.getAttribute("correctclass"),
                                            elInf,x1, y1, x2, y2));
                                }
                            }
                        }
                    }
                }
                InfoMap infoMap = new InfoMap();
                infoMap.setInformationMap(information);
                JAXBContext jaxbContext = JAXBContext.newInstance(InfoMap.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                //jaxbMarshaller.marshal(infoMap, System.out);
                jaxbMarshaller.marshal(infoMap, new File(dirPath2+file.getName()));

            } catch (ParserConfigurationException | SAXException | IOException | JAXBException e) {
                e.printStackTrace();
            }
        }

        File fileName2 = new File(dirPath2);
        File[] fileList2 = fileName2.listFiles();

        List<Integer> scores = new ArrayList<Integer>();
        Hashtable<String, List<Integer>> scoresByClasses = new Hashtable<String, List<Integer>>();

        List<Double> scoresOver = new ArrayList<Double>();
        Hashtable<String, List<Double>> scoresOverByClasses = new Hashtable<String, List<Double>>();


        for (File file: fileList2) {
            JAXBContext jaxbContext = JAXBContext.newInstance(InfoMap.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            InfoMap infMap = (InfoMap) jaxbUnmarshaller.unmarshal(file);
            for(String infId : infMap.getInformationMap().keySet())
            {
                String className =infMap.getInformationMap().get(infId).getClassName();
                CompleteInformation info = infMap.getInformationMap().get(infId);
                ////
                for (File file2: fileList2) {
                    InfoMap infMap2 = (InfoMap) jaxbUnmarshaller.unmarshal(file2);
                    for (String infId2 : infMap2.getInformationMap().keySet()) {
                        CompleteInformation info2 = infMap2.getInformationMap().get(infId2);
                        if (infMap2.getInformationMap().get(infId2).getClassName().equals(className)){
                            if(scoresByClasses.get(className)!= null){
                                System.out.println("1"+className);
                                /// score ALign
                                scoresByClasses.put(className,
                                Stream.concat(scoresByClasses.get(className).stream(), Stream.of(Align(info2,info)))
                                        .collect(Collectors.toList()));
                                /// score Overlapp
                                scoresOverByClasses.put(className,
                                        Stream.concat(scoresOverByClasses.get(className).stream(), Stream.of(OverlapArea(info2,info)))
                                                .collect(Collectors.toList()));

                            }else {
                                System.out.println("2" +className);
                                /// score Align
                                scoresByClasses.put(className,new ArrayList<Integer>() {{
                                    add(Align(info2,info));
                                } });
                                /// score Overlapp
                                scoresOverByClasses.put(className,new ArrayList<Double>() {{
                                    add(OverlapArea(info2,info));
                                } });
                            }
                            scores.add(Align(info2,info));
                            scoresOver.add(OverlapArea(info2,info));
                        }
                    }
                }
                ////
            }
        }
        System.out.println("size = " + scores.size());
        IntSummaryStatistics averageScores = scores.stream()
                .mapToInt((a) -> a)
                .summaryStatistics();
        System.out.println("Average score align  = "+averageScores.getAverage());

        DoubleSummaryStatistics averageOverlScores = scoresOver.stream()
                .mapToDouble((a) -> a)
                .summaryStatistics();
        System.out.println("Average score overlap  = "+averageOverlScores.getAverage());

        ////
        /*
        String s = null;
        try {

            // run the Unix "ps -ef" command
            // using the Runtime exec method:
           // Process p = Runtime.getRuntime().exec("ps -ef");
            Process p0 = Runtime.getRuntime().exec("conda activate bleu");
            Process p = Runtime.getRuntime().exec("python bleu.py");

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }

            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }

            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
            System.exit(-1);
        }*/


    }

}
