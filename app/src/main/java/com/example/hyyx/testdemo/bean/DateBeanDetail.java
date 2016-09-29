package com.example.hyyx.testdemo.bean;

/**
 * Created by hyyx on 16/9/5.
 */

public class DateBeanDetail {
    private String clock;
    private String work;

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public DateBeanDetail(String clock, String work) {
        this.clock = clock;
        this.work = work;
    }

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }
}

