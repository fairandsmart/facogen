package com.fairandsmart.generator.evaluation;

public class ElementaryInfo implements Comparable<ElementaryInfo>{
    private int x;
    private int y;
    private String content;

    public ElementaryInfo() {
    }

    public ElementaryInfo(int x, int y, String content) {
        this.x = x;
        this.y = y;
        this.content = content;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public String getContent() {
        return content;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ElementaryInfo{" +
                "x=" + x +
                ", y=" + y +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public int compareTo(ElementaryInfo elementaryInfo) {
        if(this.getY()==elementaryInfo.getY()) {
            return this.getX().compareTo(elementaryInfo.getX());
        }else {
            return this.getY().compareTo(elementaryInfo.getY());
        }
    }
}
