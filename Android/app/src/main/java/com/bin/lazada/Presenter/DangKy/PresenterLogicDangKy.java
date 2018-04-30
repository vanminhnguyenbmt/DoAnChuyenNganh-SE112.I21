package com.bin.lazada.Presenter.DangKy;

import com.bin.lazada.Model.DangNhap_DangKy.ModelDangKy;
import com.bin.lazada.ObjectClass.NhanVien;
import com.bin.lazada.View.DangNhap_DangKy.ViewDangKy;

public class PresenterLogicDangKy implements IPresenterDangKy {
    ViewDangKy viewDangKy;
    ModelDangKy modelDangKy;

    public PresenterLogicDangKy(ViewDangKy viewDangKy) {
        this.viewDangKy = viewDangKy;
        modelDangKy = new ModelDangKy();
    }

    @Override
    public void ThucHienDangKy(NhanVien nhanVien) {
        Boolean kiemtra = modelDangKy.DangKyThanhVien(nhanVien);
        if(kiemtra) {
            viewDangKy.DangKyThanhCong();
        }else {
            viewDangKy.DangKyThatBai();
        }
    }
}
