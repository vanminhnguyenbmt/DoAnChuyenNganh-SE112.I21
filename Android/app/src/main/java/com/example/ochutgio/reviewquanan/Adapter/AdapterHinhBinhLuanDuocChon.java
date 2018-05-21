package com.example.ochutgio.reviewquanan.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ochutgio.reviewquanan.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by ochutgio on 5/7/2018.
 */

public class AdapterHinhBinhLuanDuocChon extends RecyclerView.Adapter<AdapterHinhBinhLuanDuocChon.ViewHolder> {

    Context context;
    int layout;
    List<String> listHinh;

    public AdapterHinhBinhLuanDuocChon(Context context, int layout, List<String> listHinh){
        this.context = context;
        this.layout = layout;
        this.listHinh = listHinh;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imvChonHinh;
        ImageView imvXoaHinh;

        public ViewHolder(View itemView) {
            super(itemView);

            imvChonHinh = (ImageView) itemView.findViewById(R.id.imvChonHinh);
            imvXoaHinh = (ImageView) itemView.findViewById(R.id.imvXoaHinh);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

//        Uri uri = Uri.parse(listHinh.get(position));
//        Log.d("kiemtra", uri + "");
        File f = new File(listHinh.get(position));
        Picasso.get().load(f).resize(100, 100).into(holder.imvChonHinh);
        //holder.imvChonHinh.setImageURI(uri);

        holder.imvXoaHinh.setTag(position);
        holder.imvXoaHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int vitri = (int) view.getTag();
                listHinh.remove(vitri);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listHinh.size() > 12) return 12;
        else return listHinh.size();
    }


}
