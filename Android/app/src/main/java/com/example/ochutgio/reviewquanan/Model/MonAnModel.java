package com.example.ochutgio.reviewquanan.Model;

import android.text.Editable;

import com.example.ochutgio.reviewquanan.Controller.Interface.AnGiInterface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by ochutgio on 4/21/2018.
 */

public class MonAnModel {

    long giatien;
    String hinhanh;
    String tenmon;
    String loaimonan;
    String mamonan;

    DatabaseReference noteRoot;

    public MonAnModel(){
        noteRoot = FirebaseDatabase.getInstance().getReference();
    }

    public long getGiatien() {
        return giatien;
    }

    public void setGiatien(long giatien) {
        this.giatien = giatien;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTenmon() {
        return tenmon;
    }

    public void setTenmon(String tenmon) {
        this.tenmon = tenmon;
    }

    public String getLoaimonan() {
        return loaimonan;
    }

    public void setLoaimonan(String loaimonan) {
        this.loaimonan = loaimonan;
    }

    public String getMamonan() {
        return mamonan;
    }

    public void setMamonan(String mamonan) {
        this.mamonan = mamonan;
    }
    public void getDanhSachMonAnModel(AnGiInterface anGiInterface){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshotMonAn = dataSnapshot.child("thucdonquanans");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        noteRoot.addListenerForSingleValueEvent(valueEventListener);
    }
}
