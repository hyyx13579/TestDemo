package com.example.hyyx.testdemo.adapter;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.hyyx.testdemo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by hyyx on 16/9/5.
 */
public class DateAdapterTwo extends BaseAdapter {


    private final LayoutInflater systemService;
    private final int getYear;
    private final int getMonth;
    private final int getDate;
    private Context context;
    private List<String> data;
    private String currentYear;
    private String currentMouth;
    private int dayOfWeek = 0;
    private GridView gv;
    private int currentMouthDay = 0;
    private int k = 0;
    private ListView lvPpw;
    private PopupWindow ppw;


    public DateAdapterTwo(Context context) {
        this.context = context;
        systemService = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        data = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        getYear = calendar.get(Calendar.YEAR);
        getMonth = calendar.get(Calendar.MONTH) + 1;
        getDate = calendar.get(Calendar.DATE);


    }

    public void setCurrentYearAndMounth(String year, String mouth) {
        currentYear = year;
        currentMouth = mouth;
        dealCurrentMouthToOneDay(currentYear, currentMouth);
        int j = 1;

        if (currentMouthDay + dayOfWeek <= 35) {
            k = 35 - (currentMouthDay + dayOfWeek);
        } else if (currentMouthDay + dayOfWeek > 35 && currentMouthDay + dayOfWeek <= 42) {
            k = 42 - (currentMouthDay + dayOfWeek);
        }
        for (int i = 0; i < currentMouthDay + k + dayOfWeek; i++) {
            if (i >= dayOfWeek && i < dayOfWeek + currentMouthDay) {
                data.add(j + "");
                j++;
            } else if (i < dayOfWeek) {
                data.add("0");
            } else if (i >= dayOfWeek + currentMouthDay) {
                data.add("0");
            }


        }

    }

    public int setDayOfWork() {
        return dayOfWeek;
    }

    public int setCell() {
        return k;
    }


