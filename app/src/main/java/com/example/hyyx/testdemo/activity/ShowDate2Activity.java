package com.example.hyyx.testdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.hyyx.testdemo.R;
import com.example.hyyx.testdemo.view.DateAndListView;
import com.example.hyyx.testdemo.view.MyDateShowView;

public class ShowDate2Activity extends AppCompatActivity {

    private MyDateShowView myDateShowView;
    private DateAndListView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_date2);
        initView();

    }

    private void initView() {
        myView = ((DateAndListView) findViewById(R.id.dateandlist));
        myView.setCurrentYearAndMounth("2016", "9");
    }
}
