package com.bin.lazada.View.HienThiSanPhamTheoDanhMuc;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bin.lazada.Adapter.AdapterTopDienThoaiDienTu;
import com.bin.lazada.ObjectClass.ILoadMore;
import com.bin.lazada.ObjectClass.LoadMoreScroll;
import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.Presenter.ChiTietSanPham.PresenterLogicChiTietSanPham;
import com.bin.lazada.Presenter.HienThiSanPhamTheoDanhMuc.PresenterLogicHienThiSanPhamTheoDanhMuc;
import com.bin.lazada.R;
import com.bin.lazada.View.GioHang.GioHangActivity;
import com.bin.lazada.View.TrangChu.TrangChuActivity;
import com.bin.lazada.View.TrangChu.ViewHienThiSanPhamTheoDanhMuc;

import java.util.List;

public class HienThiSanPhamTheoDanhMucActivity extends AppCompatActivity implements ViewHienThiSanPhamTheoDanhMuc, View.OnClickListener, ILoadMore{

    RecyclerView recyclerView;
    Button btnThayDoiTrangThaiRecycler;
    ProgressBar progressBar;
    boolean danggrid = true;
    RecyclerView.LayoutManager layoutManager;
    PresenterLogicHienThiSanPhamTheoDanhMuc sanPhamTheoDanhMuc;
    AdapterTopDienThoaiDienTu adapterTopDienThoaiDienTu;
    int masp;
    boolean kiemtra;
    Toolbar toolbar;
    List<SanPham> sanPhamList1;
    boolean onPause = false;
    PresenterLogicChiTietSanPham presenterLogicChiTietSanPham;
    TextView txtGioHang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hienthisanphamtheodanhmuc);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerHienThiSanPhamTheoDanhMuc);
        btnThayDoiTrangThaiRecycler = (Button) findViewById(R.id.btnThayDoiTrangThaiRecycler);
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        Intent intent = getIntent();
        masp = intent.getIntExtra("MALOAI", 0);
        String tensanpham = intent.getStringExtra("TENLOAI");
        kiemtra = intent.getBooleanExtra("KIEMTRA", false);

        sanPhamTheoDanhMuc = new PresenterLogicHienThiSanPhamTheoDanhMuc(this);
        sanPhamTheoDanhMuc.LayDanhSachSanPhamTheoMaLoai(masp, kiemtra);

        btnThayDoiTrangThaiRecycler.setOnClickListener(this);

        toolbar.setTitle(tensanpham);
        toolbar.setTitleTextColor(getIdColor(R.color.colorWhite));

        setSupportActionBar(toolbar);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutrangchu, menu);

        //tìm custom layout giỏ hàng trong MenuItem và setText số lượng
        MenuItem itemGioHang = menu.findItem(R.id.itGioHang);
        View giaoDienCustomGioHang = itemGioHang.getActionView();
        txtGioHang = (TextView) giaoDienCustomGioHang.findViewById(R.id.txtSoLuongSanPhamGioHang);

        //Set sự kiện click chuyển trang cho giỏ hàng
        giaoDienCustomGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGioHang = new Intent(HienThiSanPhamTheoDanhMucActivity.this, GioHangActivity.class);
                startActivity(iGioHang);
            }
        });

        presenterLogicChiTietSanPham = new PresenterLogicChiTietSanPham();
        txtGioHang.setText(String.valueOf(presenterLogicChiTietSanPham.DemSanPhamCoTrongGioHang(this)));

        return true;
    }

    @Override
    public void HienThiDanhSachSanPham(List<SanPham> sanPhamList) {
        sanPhamList1 = sanPhamList;
        if(danggrid) {
            layoutManager = new GridLayoutManager(HienThiSanPhamTheoDanhMucActivity.this, 2);
            adapterTopDienThoaiDienTu = new AdapterTopDienThoaiDienTu(HienThiSanPhamTheoDanhMucActivity.this, R.layout.custom_layout_hienthisanphamtheodanhmuc,sanPhamList1);
        }else {
            layoutManager = new LinearLayoutManager(HienThiSanPhamTheoDanhMucActivity.this);
            adapterTopDienThoaiDienTu = new AdapterTopDienThoaiDienTu(HienThiSanPhamTheoDanhMucActivity.this, R.layout.custom_layout_list_topdienthoaivamaytinhbang,sanPhamList1);
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterTopDienThoaiDienTu);
        recyclerView.addOnScrollListener(new LoadMoreScroll(layoutManager, this));
        adapterTopDienThoaiDienTu.notifyDataSetChanged();
    }

    @Override
    public void LoiHienThiDanhSachSanPham() {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnThayDoiTrangThaiRecycler:
                danggrid = !danggrid;
                sanPhamTheoDanhMuc.LayDanhSachSanPhamTheoMaLoai(masp, kiemtra);
                break;
        }
    }

    @Override
    public void LoadMore(int tongitem) {
        List<SanPham> sanPhamsLoadMore = sanPhamTheoDanhMuc.LayDanhSachSanPhamTheoMaLoaiLoadMore(masp, kiemtra, tongitem, progressBar);
        sanPhamList1.addAll(sanPhamsLoadMore);

        adapterTopDienThoaiDienTu.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(onPause) {
            presenterLogicChiTietSanPham = new PresenterLogicChiTietSanPham();
            txtGioHang.setText(String.valueOf(presenterLogicChiTietSanPham.DemSanPhamCoTrongGioHang(this)));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        onPause = true;
    }
}
