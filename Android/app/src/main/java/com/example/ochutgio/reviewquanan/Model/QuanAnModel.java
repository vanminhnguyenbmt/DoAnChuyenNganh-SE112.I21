package com.example.ochutgio.reviewquanan.Model;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.example.ochutgio.reviewquanan.Controller.Interface.OdauInterface;
import com.example.ochutgio.reviewquanan.Controller.ThucDonController;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ochutgio on 4/19/2018.
 */

public class QuanAnModel implements Parcelable {

    boolean giaohang;
    long luotthich;
    long giatoida;
    long giatoithieu;

    String giomocua;
    String giodongcua;
    String tenquanan;
    String videogioithieu;

    String maquanan;
    List<String> tienich;
    List<BinhLuanModel> binhluanquanan;
    List<String> hinhanhquanan;
    List<ChiNhanhQuanAnModel> chinhanhquanan;
    List<ThucDonModel> thucdonquanan;
    Bitmap bitmaphinhanhquanan;
    Bitmap bitmaphinhmonan;

    private DatabaseReference noteRoot;

    public QuanAnModel(){
        noteRoot = FirebaseDatabase.getInstance().getReference();
    }

    protected QuanAnModel(Parcel in) {
        giaohang = in.readByte() != 0;
        luotthich = in.readLong();
        giatoida = in.readLong();
        giatoithieu = in.readLong();
        giomocua = in.readString();
        giodongcua = in.readString();
        tenquanan = in.readString();
        maquanan = in.readString();
        videogioithieu = in.readString();
        tienich = in.createStringArrayList();
        hinhanhquanan = in.createStringArrayList();
        chinhanhquanan = new ArrayList<ChiNhanhQuanAnModel>();
        in.readTypedList(chinhanhquanan, ChiNhanhQuanAnModel.CREATOR);
        binhluanquanan = new ArrayList<BinhLuanModel>();
        in.readTypedList(binhluanquanan, BinhLuanModel.CREATOR);
    }

    public static final Creator<QuanAnModel> CREATOR = new Creator<QuanAnModel>() {
        @Override
        public QuanAnModel createFromParcel(Parcel in) {
            return new QuanAnModel(in);
        }

        @Override
        public QuanAnModel[] newArray(int size) {
            return new QuanAnModel[size];
        }
    };

    public Bitmap getBitmaphinhanhquanan() {
        return bitmaphinhanhquanan;
    }

    public void setBitmaphinhanhquanan(Bitmap bitmaphinhanhquanan) {
        this.bitmaphinhanhquanan = bitmaphinhanhquanan;
    }

    public Bitmap getBitmaphinhmonan() {
        return bitmaphinhmonan;
    }

    public void setBitmaphinhmonan(Bitmap bitmaphinhmonan) {
        this.bitmaphinhmonan = bitmaphinhmonan;
    }

    public long getGiatoida() {
        return giatoida;
    }

    public void setGiatoida(long giatoida) {
        this.giatoida = giatoida;
    }

    public void setGiatoithieu(long giatoithieu) {
        this.giatoithieu = giatoithieu;
    }

    public long getGiatoithieu() {
        return giatoithieu;
    }

    public String getMaquanan() {
        return maquanan;
    }

    public void setMaquanan(String maquanan) {
        this.maquanan = maquanan;
    }

    public List<ChiNhanhQuanAnModel> getChinhanhquanan() {
        return chinhanhquanan;
    }

    public void setChinhanhquanan(List<ChiNhanhQuanAnModel> chinhanhquanan) {
        this.chinhanhquanan = chinhanhquanan;
    }


    public List<BinhLuanModel> getBinhluanquanan() {
        return binhluanquanan;
    }

    public void setBinhluanquanan(List<BinhLuanModel> binhLuanModelList) {
        this.binhluanquanan = binhLuanModelList;
    }

    public List<ThucDonModel> getThucdonquanan() {
        return thucdonquanan;
    }

    public void setThucdonquanan(List<ThucDonModel> thucdonquanan) {
        this.thucdonquanan = thucdonquanan;
    }

    public List<String> getHinhanhquanan() {
        return hinhanhquanan;
    }

