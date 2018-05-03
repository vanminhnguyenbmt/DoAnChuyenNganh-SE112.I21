package com.bin.lazada.ObjectClass;

import android.widget.ImageView;

import java.util.List;

public class DienTu {
    List<ThuongHieu> thuongHieus;
    List<SanPham> sanPhams;
    String tenNoiBat, tenTopNoiBat;

    public String getTenNoiBat() {
        return tenNoiBat;
    }

    public void setTenNoiBat(String tenNoiBat) {
        this.tenNoiBat = tenNoiBat;
    }

    public String getTenTopNoiBat() {
        return tenTopNoiBat;
    }

    public void setTenTopNoiBat(String tenTopNoiBat) {
        this.tenTopNoiBat = tenTopNoiBat;
    }

    public List<ThuongHieu> getThuongHieus() {
        return thuongHieus;
    }

    public void setThuongHieus(List<ThuongHieu> thuongHieus) {
        this.thuongHieus = thuongHieus;
    }

    public List<SanPham> getSanPhams() {
        return sanPhams;
    }

    public void setSanPhams(List<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
    }
}
