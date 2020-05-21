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

public class danangAdapter extends BaseAdapter {
    Context context;
    ArrayList<KhachSan> arr_danang;

    public danangAdapter(Context context, ArrayList<KhachSan> arr_danang) {
        this.context = context;
        this.arr_danang = arr_danang;
    }

    @Override
    public int getCount() {
        return arr_danang.size();
    }

    @Override
    public Object getItem(int position) {
        return arr_danang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder
    {
        public TextView txtTenDanang, txtGiaDanang, txtMotaDanang, txtDiachiDanang;
        public ImageView imgDanang;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_danang,null);
            viewHolder.txtTenDanang = (TextView) view.findViewById(R.id.txtTenDN);
            viewHolder.txtGiaDanang = (TextView) view.findViewById(R.id.txtGiaDN);
            viewHolder.txtMotaDanang = (TextView) view.findViewById(R.id.txtMotaDN);
            viewHolder.imgDanang = (ImageView) view.findViewById(R.id.imgDN);
            viewHolder.txtDiachiDanang = (TextView) view.findViewById(R.id.txtDiaChiDN);
            view.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        KhachSan khachSan = (KhachSan) getItem(position);
        viewHolder.txtTenDanang.setText(khachSan.getTenks());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaDanang.setText(decimalFormat.format(khachSan.getGiaks()) + " VND");
        viewHolder.txtMotaDanang.setMaxLines(2);
        viewHolder.txtMotaDanang.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotaDanang.setText(khachSan.getMota());
        viewHolder.txtDiachiDanang.setText(khachSan.getDiachi());
        Picasso.with(context).load(khachSan.getHinhanhks())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgDanang);
        return view;
    }

    public void updateArrayList(ArrayList<KhachSan> newList) {
        arr_danang = new ArrayList<>();
        arr_danang.addAll(newList);
        notifyDataSetChanged();
    }
}