    public void setHinhanhquanan(List<String> hinhanhquanan) {
        this.hinhanhquanan = hinhanhquanan;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public boolean isGiaohang() {
        return giaohang;
    }

    public void setGiaohang(boolean giaohang) {
        this.giaohang = giaohang;
    }

    public String getGiomocua() {
        return giomocua;
    }

    public void setGiomocua(String giomocua) {
        this.giomocua = giomocua;
    }

    public String getGiodongcua() {
        return giodongcua;
    }

    public void setGiodongcua(String giodongcua) {
        this.giodongcua = giodongcua;
    }

    public String getTenquanan() {
        return tenquanan;
    }

    public void setTenquanan(String tenquanan) {
        this.tenquanan = tenquanan;
    }

    public String getVideogioithieu() {
        return videogioithieu;
    }

    public void setVideogioithieu(String videogioithieu) {
        this.videogioithieu = videogioithieu;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    private DataSnapshot dataRoot;
    public void getDanhSachQuanAn(final OdauInterface odauInterface, final Location vitrihientai, final int itemtieptheo, final int itemdaco){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataRoot = dataSnapshot;
                layDanhSachQuanAn(dataSnapshot, odauInterface, vitrihientai, itemtieptheo, itemdaco);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        if(dataRoot != null){
            layDanhSachQuanAn(dataRoot, odauInterface, vitrihientai, itemtieptheo, itemdaco);
        }else {
            noteRoot.addListenerForSingleValueEvent(valueEventListener);
        }

    }

    private void layDanhSachQuanAn(DataSnapshot dataSnapshot, OdauInterface odauInterface, Location vitrihientai, int itemtieptheo, int itemdaco){
        DataSnapshot dataSnapshotQuanAn = dataSnapshot.child("quanans");

        int i = 0;
        for(DataSnapshot valueQuanAn : dataSnapshotQuanAn.getChildren()){

            if (i == itemtieptheo){
                break;
            }

            if (i < itemdaco){
                i++;
                continue;
            }
            i++;

            QuanAnModel quanAnModel = valueQuanAn.getValue(QuanAnModel.class);
            quanAnModel.setMaquanan(valueQuanAn.getKey());

            /// lay danh sach hinh anh quan an
            DataSnapshot dataHinhAnhQuanAnList = dataSnapshot.child("hinhanhquanans").child(valueQuanAn.getKey());
            List<String> hinhAnhQuanAnlist = new ArrayList<>();
            for(DataSnapshot valueHinhAnh : dataHinhAnhQuanAnList.getChildren()){
                hinhAnhQuanAnlist.add(valueHinhAnh.getValue(String.class));
            }
            quanAnModel.setHinhanhquanan(hinhAnhQuanAnlist);

            /// lay danh sach binh luan quan an
            DataSnapshot dataBinhLuanQuanAnList = dataSnapshot.child("binhluans").child(valueQuanAn.getKey());
            List<BinhLuanModel> binhLuanModelList = new ArrayList<>();
            for(DataSnapshot valueBinhLuan : dataBinhLuanQuanAnList.getChildren()){
                BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                binhLuanModel.setThanhVienModel(thanhVienModel);

                List<String> hinhanhBinhLuan = new ArrayList<>();
                DataSnapshot dataHinhAnhBinhLuan = dataSnapshot.child("hinhanhbinhluans").child(valueBinhLuan.getKey());
                for(DataSnapshot valueHinhAnhBinhLuan : dataHinhAnhBinhLuan.getChildren()){
                    hinhanhBinhLuan.add(valueHinhAnhBinhLuan.getValue(String.class));
                }
                binhLuanModel.setHinhanhBinhLuan(hinhanhBinhLuan);
                binhLuanModelList.add(binhLuanModel);
            }
            quanAnModel.setBinhluanquanan(binhLuanModelList);

            /// lay chi nhanh quan an
            DataSnapshot dataChiNhanhQuanList = dataSnapshot.child("chinhanhquanans").child(valueQuanAn.getKey());
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
            DataSnapshot dataThucDonList = dataSnapshot.child("thucdonquanans").child(valueQuanAn.getKey());
            List<ThucDonModel> thucDonModelList= new ArrayList<>();
            for (DataSnapshot valueThucDon : dataThucDonList.getChildren()){
                ThucDonModel thucDonModel = new ThucDonModel();
                DataSnapshot noteThucDon = dataSnapshot.child("thucdons").child(valueThucDon.getKey());
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

            ///
            odauInterface.getDanhSachQuanAnModel(quanAnModel);
        }
    }

    public void timQuanAn(final OdauInterface odauInterface,final DataSnapshot dataQuanAn, final Location vitrihientai){

        final DatabaseReference dataNodeRoot = FirebaseDatabase.getInstance().getReference();
        dataNodeRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataRoot) {
                for(DataSnapshot valueQuanAn : dataQuanAn.getChildren()){

                    QuanAnModel quanAnModel = valueQuanAn.getValue(QuanAnModel.class);
                    quanAnModel.setMaquanan(valueQuanAn.getKey());
                    Log.d("kiemtra", valueQuanAn.getKey());
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
                    ///
                    odauInterface.timQuanAn(quanAnModel);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (giaohang ? 1 : 0));
        parcel.writeLong(luotthich);
        parcel.writeLong(giatoida);
        parcel.writeLong(giatoithieu);
        parcel.writeString(giomocua);
        parcel.writeString(giodongcua);
        parcel.writeString(tenquanan);
        parcel.writeString(maquanan);
        parcel.writeString(videogioithieu);
        parcel.writeStringList(tienich);
        parcel.writeStringList(hinhanhquanan);
        parcel.writeTypedList(chinhanhquanan);
        parcel.writeTypedList(binhluanquanan);
    }
}
