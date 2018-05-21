package com.example.ochutgio.reviewquanan.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ochutgio on 5/3/2018.
 */

public class ChiNhanhQuanAnModel implements Parcelable {
    String diachi;
    double latitude;
    double longitude;
    double khoangcach;

    public ChiNhanhQuanAnModel(){

    }

    protected ChiNhanhQuanAnModel(Parcel in) {
        diachi = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        khoangcach = in.readDouble();
    }

    public static final Creator<ChiNhanhQuanAnModel> CREATOR = new Creator<ChiNhanhQuanAnModel>() {
        @Override
        public ChiNhanhQuanAnModel createFromParcel(Parcel in) {
            return new ChiNhanhQuanAnModel(in);
        }

        @Override
        public ChiNhanhQuanAnModel[] newArray(int size) {
            return new ChiNhanhQuanAnModel[size];
        }
    };

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getKhoangcach() {
        return khoangcach;
    }

    public void setKhoangcach(double khoangcach) {
        this.khoangcach = khoangcach;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(diachi);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeDouble(khoangcach);
    }
}
