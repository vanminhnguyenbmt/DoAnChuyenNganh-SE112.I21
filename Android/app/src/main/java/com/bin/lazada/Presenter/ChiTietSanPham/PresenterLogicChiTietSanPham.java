package com.bin.lazada.Presenter.ChiTietSanPham;

import android.content.Context;

import com.bin.lazada.Model.ChiTietSanPham.ModelChiTietSanPham;
import com.bin.lazada.Model.GioHang.ModelGioHang;
import com.bin.lazada.ObjectClass.DanhGia;
import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.View.ChiTietSanPham.ViewChiTietSanPham;

import java.util.List;

public class PresenterLogicChiTietSanPham implements IPresenterChiTietSanPham {

    ViewChiTietSanPham viewChiTietSanPham;
    ModelChiTietSanPham modelChiTietSanPham;
    ModelGioHang modelGioHang;

    public PresenterLogicChiTietSanPham() {
        modelChiTietSanPham = new ModelChiTietSanPham();
        modelGioHang = new ModelGioHang();
    }

    public PresenterLogicChiTietSanPham(ViewChiTietSanPham viewChiTietSanPham) {
        this.viewChiTietSanPham = viewChiTietSanPham;
        modelChiTietSanPham = new ModelChiTietSanPham();
        modelGioHang = new ModelGioHang();
    }

    @Override
    public void LayChiTietSanPham(int masp) {
        SanPham sanPham = modelChiTietSanPham.LayChiTietSanPham("LaySanPhamVaChiTietTheoMaSP", "CHITIETSANPHAM", masp);
        if(sanPham.getMASP() > 0) {
            String[] linkhinhanh = sanPham.getANHNHO().split(",");
            viewChiTietSanPham.HienThiSliderSanPham(linkhinhanh);
            viewChiTietSanPham.HienThiChiTietSanPham(sanPham);
        }
    }

    @Override
    public void LayDanhSachDanhGiaCuaSanPham(int masp, int limit) {
        List<DanhGia> danhGias = modelChiTietSanPham.LayDanhSachDanhGiaCuaSanPham(masp, limit);

        if(danhGias.size() > 0) {
            viewChiTietSanPham.HienThiDanhGia(danhGias);
        }
    }

    @Override
    public void ThemGioHang(SanPham sanPham, Context context) {
        modelGioHang.MoKetNoiSQL(context);
        boolean kiemtra = modelGioHang.ThemGioHang(sanPham);
        if(kiemtra) {
            viewChiTietSanPham.ThemGioHangThanhCong();
        }else {
            viewChiTietSanPham.ThemGioHangThatBai();
        }
    }

    public int DemSanPhamCoTrongGioHang(Context context) {
        modelGioHang.MoKetNoiSQL(context);
        List<SanPham> sanPhamList = modelGioHang.LayDanhSachSanPhamTrongGioHang();
        int dem = sanPhamList.size();
        return dem;
    }
}
