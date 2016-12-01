package com.example.hyyx.testdemo.view.wheelView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.example.hyyx.testdemo.R;
import com.example.hyyx.testdemo.utils.TimeUtils;


public class WheelMain {

    private View view;
    private WheelView wv_year;
    private WheelView wv_month;
    private WheelView wv_day;
    public int screenheight;
    private boolean hasSelectTime;
    private static int START_YEAR = 0, END_YEAR = 23;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public static int getSTART_YEAR() {
        return START_YEAR;
    }

    public static void setSTART_YEAR(int sTART_YEAR) {
        START_YEAR = sTART_YEAR;
    }

    public static int getEND_YEAR() {
        return END_YEAR;
    }

    public static void setEND_YEAR(int eND_YEAR) {
        END_YEAR = eND_YEAR;
    }

    public WheelMain(Context c, int id) {
        super();
        this.view = LayoutInflater.from(c).inflate(id, null);
        hasSelectTime = false;
    }

    public WheelMain(Context c, int id, boolean hasSelectTime) {
        super();
        this.view = LayoutInflater.from(c).inflate(id, null);
        this.hasSelectTime = hasSelectTime;
    }

    public View initDateTimePicker(int year, int month, int day, WheelView.ScrollCallback scrollbaCallback) {
        return this.initDateTimePicker(0, 0, 0, day, day);
    }

    public View initDateTimePicker(int count1, String format1, String unit1, int count2, String format2, String unit2,
                                   WheelView.ScrollCallback scrollbaCallback) {

        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(1950, count1, format1));
        wv_year.setCyclic(true);
        wv_year.setLabel(unit1);
        wv_year.setCurrentItem(0);
        wv_year.setCallback(scrollbaCallback);

        wv_month = (WheelView) view.findViewById(R.id.month);
        wv_month.setAdapter(new NumericWheelAdapter(00, count2, format2));
        wv_month.setCyclic(true);
        wv_month.setLabel(unit2);
        wv_month.setCallback(scrollbaCallback);
        wv_month.setCurrentItem(0);
        return view;
    }

    public View initDateTimePicker(int year, int month, int day, int h, int m) {


        wv_year = (WheelView) view.findViewById(R.id.year);
        wv_year.setAdapter(new NumericWheelAdapter(1900, 2100, "%04d"));
        wv_year.setCyclic(true);
        wv_year.setLabel("年");
        wv_year.setCurrentItem(TimeUtils.getCurrentYear());
        wv_year.addScrollingListener(scrollListener);

        wv_month = (WheelView) view.findViewById(R.id.month);
        wv_month.setAdapter(new NumericWheelAdapter(1, 12, "%02d"));
        wv_month.setCyclic(true);
        wv_month.setLabel("月");
        wv_month.addScrollingListener(scrollListener);
        wv_month.setCurrentItem(TimeUtils.getCurrentaMonth() - 1);

//        wv_day = (WheelView) view.findViewById(R.id.day);
//        initDay(TimeUtils.getCurrentYear(), TimeUtils.getCurrentaMonth());
//        wv_day.setCyclic(true);
//        wv_day.setLabel("日");
//        wv_day.setCurrentItem(TimeUtils.getCurrentaDate()-1);
//        wv_day.addScrollingListener(scrollListener);
        return view;
    }


    public int getwv_year() {
        return wv_year.getCurrentItem();
    }

    public int getwv_month() {
        return wv_month.getCurrentItem();
    }

    public int getwv_day() {
        return wv_day.getCurrentItem();
    }

    public void setwv_day(int year) {
        wv_day.setCurrentItem(year);
    }

    public void setwv_year(int year) {
        wv_year.setCurrentItem(year);
    }

    public void setwv_month(int month) {
        wv_month.setCurrentItem(month);
    }


    /**
     * 判断每个月几天
     * @param year
     * @param month
     * @return
     */
    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

    private void initDay(int arg1, int arg2) {
        wv_day.setAdapter(new NumericWheelAdapter(1, getDay(arg1, arg2), "%02d"));
    }


    /**
     * 日历滚动里面的数据
     */
  public OnWheelScrollListener scrollListener = new OnWheelScrollListener() {

        @Override
        public void onScrollingStarted(WheelView wheel) {

        }

        @Override
        public void onScrollingFinished(WheelView wheel) {
            int n_year = wv_year.getCurrentItem() + 1950;
            int n_month = wv_month.getCurrentItem() + 1;
           // initDay(n_year,n_month);
        }
    };
}
