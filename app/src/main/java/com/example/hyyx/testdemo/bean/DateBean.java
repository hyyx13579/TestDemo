package com.example.hyyx.testdemo.bean;

import java.util.List;

/**
 * Created by hyyx on 16/9/5.
 */
public class DateBean {

    public DateBean(List<DateBeanDetail> dateBeanDetails) {
        this.dateBeanDetails = dateBeanDetails;
    }

    public List<DateBeanDetail> getDateBeanDetails() {
        return dateBeanDetails;
    }

    public void setDateBeanDetails(List<DateBeanDetail> dateBeanDetails) {
        this.dateBeanDetails = dateBeanDetails;
    }

    private List<DateBeanDetail> dateBeanDetails;



}
