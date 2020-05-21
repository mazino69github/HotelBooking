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
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class khachsandadatAdapter extends RecyclerView.Adapter<khachsandadatAdapter.ItemHolder>{
    Context context;
    ArrayList<KhachSan> mangkhachsandadat;

    public khachsandadatAdapter(Context context, ArrayList<KhachSan> mangkhachsandadat) {
        this.context = context;
        this.mangkhachsandadat = mangkhachsandadat;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_khachsandadat,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        KhachSan khachSan = mangkhachsandadat.get(position);
        holder.txtgiaks.setText(khachSan.getTenks());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiaks.setText(decimalFormat.format(khachSan.getGiaks()) + " VND");
        Picasso.with(context).load(khachSan.getHinhanhks())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(holder.imghinhks);
    }

    @Override
    public int getItemCount() {
        return mangkhachsandadat.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhks;
        public TextView txttenks, txtgiaks;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imghinhks = (ImageView) itemView.findViewById(R.id.imageviewks);
            txtgiaks = (TextView) itemView.findViewById(R.id.textviewgiaks);
            txttenks = (TextView) itemView.findViewById(R.id.textviewtenks);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietKhachSanActivity.class);
                    intent.putExtra("thongtinks", mangkhachsandadat.get(getLayoutPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }

    public void updateArrayList(ArrayList<KhachSan> newList) {
        mangkhachsandadat = new ArrayList<>();
        mangkhachsandadat.addAll(newList);
        notifyDataSetChanged();
    }
}
