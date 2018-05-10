package com.bin.lazada.View.GioHang;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.bin.lazada.Adapter.AdapterGioHang;
import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.Presenter.GioHang.PresenterLogicGioHang;
import com.bin.lazada.R;
import com.bin.lazada.View.ThanhToan.ThanhToanActivity;

import java.util.List;

public class GioHangActivity extends AppCompatActivity implements ViewGioHang, View.OnClickListener{

    RecyclerView recyclerView;
    PresenterLogicGioHang presenterLogicGioHang;
    Toolbar toolbar;
    Button btnMuaNgay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_giohang);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerGioHang);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        btnMuaNgay = (Button) findViewById(R.id.btnMuaNgay);

        toolbar.setTitle("Giỏ hàng");
        toolbar.setTitleTextColor(getIdColor(R.color.colorWhite));
        setSupportActionBar(toolbar);

        presenterLogicGioHang = new PresenterLogicGioHang(this);
        presenterLogicGioHang.LayDanhSachSanPhamTrongGioHang(this);

        btnMuaNgay.setOnClickListener(this);

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
    public void HienThiDanhSachSanPhamTrongGioHang(List<SanPham> sanPhamList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        AdapterGioHang adapterGioHang = new AdapterGioHang(this, sanPhamList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterGioHang);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnMuaNgay:
                Intent iThanhToan = new Intent(GioHangActivity.this, ThanhToanActivity.class);
                startActivity(iThanhToan);
                break;
        }
    }
}
