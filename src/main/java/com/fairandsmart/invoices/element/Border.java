package com.fairandsmart.invoices.element;

import java.awt.*;

public class Border {

    private Color color;
    private int size;
    private boolean left;
    private boolean top;
    private boolean right;
    private boolean bottom;

    public Border() {
    }

    public Border(Color color, int size) {
        this.color = color;
        this.size = size;
    }

    public Border(Color color, int size, boolean left, boolean top, boolean right, boolean bottom) {
        this.color = color;
        this.size = size;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isBottom() {
        return bottom;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }
}
