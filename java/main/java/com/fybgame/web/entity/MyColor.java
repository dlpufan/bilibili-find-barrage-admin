package com.fybgame.web.entity;

import java.awt.*;
import java.io.Serializable;

/**
 * @Author:fyb
 * @Date: 2021/3/5 3:21
 * @Version:1.0
 */
public class MyColor implements Serializable {
    public int r;
    public int g;
    public int b;
    public MyColor(Integer color){
            Color color1 = new Color(color);
            r = color1.getRed();
            g = color1.getGreen();
            b = color1.getBlue();
    }

    public MyColor() {
    }

    @Override
    public String toString() {
        return "MyColor{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
