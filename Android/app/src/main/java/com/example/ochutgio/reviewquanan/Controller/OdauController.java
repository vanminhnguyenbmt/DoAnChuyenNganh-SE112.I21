package com.example.ochutgio.reviewquanan.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.ochutgio.reviewquanan.Adapter.AdapterRecyclerOdau;
import com.example.ochutgio.reviewquanan.Controller.Interface.OdauInterface;
import com.example.ochutgio.reviewquanan.Model.QuanAnModel;
import com.example.ochutgio.reviewquanan.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by ochutgio on 4/19/2018.
 */

public class OdauController {

    Context context;
    QuanAnModel quanAnModel;
    AdapterRecyclerOdau adapterRecyclerOdau;
    List<QuanAnModel> quanAnModelList;
    int itemdaco = 3;
    int buocnhay = 3;

    public OdauController(Context context) {
        this.context = context;
        quanAnModel = new QuanAnModel();
    }

    public void getDanhSachQuanAnController(Context context, RecyclerView recyclerOdau, final Location vitrihientai, final ProgressBar progressBar){
        quanAnModelList  = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerOdau.setLayoutManager(layoutManager);
        adapterRecyclerOdau = new AdapterRecyclerOdau(context, quanAnModelList, R.layout.custom_layout_recycleview_odau);
        recyclerOdau.setAdapter(adapterRecyclerOdau);

        progressBar.setVisibility(View.VISIBLE);
        final OdauInterface odauInterface = new OdauInterface() {
            @Override
            public void getDanhSachQuanAnModel(final QuanAnModel quanAnModel) {

                if(quanAnModel.getHinhanhquanan().size() > 0) {
                StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("Photo").child(quanAnModel.getHinhanhquanan().get(0));
                long ONE_MEGABYTE = 1024 * 1024;
                storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmapHinhQuanAn = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        quanAnModel.setBitmaphinhanhquanan(bitmapHinhQuanAn);
                        quanAnModelList.add(quanAnModel);
                        adapterRecyclerOdau.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                        }
                    });
                }
            }

            @Override
            public void timQuanAn(QuanAnModel quanAnModel) {

            }
        };

        recyclerOdau.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                itemdaco += buocnhay;
                quanAnModel.getDanhSachQuanAn(odauInterface, vitrihientai, itemdaco, itemdaco - buocnhay);
            }
        });

        quanAnModel.getDanhSachQuanAn(odauInterface, vitrihientai, itemdaco, 0);
    }
}
