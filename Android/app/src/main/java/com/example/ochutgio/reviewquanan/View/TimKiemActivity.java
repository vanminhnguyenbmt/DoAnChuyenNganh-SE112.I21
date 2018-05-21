package com.example.ochutgio.reviewquanan.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ProgressBar;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.example.ochutgio.reviewquanan.Controller.TimKiemController;
import com.example.ochutgio.reviewquanan.Model.BinhLuanModel;
import com.example.ochutgio.reviewquanan.Model.ChiNhanhQuanAnModel;
import com.example.ochutgio.reviewquanan.Model.MonAnModel;
import com.example.ochutgio.reviewquanan.Model.QuanAnModel;
import com.example.ochutgio.reviewquanan.Model.ThanhVienModel;
import com.example.ochutgio.reviewquanan.Model.ThucDonModel;
import com.example.ochutgio.reviewquanan.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ochutgio on 5/12/2018.
 */

public class TimKiemActivity extends AppCompatActivity{
    ProgressBar progressBar;
    SearchView svTimKiem;
    RecyclerView recyclerTimKiem;

    TimKiemController timKiemController;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_timkiem);

        timKiemController = new TimKiemController(this);
        sharedPreferences = getSharedPreferences("toado", Context.MODE_PRIVATE);
        final Location vitrihientai = new Location("");
        vitrihientai.setLatitude(Double.parseDouble(sharedPreferences.getString("latitude","0")));
        vitrihientai.setLongitude(Double.parseDouble(sharedPreferences.getString("longitude","0")));

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);

        recyclerTimKiem = (RecyclerView) findViewById(R.id.recyclerTimKiem);
        svTimKiem = (SearchView) findViewById(R.id.svTimKiem);



        svTimKiem.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(s.trim().length() > 0){
                    final DatabaseReference dataQuanAn = FirebaseDatabase.getInstance().getReference().child("quanans");
                    Query query = dataQuanAn.orderByChild("tenquanan").startAt(s).endAt(s+"\uf8ff");
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(final DataSnapshot dataSnapshotQuanAn) {
                            for(DataSnapshot valueQuanAn : dataSnapshotQuanAn.getChildren()){
                                Log.d("kiemtra", valueQuanAn.getValue()+"");
                            }
                            if(dataSnapshotQuanAn.getValue() != null){
                                timKiemController.timQuanAn(TimKiemActivity.this, recyclerTimKiem, vitrihientai, dataSnapshotQuanAn, progressBar);
                            }else {
                                Toast.makeText(TimKiemActivity.this, "Không tìm thấy dữ liệu", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
//
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }


        });

    }

    private void layDanhSachQuanAn(DataSnapshot dataRoot, DataSnapshot dataQuanAn, Location vitrihientai){

        for(DataSnapshot valueQuanAn : dataQuanAn.getChildren()){

            QuanAnModel quanAnModel = valueQuanAn.getValue(QuanAnModel.class);
            quanAnModel.setMaquanan(valueQuanAn.getKey());

            /// lay danh sach hinh anh quan an
            DataSnapshot dataHinhAnhQuanAnList = dataRoot.child("hinhanhquanans").child(valueQuanAn.getKey());
            List<String> hinhAnhQuanAnlist = new ArrayList<>();
            for(DataSnapshot valueHinhAnh : dataHinhAnhQuanAnList.getChildren()){
                hinhAnhQuanAnlist.add(valueHinhAnh.getValue(String.class));
            }
            quanAnModel.setHinhanhquanan(hinhAnhQuanAnlist);

            /// lay danh sach binh luan quan an
            DataSnapshot dataBinhLuanQuanAnList = dataRoot.child("binhluans").child(valueQuanAn.getKey());
            List<BinhLuanModel> binhLuanModelList = new ArrayList<>();
            for(DataSnapshot valueBinhLuan : dataBinhLuanQuanAnList.getChildren()){
                BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                ThanhVienModel thanhVienModel = dataRoot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                binhLuanModel.setThanhVienModel(thanhVienModel);

                List<String> hinhanhBinhLuan = new ArrayList<>();
                DataSnapshot dataHinhAnhBinhLuan = dataRoot.child("hinhanhbinhluans").child(valueBinhLuan.getKey());
                for(DataSnapshot valueHinhAnhBinhLuan : dataHinhAnhBinhLuan.getChildren()){
                    hinhanhBinhLuan.add(valueHinhAnhBinhLuan.getValue(String.class));
                }
                binhLuanModel.setHinhanhBinhLuan(hinhanhBinhLuan);
                binhLuanModelList.add(binhLuanModel);
            }
            quanAnModel.setBinhluanquanan(binhLuanModelList);

            /// lay chi nhanh quan an
            DataSnapshot dataChiNhanhQuanList = dataRoot.child("chinhanhquanans").child(valueQuanAn.getKey());
            List<ChiNhanhQuanAnModel> chiNhanhQuanAnModelList = new ArrayList<>();
            for(DataSnapshot valueChiNhanhQuanAn : dataChiNhanhQuanList.getChildren()){

                ChiNhanhQuanAnModel chiNhanhQuanAnModel = valueChiNhanhQuanAn.getValue(ChiNhanhQuanAnModel.class);

                Location vitriquanan = new Location("");
                vitriquanan.setLatitude(chiNhanhQuanAnModel.getLatitude());
                vitriquanan.setLongitude(chiNhanhQuanAnModel.getLongitude());
                double khoangcach = vitrihientai.distanceTo(vitriquanan) / 1000;
                chiNhanhQuanAnModel.setKhoangcach(khoangcach);

                chiNhanhQuanAnModelList.add(chiNhanhQuanAnModel);
            }
            quanAnModel.setChinhanhquanan(chiNhanhQuanAnModelList);

            /// lay thuc don quan an
            DataSnapshot dataThucDonList = dataRoot.child("thucdonquanans").child(valueQuanAn.getKey());
            List<ThucDonModel> thucDonModelList= new ArrayList<>();
            for (DataSnapshot valueThucDon : dataThucDonList.getChildren()){
                ThucDonModel thucDonModel = new ThucDonModel();
                DataSnapshot noteThucDon = dataRoot.child("thucdons").child(valueThucDon.getKey());
                thucDonModel.setMathucdon(noteThucDon.getKey());
                thucDonModel.setTenthucdon(noteThucDon.getValue(String.class));
                List<MonAnModel> monAnModelList = new ArrayList<>();
                for(DataSnapshot valueMonAn : valueThucDon.getChildren()){
                    MonAnModel monAnModel = valueMonAn.getValue(MonAnModel.class);
                    monAnModelList.add(monAnModel);
                }
                thucDonModel.setMonAnModelList(monAnModelList);
                thucDonModelList.add(thucDonModel);
            }
            quanAnModel.setThucdonquanan(thucDonModelList);

        }
    }

}
