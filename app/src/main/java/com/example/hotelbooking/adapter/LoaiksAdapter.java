package com.example.hotelbooking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.model.Loaiks;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiksAdapter  extends BaseAdapter {
    ArrayList<Loaiks> arrayListloaisp;
    Context context;

    public LoaiksAdapter(ArrayList<Loaiks> arrayListloaisp, Context context) {
        this.arrayListloaisp = arrayListloaisp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListloaisp.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListloaisp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView txttenloaiks;
        ImageView imgloaiks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_listview_loaiks, null);
            viewHolder.txttenloaiks = (TextView) convertView.findViewById(R.id.textview_loaiks);
            viewHolder.imgloaiks = (ImageView) convertView.findViewById(R.id.imageview_loaiks);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Loaiks loaiks = (Loaiks) getItem(position);
        viewHolder.txttenloaiks.setText(loaiks.getTenloaiks());
        Picasso.with(context).load(loaiks.getHinhanhloaiks())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgloaiks);
        return convertView;
    }
}
