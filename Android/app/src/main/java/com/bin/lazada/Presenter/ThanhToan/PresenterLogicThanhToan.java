package com.bin.lazada.Presenter.ThanhToan;

import android.content.Context;

import com.bin.lazada.Model.GioHang.ModelGioHang;
import com.bin.lazada.Model.ThanhToan.ModelThanhToan;
import com.bin.lazada.ObjectClass.HoaDon;
import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.View.ThanhToan.ViewThanhToan;

import java.util.List;

public class PresenterLogicThanhToan implements IPresenterThanhToan {

    ViewThanhToan viewThanhToan;
    ModelThanhToan modelThanhToan;
    ModelGioHang modelGioHang;
    List<SanPham> sanPhamList;

    public PresenterLogicThanhToan(ViewThanhToan viewThanhToan, Context context) {
        this.viewThanhToan = viewThanhToan;
        modelThanhToan = new ModelThanhToan();
        modelGioHang = new ModelGioHang();
        modelGioHang.MoKetNoiSQL(context);
    }

    @Override
    public void ThemHoaDon(HoaDon hoaDon) {
        boolean kiemtra = modelThanhToan.ThemHoaDon(hoaDon);
        if(kiemtra) {
            viewThanhToan.DatHangThanhCong();

            //sau khi đặt hàng thành công thì tiến hành xóa sản phẩm trong giỏ hàng
            int dem = sanPhamList.size();
            for (int i = 0; i < dem; i++) {
                modelGioHang.XoaSanPhamTrongGioHang(sanPhamList.get(i).getMASP());
            }

        }else {
            viewThanhToan.DatHangThatBai();
        }
    }

    @Override
    public void LayDanhSachSanPhamTrongGioHang() {
        sanPhamList = modelGioHang.LayDanhSachSanPhamTrongGioHang();
        viewThanhToan.LayDanhSachSanPhamTrongGioHang(sanPhamList);
    }
}
