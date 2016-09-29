package com.example.hyyx.testdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hyyx.testdemo.R;
import com.example.hyyx.testdemo.adapter.DateAdapter;
import com.example.hyyx.testdemo.adapter.ListViewAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hyyx on 16/9/14.
 */
public class DateAndListView extends LinearLayout {


    private GridView gvDate;
    private TextView tvMeetingDate;
    private DateAdapter dateAdapter;
    private String y;
    private String m;
    private ListView lv;
    private ListViewAdapter listViewAdapter;

    public DateAndListView(Context context) {
        super(context);
        init(context);
    }


    public DateAndListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DateAndListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View inflate = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.date_show_list, null);
        gvDate = ((GridView) inflate.findViewById(R.id.date_gridview));
        tvMeetingDate = ((TextView) inflate.findViewById(R.id.meeting_date_tv));
        dateAdapter = new DateAdapter(context);
        gvDate.setAdapter(dateAdapter);
        lv = ((ListView) inflate.findViewById(R.id.listview));
        listViewAdapter = new ListViewAdapter(context, null);
        lv.setAdapter(listViewAdapter);


        gvDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String s = adapterView.getItemAtPosition(i).toString();
                if ("0".equals(s)) {
                    return;
                }
                String pTime = y + "-" + m + "-" + s;

                tvMeetingDate.setText(pTime + " " + "星期" + getWeek(pTime));


                // TODO: 16/9/19 获得下方listview 的数据源

                List<String> data = new ArrayList<String>();
                for (int j = 0; j < 15; j++) {
                    data.add(j + "");
                }

                listViewAdapter.setDate(data);

            }
        });


        addView(inflate);
    }

    public void setCurrentYearAndMounth(String year, String mouth) {
        y = year;
        m = mouth;
        dateAdapter.setCurrentYearAndMounth(year, mouth);
    }

    private String getWeek(String pTime) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }


        return Week;
    }


}
