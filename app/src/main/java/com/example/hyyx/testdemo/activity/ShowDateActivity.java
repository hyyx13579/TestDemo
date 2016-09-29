package com.example.hyyx.testdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.example.hyyx.testdemo.R;
import com.example.hyyx.testdemo.adapter.DateAdapter;
import com.example.hyyx.testdemo.bean.DateBean;
import com.example.hyyx.testdemo.bean.DateBeanDetail;
import com.example.hyyx.testdemo.view.TitleWeek;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;

public class ShowDateActivity extends AppCompatActivity {

    private TitleWeek titleWeek;
    private GridView gvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_date);
        ininView();
    }

    private void ininView() {
        List<DateBean> data = new ArrayList<>();

        titleWeek = ((TitleWeek) findViewById(R.id.titleweek));
        gvDate = ((GridView) findViewById(R.id.date_gridview));
        DateAdapter dateAdapter = new DateAdapter(this);
        dateAdapter.setCurrentYearAndMounth("2016", "8");
        gvDate.setAdapter(dateAdapter);






    }
}
