package com.bin.lazada.View.DanhGia;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.bin.lazada.ObjectClass.DanhGia;
import com.bin.lazada.ObjectClass.NhanVien;
import com.bin.lazada.Presenter.DanhGia.PresenterLogicDanhGia;
import com.bin.lazada.R;

import java.util.List;

public class ThemDanhGiaActivity extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener, ViewDanhGia, View.OnClickListener {

    TextInputLayout input_edTieuDe, input_edNoiDung;
    EditText edTieuDe, edNoiDung;
    RatingBar rbDanhGia;
    int masp = 0;
    int sosao = 0;
    Button btnDongYDanhGia;
    PresenterLogicDanhGia presenterLogicDanhGia;
    NhanVien nhanVien;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themdanhgia);

        input_edTieuDe = (TextInputLayout) findViewById(R.id.input_edTieuDeDanhGia);
        input_edNoiDung = (TextInputLayout) findViewById(R.id.input_edNoiDungDanhGia);
        edTieuDe = (EditText) findViewById(R.id.edTieuDe);
        edNoiDung = (EditText) findViewById(R.id.edNoiDung);
        rbDanhGia = (RatingBar) findViewById(R.id.rbDanhGia);
        btnDongYDanhGia = (Button) findViewById(R.id.btnDongYDanhGia);

        masp = getIntent().getIntExtra("masp", 0);

        presenterLogicDanhGia = new PresenterLogicDanhGia(this);
        nhanVien = new NhanVien();

        rbDanhGia.setOnRatingBarChangeListener(this);
        btnDongYDanhGia.setOnClickListener(this);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        sosao = (int) rating;
    }

    @Override
    public void DanhGiaThanhCong() {
        Toast.makeText(this, "Đánh giá thành công !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void DanhGiaThatBai() {
        Toast.makeText(this, "Bạn không thể đánh giá sản phẩm này !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void HienThiDanhSachDanhGiaTheoSanPham(List<DanhGia> danhGiaList) {

    }

    @Override
    public void onClick(View v) {
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
//        String madg = telephonyManager.getDeviceId();

        String madg = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String tenthietbi = Build.MODEL;
        String tieude = edTieuDe.getText().toString();
        String noidung = edNoiDung.getText().toString();

        if(tieude.trim().length() > 0 ) {
            input_edTieuDe.setErrorEnabled(false);
            input_edTieuDe.setError("");
        }else {
            input_edTieuDe.setErrorEnabled(true);
            input_edTieuDe.setError("Bạn chưa nhập tiêu đề");
        }

        if(noidung.trim().length() > 0 ) {
            input_edNoiDung.setErrorEnabled(false);
            input_edNoiDung.setError("");
        }else {
            input_edNoiDung.setErrorEnabled(true);
            input_edNoiDung.setError("Bạn chưa nhập nội dung");
        }

        if(!input_edNoiDung.isErrorEnabled() && !input_edTieuDe.isErrorEnabled()) {
            DanhGia danhGia = new DanhGia();
            danhGia.setMADG(madg);
            danhGia.setMASP(masp);
            danhGia.setTIEUDE(tieude);
            danhGia.setNOIDUNG(noidung);
            danhGia.setSOSAO(sosao);
            danhGia.setTENTHIETBI(tenthietbi);

            presenterLogicDanhGia.ThemDanhGia(danhGia);
            finish();
        }
    }
}
