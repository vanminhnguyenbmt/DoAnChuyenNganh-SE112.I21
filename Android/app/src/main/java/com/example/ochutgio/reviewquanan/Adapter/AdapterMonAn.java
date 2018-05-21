package com.example.ochutgio.reviewquanan.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.text.style.IconMarginSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ochutgio.reviewquanan.Model.MonAnModel;
import com.example.ochutgio.reviewquanan.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by ochutgio on 5/8/2018.
 */

public class AdapterMonAn extends RecyclerView.Adapter<AdapterMonAn.ViewHolder> {

    Context context;
    List<MonAnModel> monAnModelList;

    public AdapterMonAn(Context context, List<MonAnModel> monAnModelList){
        this.context = context;
        this.monAnModelList = monAnModelList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imvHinhMonAn;
        TextView txtTenMonAn;
        TextView txtGiaTien;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTenMonAn = (TextView) itemView.findViewById(R.id.txtTenMonAn);
            txtGiaTien = (TextView) itemView.findViewById(R.id.txtGiaTien);
            imvHinhMonAn = (ImageView) itemView.findViewById(R.id.imvHinhMonAn);
        }
    }

    @Override
    public AdapterMonAn.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_monan, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterMonAn.ViewHolder holder, int position) {
        MonAnModel monAnModel = monAnModelList.get(position);
        holder.txtTenMonAn.setText(monAnModel.getTenmon());
        holder.txtGiaTien.setText(monAnModel.getGiatien()+ " VNĐ");
        if(monAnModel.getHinhanh() != null){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Photo").child(monAnModel.getHinhanh());
            long ONE_MEGABYTE = 1024 * 1024;
            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmapHinhMonAn = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.imvHinhMonAn.setImageBitmap(bitmapHinhMonAn);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return monAnModelList.size();
    }


}
