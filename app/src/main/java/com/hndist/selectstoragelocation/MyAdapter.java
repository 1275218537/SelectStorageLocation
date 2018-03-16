package com.hndist.selectstoragelocation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 存储路径
 * Created by Airn on 2017/11/14.
 */

public class MyAdapter extends BaseAdapter {
    private List<String> strAllPath;

    public MyAdapter(List<String> strAllPath) {
        this.strAllPath = strAllPath;
    }

    @Override
    public int getCount() {
        return strAllPath == null ? 0 : strAllPath.size();
    }

    @Override
    public Object getItem(int position) {
        return strAllPath.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lv_storage_path, null);
            viewHolder.tv_path = (TextView) convertView.findViewById(R.id.tv_path);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_path.setText(strAllPath.get(position) == null ? "" : strAllPath.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView tv_path;
    }
}
