package com.bin.lazada.View.TrangChu.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bin.lazada.Adapter.AdapterNoiBat;
import com.bin.lazada.Adapter.AdapterThuongHieuLonDienTu;
import com.bin.lazada.Adapter.AdapterTopDienThoaiDienTu;
import com.bin.lazada.ObjectClass.DienTu;
import com.bin.lazada.ObjectClass.KhuyenMai;
import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.ObjectClass.ThuongHieu;
import com.bin.lazada.Presenter.KhuyenMai.PresenterLogicKhuyenMai;
import com.bin.lazada.Presenter.TrangChu_DienTu.PresenterLogicDienTu;
import com.bin.lazada.R;
import com.bin.lazada.View.ChiTietSanPham.ChiTietSanPhamActivity;
import com.bin.lazada.View.HienThiSanPhamTheoDanhMuc.HienThiSanPhamTheoDanhMucActivity;
import com.bin.lazada.View.TrangChu.ViewDienTu;
import com.bin.lazada.View.TrangChu.ViewKhuyenMai;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FragmentNoiBat extends Fragment implements ViewDienTu, ViewKhuyenMai, View.OnClickListener{

    RecyclerView recyclerTopCacThuongHieuLon, recyclerHangMoiVe;
    List<DienTu> dienTuList;
    PresenterLogicDienTu presenterLogicDienTu;
    PresenterLogicKhuyenMai presenterLogicKhuyenMai;
    ImageView imgSanPham1, imgSanPham2, imgSanPham3, imgKhuyenMaiDienTu, imgNoiBat1, imgNoiBat2, imgNoiBat3, imgNoiBat4;
    TextView txtSanPham1, txtSanPham2, txtSanPham3;
    NestedScrollView nestedScrollView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_noibat, container, false);

        nestedScrollView = (NestedScrollView) view.findViewById(R.id.nestedScrollView);
        recyclerTopCacThuongHieuLon = (RecyclerView) view.findViewById(R.id.recyclerTopThuongHieuLon);
        recyclerHangMoiVe = (RecyclerView) view.findViewById(R.id.recyclerHangMoiVe);
//        ViewCompat.setNestedScrollingEnabled(recyclerTopCacThuongHieuLon, false);
        ViewCompat.setNestedScrollingEnabled(recyclerHangMoiVe, false);

        imgSanPham1 = (ImageView) view.findViewById(R.id.imgSanPham1);
        imgSanPham2 = (ImageView) view.findViewById(R.id.imgSanPham2);
        imgSanPham3 = (ImageView) view.findViewById(R.id.imgSanPham3);
        imgKhuyenMaiDienTu = (ImageView) view.findViewById(R.id.imgKhuyenMaiDienTu);
        imgNoiBat1 = (ImageView) view.findViewById(R.id.imgNoiBat1);
        imgNoiBat2 = (ImageView) view.findViewById(R.id.imgNoiBat2);
        imgNoiBat3 = (ImageView) view.findViewById(R.id.imgNoiBat3);
        imgNoiBat4 = (ImageView) view.findViewById(R.id.imgNoiBat4);

        txtSanPham1 = (TextView) view.findViewById(R.id.txtSanPham1);
        txtSanPham2 = (TextView) view.findViewById(R.id.txtSanPham2);
        txtSanPham3 = (TextView) view.findViewById(R.id.txtSanPham3);

        presenterLogicDienTu = new PresenterLogicDienTu(this);
        dienTuList = new ArrayList<>();

        presenterLogicDienTu.LayDanhSachLogoThuongHieu();
        presenterLogicDienTu.LayDanhSachSanPhamMoi();

        presenterLogicKhuyenMai = new PresenterLogicKhuyenMai(this);
        presenterLogicKhuyenMai.LayDanhSachKhuyenMai();

