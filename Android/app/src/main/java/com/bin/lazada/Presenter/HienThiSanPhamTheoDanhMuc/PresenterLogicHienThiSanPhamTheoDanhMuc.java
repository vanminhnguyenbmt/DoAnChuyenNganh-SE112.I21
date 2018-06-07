package com.bin.lazada.Presenter.HienThiSanPhamTheoDanhMuc;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.bin.lazada.Model.HienThiSanPhamTheoDanhMuc.ModelHienThiSanPhamTheoDanhMuc;
import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.View.TrangChu.ViewHienThiSanPhamTheoDanhMuc;

import java.util.ArrayList;
import java.util.List;

public class PresenterLogicHienThiSanPhamTheoDanhMuc implements IPresenterHienThiSanPhamTheoDanhMuc {

    ViewHienThiSanPhamTheoDanhMuc viewHienThiSanPhamTheoDanhMuc;
    ModelHienThiSanPhamTheoDanhMuc modelHienThiSanPhamTheoDanhMuc;

    public PresenterLogicHienThiSanPhamTheoDanhMuc(ViewHienThiSanPhamTheoDanhMuc viewHienThiSanPhamTheoDanhMuc) {
        this.viewHienThiSanPhamTheoDanhMuc = viewHienThiSanPhamTheoDanhMuc;
        modelHienThiSanPhamTheoDanhMuc = new ModelHienThiSanPhamTheoDanhMuc();
    }

    @Override
    public void LayDanhSachSanPhamTheoMaLoai(int masp, int maloaisp, boolean kiemtra) {
        List<SanPham> sanPhamList = new ArrayList<>();
        if(kiemtra) {
            sanPhamList = modelHienThiSanPhamTheoDanhMuc.LayDanhSachSanPhamTheoMaLoai(masp, maloaisp,"LayDanhSachSanPhamTheoMaThuongHieu","DANHSACHSANPHAM", 0);
        }else {
            sanPhamList = modelHienThiSanPhamTheoDanhMuc.LayDanhSachSanPhamTheoMaLoai(masp, maloaisp,"LayDanhSachSanPhamTheoMaLoaiDanhMuc","DANHSACHSANPHAM", 0);
        }

        if(sanPhamList.size() > 0 ) {
            viewHienThiSanPhamTheoDanhMuc.HienThiDanhSachSanPham(sanPhamList);
        }else {
            viewHienThiSanPhamTheoDanhMuc.LoiHienThiDanhSachSanPham();
        }
    }

    @Override
    public void LayDanhSachSanPhamTheoThuongHieu(int math) {
        List<SanPham> sanPhamList = new ArrayList<>();
        sanPhamList = modelHienThiSanPhamTheoDanhMuc.LayDanhSachSanPhamTheoThuongHieu(math, "LayDanhSachSanPhamTheoThuongHieu", "DANHSACHSANPHAM", 0);

        if (sanPhamList.size() > 0) {
            viewHienThiSanPhamTheoDanhMuc.HienThiDanhSachSanPham(sanPhamList);
        }else {
            viewHienThiSanPhamTheoDanhMuc.LoiHienThiDanhSachSanPham();
        }
    }

    @Override
    public void LayDanhSachSanPhamBanChayTrongThang() {
        List<SanPham> sanPhamList = new ArrayList<>();
        sanPhamList = modelHienThiSanPhamTheoDanhMuc.LayDanhSachSanPhamBanChayTrongThang("LayTopSanPhamTrongThang", "DANHSACHSANPHAM", 0);

        if (sanPhamList.size() > 0) {
            viewHienThiSanPhamTheoDanhMuc.HienThiDanhSachSanPham(sanPhamList);
        }else {
            viewHienThiSanPhamTheoDanhMuc.LoiHienThiDanhSachSanPham();
        }
    }

    @Override
    public void LayDanhSachSmartPhoneGiaRe(int maloaisp) {
        List<SanPham> sanPhamList = new ArrayList<>();
        sanPhamList = modelHienThiSanPhamTheoDanhMuc.LayDanhSachSmartPhoneGiaRe(maloaisp,"LayDanhSachSmartPhoneGiaRe", "DANHSACHSANPHAM", 0);

        if (sanPhamList.size() > 0) {
            viewHienThiSanPhamTheoDanhMuc.HienThiDanhSachSanPham(sanPhamList);
        }else {
            viewHienThiSanPhamTheoDanhMuc.LoiHienThiDanhSachSanPham();
        }
    }

