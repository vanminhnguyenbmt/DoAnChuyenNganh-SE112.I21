package com.bin.lazada.View.ChiTietSanPham;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bin.lazada.Adapter.AdapterDanhGia;
import com.bin.lazada.Adapter.AdapterViewPagerSlider;
import com.bin.lazada.ObjectClass.ChiTietKhuyenMai;
import com.bin.lazada.ObjectClass.ChiTietSanPham;
import com.bin.lazada.ObjectClass.DanhGia;
import com.bin.lazada.ObjectClass.SanPham;
import com.bin.lazada.Presenter.ChiTietSanPham.FragmentSliderChiTietSanPham;
import com.bin.lazada.Presenter.ChiTietSanPham.PresenterLogicChiTietSanPham;
import com.bin.lazada.R;
import com.bin.lazada.View.DanhGia.DanhSachDanhGiaActivity;
import com.bin.lazada.View.DanhGia.ThemDanhGiaActivity;
import com.bin.lazada.View.GioHang.GioHangActivity;
import com.bin.lazada.View.ThanhToan.ThanhToanActivity;
import com.bin.lazada.View.TrangChu.TrangChuActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ChiTietSanPhamActivity extends AppCompatActivity implements ViewChiTietSanPham, ViewPager.OnPageChangeListener, View.OnClickListener{

    ViewPager viewPager;
    PresenterLogicChiTietSanPham presenterLogicChiTietSanPham;
    TextView[] txtDots;
    LinearLayout layoutDots;
    List<Fragment> fragmentList;
    TextView txtTenSanPham, txtGiaTien, txtTenCHDongGoi, txtThongTinChiTiet, txtTieuDeThongSoKyThuat, txtXemTatCaNhanXet, txtGioHang, txtGiamGia;
    Toolbar toolbar;
    ImageView imgXemThemChiTiet, imgThemGioHang;
    Button btnMuaNgay;
    LinearLayout lnThongSoKyThuat;
    Boolean kiemtraxochitiet = false;
    TextView txtVietDanhGia;
    int masp;
    List<DanhGia> danhGiaList;
    RecyclerView recyclerDanhGiaChiTiet;
    SanPham sanPhamGioHang;
    boolean onPause = false;

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
        txtVietDanhGia = (TextView) findViewById(R.id.txtVietDanhGia);
        recyclerDanhGiaChiTiet = (RecyclerView) findViewById(R.id.recyclerDanhGiaChiTiet);
        txtXemTatCaNhanXet = (TextView) findViewById(R.id.txtXemTatCaNhanXet);
        imgThemGioHang = (ImageView) findViewById(R.id.imgThemGioHang);
        btnMuaNgay = (Button) findViewById(R.id.btnMuaNgay);
        txtGiamGia = (TextView) findViewById(R.id.txtGiamGia);

        toolbar.setTitle("Chi tiết sản phẩm");
        toolbar.setTitleTextColor(getIdColor(R.color.colorWhite));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        masp = getIntent().getIntExtra("masp", 0);
        presenterLogicChiTietSanPham = new PresenterLogicChiTietSanPham(this);
        presenterLogicChiTietSanPham.LayChiTietSanPham(masp);
        presenterLogicChiTietSanPham.LayDanhSachDanhGiaCuaSanPham(masp, 0);

        txtVietDanhGia.setOnClickListener(this);
        txtXemTatCaNhanXet.setOnClickListener(this);
        imgThemGioHang.setOnClickListener(this);
        btnMuaNgay.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void HienThiChiTietSanPham(final SanPham sanPham) {
        masp = sanPham.getMASP();

        sanPhamGioHang = sanPham;
        sanPhamGioHang.setSOLUONGTONKHO(sanPham.getSOLUONG());

        txtTenSanPham.setText(sanPham.getTENSP());

        //hiển thị giá khuyến mãi nếu có
        ChiTietKhuyenMai chiTietKhuyenMai = sanPham.getChiTietKhuyenMai();
        int giatien = sanPham.getGIA();
        if(chiTietKhuyenMai != null) {
            int phantramkm = chiTietKhuyenMai.getPHANTRAMKM();

            if(phantramkm != 0) {
                NumberFormat numberFormat = new DecimalFormat("###,###");
                String gia = numberFormat.format(giatien).toString();

                txtGiamGia.setVisibility(View.VISIBLE);
                txtGiamGia.setPaintFlags(txtGiamGia.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                txtGiamGia.setText(gia + " VNĐ ");

                giatien = giatien - giatien * phantramkm/100;
            }
        }

        NumberFormat numberFormat = new DecimalFormat("###,###");
        String gia = numberFormat.format(giatien).toString();
        txtGiaTien.setText(gia + " VNĐ");
        txtTenCHDongGoi.setText(sanPham.getTENNV());
        txtThongTinChiTiet.setText(Html.fromHtml(sanPham.getTHONGTIN().substring(0, 100)));

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
                        txtThongTinChiTiet.setText(Html.fromHtml(sanPham.getTHONGTIN()));
                        imgXemThemChiTiet.setImageDrawable(getHinhChiTiet(R.drawable.ic_keyboard_arrow_up_black_24dp));
                        lnThongSoKyThuat.setVisibility(View.VISIBLE);
                        txtTieuDeThongSoKyThuat.setVisibility(View.VISIBLE);
                        HienThiThongSoKyThuat(sanPham);
                    }else {
                        //sau khi đóng chi tiết
                        txtThongTinChiTiet.setText(Html.fromHtml(sanPham.getTHONGTIN().substring(0, 100)));
                        imgXemThemChiTiet.setImageDrawable(getHinhChiTiet(R.drawable.ic_keyboard_arrow_down_black_24dp));
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

        //tìm custom layout giỏ hàng trong MenuItem và setText số lượng
        MenuItem itemGioHang = menu.findItem(R.id.itGioHang);
        View giaoDienCustomGioHang = itemGioHang.getActionView();
        txtGioHang = (TextView) giaoDienCustomGioHang.findViewById(R.id.txtSoLuongSanPhamGioHang);

        txtGioHang.setText(String.valueOf(presenterLogicChiTietSanPham.DemSanPhamCoTrongGioHang(this)));

        //Set sự kiện click chuyển trang cho giỏ hàng
        giaoDienCustomGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iGioHang = new Intent(ChiTietSanPhamActivity.this, GioHangActivity.class);
                startActivity(iGioHang);
            }
        });

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

    @Override
    public void HienThiDanhGia(List<DanhGia> danhGiaList) {
        AdapterDanhGia adapterDanhGia = new AdapterDanhGia(this, danhGiaList, 2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerDanhGiaChiTiet.setLayoutManager(layoutManager);
        recyclerDanhGiaChiTiet.setAdapter(adapterDanhGia);
        adapterDanhGia.notifyDataSetChanged();
    }

    @Override
    public void ThemGioHangThanhCong() {
        Toast.makeText(this, "Sản phẩm đã được thêm vào giỏ hàng !", Toast.LENGTH_SHORT).show();
        txtGioHang.setText(String.valueOf(presenterLogicChiTietSanPham.DemSanPhamCoTrongGioHang(this)));
    }

    @Override
    public void ThemGioHangThatBai() {
        Toast.makeText(this, "Sản phẩm đã có trong giỏ hàng !", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.txtVietDanhGia:
                Intent iThemDanhGia = new Intent(this, ThemDanhGiaActivity.class);
                iThemDanhGia.putExtra("masp", masp);
                startActivity(iThemDanhGia);
                break;
            case R.id.txtXemTatCaNhanXet:
                Intent iDanhSachDanhGia = new Intent(ChiTietSanPhamActivity.this, DanhSachDanhGiaActivity.class);
                iDanhSachDanhGia.putExtra("masp", masp);
                startActivity(iDanhSachDanhGia);
                break;
            case R.id.imgThemGioHang:
                Fragment fragment = fragmentList.get(viewPager.getCurrentItem());
                View view = fragment.getView();
                ImageView imageView = (ImageView) view.findViewById(R.id.imgHinhSlider);
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] hinhsanphamgiohang = byteArrayOutputStream.toByteArray();
                sanPhamGioHang.setHinhgiohang(hinhsanphamgiohang);
                sanPhamGioHang.setSOLUONG(1);

                presenterLogicChiTietSanPham.ThemGioHang(sanPhamGioHang, this);
                break;

            case R.id.btnMuaNgay:
                //thêm sản phẩm vào giỏ hàng
                Fragment fragment1 = fragmentList.get(viewPager.getCurrentItem());
                View view1 = fragment1.getView();
                ImageView imageView1 = (ImageView) view1.findViewById(R.id.imgHinhSlider);
                Bitmap bitmap1 = ((BitmapDrawable)imageView1.getDrawable()).getBitmap();

                ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream1);
                byte[] hinhsanphamgiohang1 = byteArrayOutputStream1.toByteArray();
                sanPhamGioHang.setHinhgiohang(hinhsanphamgiohang1);
                sanPhamGioHang.setSOLUONG(1);

                presenterLogicChiTietSanPham.ThemGioHang(sanPhamGioHang, this);

                //chuyển sang trang thanh toán
                Intent iThanhToan = new Intent(ChiTietSanPhamActivity.this, ThanhToanActivity.class);
                startActivity(iThanhToan);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(onPause) {
            presenterLogicChiTietSanPham = new PresenterLogicChiTietSanPham();
            txtGioHang.setText(String.valueOf(presenterLogicChiTietSanPham.DemSanPhamCoTrongGioHang(this)));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        onPause = true;
    }
}
