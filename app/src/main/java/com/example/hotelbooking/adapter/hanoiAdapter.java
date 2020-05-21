package com.example.hotelbooking.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.model.KhachSan;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class hanoiAdapter extends BaseAdapter {
    Context context;
    ArrayList<KhachSan> arr_hanoi;

    public hanoiAdapter(Context context, ArrayList<KhachSan> arr_hanoi) {
        this.context = context;
        this.arr_hanoi = arr_hanoi;
    }

    @Override
    public int getCount() {
        return arr_hanoi.size();
    }

    @Override
    public Object getItem(int position) {
        return arr_hanoi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder
    {
        public TextView txtTenHanoi, txtGiaHanoi, txtMotaHanoi, txtDiachiHanoi;
        public ImageView imgHanoi;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_hanoi,null);
            viewHolder.txtTenHanoi = (TextView) view.findViewById(R.id.txtTenHN);
            viewHolder.txtGiaHanoi = (TextView) view.findViewById(R.id.txtGiaHN);
            viewHolder.txtMotaHanoi = (TextView) view.findViewById(R.id.txtMotaHN);
            viewHolder.imgHanoi = (ImageView) view.findViewById(R.id.imgHN);
            viewHolder.txtDiachiHanoi = (TextView) view.findViewById(R.id.txtDiaChiHN);
            view.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        KhachSan khachSan = (KhachSan) getItem(position);
        viewHolder.txtTenHanoi.setText(khachSan.getTenks());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaHanoi.setText(decimalFormat.format(khachSan.getGiaks()) + " VND");
        viewHolder.txtMotaHanoi.setMaxLines(2);
        viewHolder.txtMotaHanoi.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotaHanoi.setText(khachSan.getMota());
        viewHolder.txtDiachiHanoi.setText(khachSan.getDiachi());
        Picasso.with(context).load(khachSan.getHinhanhks())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgHanoi);
        return view;
    }

    public void updateArrayList(ArrayList<KhachSan> newList) {
        arr_hanoi = new ArrayList<>();
        arr_hanoi.addAll(newList);
        notifyDataSetChanged();
    }
}
