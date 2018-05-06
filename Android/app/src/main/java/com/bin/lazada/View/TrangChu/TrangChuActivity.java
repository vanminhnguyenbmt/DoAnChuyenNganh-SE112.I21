package com.bin.lazada.View.TrangChu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.bin.lazada.Adapter.ExpandAdapter;
import com.bin.lazada.Adapter.ViewPagerAdapter;
import com.bin.lazada.Model.DangNhap_DangKy.ModelDangNhap;
import com.bin.lazada.ObjectClass.LoaiSanPham;
import com.bin.lazada.Presenter.TrangChu.XuLyMenu.PresenterLogicXuLyMenu;
import com.bin.lazada.R;
import com.bin.lazada.View.DangNhap_DangKy.DangNhapActivity;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TrangChuActivity extends AppCompatActivity implements ViewXuLyMenu, GoogleApiClient.OnConnectionFailedListener, AppBarLayout.OnOffsetChangedListener {

    public static final String SERVER_NAME = "http://192.168.137.1/weblazada/loaisanpham.php";
    public static final String SERVER = "http://192.168.137.1/weblazada";

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    ExpandableListView expandableListView;
    PresenterLogicXuLyMenu logicXuLyMenu;
    String tennguoidung = "";
    AccessToken accessToken;
    Menu menu;
    ModelDangNhap modelDangNhap;
    MenuItem itemDangNhap, menuItemDangXuat;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInResult googleSignInResult;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //phải khởi tạo sdk facebook trước khi setContentView
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.trangchu_layout);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        expandableListView = (ExpandableListView) findViewById(R.id.epMenu);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //phải add sau toolbar thì mới custom style được
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        logicXuLyMenu = new PresenterLogicXuLyMenu(this);
        modelDangNhap = new ModelDangNhap();

        logicXuLyMenu.LayDanhSachMenu();
        mGoogleApiClient = modelDangNhap.LayGoogleApiClient(this, this);

        appBarLayout.addOnOffsetChangedListener(this);
    }

    //hiển thị menu trang chủ
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menutrangchu, menu);
        this.menu = menu;

        itemDangNhap = menu.findItem(R.id.itDangNhap);
        menuItemDangXuat = menu.findItem(R.id.itDangXuat);

        accessToken = logicXuLyMenu.LayTokenNguoiDungFacebook();
        googleSignInResult = modelDangNhap.LayThongTinDangNhapGoogle(mGoogleApiClient);

        if(accessToken != null) {
            //vì executeAsync chạy bất đồng bộ nên không thể tách hàm này ra model
            GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        tennguoidung = object.getString("name");
                        itemDangNhap.setTitle(tennguoidung);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

            Bundle parameter = new Bundle();
            parameter.putString("fields", "name");
            graphRequest.setParameters(parameter);
            graphRequest.executeAsync();
        }

        if(googleSignInResult != null) {
            itemDangNhap.setTitle(googleSignInResult.getSignInAccount().getDisplayName());
        }

        String tennv = modelDangNhap.LayCacheDangNhap(this);
        if(!tennv.equals("")) {
            itemDangNhap.setTitle(tennv);
        }

        if(accessToken != null || googleSignInResult != null || !tennv.equals("")) {
            menuItemDangXuat.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        switch (id) {
            case R.id.itDangNhap:
                if(accessToken == null && googleSignInResult == null && modelDangNhap.LayCacheDangNhap(this).equals("")) {
                    Intent iDangNhap = new Intent(this, DangNhapActivity.class);
                    startActivity(iDangNhap);
                };break;

            case R.id.itDangXuat:
                if(accessToken != null) {
                    LoginManager.getInstance().logOut();
                    this.menu.clear();
                    this.onCreateOptionsMenu(this.menu);
                }
                if(googleSignInResult != null) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                    this.menu.clear();
                    this.onCreateOptionsMenu(this.menu);
                }
                if(!modelDangNhap.LayCacheDangNhap(this).equals("")) {
                    modelDangNhap.CapNhapCacheDangNhap(this,"");
                    this.menu.clear();
                    this.onCreateOptionsMenu(this.menu);
                }
        }

        return true;
    }

    @Override
    public void HienThiDanhSachMenu(List<LoaiSanPham> loaiSanPhamList) {
        ExpandAdapter expandAdapter = new ExpandAdapter(this, loaiSanPhamList);
        expandableListView.setAdapter(expandAdapter);
        expandAdapter.notifyDataSetChanged();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if(collapsingToolbarLayout.getHeight() + verticalOffset <= 1.5 * ViewCompat.getMinimumHeight(collapsingToolbarLayout)) {
            LinearLayout linearLayout = (LinearLayout) appBarLayout.findViewById(R.id.lnSearch);
            linearLayout.animate().alpha(0).setDuration(250);
        }else {
            LinearLayout linearLayout = (LinearLayout) appBarLayout.findViewById(R.id.lnSearch);
            linearLayout.animate().alpha(1).setDuration(250);
        }
    }
}
