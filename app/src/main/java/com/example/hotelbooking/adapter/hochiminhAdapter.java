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

public class hochiminhAdapter extends BaseAdapter {

    Context context;
    ArrayList<KhachSan> arr_hochiminh;

    public hochiminhAdapter(Context context, ArrayList<KhachSan> arr_hochiminh) {
        this.context = context;
        this.arr_hochiminh = arr_hochiminh;
    }

    @Override
    public int getCount() {
        return arr_hochiminh.size();
    }

    @Override
    public Object getItem(int position) {
        return arr_hochiminh.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder
    {
        public TextView txtTenHochiminh, txtGiaHochiminh, txtMotaHochiminh, txtDiachiHochiminh;
        public ImageView imgHochiminh;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_hochiminh,null);
            viewHolder.txtTenHochiminh = (TextView) view.findViewById(R.id.txtTenHCM);
            viewHolder.txtGiaHochiminh = (TextView) view.findViewById(R.id.txtGiaHCM);
            viewHolder.txtMotaHochiminh = (TextView) view.findViewById(R.id.txtMotaHCM);
            viewHolder.imgHochiminh = (ImageView) view.findViewById(R.id.imgHcm);
            viewHolder.txtDiachiHochiminh = (TextView) view.findViewById(R.id.txtDiaChiHCM);
            view.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        KhachSan khachSan = (KhachSan) getItem(position);
        viewHolder.txtTenHochiminh.setText(khachSan.getTenks());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaHochiminh.setText(decimalFormat.format(khachSan.getGiaks()) + " VND");
        viewHolder.txtMotaHochiminh.setMaxLines(2);
        viewHolder.txtMotaHochiminh.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotaHochiminh.setText(khachSan.getMota());
        viewHolder.txtDiachiHochiminh.setText(khachSan.getDiachi());
        Picasso.with(context).load(khachSan.getHinhanhks())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgHochiminh);
        return view;
    }

    public void updateArrayList(ArrayList<KhachSan> newList) {
        arr_hochiminh = new ArrayList<>();
        arr_hochiminh.addAll(newList);
        notifyDataSetChanged();
    }
}
