package com.bin.lazada.View.DangNhap_DangKy.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.bin.lazada.ObjectClass.NhanVien;
import com.bin.lazada.Presenter.DangKy.PresenterLogicDangKy;
import com.bin.lazada.R;
import com.bin.lazada.View.DangNhap_DangKy.ViewDangKy;

public class FragmentDangKy extends Fragment implements ViewDangKy, View.OnClickListener, View.OnFocusChangeListener{

    PresenterLogicDangKy presenterLogicDangKy;
    Button btnDangKy;
    EditText edHoTen, edMatKhau, edNhapLaiMatKhau, edDiaChiEmail;
    SwitchCompat sEmailDocQuyen;
    TextInputLayout input_edHoTen, input_edMatKhau, input_edNhapLaiMatKhau, input_edDiaChiEmail;
    Boolean kiemtrathongtin = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_dangky, container, false);

        btnDangKy = (Button) view.findViewById(R.id.btnDangKy);
        edHoTen = (EditText) view.findViewById(R.id.edHoTenDk);
        edMatKhau = (EditText) view.findViewById(R.id.edMatKhauDK);
        edNhapLaiMatKhau = (EditText) view.findViewById(R.id.edNhapLaiMatKhauDK);
        edDiaChiEmail = (EditText) view.findViewById(R.id.edDiaChiEmailDK);
        sEmailDocQuyen = (SwitchCompat) view.findViewById(R.id.sEmailDocQuyen);
        input_edHoTen = (TextInputLayout) view.findViewById(R.id.input_edHoTenDK);
        input_edMatKhau = (TextInputLayout) view.findViewById(R.id.input_edMatKhauDK);
        input_edNhapLaiMatKhau = (TextInputLayout) view.findViewById(R.id.input_edNhapLaiMatKhauDK);
        input_edDiaChiEmail = (TextInputLayout) view.findViewById(R.id.input_edDiaChiEmailDK);

        presenterLogicDangKy = new PresenterLogicDangKy(this);

        btnDangKy.setOnClickListener(this);
        edHoTen.setOnFocusChangeListener(this);
        edNhapLaiMatKhau.setOnFocusChangeListener(this);
        edDiaChiEmail.setOnFocusChangeListener(this);


        return view;
    }

    @Override
    public void DangKyThanhCong() {
        Toast.makeText(getActivity(), "Đăng ký thành công !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void DangKyThatBai() {
        Toast.makeText(getActivity(), "Đăng ký thất bại !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnDangKy:
                btnDangKy();
                break;
        }
    }

    String emaildocquyen = "";
    private void btnDangKy() {
        String hoten = edHoTen.getText().toString();
        String email = edDiaChiEmail.getText().toString();
        String matkhau = edMatKhau.getText().toString();
        String nhaplaimatkhau = edNhapLaiMatKhau.getText().toString();
        sEmailDocQuyen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                emaildocquyen = isChecked + "";
            }
        });

        if(kiemtrathongtin) {
            NhanVien nhanVien = new NhanVien();
            nhanVien.setTenNV(hoten);
            nhanVien.setTenDN(email);
            nhanVien.setMatKhau(matkhau);
            nhanVien.setEmailDocQuyen(emaildocquyen);
            nhanVien.setMaLoaiNV(2);

            presenterLogicDangKy.ThucHienDangKy(nhanVien);
        }else {

        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        int id = v.getId();
        switch (id) {
            case R.id.edHoTenDk:
                if(!hasFocus) {
                    String chuoi = ((EditText) v).getText().toString();
                    if(chuoi.trim().equals("") || chuoi.equals(null)) {
                        input_edHoTen.setErrorEnabled(true);
                        input_edHoTen.setError("Vui lòng nhập vào họ và tên !");
                        kiemtrathongtin = false;
                    }else {
                        input_edHoTen.setErrorEnabled(false);
                        input_edHoTen.setError("");
                        kiemtrathongtin = true;
                    }
                }
                break;
            case R.id.edDiaChiEmailDK:
                if(!hasFocus) {
                    String chuoi = ((EditText) v).getText().toString();
                    Boolean kiemtraemail = Patterns.EMAIL_ADDRESS.matcher(chuoi).matches();

                    if(chuoi.trim().equals("") || chuoi.equals(null)) {
                        input_edDiaChiEmail.setErrorEnabled(true);
                        input_edDiaChiEmail.setError("Vui lòng nhập vào email !");
                        kiemtrathongtin = false;
                    }else {
                        if(!kiemtraemail) {
                            input_edDiaChiEmail.setErrorEnabled(true);
                            input_edDiaChiEmail.setError("Nhập sai định dạng email !");
                            kiemtrathongtin = false;
                        }else {
                            input_edDiaChiEmail.setErrorEnabled(false);
                            input_edDiaChiEmail.setError("");
                            kiemtrathongtin = true;
                        }
                    }
                }
                break;
            case R.id.edMatKhauDK:
                break;
            case R.id.edNhapLaiMatKhauDK:
                if(!hasFocus) {
                    String chuoi = ((EditText) v).getText().toString();
                    String matkhau = edMatKhau.getText().toString();

                    if(!chuoi.equals(matkhau)) {
                        input_edNhapLaiMatKhau.setErrorEnabled(true);
                        input_edNhapLaiMatKhau.setError("Mật khẩu không trùng khớp !");
                        kiemtrathongtin = false;
                    }else {
                        input_edNhapLaiMatKhau.setErrorEnabled(false);
                        input_edNhapLaiMatKhau.setError("");
                        kiemtrathongtin = true;
                    }
                }
                break;
        }
    }
}
