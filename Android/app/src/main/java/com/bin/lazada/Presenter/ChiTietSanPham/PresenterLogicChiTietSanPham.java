package com.bin.lazada.Presenter.ChiTietSanPham;

import com.bin.lazada.Model.ChiTietSanPham.ModelChiTietSanPham;
import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.View.ChiTietSanPham.ViewChiTietSanPham;

public class PresenterLogicChiTietSanPham implements IPresenterChiTietSanPham {

    ViewChiTietSanPham viewChiTietSanPham;
    ModelChiTietSanPham modelChiTietSanPham;

    public PresenterLogicChiTietSanPham(ViewChiTietSanPham viewChiTietSanPham) {
        this.viewChiTietSanPham = viewChiTietSanPham;
        modelChiTietSanPham = new ModelChiTietSanPham();
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
}
