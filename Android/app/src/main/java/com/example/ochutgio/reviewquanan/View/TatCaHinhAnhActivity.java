package com.example.ochutgio.reviewquanan.View;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.ochutgio.reviewquanan.Adapter.AdapterTatCaHinhAnh;
import com.example.ochutgio.reviewquanan.Adapter.AdapterViewPagerSlideHinh;
import com.example.ochutgio.reviewquanan.Model.BinhLuanModel;
import com.example.ochutgio.reviewquanan.Model.QuanAnModel;
import com.example.ochutgio.reviewquanan.R;
import com.example.ochutgio.reviewquanan.View.Fragment.SlideHinhFrament;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ochutgio on 5/16/2018.
 */

public class TatCaHinhAnhActivity extends AppCompatActivity {
    QuanAnModel quanAnModel;
    List<Fragment> fragmentList;

    RecyclerView recyclerHinhAnh;
    TextView txtTenQuanAn;
    Toolbar toolbar;
    ViewPager viewPagerSlideHinh;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tatcahinhanh);

        quanAnModel = getIntent().getParcelableExtra("hinhanhquanan");

        recyclerHinhAnh = (RecyclerView) findViewById(R.id.recyclerHinhAnh);
        txtTenQuanAn = (TextView) findViewById(R.id.txtTenQuanAn);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPagerSlideHinh = (ViewPager) findViewById(R.id.viewpagerSlideHinh);

        progressDialog = new ProgressDialog(this);

        txtTenQuanAn.setText(quanAnModel.getTenquanan());

        /// set toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final List<Bitmap>  bitmapList = new ArrayList<>();
        progressDialog.setMessage("Đang tải hình");
        progressDialog.setIndeterminate(true);
        //progressDialog.setCancelable(false);
        progressDialog.show();
        for(final BinhLuanModel binhLuanModel : quanAnModel.getBinhluanquanan()){
            for(String linkhinh : binhLuanModel.getHinhanhBinhLuan()){
                StorageReference storageHinhBinhLuan = FirebaseStorage.getInstance().getReference().child("Photo").child(linkhinh);
                long ONE_MEGABYTE = 1024*1024;
                storageHinhBinhLuan.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        bitmapList.add(Bitmap.createScaledBitmap(bitmap, 120, 120, false));
                        progressDialog.dismiss();
                        AdapterTatCaHinhAnh adapter = new AdapterTatCaHinhAnh(TatCaHinhAnhActivity.this, R.layout.custom_layout_tatcahinhanh, bitmapList);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(TatCaHinhAnhActivity.this, 3);
                        recyclerHinhAnh.setLayoutManager(layoutManager);
                        recyclerHinhAnh.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }

        fragmentList = new ArrayList<>();
        for(int i = 0; i < bitmapList.size(); i++) {
            SlideHinhFrament slideHinhFrament = new SlideHinhFrament();
            /// chuyen bitmap thanh byteArray va gui cho Fragment
            Bundle bundle = new Bundle();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmapList.get(i).compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bundle.putByteArray("byteArray", byteArray );
            slideHinhFrament.setArguments(bundle);

            fragmentList.add(slideHinhFrament );
        }

        AdapterViewPagerSlideHinh adapterViewPagerSlideHinh = new AdapterViewPagerSlideHinh(getSupportFragmentManager(), fragmentList);
        viewPagerSlideHinh.setAdapter(adapterViewPagerSlideHinh);
        adapterViewPagerSlideHinh.notifyDataSetChanged();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
