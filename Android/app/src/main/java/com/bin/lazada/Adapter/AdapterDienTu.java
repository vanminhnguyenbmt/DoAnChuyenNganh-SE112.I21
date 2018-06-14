package com.bin.lazada.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bin.lazada.ObjectClass.DienTu;
import com.bin.lazada.ObjectClass.KhuyenMai;
import com.bin.lazada.Presenter.KhuyenMai.PresenterLogicKhuyenMai;
import com.bin.lazada.R;
import com.bin.lazada.View.TrangChu.ViewKhuyenMai;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterDienTu extends RecyclerView.Adapter<AdapterDienTu.ViewHolderDienTu> implements ViewKhuyenMai{

    Context context;
    List<DienTu> dienTuList;
    List<KhuyenMai> khuyenMaiList;

    public AdapterDienTu(Context context, List<DienTu> dienTuList) {
        this.context = context;
        this.dienTuList = dienTuList;
    }

    @Override
    public void HienThiDanhSachKhuyenMai(List<KhuyenMai> khuyenMaiList) {
        this.khuyenMaiList = khuyenMaiList;
    }

    public class ViewHolderDienTu extends RecyclerView.ViewHolder {

        RecyclerView recyclerViewThuongHieuLon, recyclerViewTopSanPham;
        TextView txtTieuDeSanPhamNoiBat, txtTopSanPhamNoiBat;
        ImageView imgHinhKhuyenMaiDienTu;

        public ViewHolderDienTu(View itemView) {
            super(itemView);

            recyclerViewThuongHieuLon = (RecyclerView) itemView.findViewById(R.id.recyclerThuongHieuLon);
            recyclerViewTopSanPham = (RecyclerView) itemView.findViewById(R.id.recyclerTopDienThoaiMayTinhBang);
            imgHinhKhuyenMaiDienTu = (ImageView) itemView.findViewById(R.id.imgKhuyenMaiDienTu);
            txtTieuDeSanPhamNoiBat = (TextView) itemView.findViewById(R.id.txtTenSanPhamNoiBat);
            txtTopSanPhamNoiBat = (TextView) itemView.findViewById(R.id.txtTenTopSanPhamNoiBat);
        }
    }

    @NonNull
    @Override
    public ViewHolderDienTu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout_recyclerview_dientu, parent, false);

        ViewHolderDienTu viewHolderDienTu = new ViewHolderDienTu(view);
        PresenterLogicKhuyenMai presenterLogicKhuyenMai = new PresenterLogicKhuyenMai(this);
        presenterLogicKhuyenMai.LayDanhSachKhuyenMai();

        return viewHolderDienTu;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDienTu holder, int position) {
        DienTu dienTu = dienTuList.get(position);
        holder.txtTieuDeSanPhamNoiBat.setText(dienTu.getTenNoiBat().toString());
        holder.txtTopSanPhamNoiBat.setText(dienTu.getTenTopNoiBat().toString());

        Picasso.get().load(khuyenMaiList.get(position).getHINHKHUYENMAI()).resize(700, 200).into(holder.imgHinhKhuyenMaiDienTu);

        //hiển thị danh sách thương hiệu lớn (RecyclerView thương hiệu lớn)
        AdapterThuongHieuLon adapterThuongHieuLon = new AdapterThuongHieuLon(context, dienTu.getThuongHieus(), dienTu.getThuonghieu());

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 3, GridLayoutManager.HORIZONTAL, false);
        holder.recyclerViewThuongHieuLon.setLayoutManager(layoutManager);
        holder.recyclerViewThuongHieuLon.setAdapter(adapterThuongHieuLon);

        adapterThuongHieuLon.notifyDataSetChanged();

        //hiển thị danh sách top sản phẩm (RecyclerView top sản phẩm)
        AdapterTopDienThoaiDienTu adapterTopDienThoaiDienTu = new AdapterTopDienThoaiDienTu(context, R.layout.custom_layout_topdienthoaivamaytinhbang, dienTu.getSanPhams());

        RecyclerView.LayoutManager layoutManagerTop = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        holder.recyclerViewTopSanPham.setLayoutManager(layoutManagerTop);
        holder.recyclerViewTopSanPham.setAdapter(adapterTopDienThoaiDienTu);
    }

    @Override
    public int getItemCount() {
        return dienTuList.size();
    }
}
