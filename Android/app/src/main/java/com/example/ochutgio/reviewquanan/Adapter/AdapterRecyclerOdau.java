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

import com.example.ochutgio.reviewquanan.Model.BinhLuanModel;
import com.example.ochutgio.reviewquanan.Model.ChiNhanhQuanAnModel;
import com.example.ochutgio.reviewquanan.Model.QuanAnModel;
import com.example.ochutgio.reviewquanan.R;
import com.example.ochutgio.reviewquanan.View.ChiTietQuanAnActivity;
import com.example.ochutgio.reviewquanan.View.TatCaBinhLuanActivity;
import com.example.ochutgio.reviewquanan.View.TatCaHinhAnhActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ochutgio on 4/19/2018.
 */

public class AdapterRecyclerOdau extends RecyclerView.Adapter<AdapterRecyclerOdau.ViewHolder> {

    List<QuanAnModel> quanAnModelList;
    Context context;
    int resource; /// id của custom_recyclerView_odau

    public AdapterRecyclerOdau(Context context, List<QuanAnModel> quanAnModelList, int resource) {
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenQuanAnOdau;
        TextView txtBinhLuan1;
        TextView txtBinhLuan2;
        TextView txtTongBinhLuan;
        TextView txtTongHinhAnh;
        TextView txtDiemQuanAn;
        TextView txtDiaChiQuanAn;
        TextView txtKhoangCach;
        TextView txtTrangThaiQuanAn;

        ImageView imgHinhQuanAn;
        CircleImageView imvAvatar1;
        CircleImageView imvAvatar2;

        LinearLayout containerQuanAn;
        LinearLayout containerBinhLuan1;
        LinearLayout containerBinhLuan2;
        LinearLayout containerBinhLuan;
        LinearLayout containerHinhAnh;

        public ViewHolder(View itemView) {
            super(itemView);
            /// find id control in custom_recyclerView_odau
            txtTenQuanAnOdau = (TextView) itemView.findViewById(R.id.txtTenQuanAnOdau);
            txtBinhLuan1 = (TextView) itemView.findViewById(R.id.txtBinhLuan1);
            txtBinhLuan2 = (TextView) itemView.findViewById(R.id.txtBinhLuan2);
            txtTongBinhLuan = (TextView) itemView.findViewById(R.id.txtTongBinhLuan);
            txtTongHinhAnh = (TextView) itemView.findViewById(R.id.txtTongHinhAnh);
            txtDiemQuanAn = (TextView) itemView.findViewById(R.id.txtDiemQuanAn);
            txtDiaChiQuanAn = (TextView) itemView.findViewById(R.id.txtDiaChiQuanAn);
            txtKhoangCach = (TextView) itemView.findViewById(R.id.txtKhoangCach);
            txtTrangThaiQuanAn = (TextView) itemView.findViewById(R.id.txtTrangThaiQuanAn);

            imgHinhQuanAn = (ImageView) itemView.findViewById(R.id.imgHinhQuanAn);
            imvAvatar1 = (CircleImageView) itemView.findViewById(R.id.imvAvatar1);
            imvAvatar2 = (CircleImageView) itemView.findViewById(R.id.imvAvatar2);

            containerQuanAn = (LinearLayout) itemView.findViewById(R.id.containerQuanAn);
            containerBinhLuan1 = (LinearLayout) itemView.findViewById(R.id.containerBinhLuan1);
            containerBinhLuan2 = (LinearLayout) itemView.findViewById(R.id.containerBinhLuan2);
            containerBinhLuan = (LinearLayout) itemView.findViewById(R.id.containerBinhLuan);
            containerHinhAnh = (LinearLayout) itemView.findViewById(R.id.containerHinhAnh);
        }
    }

