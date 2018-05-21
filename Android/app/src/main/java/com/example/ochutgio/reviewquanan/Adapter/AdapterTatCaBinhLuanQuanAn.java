package com.example.ochutgio.reviewquanan.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ochutgio.reviewquanan.Model.BinhLuanModel;
import com.example.ochutgio.reviewquanan.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ochutgio on 5/16/2018.
 */

public class AdapterTatCaBinhLuanQuanAn extends RecyclerView.Adapter<AdapterTatCaBinhLuanQuanAn.ViewHolder> {

    Context context;
    List<BinhLuanModel> binhLuanModelList;
    int resource;

    public AdapterTatCaBinhLuanQuanAn(Context context, int resource, List<BinhLuanModel> binhLuanModelList){
        this.context = context;
        this.resource = resource;
        this.binhLuanModelList = binhLuanModelList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView imvAvatar;
        RecyclerView recyclerHinhBinhLuan;
        TextView txtTieuDeBinhLuan;
        TextView txtNoiDungBinhLuan;
        TextView txtChamDiem;

        public ViewHolder(View itemView) {
            super(itemView);

            imvAvatar = (CircleImageView) itemView.findViewById(R.id.imvAvatar);
            recyclerHinhBinhLuan = (RecyclerView) itemView.findViewById(R.id.recyclerHinhBinhLuan);
            txtTieuDeBinhLuan = (TextView) itemView.findViewById(R.id.txtTieuDeBinhLuan);
            txtNoiDungBinhLuan = (TextView) itemView.findViewById(R.id.txtNoiDungBinhLuan);
            txtChamDiem = (TextView) itemView.findViewById(R.id.txtChamDiem);
        }
    }

    @Override
    public AdapterTatCaBinhLuanQuanAn.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        AdapterTatCaBinhLuanQuanAn.ViewHolder viewHolder = new AdapterTatCaBinhLuanQuanAn.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterTatCaBinhLuanQuanAn.ViewHolder holder, int position) {

        final BinhLuanModel binhLuanModel = binhLuanModelList.get(position);

        holder.txtTieuDeBinhLuan.setText(binhLuanModel.getTieude());

        holder.txtNoiDungBinhLuan.setText(binhLuanModel.getNoidung());
        if(binhLuanModel.getChamdiem() > 5){
            holder.txtChamDiem.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        }else {
            holder.txtChamDiem.setTextColor(Color.RED);
        }
        holder.txtChamDiem.setText(binhLuanModel.getChamdiem() + "đ");

        StorageReference storageAvatar = FirebaseStorage.getInstance().getReference().child("User").child(binhLuanModel.getThanhVienModel().getHinhanh());
        long ONE_MAGEBYTE = 1024 * 1024;
        storageAvatar.getBytes(ONE_MAGEBYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.imvAvatar.setImageBitmap(bitmap);
            }
        });

        final List<Bitmap>  bitmapList = new ArrayList<>();
        for(String linkhinh : binhLuanModel.getHinhanhBinhLuan()){
            StorageReference storageHinhBinhLuan = FirebaseStorage.getInstance().getReference().child("Photo").child(linkhinh);
            storageHinhBinhLuan.getBytes(ONE_MAGEBYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);

                    if(bitmapList.size() == binhLuanModel.getHinhanhBinhLuan().size()){
                        AdapterRecyclerHinhBinhLuan adapterRecyclerHinhBinhLuan = new AdapterRecyclerHinhBinhLuan(context, R.layout.custom_layout_hinhbinhluan, bitmapList);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
                        holder.recyclerHinhBinhLuan.setLayoutManager(layoutManager);
                        holder.recyclerHinhBinhLuan.setAdapter(adapterRecyclerHinhBinhLuan);
                        adapterRecyclerHinhBinhLuan.notifyDataSetChanged();
                    }
                }
            });
        }


    }

    @Override
    public int getItemCount() {
            return binhLuanModelList.size();
    }

}
