package com.example.ochutgio.reviewquanan.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;


import com.example.ochutgio.reviewquanan.Model.ChonHinhBinhLuanModel;
import com.example.ochutgio.reviewquanan.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by ochutgio on 5/6/2018.
 */

public class AdapterChonHinhBinhLuan extends RecyclerView.Adapter<AdapterChonHinhBinhLuan.ViewHolder> {

    Context context;
    int layout;
    List<ChonHinhBinhLuanModel> listDuongDan;

    public AdapterChonHinhBinhLuan(Context context, int layout, List<ChonHinhBinhLuanModel> listDuongDan){
        this.context = context;
        this.layout = layout;
        this.listDuongDan = listDuongDan;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imvChonHinh;
        CheckBox checkBoxChonHinh;

        public ViewHolder(View itemView) {
            super(itemView);

            imvChonHinh = (ImageView) itemView.findViewById(R.id.imvChonHinh);
            checkBoxChonHinh = (CheckBox) itemView.findViewById(R.id.checkboxChonHinh);

        }
    }

    @Override
    public AdapterChonHinhBinhLuan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolder  viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterChonHinhBinhLuan.ViewHolder holder, final int position) {

        final ChonHinhBinhLuanModel chonHinhBinhLuanModel = listDuongDan.get(position);
//        Uri uri = Uri.parse(chonHinhBinhLuanModel.getDuongdan());
//        Log.d("kiemtra", uri + "");
        File f = new File(chonHinhBinhLuanModel.getDuongdan());
        Picasso.get().load(f).resize(100, 100).into(holder.imvChonHinh);
        Picasso.get()
                .load(f)
                .resize(100, 100)
                .into(holder.imvChonHinh);
        //holder.imvChonHinh.setImageURI(uri);
        holder.checkBoxChonHinh.setChecked(chonHinhBinhLuanModel.isCheck());
        holder.checkBoxChonHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                listDuongDan.get(position).setCheck(checkBox.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
         return listDuongDan.size();
    }

}
