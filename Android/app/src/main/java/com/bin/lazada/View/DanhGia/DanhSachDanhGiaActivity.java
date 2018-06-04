package com.bin.lazada.View.DanhGia;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.bin.lazada.Adapter.AdapterDanhGia;
import com.bin.lazada.ObjectClass.DanhGia;
import com.bin.lazada.ObjectClass.ILoadMore;
import com.bin.lazada.ObjectClass.LoadMoreScroll;
import com.bin.lazada.Presenter.DanhGia.PresenterLogicDanhGia;
import com.bin.lazada.R;

import java.util.ArrayList;
import java.util.List;

public class DanhSachDanhGiaActivity extends AppCompatActivity implements ViewDanhGia, ILoadMore{

    RecyclerView recyclerDanhSachDanhGia;
    ProgressBar progressBar;
    int masp = 0;
    PresenterLogicDanhGia presenterLogicDanhGia;
    List<DanhGia> tatcaDanhGia;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_danhsachdanhgia);

        recyclerDanhSachDanhGia = (RecyclerView) findViewById(R.id.recyclerDanhSachDanhGia);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        toolbar = (Toolbar)findViewById(R.id.toolBar);

        masp = getIntent().getIntExtra("masp", 0);

        tatcaDanhGia = new ArrayList<>();

        presenterLogicDanhGia = new PresenterLogicDanhGia(this);
        presenterLogicDanhGia.LayDanhSachDanhGiaCuaSanPham(masp, 0, progressBar);

        toolbar.setTitle("Tất cả đánh giá");
        toolbar.setTitleTextColor(getIdColor(R.color.colorWhite));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    public void DanhGiaThanhCong() {

    }

    @Override
    public void DanhGiaThatBai() {

    }

    @Override
    public void HienThiDanhSachDanhGiaTheoSanPham(final List<DanhGia> danhGiaList) {
        tatcaDanhGia.addAll(danhGiaList);
        if(tatcaDanhGia.size() !=0 ) {
            progressBar.setVisibility(View.GONE);
        }
        final AdapterDanhGia adapterDanhGia = new AdapterDanhGia(this, tatcaDanhGia, 0);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerDanhSachDanhGia.setLayoutManager(layoutManager);
        recyclerDanhSachDanhGia.setAdapter(adapterDanhGia);
        recyclerDanhSachDanhGia.addOnScrollListener(new LoadMoreScroll(layoutManager, this));
        recyclerDanhSachDanhGia.post(new Runnable() {
            @Override
            public void run() {
                adapterDanhGia.notifyItemInserted(danhGiaList.size() - 1);
                adapterDanhGia.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void LoadMore(int tongitem) {
        presenterLogicDanhGia.LayDanhSachDanhGiaCuaSanPham(masp, tongitem, progressBar);
    }
}
