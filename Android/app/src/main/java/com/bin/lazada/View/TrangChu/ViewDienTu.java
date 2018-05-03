package com.bin.lazada.View.TrangChu;

import com.bin.lazada.ObjectClass.DienTu;
import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.ObjectClass.ThuongHieu;

import java.util.List;

public interface ViewDienTu {
    void HienThiDanhSach(List<DienTu> dienTus);
    void HienThiLogoThuongHieu(List<ThuongHieu> thuongHieus);
    void LoiLayDuLieu();
    void HienThiSanPhamMoiVe(List<SanPham> sanPhams);
}
