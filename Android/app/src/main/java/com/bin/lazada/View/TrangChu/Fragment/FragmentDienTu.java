package com.bin.lazada.View.TrangChu.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bin.lazada.Adapter.AdapterDienTu;
import com.bin.lazada.Adapter.AdapterThuongHieuLonDienTu;
import com.bin.lazada.Adapter.AdapterTopDienThoaiDienTu;
import com.bin.lazada.ObjectClass.DienTu;
import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.ObjectClass.ThuongHieu;
import com.bin.lazada.Presenter.TrangChu_DienTu.PresenterLogicDienTu;
import com.bin.lazada.R;
import com.bin.lazada.View.TrangChu.ViewDienTu;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FragmentDienTu extends Fragment implements ViewDienTu {

    RecyclerView recyclerView, recyclerTopCacThuongHieuLon, recyclerHangMoiVe;
    List<DienTu> dienTuList;
    PresenterLogicDienTu presenterLogicDienTu;
    ImageView imgSanPham1, imgSanPham2, imgSanPham3;
    TextView txtSanPham1, txtSanPham2, txtSanPham3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_dientu, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerDienTu);
        recyclerTopCacThuongHieuLon = (RecyclerView) view.findViewById(R.id.recyclerTopThuongHieuLon);
        recyclerHangMoiVe = (RecyclerView) view.findViewById(R.id.recyclerHangMoiVe);
        imgSanPham1 = (ImageView) view.findViewById(R.id.imgSanPham1);
        imgSanPham2 = (ImageView) view.findViewById(R.id.imgSanPham2);
        imgSanPham3 = (ImageView) view.findViewById(R.id.imgSanPham3);

        txtSanPham1 = (TextView) view.findViewById(R.id.txtSanPham1);
        txtSanPham2 = (TextView) view.findViewById(R.id.txtSanPham2);
        txtSanPham3 = (TextView) view.findViewById(R.id.txtSanPham3);

        presenterLogicDienTu = new PresenterLogicDienTu(this);

        dienTuList = new ArrayList<>();

        presenterLogicDienTu.LayDanhSachDienTu();
        presenterLogicDienTu.LayDanhSachLogoThuongHieu();
        presenterLogicDienTu.LayDanhSachSanPhamMoi();

        return view;
    }

    @Override
    public void HienThiDanhSach(List<DienTu> dienTus) {

        dienTuList = dienTus;

        AdapterDienTu adapterDienTu = new AdapterDienTu(getContext(), dienTuList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterDienTu);

        adapterDienTu.notifyDataSetChanged();

    }

    @Override
    public void HienThiLogoThuongHieu(List<ThuongHieu> thuongHieus) {
        AdapterThuongHieuLonDienTu adapterThuongHieuLonDienTu = new AdapterThuongHieuLonDienTu(getContext(), thuongHieus);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false);
        recyclerTopCacThuongHieuLon.setLayoutManager(layoutManager);
        recyclerTopCacThuongHieuLon.setAdapter(adapterThuongHieuLonDienTu);
        adapterThuongHieuLonDienTu.notifyDataSetChanged();
    }

    @Override
    public void LoiLayDuLieu() {

    }

    @Override
    public void HienThiSanPhamMoiVe(List<SanPham> sanPhams) {
        AdapterTopDienThoaiDienTu adapterTopDienThoaiDienTu = new AdapterTopDienThoaiDienTu(getContext(), R.layout.custom_layout_topdienthoaivamaytinhbang, sanPhams);

        RecyclerView.LayoutManager layoutManagerTop = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerHangMoiVe.setLayoutManager(layoutManagerTop);
        recyclerHangMoiVe.setAdapter(adapterTopDienThoaiDienTu);

        adapterTopDienThoaiDienTu.notifyDataSetChanged();

        Random random = new Random();

        int vitri = random.nextInt(sanPhams.size());
        Picasso.get().load(sanPhams.get(vitri).getANHLON()).fit().centerInside().into(imgSanPham1);
        txtSanPham1.setText(sanPhams.get(vitri).getTENSP());

        int vitri1 = random.nextInt(sanPhams.size());
        Picasso.get().load(sanPhams.get(vitri1).getANHLON()).fit().centerInside().into(imgSanPham2);
        txtSanPham2.setText(sanPhams.get(vitri1).getTENSP());

        int vitri2 = random.nextInt(sanPhams.size());
        Picasso.get().load(sanPhams.get(vitri2).getANHLON()).fit().centerInside().into(imgSanPham3);
        txtSanPham3.setText(sanPhams.get(vitri2).getTENSP());

    }
}
