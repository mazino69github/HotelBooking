package com.example.hotelbooking.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hotelbooking.R;
import com.example.hotelbooking.activity.ChiTietKhachSanActivity;
import com.example.hotelbooking.model.KhachSan;
import com.example.hotelbooking.ultil.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class KhachSanAdapter extends RecyclerView.Adapter<KhachSanAdapter.ItemHolder> {

    Context context;
    ArrayList<KhachSan> arrayKhachSan;

    public KhachSanAdapter(Context context, ArrayList<KhachSan> arrayKhachSan) {
        this.context = context;
        this.arrayKhachSan = arrayKhachSan;
    }

    @Override
    public ItemHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_khachsanmoinhat, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        KhachSan khachSan = arrayKhachSan.get(position);
        holder.txtTenks.setText(khachSan.getTenks());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiaks.setText("Giá : " + decimalFormat.format(khachSan.getGiaks())+ " VND");
        Picasso.with(context).load(khachSan.getHinhanhks())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imgHinhks);

    }

    @Override
    public int getItemCount() {
        return arrayKhachSan.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgHinhks;
        public TextView txtTenks, txtGiaks;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhks = (ImageView) itemView.findViewById(R.id.imageviewks);
            txtGiaks = (TextView) itemView.findViewById(R.id.textviewgiaks);
            txtTenks = (TextView) itemView.findViewById(R.id.textviewtenks);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietKhachSanActivity.class);
                    intent.putExtra("thongtinks",arrayKhachSan.get(getPosition()));
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast_Short(context, arrayKhachSan.get(getPosition()).getTenks());
                    context.startActivity(intent);
                }
            });
        }
    }

    public void updateArrayList(ArrayList<KhachSan> newList) {
        arrayKhachSan = new ArrayList<>();
        arrayKhachSan.addAll(newList);
        notifyDataSetChanged();
    }
}
