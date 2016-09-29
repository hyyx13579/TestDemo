package com.example.hyyx.testdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.hyyx.testdemo.view.BarGraphView;
import com.example.hyyx.testdemo.R;

public class MainActivity extends BaseActivity {


    private BarGraphView barGraph;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ((Button) findViewById(R.id.btn_main)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SecActivity.class));
            }
        });


        ((Button) findViewById(R.id.btntophoto)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SelectPhotoActivity.class));
            }
        });
        ((Button) findViewById(R.id.btn_pipeview)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PipeActivity.class));
            }
        });
        ((Button) findViewById(R.id.btn_showdate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShowDate2Activity.class));
            }
        });
        ((Button) findViewById(R.id.btn_showdate2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RollActivity.class));
            }
        });


    }


}