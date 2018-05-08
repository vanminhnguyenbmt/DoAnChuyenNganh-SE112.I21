package com.bin.lazada.Presenter.ChiTietSanPham;

import android.content.Context;

import com.bin.lazada.ObjectClass.SanPham;

public interface IPresenterChiTietSanPham {
    void LayChiTietSanPham(int masp);
    void LayDanhSachDanhGiaCuaSanPham(int masp, int limit);
    void ThemGioHang(SanPham sanPham, Context context);
}
