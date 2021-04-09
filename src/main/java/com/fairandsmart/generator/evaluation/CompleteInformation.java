package com.fairandsmart.generator.evaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompleteInformation {
    private String className;
    private String contents;
    private List<ElementaryInfo> contentList;
    private int p1x;
    private int p1y;
    private int p2x;
    private int p2y;

    public CompleteInformation() {
    }

    public CompleteInformation(String className, ElementaryInfo contents, int p1x, int p1y, int p2x, int p2y) {
        this.className = className;
        this.contentList = new ArrayList<ElementaryInfo>();
        this.contentList.add(contents);
        this.contents = contents.getContent();
        this.p1x = p1x;
        this.p1y = p1y;
        this.p2x = p2x;
        this.p2y = p2y;
    }

    public void UpdateInformation(ElementaryInfo content, int newp1x, int newp1y, int newp2x, int newp2y) {
        this.contentList.add(content);
        //Collections.sort(this.contentList);
        this.contents ="";
        for (int i=0;i<contentList.size();i++){
            if (i!=0) setContents(getContents()+" ");
            setContents(getContents()+contentList.get(i).getContent());
        }
        if (newp1x < getP1x()) setP1x(newp1x);
        if (newp1y < getP1y()) setP1y(newp1y);
        if (newp2x > getP2x()) setP2x(newp2x);
        if (newp2y > getP2y()) setP2y(newp2y);
    }

    public String getClassName() {
        return className;
    }

    public String getContents() {
        return contents;
    }

    public int getP1x() {
        return p1x;
    }

    public int getP1y() {
        return p1y;
    }

    public int getP2x() {
        return p2x;
    }

    public int getP2y() {
        return p2y;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setP1x(int p1x) {
        this.p1x = p1x;
    }

    public void setP1y(int p1y) {
        this.p1y = p1y;
    }

    public void setP2x(int p2x) {
        this.p2x = p2x;
    }

    public void setP2y(int p2y) {
        this.p2y = p2y;
    }

    public List<ElementaryInfo> getContentList() {
        return contentList;
    }

    public void setContentList(List<ElementaryInfo> contentLis) {
        this.contentList = contentLis;
    }

    @Override
    public String toString() {
        return "CompleteInformation{" +
                "className='" + className + '\'' +
                ", contents='" + contents + '\'' +
                ", p1x=" + p1x +
                ", p1y=" + p1y +
                ", p2x=" + p2x +
                ", p2y=" + p2y +
                '}';
    }
}