    @Override
    public void LayTopDanhSachThietBiLuuTru(int maloaisp) {
        List<SanPham> sanPhamList = new ArrayList<>();
        sanPhamList = modelHienThiSanPhamTheoDanhMuc.LayTopDanhSachThietBiLuuTru(maloaisp,"LayTopDanhSachThietBiLuuTru", "DANHSACHSANPHAM", 0);

        if (sanPhamList.size() > 0) {
            viewHienThiSanPhamTheoDanhMuc.HienThiDanhSachSanPham(sanPhamList);
        }else {
            viewHienThiSanPhamTheoDanhMuc.LoiHienThiDanhSachSanPham();
        }
    }

    @Override
    public void LayTopDanhSachTiViManHinhLon(int maloaisp) {
        List<SanPham> sanPhamList = new ArrayList<>();
        sanPhamList = modelHienThiSanPhamTheoDanhMuc.LayTopDanhSachTiViManHinhLon(maloaisp,"LayTopDanhSachTiViManHinhLon", "DANHSACHSANPHAM", 0);

        if (sanPhamList.size() > 0) {
            viewHienThiSanPhamTheoDanhMuc.HienThiDanhSachSanPham(sanPhamList);
        }else {
            viewHienThiSanPhamTheoDanhMuc.LoiHienThiDanhSachSanPham();
        }
    }

    public List<SanPham> LayTopDanhSachTiViManHinhLonLoadMore(int maloaisp, int limit) {
        List<SanPham> sanPhamList = new ArrayList<>();
        sanPhamList = modelHienThiSanPhamTheoDanhMuc.LayTopDanhSachTiViManHinhLon(maloaisp,"LayTopDanhSachTiViManHinhLon", "DANHSACHSANPHAM", limit);

        return  sanPhamList;
    }

    public List<SanPham> LayTopDanhSachThietBiLuuTruLoadMore(int maloaisp, int limit) {
        List<SanPham> sanPhamList = new ArrayList<>();
        sanPhamList = modelHienThiSanPhamTheoDanhMuc.LayTopDanhSachThietBiLuuTru(maloaisp,"LayTopDanhSachThietBiLuuTru", "DANHSACHSANPHAM", limit);

        return  sanPhamList;
    }

    public List<SanPham> LayDanhSachSmartPhoneGiaReLoadMore(int maloaisp, int limit) {
        List<SanPham> sanPhamList = new ArrayList<>();
        sanPhamList = modelHienThiSanPhamTheoDanhMuc.LayDanhSachSmartPhoneGiaRe(maloaisp,"LayDanhSachSmartPhoneGiaRe", "DANHSACHSANPHAM", limit);

        return  sanPhamList;
    }

    public List<SanPham> LayDanhSachSanPhamBanChayTrongThangLoadMore(int limit) {
        List<SanPham> sanPhamList = new ArrayList<>();
        sanPhamList = modelHienThiSanPhamTheoDanhMuc.LayDanhSachSanPhamBanChayTrongThang( "LayTopSanPhamTrongThang", "DANHSACHSANPHAM", limit);

        return  sanPhamList;
    }

    public List<SanPham> LayDanhSachSanPhamTheoThuongHieuLoadMore(int math, int limit) {
        List<SanPham> sanPhamList = new ArrayList<>();
        sanPhamList = modelHienThiSanPhamTheoDanhMuc.LayDanhSachSanPhamTheoThuongHieu(math, "LayDanhSachSanPhamTheoThuongHieu", "DANHSACHSANPHAM", limit);

        return  sanPhamList;
    }

    public List<SanPham> LayDanhSachSanPhamTheoMaLoaiLoadMore(int masp, int maloaisp, boolean kiemtra, int limit, ProgressBar progressBar) {
//        progressBar.setVisibility(View.VISIBLE);
        List<SanPham> sanPhamList = new ArrayList<>();

        if(kiemtra) {
            sanPhamList = modelHienThiSanPhamTheoDanhMuc.LayDanhSachSanPhamTheoMaLoai(masp, maloaisp,"LayDanhSachSanPhamTheoMaThuongHieu","DANHSACHSANPHAM", limit);
        }else {
            sanPhamList = modelHienThiSanPhamTheoDanhMuc.LayDanhSachSanPhamTheoMaLoai(masp, maloaisp,"LayDanhSachSanPhamTheoMaLoaiDanhMuc","DANHSACHSANPHAM", limit);
        }

//        if(sanPhamList.size() > 0) {
//            progressBar.setVisibility(View.GONE);
//        }

        return sanPhamList;
    }
}
