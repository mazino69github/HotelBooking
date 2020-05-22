package com.example.hotelbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.hotelbooking.R;
import com.example.hotelbooking.activity.CalendarActivity;
import com.example.hotelbooking.activity.GioHangActivity;
import com.example.hotelbooking.activity.MainActivity;
import com.example.hotelbooking.model.KhachSan;
import com.example.hotelbooking.model.LoaiPhong;
import com.example.hotelbooking.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class loaiphongAdapter extends BaseAdapter {
    Context context;
    ArrayList<LoaiPhong> arr_loaiphong;
    KhachSan khachSan;

    public loaiphongAdapter(Context context, ArrayList<LoaiPhong> arr_loaiphong, KhachSan khachSan) {
        this.context = context;
        this.arr_loaiphong = arr_loaiphong;
        this.khachSan = khachSan;
    }

    @Override
    public int getCount() {
        return arr_loaiphong.size();
    }

    @Override
    public Object getItem(int position) {
        return arr_loaiphong.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        public TextView tvtenphong, textview_giuong, tvgiaphong;
        public ImageView imageviewchonphong, imageview_sokhach;
        public Spinner spinnerDem, spinnerPhong;
        public Button btchonphong;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        loaiphongAdapter.ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new loaiphongAdapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_phong, null);
            viewHolder.tvtenphong = (TextView) view.findViewById(R.id.txtTenPhong);
            viewHolder.textview_giuong = (TextView) view.findViewById(R.id.txtGiuong);
            viewHolder.imageviewchonphong = (ImageView) view.findViewById(R.id.imgChonPhong);
            viewHolder.imageview_sokhach = (ImageView) view.findViewById(R.id.imgSoKhach);
            viewHolder.tvgiaphong = (TextView) view.findViewById(R.id.txtGiaPhong);
            viewHolder.spinnerDem = (Spinner) view.findViewById(R.id.spinner_sodem);
            viewHolder.spinnerPhong = (Spinner) view.findViewById(R.id.spinner_sophong);
            viewHolder.btchonphong = (Button) view.findViewById(R.id.btnChonPhong);
            view.setTag(viewHolder);
        } else {
            viewHolder = (loaiphongAdapter.ViewHolder) view.getTag();
        }
        LoaiPhong loaiPhong = (LoaiPhong) getItem(position);
        viewHolder.tvtenphong.setText(loaiPhong.getTenloaiphong());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.tvgiaphong.setText(decimalFormat.format(loaiPhong.getGiaphong()) + " VND");
        viewHolder.textview_giuong.setText(loaiPhong.getSogiuong());

        int sokhach = loaiPhong.getSokhach();
        int width_imageview = 50;
        switch (sokhach) {
            case 1: {
                viewHolder.imageview_sokhach.setBackground(ContextCompat.getDrawable(context, R.drawable.icon1people));
                viewHolder.imageview_sokhach.getLayoutParams().width = width_imageview;
                break;
            }
            case 2: {
                viewHolder.imageview_sokhach.setBackground(ContextCompat.getDrawable(context, R.drawable.icon2people));
                viewHolder.imageview_sokhach.getLayoutParams().width = width_imageview * 2;
                break;
            }
            case 3: {
                viewHolder.imageview_sokhach.setBackground(ContextCompat.getDrawable(context, R.drawable.icon3people));
                viewHolder.imageview_sokhach.getLayoutParams().width = width_imageview * 3;
                break;
            }
            case 4: {
                viewHolder.imageview_sokhach.setBackground(ContextCompat.getDrawable(context, R.drawable.icon4people));
                viewHolder.imageview_sokhach.getLayoutParams().width = width_imageview * 4;
                break;
            }
            case 5: {
                viewHolder.imageview_sokhach.setBackground(ContextCompat.getDrawable(context, R.drawable.icon5people));
                viewHolder.imageview_sokhach.getLayoutParams().width = width_imageview * 5;
                break;
            }
        }
        Picasso.with(context).load(loaiPhong.getHinhanh())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imageviewchonphong);

        int giaphong = loaiPhong.getGiaphong();
        spinnerDem(viewHolder, loaiPhong, giaphong);
        spinnerPhong(viewHolder, loaiPhong);
        eventbutton(viewHolder, loaiPhong, khachSan, giaphong);
        return view;
    }

    private void eventbutton(final ViewHolder viewHolder, final LoaiPhong loaiPhong, final KhachSan khachSan, final int giaphong) {
        viewHolder.btchonphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sodem = viewHolder.spinnerDem.getSelectedItem().toString();
                int SoDem = Integer.parseInt(sodem.replace(" đêm", ""));
                String sophong = viewHolder.spinnerPhong.getSelectedItem().toString();
                int SoPhong = Integer.parseInt(sophong.replace(" phòng", ""));

                if (MainActivity.arr_giohang.size() > 0) {
                    boolean exit = false;
                    for (int i = 0; i < MainActivity.arr_giohang.size(); i++) {
                        if (MainActivity.arr_giohang.get(i).getIdphong() == loaiPhong.getIdphong()) {
                            MainActivity.arr_giohang.get(i).setSoluong(MainActivity.arr_giohang.get(i).getSoluong() + SoPhong);
                            MainActivity.arr_giohang.get(i).setSodem(MainActivity.arr_giohang.get(i).getSodem() + SoDem);
                            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            Date d_tra = formatter.parse(MainActivity.arr_giohang.get(i).getNgaytraphong(),new ParsePosition(0));
                            Date d_nhan = formatter.parse(MainActivity.arr_giohang.get(i).getNgaynhanphong(),new ParsePosition(0));
                            CalendarActivity.XoaNgay(d_nhan, d_tra);
                            if (MainActivity.arr_giohang.get(i).getSoluong() > 10) {
                                MainActivity.arr_giohang.get(i).setSoluong(10);
                            }
                            if (MainActivity.arr_giohang.get(i).getSodem() > 10) {
                                MainActivity.arr_giohang.get(i).setSodem(10);
                            }
                            MainActivity.arr_giohang.get(i).setGiaphong(giaphong * MainActivity.arr_giohang.get(i).getSoluong() * MainActivity.arr_giohang.get(i).getSodem());
                            MainActivity.arr_giohang.get(i).setNgaynhanphong(null);
                            MainActivity.arr_giohang.get(i).setNgaytraphong(null);
                            exit = true;
                        }
                    }
                    if (exit == false) {
                        int giamoi = SoPhong * giaphong * SoDem;
                        MainActivity.arr_giohang.add(new Giohang(loaiPhong.getIdphong(), loaiPhong.getTenloaiphong(), loaiPhong.getSogiuong(),
                                loaiPhong.getSokhach(), loaiPhong.getHinhanh(), giamoi, loaiPhong.getIdkhachsan(), khachSan.getTenks(), SoPhong, SoDem, null, null, ""));
                    }
                } else {
                    int giamoi = SoPhong * giaphong * SoDem;
                    MainActivity.arr_giohang.add(new Giohang(loaiPhong.getIdphong(), loaiPhong.getTenloaiphong(), loaiPhong.getSogiuong(),
                            loaiPhong.getSokhach(), loaiPhong.getHinhanh(), giamoi, loaiPhong.getIdkhachsan(), khachSan.getTenks(), SoPhong, SoDem, null, null, ""));
                }
                Intent intent = new Intent(context, GioHangActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    public void spinnerDem(final ViewHolder viewHolder, final LoaiPhong loaiPhong, final int giaphong) {
        String[] sodem = new String[]{"1 đêm", "2 đêm", "3 đêm", "4 đêm", "5 đêm", "6 đêm", "7 đêm", "8 đêm", "9 đêm", "10 đêm"};
        ArrayAdapter<String> arrayAdapterDem = new ArrayAdapter<String>(context, R.layout.dong_spinnner, R.id.text, sodem);
        viewHolder.spinnerDem.setAdapter(arrayAdapterDem);
        viewHolder.spinnerDem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sodem = viewHolder.spinnerDem.getSelectedItem().toString();
                int SoDem = Integer.parseInt(sodem.replace(" đêm", ""));

                loaiPhong.setGiaphong(giaphong * SoDem);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                viewHolder.tvgiaphong.setText(decimalFormat.format(loaiPhong.getGiaphong()) + " VND");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void spinnerPhong(ViewHolder viewHolder, LoaiPhong loaiPhong) {
        String[] sophong = new String[]{"1 phòng", "2 phòng", "3 phòng", "4 phòng", "5 phòng", "6 phòng", "7 phòng", "8 phòng", "9 phòng", "10 phòng",};
        ArrayAdapter<String> arrayAdapterPhong = new ArrayAdapter<String>(context, R.layout.dong_spinnner, R.id.text, sophong);
        viewHolder.spinnerPhong.setAdapter(arrayAdapterPhong);

    }

    public void updateArrayList(ArrayList<LoaiPhong> newList) {
        arr_loaiphong = new ArrayList<>();
        arr_loaiphong.addAll(newList);
        notifyDataSetChanged();
    }
}
