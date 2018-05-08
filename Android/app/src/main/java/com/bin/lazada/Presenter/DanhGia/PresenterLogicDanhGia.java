package com.bin.lazada.Presenter.DanhGia;

import android.view.View;
import android.widget.ProgressBar;

import com.bin.lazada.Model.DanhGia.ModelDanhGia;
import com.bin.lazada.ObjectClass.DanhGia;
import com.bin.lazada.View.DanhGia.ViewDanhGia;

import java.util.List;

public class PresenterLogicDanhGia implements IPresenterDanhGia {

    ViewDanhGia viewDanhGia;
    ModelDanhGia modelDanhGia;

    public PresenterLogicDanhGia(ViewDanhGia viewDanhGia) {
        this.viewDanhGia = viewDanhGia;
        modelDanhGia = new ModelDanhGia();
    }

    @Override
    public void ThemDanhGia(DanhGia danhGia) {
        boolean kiemtra = modelDanhGia.ThemDanhGia(danhGia);
        if(kiemtra) {
            viewDanhGia.DanhGiaThanhCong();
        }else {
            viewDanhGia.DanhGiaThatBai();
        }
    }

    @Override
    public void LayDanhSachDanhGiaCuaSanPham(int masp, int limit, ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        List<DanhGia> danhGiaList = modelDanhGia.LayDanhSachDanhGiaCuaSanPham(masp, limit);

        if(danhGiaList.size() > 0) {
            progressBar.setVisibility(View.GONE);
            viewDanhGia.HienThiDanhSachDanhGiaTheoSanPham(danhGiaList);
        }
    }
}
