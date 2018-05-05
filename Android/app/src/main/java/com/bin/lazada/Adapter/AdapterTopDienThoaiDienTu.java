package com.bin.lazada.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

public class AdapterTopDienThoaiDienTu extends RecyclerView.Adapter<AdapterTopDienThoaiDienTu.ViewHolderTopDienThoai> {

    Context context;
    List<SanPham> sanPhamList;
    int layout;

    public AdapterTopDienThoaiDienTu(Context context, int layout, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
        this.layout = layout;
    }

    public class ViewHolderTopDienThoai extends RecyclerView.ViewHolder {
        ImageView imgHinhSanPham;
        TextView txtTenSP, txtGiaTien, txtGiamGia;
//        ProgressBar progressBar;

        public ViewHolderTopDienThoai(View itemView) {
            super(itemView);

            imgHinhSanPham = (ImageView) itemView.findViewById(R.id.imgTopDienThoaiDienTu);
            txtTenSP = (TextView) itemView.findViewById(R.id.txtTieuDeTopDienThoaiDienTu);
            txtGiaTien = (TextView) itemView.findViewById(R.id.txtGiaDienTu);
            txtGiamGia = (TextView) itemView.findViewById(R.id.txtGiamGiaDienTu);
//            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar_TopDienThoaiVaMTB);
        }
    }

    @NonNull
    @Override
    public ViewHolderTopDienThoai onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layout, parent, false);

        ViewHolderTopDienThoai viewHolderTopDienThoai = new ViewHolderTopDienThoai(view);
        return viewHolderTopDienThoai;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderTopDienThoai holder, int position) {
        SanPham sanPham = sanPhamList.get(position);
        Picasso.get().load(sanPham.getANHLON()).resize(140, 140).centerInside().into(holder.imgHinhSanPham);

        holder.txtTenSP.setText(sanPham.getTENSP());

        NumberFormat numberFormat = new DecimalFormat("###,###");
        String gia = numberFormat.format(sanPham.getGIA()).toString();
        holder.txtGiaTien.setText(gia + " VNĐ ");
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }
}
