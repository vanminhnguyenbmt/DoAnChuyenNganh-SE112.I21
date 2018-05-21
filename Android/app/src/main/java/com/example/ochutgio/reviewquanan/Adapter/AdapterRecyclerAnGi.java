package com.example.ochutgio.reviewquanan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ochutgio.reviewquanan.Model.ChiNhanhQuanAnModel;
import com.example.ochutgio.reviewquanan.Model.MonAnModel;
import com.example.ochutgio.reviewquanan.Model.QuanAnModel;
import com.example.ochutgio.reviewquanan.R;
import com.example.ochutgio.reviewquanan.View.ChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

/**
 * Created by ochutgio on 4/19/2018.
 */

public class AdapterRecyclerAnGi extends RecyclerView.Adapter<AdapterRecyclerAnGi.ViewHolder> {

    Context context;
    List<QuanAnModel> quanAnModelList;
    int resource; // id của custom_layout_recyclerView

    public AdapterRecyclerAnGi( List<QuanAnModel> quanAnModelList, int resource, Context context) {
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout containerQuanAn;
        ImageView imvHinhMonAn;
        TextView txtDiaChi;
        TextView txtTenMonAn;
        TextView txtTenQuanAn1;
        public ViewHolder(View itemView) {
            super(itemView);
            // find id textView/ control
            txtTenQuanAn1 = (TextView) itemView.findViewById(R.id.txtTenQuanAn);
            txtDiaChi = (TextView) itemView.findViewById(R.id.txtDiaChi);
            imvHinhMonAn = (ImageView) itemView.findViewById(R.id.imvHinhMonAn);
            txtTenMonAn = (TextView) itemView.findViewById(R.id.txtTenMonAn);
            containerQuanAn = (LinearLayout) itemView.findViewById(R.id.containerQuanAn);
        }
    }

    // khởi tạo ViewHolder
    @Override
        public AdapterRecyclerAnGi.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
            AdapterRecyclerAnGi.ViewHolder viewHolder= new AdapterRecyclerAnGi.ViewHolder(view);
        return viewHolder;
    }

        @Override
        public void onBindViewHolder(final AdapterRecyclerAnGi.ViewHolder holder, int position) {
            final QuanAnModel quanAnModel = quanAnModelList.get(position);

            /// ten quan an
            holder.txtTenQuanAn1.setText(quanAnModel.getTenquanan());

            /// lay chi nhanh quan an gan nhat va hien thi
            if(quanAnModel.getChinhanhquanan().size() > 0){
                ChiNhanhQuanAnModel chiNhanhQuanAnModelMin = quanAnModel.getChinhanhquanan().get(0);
                for(ChiNhanhQuanAnModel chiNhanhQuanAnModel : quanAnModel.getChinhanhquanan()){
                    if( chiNhanhQuanAnModelMin.getKhoangcach() > chiNhanhQuanAnModel.getKhoangcach()){
                        chiNhanhQuanAnModelMin = chiNhanhQuanAnModel;
                    }
                }

                holder.txtDiaChi.setText(chiNhanhQuanAnModelMin.getDiachi());
            }

            if(quanAnModel.getThucdonquanan().size() > 0) {

                MonAnModel monAnModel = quanAnModel.getThucdonquanan().get(0).getMonAnModelList().get(0);
                holder.txtTenMonAn.setText(monAnModel.getTenmon());
                holder.imvHinhMonAn.setImageBitmap(quanAnModel.getBitmaphinhmonan());
            }

            holder.containerQuanAn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iChiTietQuanAn = new Intent(context, ChiTietQuanAnActivity.class);
                    iChiTietQuanAn.putExtra("quanan", quanAnModel);
                    context.startActivity(iChiTietQuanAn);
                }
            });
        }

        @Override
        public int getItemCount() {
            return quanAnModelList.size();
        }


}
