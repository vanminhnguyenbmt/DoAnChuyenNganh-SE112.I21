package com.bin.lazada.Presenter.HienThiSanPhamTheoDanhMuc;

public interface IPresenterHienThiSanPhamTheoDanhMuc {
    void LayDanhSachSanPhamTheoMaLoai(int masp, int maloaisp, boolean kiemtra);
    void LayDanhSachSanPhamTheoThuongHieu(int math);
    void LayDanhSachSanPhamBanChayTrongThang();
    void LayDanhSachSmartPhoneGiaRe(int maloaisp);
    void LayTopDanhSachThietBiLuuTru(int maloaisp);
    void LayTopDanhSachTiViManHinhLon(int maloaisp);
}