//        nestedScrollView.smoothScrollTo(0, 0);

        imgNoiBat1.setOnClickListener(this);
        imgNoiBat2.setOnClickListener(this);
        imgNoiBat3.setOnClickListener(this);
        imgNoiBat4.setOnClickListener(this);

        return view;
    }

    @Override
    public void HienThiDanhSach(List<DienTu> dienTus) {

    }

    @Override
    public void HienThiLogoThuongHieu(List<ThuongHieu> thuongHieus) {
        AdapterThuongHieuLonDienTu adapterThuongHieuLonDienTu = new AdapterThuongHieuLonDienTu(getContext(), thuongHieus);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false);
        recyclerTopCacThuongHieuLon.setLayoutManager(layoutManager);
        recyclerTopCacThuongHieuLon.setAdapter(adapterThuongHieuLonDienTu);
        adapterThuongHieuLonDienTu.notifyDataSetChanged();
    }

    @Override
    public void LoiLayDuLieu() {

    }

    @Override
    public void HienThiSanPhamMoiVe(final List<SanPham> sanPhams) {
        AdapterTopDienThoaiDienTu adapterTopDienThoaiDienTu = new AdapterTopDienThoaiDienTu(getContext(), R.layout.custom_layout_topdienthoaivamaytinhbang, sanPhams);

        RecyclerView.LayoutManager layoutManagerTop = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerHangMoiVe.setLayoutManager(layoutManagerTop);
        recyclerHangMoiVe.setAdapter(adapterTopDienThoaiDienTu);
        adapterTopDienThoaiDienTu.notifyDataSetChanged();

        Random random = new Random();

        final int vitri = random.nextInt(sanPhams.size());
        Picasso.get().load(sanPhams.get(vitri).getANHLON()).fit().centerInside().into(imgSanPham1);
        txtSanPham1.setText(sanPhams.get(vitri).getTENSP());

        final int vitri1 = random.nextInt(sanPhams.size());
        Picasso.get().load(sanPhams.get(vitri1).getANHLON()).fit().centerInside().into(imgSanPham2);
        txtSanPham2.setText(sanPhams.get(vitri1).getTENSP());

        final int vitri2 = random.nextInt(sanPhams.size());
        Picasso.get().load(sanPhams.get(vitri2).getANHLON()).fit().centerInside().into(imgSanPham3);
        txtSanPham3.setText(sanPhams.get(vitri2).getTENSP());

        imgSanPham1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTietSanPham = new Intent(getContext(), ChiTietSanPhamActivity.class);
                iChiTietSanPham.putExtra("masp", sanPhams.get(vitri).getMASP());
                getContext().startActivity(iChiTietSanPham);
            }
        });

        imgSanPham2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTietSanPham = new Intent(getContext(), ChiTietSanPhamActivity.class);
                iChiTietSanPham.putExtra("masp", sanPhams.get(vitri1).getMASP());
                getContext().startActivity(iChiTietSanPham);
            }
        });

        imgSanPham3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTietSanPham = new Intent(getContext(), ChiTietSanPhamActivity.class);
                iChiTietSanPham.putExtra("masp", sanPhams.get(vitri2).getMASP());
                getContext().startActivity(iChiTietSanPham);
            }
        });
    }

    @Override
    public void HienThiDanhSachKhuyenMai(List<KhuyenMai> khuyenMaiList) {
        Random random = new Random();
        Picasso.get().load(khuyenMaiList.get(random.nextInt(khuyenMaiList.size())).getHINHKHUYENMAI()).resize(700, 200).into(imgKhuyenMaiDienTu);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.imgNoiBat1:
                FragmentManager fragmentManager = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                HienThiSanPhamTheoDanhMucActivity hienThiSanPhamTheoDanhMucActivity = new HienThiSanPhamTheoDanhMucActivity();

                Bundle bundle = new Bundle();
                bundle.putInt("MALOAI", 0);
                bundle.putInt("MALOAISP", 0);
                bundle.putBoolean("KIEMTRA", false);
                bundle.putString("TENLOAI", "Top 150 SKU Bán Chạy Trong Tháng");
                bundle.putString("CHECKADAPTER", "topsku");
                hienThiSanPhamTheoDanhMucActivity.setArguments(bundle);

                fragmentTransaction.addToBackStack("TrangChuActivity");
                fragmentTransaction.replace(R.id.themFragment, hienThiSanPhamTheoDanhMucActivity);
                fragmentTransaction.commit();

                break;

            case R.id.imgNoiBat2:
                FragmentManager fragmentManager2 = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();

                HienThiSanPhamTheoDanhMucActivity hienThiSanPhamTheoDanhMucActivity2 = new HienThiSanPhamTheoDanhMucActivity();

                Bundle bundle2 = new Bundle();
                bundle2.putInt("MALOAI", 0);
                bundle2.putInt("MALOAISP", 2);
                bundle2.putBoolean("KIEMTRA", false);
                bundle2.putString("TENLOAI", "Top SmartPhone Chất Lượng Giá Rẽ");
                bundle2.putString("CHECKADAPTER", "topsmartphone");
                hienThiSanPhamTheoDanhMucActivity2.setArguments(bundle2);

                fragmentTransaction2.addToBackStack("TrangChuActivity");
                fragmentTransaction2.replace(R.id.themFragment, hienThiSanPhamTheoDanhMucActivity2);
                fragmentTransaction2.commit();

                break;

            case R.id.imgNoiBat3:
                FragmentManager fragmentManager3 = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();

                HienThiSanPhamTheoDanhMucActivity hienThiSanPhamTheoDanhMucActivity3 = new HienThiSanPhamTheoDanhMucActivity();

                Bundle bundle3 = new Bundle();
                bundle3.putInt("MALOAI", 0);
                bundle3.putInt("MALOAISP", 53);
                bundle3.putBoolean("KIEMTRA", false);
                bundle3.putString("TENLOAI", "Top Thiết Bị Lưu Trữ");
                bundle3.putString("CHECKADAPTER", "topluutru");
                hienThiSanPhamTheoDanhMucActivity3.setArguments(bundle3);

                fragmentTransaction3.addToBackStack("TrangChuActivity");
                fragmentTransaction3.replace(R.id.themFragment, hienThiSanPhamTheoDanhMucActivity3);
                fragmentTransaction3.commit();

                break;

            case R.id.imgNoiBat4:
                FragmentManager fragmentManager4 = ((AppCompatActivity)getContext()).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction4 = fragmentManager4.beginTransaction();

                HienThiSanPhamTheoDanhMucActivity hienThiSanPhamTheoDanhMucActivity4 = new HienThiSanPhamTheoDanhMucActivity();

                Bundle bundle4 = new Bundle();
                bundle4.putInt("MALOAI", 0);
                bundle4.putInt("MALOAISP", 83);
                bundle4.putBoolean("KIEMTRA", false);
                bundle4.putString("TENLOAI", "Top Tivi Có Màn Hình Lớn");
                bundle4.putString("CHECKADAPTER", "toptivi");
                hienThiSanPhamTheoDanhMucActivity4.setArguments(bundle4);

                fragmentTransaction4.addToBackStack("TrangChuActivity");
                fragmentTransaction4.replace(R.id.themFragment, hienThiSanPhamTheoDanhMucActivity4);
                fragmentTransaction4.commit();

                break;
        }
    }
}
