package com.example.hyyx.testdemo.bean;

/**
 * Created by hyyx on 16/8/25.
 */
public class ValueColorEntity {
    private int value;

    private int Color;

    public ValueColorEntity(int value, int color) {
        this.value = value;
        Color = color;
    }

    public int getValue() {
        return value;
    }

    public int getColor() {
        return Color;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setColor(int color) {
        Color = color;
    }



}