    @Override
    public AdapterRecyclerOdau.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolder viewHolder= new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterRecyclerOdau.ViewHolder holder, int position) {
        final QuanAnModel quanAnModel = quanAnModelList.get(position);

        /// set text cho txtTenQuanAn in custom_recyclerView_odau
        holder.txtTenQuanAnOdau.setText(quanAnModel.getTenquanan());

        /// set image cho imvHinhQuanAn
        if(quanAnModel.getHinhanhquanan().size() > 0){
            holder.imgHinhQuanAn.setImageBitmap(quanAnModel.getBitmaphinhanhquanan());
        }

        if(quanAnModel.getBinhluanquanan().size() > 0){
            /// set text cho txtBinhLuan1
            BinhLuanModel binhLuanModel = quanAnModel.getBinhluanquanan().get(0);
            holder.containerBinhLuan1.setVisibility(View.VISIBLE);
            holder.txtBinhLuan1.setText(binhLuanModel.getNoidung());

            /// set hình ảnh cho imvAvatar1
            setHinhAnhBinhLuan(holder.imvAvatar1, binhLuanModel.getThanhVienModel().getHinhanh());

            if(quanAnModel.getBinhluanquanan().size() > 1){
                /// set text cho txtBinhLuan2
                holder.containerBinhLuan2.setVisibility(View.VISIBLE);
                BinhLuanModel binhLuanModel2 = quanAnModel.getBinhluanquanan().get(1);
                holder.txtBinhLuan2.setText(binhLuanModel2.getNoidung());
                /// set hình ảnh cho imvAvatar2
                setHinhAnhBinhLuan(holder.imvAvatar2, binhLuanModel2.getThanhVienModel().getHinhanh());
            }else {
                holder.containerBinhLuan2.setVisibility(View.GONE);
            }

            holder.txtTongBinhLuan.setText(quanAnModel.getBinhluanquanan().size() + "");

            /// Tính và hiển thị điểm trung bình và tổng số hình ảnh bình luận
            int tonghinhanh = 0;
            double tongdiem = 0;
            for(BinhLuanModel binhLuanModeln : quanAnModel.getBinhluanquanan()){
                tonghinhanh = tonghinhanh + binhLuanModeln.getHinhanhBinhLuan().size();
                tongdiem = tongdiem + binhLuanModeln.getChamdiem();
            }

            if(tongdiem > 0){
                double diemtb = tongdiem / quanAnModel.getBinhluanquanan().size();
                holder.txtDiemQuanAn.setText(String.format("%.1f", diemtb));
            }

            holder.txtTongHinhAnh.setText(tonghinhanh + "");

        }else {
            holder.containerBinhLuan2.setVisibility(View.GONE);
            holder.containerBinhLuan1.setVisibility(View.GONE);
            holder.txtTongBinhLuan.setText("0");
            holder.txtTongHinhAnh.setText("0");
            holder.txtDiemQuanAn.setText("_._");
        }

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

        /// xử lý thời gian đóng mở cửa
        Calendar calendar =  Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String giohientai = dateFormat.format(calendar.getTime());
        String giommocua = quanAnModel.getGiomocua();
        String giodongcua = quanAnModel.getGiodongcua();
        try{
            Date dateHienTai = dateFormat.parse(giohientai);
            Date dateMoCua = dateFormat.parse(giommocua);
            Date dateDongCua = dateFormat.parse(giodongcua);

            if (dateHienTai.after(dateMoCua) && dateHienTai.before(dateDongCua)){
                 holder.txtTrangThaiQuanAn.setText("Đang mở cửa");
            }else {
                holder.txtTrangThaiQuanAn.setText("Đã đóng cửa");
            }
        }catch (ParseException e){
            e.printStackTrace();
        }

        /// chuyen intent chitietquanan
        holder.containerQuanAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iChiTietQuanAn = new Intent(context, ChiTietQuanAnActivity.class);
                iChiTietQuanAn.putExtra("quanan", quanAnModel);
                context.startActivity(iChiTietQuanAn);
            }
        });

        holder.containerBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(quanAnModel.getBinhluanquanan().size() > 0){
                Intent iTatCaBinhLuan = new Intent(context, TatCaBinhLuanActivity.class);
                iTatCaBinhLuan.putExtra("binhluanquanan", quanAnModel);
                context.startActivity(iTatCaBinhLuan);
            }

            }
        });

        holder.containerHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(Integer.parseInt(holder.txtTongHinhAnh.getText().toString()) > 0){
                Intent iTatCaHinhAnh = new Intent(context, TatCaHinhAnhActivity.class);
                iTatCaHinhAnh.putExtra("hinhanhquanan", quanAnModel);
                context.startActivity(iTatCaHinhAnh);
           }
            }
        });
    }

    /// hàm download hình ảnh từ firebase và gán cho ImageView
    private void setHinhAnhBinhLuan(final CircleImageView imv, String linkHinh){
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("User").child(linkHinh);
        long ONE_MEGABYTE = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imv.setImageBitmap(bitmap);
            }
        });
    }

    @Override
    public int getItemCount() {
        return quanAnModelList.size();
    }

}
