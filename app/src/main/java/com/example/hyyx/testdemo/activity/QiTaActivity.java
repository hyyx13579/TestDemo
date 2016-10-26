package com.example.hyyx.testdemo.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.GridView;
import android.widget.ListView;

import com.example.hyyx.testdemo.R;
import com.example.hyyx.testdemo.adapter.DateAdapter;
import com.example.hyyx.testdemo.adapter.DateAdapterTwo;

import java.util.zip.Inflater;

public class QiTaActivity extends BaseActivity {

    private GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qi_ta);
        initView();
    }

    private void initView() {

        gv = ((GridView) findViewById(R.id.gv_qita));
        DateAdapterTwo adapter = new DateAdapterTwo(this);
        adapter.setCurrentYearAndMounth("2016", "10");
        gv.setAdapter(adapter);
       // gv.setSelector(new ColorDrawable(Color.TRANSPARENT));


    }
}
