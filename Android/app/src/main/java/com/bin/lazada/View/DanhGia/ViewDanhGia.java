package com.bin.lazada.View.DanhGia;

import com.bin.lazada.ObjectClass.DanhGia;

import java.util.List;

public interface ViewDanhGia {
    void DanhGiaThanhCong();
    void DanhGiaThatBai();
    void HienThiDanhSachDanhGiaTheoSanPham(List<DanhGia> danhGiaList);
}
