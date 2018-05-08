package com.bin.lazada.View.ChiTietSanPham;

import com.bin.lazada.ObjectClass.DanhGia;
import com.bin.lazada.ObjectClass.SanPham;

import java.util.List;

public interface ViewChiTietSanPham {
    void HienThiChiTietSanPham(SanPham sanPham);
    void HienThiSliderSanPham(String[] linkhinhsanpham);
    void HienThiDanhGia(List<DanhGia> danhGiaList);
    void ThemGioHangThanhCong();
    void ThemGioHangThatBai();
}
