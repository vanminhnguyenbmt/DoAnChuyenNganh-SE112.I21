package com.example.ochutgio.reviewquanan.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Location;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.example.ochutgio.reviewquanan.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashScreenActivity extends AppCompatActivity implements OnSuccessListener {

    private FusedLocationProviderClient mFusedLocationClient;
    private SharedPreferences sharedPreferences;

    private final int REQUEST_PERMISSION_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_splashscreen);

        sharedPreferences = getSharedPreferences("toado", MODE_PRIVATE);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        int checkPermissionLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if (checkPermissionLocation != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);

        } else {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("latitude", String.valueOf(location.getLatitude()));
                                editor.putString("longitude",String.valueOf(location.getLongitude()));
                                editor.commit();
                                //Toast.makeText(SplashScreenActivity.this, "ok", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                //Toast.makeText(SplashScreenActivity.this, "null", Toast.LENGTH_SHORT).show();
                            }

                            /// Chuyển sang activity đăng nhập
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent iDangNhap = new Intent(SplashScreenActivity.this, DangNhapActivity.class);
                                    startActivity(iDangNhap);
                                    finish();
                                }
                            }, 2000);
                        }
                    });
        }
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    // Got last known location. In some rare situations this can be null.
                                    if (location != null) {
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("latitude", String.valueOf(location.getLatitude()));
                                        editor.putString("longitude",String.valueOf(location.getLongitude()));
                                        editor.commit();

                                        Toast.makeText(SplashScreenActivity.this, "đéo null", Toast.LENGTH_SHORT).show();
                                    }else {

                                    }
                                    /// chuyển sang activity Đăng nhập
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent iDangNhap = new Intent(SplashScreenActivity.this, DangNhapActivity.class);
                                            startActivity(iDangNhap);
                                            finish();
                                        }
                                    }, 2000);
                                }
                            });
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onSuccess(Object o) {

    }

}
