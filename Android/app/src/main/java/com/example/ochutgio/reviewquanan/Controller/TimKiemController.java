package com.example.ochutgio.reviewquanan.Controller;

import android.content.Context;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.ochutgio.reviewquanan.Adapter.AdapterRecyclerOdau;
import com.example.ochutgio.reviewquanan.Adapter.AdapterTimKiem;
import com.example.ochutgio.reviewquanan.Controller.Interface.OdauInterface;
import com.example.ochutgio.reviewquanan.Model.QuanAnModel;
import com.example.ochutgio.reviewquanan.R;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class TimKiemController {
    Context context;
    QuanAnModel quanAnModel;
    AdapterTimKiem adapterTimKiem;
    List<QuanAnModel> quanAnModelList;

    public TimKiemController(Context context){
        this.context = context;
        quanAnModel = new QuanAnModel();
    }

    public void timQuanAn(Context context, RecyclerView recyclerView, final Location vitrihientai, DataSnapshot dataQuanAn, final ProgressBar progressBar){
        quanAnModelList  = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        adapterTimKiem = new AdapterTimKiem(context, quanAnModelList, R.layout.custom_layout_timkiem);
        recyclerView.setAdapter(adapterTimKiem);

        progressBar.setVisibility(View.VISIBLE);
        final OdauInterface odauInterface = new OdauInterface(){
            @Override
            public void getDanhSachQuanAnModel(QuanAnModel quanAnModel) {

            }

            @Override
            public void timQuanAn(QuanAnModel quanAnModel) {
                quanAnModelList.add(quanAnModel);
                adapterTimKiem.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        };
        quanAnModel.timQuanAn(odauInterface, dataQuanAn, vitrihientai);
    }

}
