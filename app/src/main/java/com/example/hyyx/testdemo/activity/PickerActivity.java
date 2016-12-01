package com.example.hyyx.testdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hyyx.testdemo.R;
import com.example.hyyx.testdemo.utils.PpwUtils;
import com.example.hyyx.testdemo.utils.TimeUtils;
import com.example.hyyx.testdemo.view.wheelView.WheelMain;

public class PickerActivity extends BaseActivity {

    private View mMainView;
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainView = LayoutInflater.from(this).inflate(R.layout.activity_picker, null);
        setContentView(mMainView);
        initView();
    }

    private void initView() {
        tvTime = ((TextView) findViewById(R.id.tv_time));
        ((Button) findViewById(R.id.btn_chooseTime)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateSelector();
            }
        });

    }

    private PpwUtils ppwUtils;
    private WheelMain wheelMain;

    private void showDateSelector() {
        final String time = TimeUtils.getCurDay2String();

        if (ppwUtils == null) {
            final View bottomView = LayoutInflater.from(this).inflate(R.layout.date_selector_bottom_layout, null);
            wheelMain = new WheelMain(this, R.layout.date_selecter_content_layout);
            final LinearLayout contentView = (LinearLayout) wheelMain.initDateTimePicker(0, 0, 0, 0, 0);
            // final LinearLayout contentView =wheelMain.initDateTimePicker();
            ppwUtils = new PpwUtils(this, contentView, bottomView) {
                @Override
                protected void initView(View contentView, View bottomView) {

                }

                @Override
                protected void createParameter(View contentView, View bottomView) {

                }
            };

            final TextView okTxt = (TextView) bottomView.findViewById(R.id.date_ok_txt);
            final TextView cancleTxt = (TextView) bottomView.findViewById(R.id.date_cancle_txt);
            okTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ppwUtils.dismiss();
                    String month = null;
                    String day = null;
                    if ((wheelMain.getwv_month() + 1) < 10) {
                        month = "0" + (wheelMain.getwv_month() + 1);
                    } else {
                        month = "" + (wheelMain.getwv_month() + 1);
                    }
//                    if ((wheelMain.getwv_day() + 1) < 10) {
//                        day = "0" + (wheelMain.getwv_day() + 1);
//                    } else {
//                        day = (wheelMain.getwv_day() + 1) + "";
//                    }


                    int year = wheelMain.getwv_year() + 1900;
                    tvTime.setText(year + "-" + month);


                }
            });
            cancleTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ppwUtils.dismiss();
                }
            });
        }

        wheelMain.setwv_year((TimeUtils.getYearFromString(time, "yyyy-MM-dd")));
        wheelMain.setwv_month(TimeUtils.getMonthFromString(time, "yyyy-MM-dd"));
        // wheelMain.setwv_day((TimeUtils.getDayFromString(time, "yyyy-MM-dd") - 1));
        ppwUtils.show(mMainView);
    }


}
