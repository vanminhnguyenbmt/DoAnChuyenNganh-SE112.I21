package com.example.ochutgio.reviewquanan.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.ochutgio.reviewquanan.Adapter.AdapterTatCaBinhLuanQuanAn;
import com.example.ochutgio.reviewquanan.Model.QuanAnModel;
import com.example.ochutgio.reviewquanan.R;

/**
 * Created by ochutgio on 5/16/2018.
 */

public class TatCaBinhLuanActivity extends AppCompatActivity {

    RecyclerView recyclerBinhLuanQuanAn;
    Toolbar toolbar;
    TextView txtTenQuanAn;
    QuanAnModel quanAnModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tatcabinhluan);

        quanAnModel = getIntent().getParcelableExtra("binhluanquanan");
        txtTenQuanAn = (TextView) findViewById(R.id.txtTenQuanAn);
        recyclerBinhLuanQuanAn = (RecyclerView) findViewById(R.id.recyclerBinhLuan) ;
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        /// set toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTenQuanAn.setText(quanAnModel.getTenquanan());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerBinhLuanQuanAn.setLayoutManager(layoutManager);
        AdapterTatCaBinhLuanQuanAn adapterBinhLuanQuanAn = new AdapterTatCaBinhLuanQuanAn(this, R.layout.custom_layout_binhluanquanan, quanAnModel.getBinhluanquanan());
        recyclerBinhLuanQuanAn.setAdapter(adapterBinhLuanQuanAn);
        adapterBinhLuanQuanAn.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
       return true;
    }
}
