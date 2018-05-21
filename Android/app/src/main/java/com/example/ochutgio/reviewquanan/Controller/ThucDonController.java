package com.example.ochutgio.reviewquanan.Controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ochutgio.reviewquanan.Adapter.AdapterThucDon;
import com.example.ochutgio.reviewquanan.Controller.Interface.ThucDonInterface;
import com.example.ochutgio.reviewquanan.Model.ThucDonModel;

import java.util.List;

/**
 * Created by ochutgio on 5/8/2018.
 */

public class ThucDonController {

    ThucDonModel thucDonModel;

    public ThucDonController(){
        thucDonModel = new ThucDonModel();
    }

    public void getThucDonQuanAn(final Context context, String maquanan, final RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ThucDonInterface thucDonInterface = new ThucDonInterface(){
            @Override
            public void getThucDonThanhCong(List<ThucDonModel> thucDonModelList) {
                AdapterThucDon expandAdapter = new AdapterThucDon(context, thucDonModelList);
                recyclerView.setAdapter(expandAdapter);
                expandAdapter.notifyDataSetChanged();
            }
        };

        thucDonModel.getThucDonQuanAn(maquanan, thucDonInterface );
    }
}
