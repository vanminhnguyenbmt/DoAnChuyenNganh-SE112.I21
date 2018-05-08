package com.bin.lazada.Presenter.DanhGia;

import android.widget.ProgressBar;

import com.bin.lazada.ObjectClass.DanhGia;

public interface IPresenterDanhGia {
    void ThemDanhGia(DanhGia danhGia);
    void LayDanhSachDanhGiaCuaSanPham(int masp, int limit, ProgressBar progressBar);
}
