package com.bin.lazada.Presenter.TimKiem;

import com.bin.lazada.Model.TimKiem.ModelTimKiem;
import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.View.TimKiem.ViewTimKiem;

import java.util.ArrayList;
import java.util.List;

public class PresenterLogicTimKiem implements IPresenterTimKiem {

    ViewTimKiem viewTimKiem;
    ModelTimKiem modelTimKiem;

    public PresenterLogicTimKiem(ViewTimKiem viewTimKiem) {
        this.viewTimKiem = viewTimKiem;
        modelTimKiem = new ModelTimKiem();
    }

    @Override
    public void TimKiemSanPhamTheoTenSP(String tensp, int limit) {
        List<SanPham> sanPhamList = modelTimKiem.LayDanhSachSanPhamTheoTenSP(tensp, "TimKiemSanPhamTheoTenSP", "DANHSACHSANPHAM", limit);

        if(sanPhamList.size() > 0) {
            viewTimKiem.TimKiemThanhCong(sanPhamList);
        }else {
            viewTimKiem.TimKiemThatBai();
        }
    }

    public List<SanPham> TimKiemSanPhamTheoTenSPLoadMore(String tensp, int limit) {
        List<SanPham> sanPhamList = new ArrayList<>();
        sanPhamList = modelTimKiem.LayDanhSachSanPhamTheoTenSP(tensp, "TimKiemSanPhamTheoTenSP", "DANHSACHSANPHAM", limit);

        return  sanPhamList;
    }
}
