package com.example.ochutgio.reviewquanan.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ochutgio.reviewquanan.Adapter.AdapterChonHinhBinhLuan;
import com.example.ochutgio.reviewquanan.Model.ChonHinhBinhLuanModel;
import com.example.ochutgio.reviewquanan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ochutgio on 5/6/2018.
 */

public class ChonHinhBinhLuanActivity extends AppCompatActivity {

    final int REQUEST_IMVHINH = 111;

    List<ChonHinhBinhLuanModel> listDuongDan;
    List<String> listDuocChon;
    AdapterChonHinhBinhLuan adapterChonHinhBinhLuan;

    TextView txtXong;
//    ImageView imvGoiCamera;
    RecyclerView recyclerChonHinh;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chonhinhbinhluan);

        listDuongDan = new ArrayList<>();
        listDuocChon = new ArrayList<>();
//        listDuocChon.add("/storage/emulated/0/DCIM/Camera/IMG_20171203_033400.jpg") ;
//        listDuocChon.add("/storage/emulated/0/DCIM/Camera/IMG20171218164006.jpg") ;

        recyclerChonHinh = (RecyclerView) findViewById(R.id.recyclerChonHinh);
//        imvGoiCamera = (ImageView) findViewById(R.id.imvGoiCamera);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtXong = (TextView)  findViewById(R.id.txtXong);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerChonHinh.setLayoutManager(layoutManager);
        adapterChonHinhBinhLuan = new AdapterChonHinhBinhLuan(this, R.layout.custom_layout_chonhinhbinhluan, listDuongDan);
        recyclerChonHinh.setAdapter(adapterChonHinhBinhLuan);

        // set toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /// xin quyền đọc dữ liệu bộ nhớ ngoài
        int checkReadStorePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(checkReadStorePermission != PackageManager.PERMISSION_GRANTED){
           ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }else {
            getAllImages();
        }

        txtXong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(ChonHinhBinhLuanModel value : listDuongDan){
                    if(value.isCheck() == true){
                        listDuocChon.add(value.getDuongdan());
                    }
                }

                Intent data = getIntent();
                data.putStringArrayListExtra("listhinhdachon",(ArrayList<String>) listDuocChon);
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }

    //        imvGoiCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, REQUEST_IMVHINH);
//            }
//        });


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode == REQUEST_IMVHINH){
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
////            imvTam.setImageBitmap(bitmap);
//        }
//    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getAllImages();
            }
        }

    }

    public void getAllImages(){
        String[] projection = {MediaStore.Images.Media.DATA};
        String orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC";
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, projection, null, null,orderBy);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String duongdan = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            ChonHinhBinhLuanModel chonHinhBinhLuanModel = new ChonHinhBinhLuanModel(duongdan, false);
            listDuongDan.add(chonHinhBinhLuanModel);
            adapterChonHinhBinhLuan.notifyDataSetChanged();
            cursor.moveToNext();
        }
        cursor.close();
    }
}
