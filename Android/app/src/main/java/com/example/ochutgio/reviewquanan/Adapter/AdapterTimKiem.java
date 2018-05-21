package com.example.ochutgio.reviewquanan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ochutgio.reviewquanan.Model.ChiNhanhQuanAnModel;
import com.example.ochutgio.reviewquanan.Model.QuanAnModel;
import com.example.ochutgio.reviewquanan.R;
import com.example.ochutgio.reviewquanan.View.ChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdapterTimKiem extends RecyclerView.Adapter<AdapterTimKiem.ViewHolder> {

    List<QuanAnModel> quanAnModelList;
    Context context;
    int resource;

    public AdapterTimKiem(Context context, List<QuanAnModel> quanAnModelList, int resource){
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenQuanAn;
        TextView txtDiaChiQuanAn;
        ImageView imvQuanAn;
        TextView txtKhoangCach;
        LinearLayout containerQuanAn;

        public ViewHolder(View itemView) {
            super(itemView);

            txtDiaChiQuanAn = (TextView) itemView.findViewById(R.id.txtDiaChiQuanAn);
            txtTenQuanAn = (TextView) itemView.findViewById(R.id.txtTenQuanAn);
            imvQuanAn = (ImageView) itemView.findViewById(R.id.imvQuanAn);
            txtKhoangCach = (TextView) itemView.findViewById(R.id.txtKhoangCach);
            containerQuanAn = (LinearLayout) itemView.findViewById(R.id.containerQuanAn);
        }
    }

    @Override
    public AdapterTimKiem.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterTimKiem.ViewHolder holder, int position) {
        final QuanAnModel quanAnModel = quanAnModelList.get(position);

        if(quanAnModel != null){
            /// set text cho txtTenQuanAn in custom_recyclerView_odau
            holder.txtTenQuanAn.setText(quanAnModel.getTenquanan());

            /// lay chi nhanh quan an gan nhat va hien thi
            if(quanAnModel.getChinhanhquanan().size() > 0){
                ChiNhanhQuanAnModel chiNhanhQuanAnModelMin = quanAnModel.getChinhanhquanan().get(0);
                for(ChiNhanhQuanAnModel chiNhanhQuanAnModel : quanAnModel.getChinhanhquanan()){
                    if( chiNhanhQuanAnModelMin.getKhoangcach() > chiNhanhQuanAnModel.getKhoangcach()){
                        chiNhanhQuanAnModelMin = chiNhanhQuanAnModel;
                    }
                }

                holder.txtDiaChiQuanAn.setText(chiNhanhQuanAnModelMin.getDiachi());
                holder.txtKhoangCach.setText(String.format("%.1f", chiNhanhQuanAnModelMin.getKhoangcach()) + "km");
            }

            if(quanAnModel.getHinhanhquanan() != null){
                StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("Photo").child(quanAnModel.getHinhanhquanan().get(0));
                long ONE_MEGABYTE = 1024 * 1024;
                storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.imvQuanAn.setImageBitmap(bitmap);
                    }
                });
            }

            ///
            holder.containerQuanAn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iChiTietQuanAn = new Intent(context, ChiTietQuanAnActivity.class);
                    iChiTietQuanAn.putExtra("quanan", quanAnModel);
                    context.startActivity(iChiTietQuanAn);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return quanAnModelList.size();
    }


}
