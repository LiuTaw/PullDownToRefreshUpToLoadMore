package com.liutaw.pulldowntorefreshuptoloadmore.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.liutaw.pulldowntorefreshuptoloadmore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liutao on 15/12/29.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<String> titles;

    public MyAdapter(Context context) {
        this.context = context;
        titles = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public Object getItem(int position) {
        return titles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String content = (String) getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item, null);
            viewHolder.text_title = (TextView) convertView.findViewById(R.id.text_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.text_title.setText(content);
        return convertView;
    }

    static class ViewHolder {
        TextView text_title;
    }

    public void addData(List<String> data) {
        if (data == null) return;
        this.titles.addAll(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.titles.clear();
        notifyDataSetChanged();
    }
}