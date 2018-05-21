package com.example.ochutgio.reviewquanan.Model;

import android.util.Log;

import com.example.ochutgio.reviewquanan.Controller.Interface.ThucDonInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ochutgio on 5/8/2018.
 */

public class ThucDonModel {
    String mathucdon;
    String tenthucdon;
    List<MonAnModel> monAnModelList;


    public ThucDonModel(){

    }

    public List<MonAnModel> getMonAnModelList() {
        return monAnModelList;
    }

    public void setMonAnModelList(List<MonAnModel> monAnModelList) {
        this.monAnModelList = monAnModelList;
    }

    public String getMathucdon() {
        return mathucdon;
    }

    public void setMathucdon(String mathucdon) {
        this.mathucdon = mathucdon;
    }

    public String getTenthucdon() {
        return tenthucdon;
    }

    public void setTenthucdon(String tenthucdon) {
        this.tenthucdon = tenthucdon;
    }

    public  void getThucDonQuanAn(final String maquanan, final ThucDonInterface thucDonInterface){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("thucdonquanans").child(maquanan);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                /// duyệt danh danh sách thực đơn
                final List<ThucDonModel> thucDonModelList = new ArrayList<>();
                for(DataSnapshot valueThucDon : dataSnapshot.getChildren()){
                    final ThucDonModel thucDonModel = new ThucDonModel();
                    DatabaseReference noteThucDon = FirebaseDatabase.getInstance().getReference().child("thucdons").child(valueThucDon.getKey());
                    noteThucDon.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshotThucDon) {
                            thucDonModel.setMathucdon(dataSnapshotThucDon.getKey());
                            thucDonModel.setTenthucdon(dataSnapshotThucDon.getValue(String.class));
                            /// duyệt danh sách món ăn
                            List<MonAnModel> monAnModelList = new ArrayList<>();
                            for (DataSnapshot valueMonAn : dataSnapshot.child(thucDonModel.getMathucdon()).getChildren() ){
                                MonAnModel monAnModel = valueMonAn.getValue(MonAnModel.class);
                                monAnModel.setMamonan(valueMonAn.getKey());
                                monAnModelList.add(monAnModel);
                                //Log.d("kiemtra", monAnModel.getTenmon());
                            }
                            thucDonModel.setMonAnModelList(monAnModelList);
                            thucDonModelList.add(thucDonModel);

                            //Log.d("kiemtra", thucDonModelList.size() + "");
                            thucDonInterface.getThucDonThanhCong(thucDonModelList);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