    public void dealCurrentMouthToOneDay(String currentYear, String currentMouth) {
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


    @Override
    public int getCount() {
        return data != null ? (currentMouthDay + dayOfWeek + k) : 0;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = systemService.inflate(R.layout.item_date_two, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
            //  holder.update();
        } else {
            holder = ((ViewHolder) view.getTag());
        }

        if (!"0".equals(data.get(i))) {
            holder.cell.setVisibility(View.VISIBLE);
            holder.tvCurrentDay.setText(data.get(i));
            if (currentYear.equals("" + getYear) && currentMouth.equals("" + getMonth)) {

                if (data.get(i).equals("" + getDate)) {
                    holder.tvNowDay.setVisibility(View.VISIBLE);
                    // TODO: 16/10/24 假数据
                    holder.tvOperation.setVisibility(View.VISIBLE);
                    holder.tvMeeting.setVisibility(View.VISIBLE);
                    holder.tvOutPatient.setVisibility(View.VISIBLE);
                    holder.tvline.setVisibility(View.VISIBLE);
                    final ViewHolder finalHolder = holder;
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showPpw(finalHolder.cell);
                        }
                    });

                }
            }
            if (data.get(i).equals("1")) {
                holder.tvOperation.setVisibility(View.VISIBLE);
                holder.tvline.setVisibility(View.VISIBLE);
                final ViewHolder finalHolder1 = holder;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPpw(finalHolder1.cell);
                    }
                });
            }
            if (data.get(i).equals("30")) {
                holder.tvOperation.setVisibility(View.VISIBLE);
                holder.tvMeeting.setVisibility(View.VISIBLE);
                holder.tvline.setVisibility(View.VISIBLE);
                final ViewHolder finalHolder2 = holder;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPpw(finalHolder2.cell);
                    }
                });

            }


        } else {
            holder.cell.setVisibility(View.GONE);
        }

        return view;
    }


    private void showPpw(final View anchorView) {

        final View contentView = LayoutInflater.from(anchorView.getContext()).inflate(R.layout.popuw_content_top_arrow_layout, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, contentView.getMeasuredHeight(), false);
        lvPpw = ((ListView) contentView.findViewById(R.id.lv_ppw));
        List<String> data = new ArrayList<String>();
        for (int j = 0; j < 15; j++) {
            data.add(j + "");
        }
        lvPpw.setAdapter(new ListViewAdapter(context, data));
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                autoAdjustArrowPos(popupWindow, contentView, anchorView);
                contentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });


        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(false);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;   // 这里面拦截不到返回键
            }
        });

        popupWindow.showAsDropDown(anchorView);


    }


    private void autoAdjustArrowPos(PopupWindow popupWindow, View contentView, View anchorView) {
        View upArrow = contentView.findViewById(R.id.up_arrow);
        View downArrow = contentView.findViewById(R.id.down_arrow);

        int pos[] = new int[2];
        contentView.getLocationOnScreen(pos);
        int popLeftPos = pos[0];
        anchorView.getLocationOnScreen(pos);
        int anchorLeftPos = pos[0];
        //目标view的x坐标－ppw的x坐标＋view的宽度／2－箭头的宽度／2
        int arrowLeftMargin = anchorLeftPos - popLeftPos + anchorView.getWidth() / 2 - upArrow.getWidth() / 2;
        //判断popupwindow是否超出父控件的高度，true向上箭头，false向下箭头
        upArrow.setVisibility(popupWindow.isAboveAnchor() ? View.INVISIBLE : View.VISIBLE);
        downArrow.setVisibility(popupWindow.isAboveAnchor() ? View.VISIBLE : View.INVISIBLE);

        RelativeLayout.LayoutParams upArrowParams = (RelativeLayout.LayoutParams) upArrow.getLayoutParams();
        upArrowParams.leftMargin = arrowLeftMargin;
        RelativeLayout.LayoutParams downArrowParams = (RelativeLayout.LayoutParams) downArrow.getLayoutParams();
        downArrowParams.leftMargin = arrowLeftMargin;
    }


    static class ViewHolder {
        private RelativeLayout cell;
        private TextView tvCurrentDay;
        private TextView tvNowDay;
        private TextView tvline;
        private TextView tvOperation;
        private TextView tvMeeting;
        private TextView tvOutPatient;

        public ViewHolder(View convertView) {
            tvCurrentDay = ((TextView) convertView.findViewById(R.id.current_day));
            cell = ((RelativeLayout) convertView.findViewById(R.id.cell));
            tvNowDay = ((TextView) convertView.findViewById(R.id.tv_item_nowday));
            tvline = ((TextView) convertView.findViewById(R.id.tv_line));
            tvOperation = ((TextView) convertView.findViewById(R.id.tv_item_operation));
            tvMeeting = ((TextView) convertView.findViewById(R.id.tv_item_meeting));
            tvOutPatient = ((TextView) convertView.findViewById(R.id.tv_item_outpatient));


        }

//        public void update() {
//            // 精确计算GridView的item高度
//            lvItemDate.getViewTreeObserver().addOnGlobalLayoutListener(
//                    new ViewTreeObserver.OnGlobalLayoutListener() {
//                        public void onGlobalLayout() {
//                            int position = (Integer) tvCurrentDay.getTag();
//                            // 这里是保证同一行的item高度是相同的！！也就是同一行是齐整的 height相等
//                            if (position > 0 && position % 2 == 1) {
//                                View v = (View) lvItemDate.getTag();
//                                int height = v.getHeight();
//                                if (gv == null) {
//                                    return;
//                                }
//                                View view = gv.getChildAt(position - 1);
//                                int lastheight = view.getHeight();
//                                // 得到同一行的最后一个item和前一个item想比较，把谁的height大，就把两者中                                                                // height小的item的高度设定为height较大的item的高度一致，也就是保证同一                                                                 // 行高度相等即可
//                                if (height > lastheight) {
//                                    view.setLayoutParams(new GridView.LayoutParams(
//                                            GridView.LayoutParams.WRAP_CONTENT,
//                                            height));
//                                } else if (height < lastheight) {
//                                    v.setLayoutParams(new GridView.LayoutParams(
//                                            GridView.LayoutParams.WRAP_CONTENT,
//                                            lastheight));
//                                }
//                            }
//                        }
//                    });
//        }


    }


}
