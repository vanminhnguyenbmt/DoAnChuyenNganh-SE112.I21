package com.bin.lazada.View.ChiTietSanPham;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bin.lazada.Adapter.AdapterViewPagerSlider;
import com.bin.lazada.ObjectClass.ChiTietSanPham;
import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.Presenter.ChiTietSanPham.FragmentSliderChiTietSanPham;
import com.bin.lazada.Presenter.ChiTietSanPham.PresenterLogicChiTietSanPham;
import com.bin.lazada.R;
import com.bin.lazada.View.TrangChu.TrangChuActivity;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ChiTietSanPhamActivity extends AppCompatActivity implements ViewChiTietSanPham, ViewPager.OnPageChangeListener{

    ViewPager viewPager;
    PresenterLogicChiTietSanPham presenterLogicChiTietSanPham;
    TextView[] txtDots;
    LinearLayout layoutDots;
    List<Fragment> fragmentList;
    TextView txtTenSanPham, txtGiaTien, txtTenCHDongGoi, txtThongTinChiTiet, txtTieuDeThongSoKyThuat;
    Toolbar toolbar;
    ImageView imgXemThemChiTiet;
    LinearLayout lnThongSoKyThuat;
    Boolean kiemtraxochitiet = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chitietsanpham);

        viewPager = (ViewPager) findViewById(R.id.viewpagerSLider);
        layoutDots = (LinearLayout) findViewById(R.id.layoutDots);
        txtTenSanPham = (TextView) findViewById(R.id.txtTenSanPham);
        txtGiaTien = (TextView) findViewById(R.id.txtGiaTien);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        txtTenCHDongGoi = (TextView) findViewById(R.id.txtTenCHDongGoi);
        txtThongTinChiTiet = (TextView) findViewById(R.id.txtThongTinChiTiet);
        imgXemThemChiTiet = (ImageView) findViewById(R.id.imgXemThemChiTiet);
        lnThongSoKyThuat = (LinearLayout) findViewById(R.id.lnThongSoKyThuat);
        txtTieuDeThongSoKyThuat = (TextView) findViewById(R.id.txtTieuDeThongSoKyThuat);

        setSupportActionBar(toolbar);

        int masp = getIntent().getIntExtra("masp", 0);
        presenterLogicChiTietSanPham = new PresenterLogicChiTietSanPham(this);
        presenterLogicChiTietSanPham.LayChiTietSanPham(masp);

    }

    @Override
    public void HienThiChiTietSanPham(final SanPham sanPham) {
        txtTenSanPham.setText(sanPham.getTENSP());
        NumberFormat numberFormat = new DecimalFormat("###,###");
        String gia = numberFormat.format(sanPham.getGIA()).toString();
        txtGiaTien.setText(gia + " VNĐ");
        txtTenCHDongGoi.setText(sanPham.getTENNV());
        txtThongTinChiTiet.setText(sanPham.getTHONGTIN().substring(0, 100));

        if(sanPham.getTHONGTIN().length() < 100) {
            imgXemThemChiTiet.setVisibility(View.GONE);
        }else {
            imgXemThemChiTiet.setVisibility(View.VISIBLE);

            imgXemThemChiTiet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    kiemtraxochitiet = !kiemtraxochitiet;
                    if(kiemtraxochitiet) {
                        //sau khi xổ chi tiết
                        txtThongTinChiTiet.setText(sanPham.getTHONGTIN());
                        imgXemThemChiTiet.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
                        lnThongSoKyThuat.setVisibility(View.VISIBLE);
                        txtTieuDeThongSoKyThuat.setVisibility(View.VISIBLE);
                        HienThiThongSoKyThuat(sanPham);
                    }else {
                        //sau khi đóng chi tiết
                        txtThongTinChiTiet.setText(sanPham.getTHONGTIN().substring(0, 100));
                        imgXemThemChiTiet.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
                        lnThongSoKyThuat.setVisibility(View.GONE);
                        txtTieuDeThongSoKyThuat.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void HienThiThongSoKyThuat(SanPham sanPham) {
        List<ChiTietSanPham> chiTietSanPhams = sanPham.getChiTietSanPhamList();
        lnThongSoKyThuat.removeAllViews();

//        TextView txtTieuDeThongSoKyThuat = new TextView(this);
//        txtTieuDeThongSoKyThuat.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        txtTieuDeThongSoKyThuat.setText("Thông số kỹ thuật");
//        lnThongSoKyThuat.addView(txtTieuDeThongSoKyThuat);

        for(int i = 0; i < chiTietSanPhams.size(); i++) {
            LinearLayout lnChiTiet = new LinearLayout(this);
            lnChiTiet.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            lnChiTiet.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtTenThongSo = new TextView(this);
            txtTenThongSo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            txtTenThongSo.setText(chiTietSanPhams.get(i).getTENCHITIET());

            TextView txtGiaTriThongSo = new TextView(this);
            txtGiaTriThongSo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            txtGiaTriThongSo.setText(chiTietSanPhams.get(i).getGIATRI());

            lnChiTiet.addView(txtTenThongSo);
            lnChiTiet.addView(txtGiaTriThongSo);

            lnThongSoKyThuat.addView(lnChiTiet);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menutrangchu, menu);
        return true;
    }

    @Override
    public void HienThiSliderSanPham(String[] linkhinhsanpham) {
        //Tạo list fragment cho slider hình sản phẩm
        fragmentList = new ArrayList<>();
        for(int i = 0; i < linkhinhsanpham.length; i++) {
            FragmentSliderChiTietSanPham fragmentSliderChiTietSanPham = new FragmentSliderChiTietSanPham();

            //gửi link hình qua FragmentSliderChiTietSanPham
            Bundle bundle = new Bundle();
            bundle.putString("linkhinh", TrangChuActivity.SERVER + linkhinhsanpham[i]);
            fragmentSliderChiTietSanPham.setArguments(bundle);

            fragmentList.add(fragmentSliderChiTietSanPham);
        }

        AdapterViewPagerSlider adapterViewPagerSlider = new AdapterViewPagerSlider(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapterViewPagerSlider);
        adapterViewPagerSlider.notifyDataSetChanged();

        ThemDotSlider(0);

        viewPager.addOnPageChangeListener(this);
    }

    private void ThemDotSlider(int vitrihientai) {
        txtDots = new TextView[fragmentList.size()];

        layoutDots.removeAllViews();
        for(int i = 0; i < fragmentList.size(); i++) {
            txtDots[i] = new TextView(this);
            txtDots[i].setText(Html.fromHtml("&#8226;"));
            txtDots[i].setTextSize(40);
            txtDots[i].setTextColor(getIdColor(R.color.colorSLiderInActive));

            layoutDots.addView(txtDots[i]);
        }

        txtDots[vitrihientai].setTextColor(getIdColor(R.color.bgToolbar));
    }

    private int getIdColor(int idcolor) {
        int color = 0;
        if(Build.VERSION.SDK_INT > 21) {
            color = ContextCompat.getColor(this, idcolor);
        }else {
            color = getResources().getColor(idcolor);
        }
        return color;
    }

    private Drawable getHinhChiTiet(int idDrawable) {
        Drawable drawable;
        if(Build.VERSION.SDK_INT > 21) {
            drawable = ContextCompat.getDrawable(this, idDrawable);
        }else {
            drawable = getResources().getDrawable(idDrawable);
        }

        return drawable;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ThemDotSlider(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
