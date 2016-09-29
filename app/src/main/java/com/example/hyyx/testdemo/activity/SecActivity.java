package com.example.hyyx.testdemo.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.hyyx.testdemo.fragment.MyFragment;
import com.example.hyyx.testdemo.fragment.MyFragmentTwo;
import com.example.hyyx.testdemo.R;

public class SecActivity extends BaseActivity {


    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private View statusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
          statusBar = findViewById(R.id.statu_bar);
        //判断SDK版本是否大于等于19，大于就让他显示，小于就要隐藏，不然低版本会多出来一个
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            statusBar.setVisibility(View.VISIBLE);
            setTranslucentStatus(true);

            //还有设置View的高度，因为每个型号的手机状态栏高度都不相同
        } else {
            statusBar.setVisibility(View.GONE);
        }
        fragmentManager = this.getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame, new MyFragment());
        transaction.commit();
        ((Button) findViewById(R.id.btn_one)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frame, new MyFragment());
                transaction.commit();
                                                                     }
                                                                 }

        );

        ((Button) findViewById(R.id.btn_two)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusBar.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.frame, new MyFragmentTwo());
                transaction.commit();

                                                                     }
                                                                 }

        );

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}

