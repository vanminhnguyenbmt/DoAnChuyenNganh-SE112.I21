package com.example.ochutgio.reviewquanan.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.AsyncTask;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;

import com.example.ochutgio.reviewquanan.Model.ThanhVienModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import android.widget.ImageView;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.example.ochutgio.reviewquanan.Adapter.AdapterViewPagerTrangchu;
import com.example.ochutgio.reviewquanan.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


/**
 * Created by ochutgio on 4/3/2018.
 */

public class TrangChuActivity extends AppCompatActivity {

    ViewPager viewPagerTrangChu;
    DrawerLayout mDrawerLayout;
    ImageView imgLogo;
    ImageView imgSearch;
    RadioGroup rbg_odau_angi;

    RadioButton rb_odau;
    RadioButton rb_angi;

    SharedPreferences sharedPreferences;

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trangchu);

        sharedPreferences = getSharedPreferences("LuuDangNhap", MODE_PRIVATE);

        mDrawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        imgLogo = (ImageView)findViewById(R.id.imgLogo);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        viewPagerTrangChu = (ViewPager) findViewById(R.id.viewpager_trangchu);
        rbg_odau_angi = (RadioGroup) findViewById(R.id.rbg_odau_angi);

        rb_angi = (RadioButton) findViewById(R.id.rb_angi);
        rb_odau = (RadioButton) findViewById(R.id.rb_odau);


        /// set adapter cho viewpager
        AdapterViewPagerTrangchu adapterViewPagerTrangchu = new AdapterViewPagerTrangchu(getSupportFragmentManager());
        viewPagerTrangChu.setAdapter(adapterViewPagerTrangchu);

        /// hàm gọi menu khi click logo
        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iTimKiem = new Intent(TrangChuActivity.this, TimKiemActivity.class);
                startActivity(iTimKiem);
            }
        });

        // lắng nghe các sự kiện của menu
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {}
            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                final TextView txtTenUser = (TextView) findViewById(R.id.txtTenUser);
                final ImageView imvProfile = (ImageView) findViewById(R.id.profile_image);
                String mauser = sharedPreferences.getString("mauser", "");
                DatabaseReference dataUser = FirebaseDatabase.getInstance().getReference().child("thanhviens");
                dataUser.child(mauser).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() != null){
                            ThanhVienModel thanhVienModel = dataSnapshot.getValue(ThanhVienModel.class);
                            txtTenUser.setText(thanhVienModel.getHoten());

                            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference().child("User").child(thanhVienModel.getHinhanh());
                            long ONE_MEGABYTE = 1024 * 1024;
                            storageHinhUser.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                                @Override
                                public void onSuccess(byte[] bytes) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    imvProfile.setImageBitmap(bitmap);
                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
            @Override
            public void onDrawerClosed(@NonNull View drawerView) {}
            @Override
            public void onDrawerStateChanged(int newState) {}
        });

        /// sự kiện click vào item menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    case R.id.nav_item5:
                        FirebaseAuth.getInstance().signOut();
                        Intent iDangNhap = new Intent(TrangChuActivity.this, DangNhapActivity.class);
                        startActivity(iDangNhap);
                        finish();
                        break;

                    case R.id.nav_item4:
                        break;

                    case R.id.nav_item3:
                        Intent iThemQuanAn = new Intent(TrangChuActivity.this, ThemQuanAnActivity.class);
                        startActivity(iThemQuanAn);
                        break;

                    case R.id.nav_item2:
                        break;

                    case R.id.nav_item1:
//                        Intent iHoSo = new Intent(TrangChuActivity.this, HoSoActivity.class);
//                        startActivity(iHoSo);
                        break;
                }

                mDrawerLayout.closeDrawers();

                return true;
            }
        });


        /// set fragment vào viewPager
        viewPagerTrangChu.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        rb_angi.setChecked(true);
                        break;
                    case 1:
                        rb_odau.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /// xử lý gọi fragment nào khi check radiogroup_odau_angi
        rbg_odau_angi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_angi:
                        viewPagerTrangChu.setCurrentItem(0);
                        break;
                    case R.id.rb_odau:
                        viewPagerTrangChu.setCurrentItem(1);
                        break;
                }
            }
        });

    }

    /// sự kiện nhấn nút Back
    @Override
    public void onBackPressed() {
        AlertDialog myAlertDialog = thoatAlertDialog();
        myAlertDialog.show();
    }

    /// tạo hộp thoại xác nhận thoát ứng dụng
    private AlertDialog thoatAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác Nhận!");
        builder.setMessage("Bạn có muốn thoát ứng dụng ?");
        builder.setCancelable(false);
        builder.setNegativeButton("Thoát",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.setNeutralButton("Hủy", null);

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
