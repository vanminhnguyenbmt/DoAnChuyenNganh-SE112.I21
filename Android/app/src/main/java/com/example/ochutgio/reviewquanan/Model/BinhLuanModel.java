package com.example.ochutgio.reviewquanan.Model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ochutgio.reviewquanan.View.BinhLuanActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by ochutgio on 4/24/2018.
 */

public class BinhLuanModel implements Parcelable {
    String noidung;
    String tieude;
    String mauser;
    long chamdiem, luotthich;

    List<String> hinhanhBinhLuan;

    ThanhVienModel thanhVienModel;

    public BinhLuanModel(){

    }

    protected BinhLuanModel(Parcel in) {
        noidung = in.readString();
        tieude = in.readString();
        mauser = in.readString();
        chamdiem = in.readLong();
        luotthich = in.readLong();
        hinhanhBinhLuan = in.createStringArrayList();
        thanhVienModel = in.readParcelable(ThanhVienModel.class.getClassLoader());
    }

    public static final Creator<BinhLuanModel> CREATOR = new Creator<BinhLuanModel>() {
        @Override
        public BinhLuanModel createFromParcel(Parcel in) {
            return new BinhLuanModel(in);
        }

        @Override
        public BinhLuanModel[] newArray(int size) {
            return new BinhLuanModel[size];
        }
    };

    public List<String> getHinhanhBinhLuan() {
        return hinhanhBinhLuan;
    }

    public void setHinhanhBinhLuan(List<String> hinhanhBinhLuan) {
        this.hinhanhBinhLuan = hinhanhBinhLuan;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }

    public long getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(long chamdiem) {
        this.chamdiem = chamdiem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public ThanhVienModel getThanhVienModel() {
        return thanhVienModel;
    }

    public void setThanhVienModel(ThanhVienModel thanhVienModel) {
        this.thanhVienModel = thanhVienModel;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(noidung);
        parcel.writeString(tieude);
        parcel.writeString(mauser);
        parcel.writeLong(chamdiem);
        parcel.writeLong(luotthich);
        parcel.writeStringList(hinhanhBinhLuan);
        parcel.writeParcelable(thanhVienModel, i);
    }

    public void ThemBinhLuan(BinhLuanModel binhLuanModel, final List<String> listHinh, String maquanan, final Context context){

        DatabaseReference noteBinhLuan = FirebaseDatabase.getInstance().getReference().child("binhluans");
        final String mabinhluan = noteBinhLuan.child(maquanan).push().getKey();
        noteBinhLuan.child(maquanan).child(mabinhluan).setValue(binhLuanModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isComplete()){
                    if(listHinh.size() > 0){
                        for(final String valueHinh : listHinh){
                            Bitmap bitmap = decodeFile(valueHinh);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            byte[] data = stream.toByteArray();

                            final Uri uri = Uri.fromFile(new File(valueHinh));
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Photo/" + uri.getLastPathSegment());
                            storageReference.putBytes(data).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(context, uri.getLastPathSegment() + " upload thất bại", Toast.LENGTH_SHORT).show();
                                    }else {
                                        FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans").child(mabinhluan).push().setValue(uri.getLastPathSegment());
                                    }
                                }
                            });
                            ///
                        }
                    }
                    ///
                    Toast.makeText(context, "Thêm bình luận thành công", Toast.LENGTH_SHORT).show();
                    ((Activity)context).finish();
                }else {
                    Toast.makeText(context, "Thêm bình luận thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /// hàm resize kích thước ảnh
    private Bitmap decodeFile(String imgPath)
    {
        Bitmap b = null;
        int max_size = 600;
        File f = new File(imgPath);
        try {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
            int scale = 1;
            if (o.outHeight > max_size || o.outWidth > max_size)
            {
                scale = (int) Math.pow(2, (int) Math.ceil(Math.log(max_size / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
            }
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        }
        catch (Exception e)
        {
        }
        return b;
    }
}
