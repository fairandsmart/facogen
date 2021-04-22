package com.fairandsmart.generator.evaluation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Hashtable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement (name="informations")
@XmlAccessorType(XmlAccessType.FIELD)
public class InfoMap {

    private Hashtable<String, CompleteInformation> InformationMap = new Hashtable<String, CompleteInformation>();
    private int width;
    private int height;
    public Map<String, CompleteInformation> getInformationMap() {
        return InformationMap;
    }

    public void setInformationMap(Hashtable<String, CompleteInformation> informationMap) {
        this.InformationMap = informationMap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}