package com.bin.lazada.View.ThanhToan;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bin.lazada.ObjectClass.ChiTietHoaDon;
import com.bin.lazada.ObjectClass.HoaDon;
import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.Presenter.ThanhToan.PresenterLogicThanhToan;
import com.bin.lazada.R;
import com.bin.lazada.View.TrangChu.TrangChuActivity;

import java.util.ArrayList;
import java.util.List;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener, ViewThanhToan{

    Toolbar toolbar;
    EditText edTenNguoiNhan, edDiaChi, edSoDT;
    ImageButton imgNhanTienKhiGiaoHang, imgChuyenKhoan;
    TextView txtNhanTienKhiGiaoHang, txtChuyenKhoan;
    Button btnThanhToan;
    CheckBox cbThoaThuan;
    PresenterLogicThanhToan presenterLogicThanhToan;
    List<ChiTietHoaDon> chiTietHoaDons = new ArrayList<>();
    int chonHinhThuc = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanhtoan);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        edTenNguoiNhan = (EditText) findViewById(R.id.edTenNguoiNhan);
        edDiaChi = (EditText) findViewById(R.id.edDiaChi);
        edSoDT = (EditText) findViewById(R.id.edSoDT);
        imgNhanTienKhiGiaoHang = (ImageButton) findViewById(R.id.imgNhanTienKhiGiaoHang);
        imgChuyenKhoan = (ImageButton) findViewById(R.id.imgChuyenKhoan);
        btnThanhToan = (Button) findViewById(R.id.btnThanhToan);
        cbThoaThuan = (CheckBox) findViewById(R.id.cbThoaThuan);
        txtNhanTienKhiGiaoHang = (TextView) findViewById(R.id.txtNhanTienKhiGiaoHang);
        txtChuyenKhoan = (TextView) findViewById(R.id.txtChuyenKhoan);

        presenterLogicThanhToan = new PresenterLogicThanhToan(this, this);
        presenterLogicThanhToan.LayDanhSachSanPhamTrongGioHang();

        toolbar.setTitleTextColor(getIdColor(R.color.colorWhite));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnThanhToan.setOnClickListener(this);
        imgNhanTienKhiGiaoHang.setOnClickListener(this);
        imgChuyenKhoan.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private int getIdColor(int idcolor) {
        int color = 0;
        if(Build.VERSION.SDK_INT > 21) {
            color = ContextCompat.getColor(this, idcolor);
        }else {
            color = getResources().getColor(idcolor);
        }
        return color;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnThanhToan:
                String tennguoinhan = edTenNguoiNhan.getText().toString();
                String sodt = edSoDT.getText().toString();
                String diachi = edDiaChi.getText().toString();

                if(tennguoinhan.trim().length() > 0 && sodt.trim().length() > 0 && diachi.trim().length() > 0) {
                    if(cbThoaThuan.isChecked()) {
                        HoaDon hoaDon = new HoaDon();
                        hoaDon.setTENNGUOINHAN(tennguoinhan);
                        hoaDon.setSODT(sodt);
                        hoaDon.setDIACHI(diachi);
                        hoaDon.setCHUYENKHOAN(chonHinhThuc);
                        hoaDon.setChiTietHoaDonList(chiTietHoaDons);

                        presenterLogicThanhToan.ThemHoaDon(hoaDon);
                    }else {
                        Toast.makeText(this, "Bạn chưa nhấn chọn vào ô thỏa thuận !", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "Bạn chưa nhập đầy đủ thông tin !", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.imgNhanTienKhiGiaoHang:
                ChonHinhThucGiaoHang(txtNhanTienKhiGiaoHang, txtChuyenKhoan);
                chonHinhThuc = 0;
                break;

            case R.id.imgChuyenKhoan:
                ChonHinhThucGiaoHang(txtChuyenKhoan, txtNhanTienKhiGiaoHang);
                chonHinhThuc = 1;
                break;
        }
    }

    private void ChonHinhThucGiaoHang(TextView txtDuocChon, TextView txtHuyChon) {
        txtDuocChon.setTextColor(getIdColor(R.color.colorFacebook));
        txtHuyChon.setTextColor(getIdColor(R.color.colorBlack));
    }

    @Override
    public void DatHangThanhCong() {
        Toast.makeText(this, "Đặt hàng thành công !", Toast.LENGTH_SHORT).show();
        Intent iTrangChu = new Intent(ThanhToanActivity.this, TrangChuActivity.class);
        startActivity(iTrangChu);
    }

    @Override
    public void DatHangThatBai() {
        Toast.makeText(this, "Đặt hàng thất bại !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LayDanhSachSanPhamTrongGioHang(List<SanPham> sanPhamList) {

        for(int i = 0; i < sanPhamList.size(); i++) {
            ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
            chiTietHoaDon.setMASP(sanPhamList.get(i).getMASP());
            chiTietHoaDon.setSOLUONG(sanPhamList.get(i).getSOLUONG());

            chiTietHoaDons.add(chiTietHoaDon);
        }
    }
}
