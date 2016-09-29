package com.example.hyyx.testdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hyyx.testdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyyx on 16/9/19.
 */
public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<String> data;

    public ListViewAdapter(Context context, List<String> data) {
        inflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }

    }

    public void setDate(List<String> data) {
        if (data != null) {
            this.data.clear();
            this.data.addAll(data);
            notifyDataSetChanged();


        }


    }


    @Override
    public int getCount() {
        return data != null ? data.size() : 0;
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

        ViewHolder viewHolder = null;
        if (null == view) {
            view = inflater.inflate(R.layout.item_lv_data, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (i == 0) {
            viewHolder.pointUpLine.setVisibility(View.GONE);
            viewHolder.pointDownLine.setVisibility(View.VISIBLE);

        } else if (i == data.size() - 1) {
            viewHolder.pointDownLine.setVisibility(View.GONE);
            viewHolder.pointUpLine.setVisibility(View.VISIBLE);
        }else {
            viewHolder.pointUpLine.setVisibility(View.VISIBLE);
            viewHolder.pointDownLine.setVisibility(View.VISIBLE);
        }



        // TODO: 16/9/19 item判断的具体的逻辑


        return view;
    }

    static class ViewHolder {

        private TextView pointUpLine;
        private TextView pointDownLine;

        public ViewHolder(View convertView) {

            pointUpLine = ((TextView) convertView.findViewById(R.id.point_up_line));
            pointDownLine = ((TextView) convertView.findViewById(R.id.point_down_line));


        }


    }
}
