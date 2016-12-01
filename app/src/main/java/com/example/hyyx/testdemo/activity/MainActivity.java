package com.example.hyyx.testdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.hyyx.testdemo.R;
import com.example.hyyx.testdemo.view.BarGraphView;


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

        ((Button) findViewById(R.id.btn_qita)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QiTaActivity.class));
            }
        });


        ((Button) findViewById(R.id.btn_qiniu)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QiNiuCeShiActivity.class));
            }
        });

        ((Button) findViewById(R.id.btn_pick)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PickerActivity.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}