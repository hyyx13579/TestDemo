package com.example.hyyx.testdemo.view;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hyyx.testdemo.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by hyyx on 16/9/6.
 */
public class MyDateShowView extends LinearLayout {
    private LayoutInflater inflater;
    private XCFlowLayout xcfLowLayout;
    private String currentYear;
    private String currentMouth;
    private int dayOfWeek = 0;//距离月初有几天
    private int currentMouthDay = 0;
    private Context context;
    private int height;
    private int width;
    private DisplayMetrics mDisplayMetrics;
    private int measuredWidth;
    private int screenWidth;


    public MyDateShowView(Context context) {
        super(context);
        initView(context);
    }


    public MyDateShowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public MyDateShowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        inflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        View inflate = inflater.inflate(R.layout.mydateshow, null);
        xcfLowLayout = ((XCFlowLayout) inflate.findViewById(R.id.mydateshow_xcflowlayout));
        addView(inflate);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();//屏幕宽度
        int height = wm.getDefaultDisplay().getHeight();
    }


    public void setLabel() {


        int k = 1;

        for (int i = 0; i < currentMouthDay + dayOfWeek; i++) {
            if (i < dayOfWeek) {

                TextView textView = new TextView(context);
                textView.setText(" ");
                textView.setWidth(screenWidth / 7);
                textView.setGravity(Gravity.CENTER);
                xcfLowLayout.addView(textView);

            } else {
                TextView textView = new TextView(context);
                textView.setText(k + "");
                textView.setWidth(screenWidth / 7);
                textView.setGravity(Gravity.CENTER);
                xcfLowLayout.addView(textView);
                k++;

            }

        }
        xcfLowLayout.post(new Runnable() {
            @Override
            public void run() {
                xcfLowLayout.invalidate();
            }
        });


    }

    public void setCurrentYearAndMounth(String year, String mouth) {
        currentYear = year;
        currentMouth = mouth;
        dealDateData(currentYear, currentMouth);
        setLabel();
    }

    public void dealDateData(String currentYear, String currentMouth) {
        Calendar calendar = Calendar.getInstance();
        String dateStr = currentYear + "-" + currentMouth + "-01";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            calendar.setTime(format.parse(dateStr));

            switch (calendar.get(Calendar.DAY_OF_WEEK)) {
                case 1: //周日

                    dayOfWeek = 0;

                    break;
                case 2: //周一

                    dayOfWeek = 1;

                    break;
                case 3:

                    dayOfWeek = 2;

                    break;
                case 4:

                    dayOfWeek = 3;

                    break;
                case 5:

                    dayOfWeek = 4;

                    break;
                case 6:

                    dayOfWeek = 5;

                    break;
                case 7:

                    dayOfWeek = 6;

                    break;
                default:
                    break;
            }


        } catch (Exception e) {
            // TODO: handle exception
        }


        switch (Integer.parseInt(currentMouth)) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                currentMouthDay = 31;
                break;
            //对于2月份需要判断是否为闰年
            case 2:
                if ((Integer.parseInt(currentYear) % 4 == 0 && (Integer.parseInt(currentYear) % 100 != 0) || ((Integer.parseInt(currentYear) % 400 == 0)))) {
                    currentMouthDay = 29;
                    break;
                } else {
                    currentMouthDay = 28;
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                currentMouthDay = 30;
                break;

        }


    }


}
