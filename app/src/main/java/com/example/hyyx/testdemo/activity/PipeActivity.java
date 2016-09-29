package com.example.hyyx.testdemo.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.hyyx.testdemo.view.BarGraphView;
import com.example.hyyx.testdemo.view.PipeChartView;
import com.example.hyyx.testdemo.R;
import com.example.hyyx.testdemo.bean.ValueColorEntity;

import java.util.ArrayList;
import java.util.List;

public class PipeActivity extends AppCompatActivity {

    private int[] colors = {Color.rgb(236, 134, 147),
            Color.rgb(243, 153, 119), Color.rgb(248, 206, 132),
            Color.rgb(136, 200, 150)};
    private List<ValueColorEntity> mDatas = new ArrayList<>();
    private PipeChartView pipe;
    private FrameLayout frameLayout;
    private FrameLayout frameLayoutTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pipe);
        pipe = new PipeChartView(this, null);
        mDatas.add(new ValueColorEntity(1, getResources().getColor(R.color.light_blue)));
        mDatas.add(new ValueColorEntity(2, getResources().getColor(R.color.titlecolor)));
        mDatas.add(new ValueColorEntity(0, getResources().getColor(R.color.colorPrimary)));
        mDatas.add(new ValueColorEntity(0, getResources().getColor(R.color.colorAccent)));
        pipe.setData(mDatas);
        frameLayout = ((FrameLayout) findViewById(R.id.frame_pipe));
        frameLayout.addView(pipe);


        frameLayoutTwo = ((FrameLayout) findViewById(R.id.frame_bar));
        BarGraphView bar = new BarGraphView(this, null);
        //设置x轴的数据
        bar.setXdataInfo(new String[]{"16/8/12", "16/8/13"});
        //设置Y轴的数据
        bar.setDefaultYdataInfo(BarGraphView.TYPE_TEMP);
        bar.setBrokenLineInfo(new double[]{36, 37, 38.5, 37.3, 39.1, 36.5});
        //设置红线
        bar.setRedLine(36, 37);
        //
        bar.setAxisX(900, 9);
        frameLayoutTwo.addView(bar);


    }
}
