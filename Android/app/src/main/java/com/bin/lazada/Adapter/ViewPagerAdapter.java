package com.bin.lazada.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bin.lazada.View.TrangChu.Fragment.FragmentChuongTrinhKhuyenMai;
import com.bin.lazada.View.TrangChu.Fragment.FragmentDienTu;
import com.bin.lazada.View.TrangChu.Fragment.FragmentLamDep;
import com.bin.lazada.View.TrangChu.Fragment.FragmentMeVaBe;
import com.bin.lazada.View.TrangChu.Fragment.FragmentNhaCuaVaDoiSong;
import com.bin.lazada.View.TrangChu.Fragment.FragmentNoiBat;
import com.bin.lazada.View.TrangChu.Fragment.FragmentTheThaoVaDuLich;
import com.bin.lazada.View.TrangChu.Fragment.FragmentThoiTrang;
import com.bin.lazada.View.TrangChu.Fragment.FragmentThuongHieu;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> listFragment = new ArrayList<Fragment>();
    List<String> titleFragment = new ArrayList<String>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        listFragment.add(new FragmentNoiBat());
        listFragment.add(new FragmentChuongTrinhKhuyenMai());
        listFragment.add(new FragmentDienTu());
//        listFragment.add(new FragmentNhaCuaVaDoiSong());
//        listFragment.add(new FragmentMeVaBe());
//        listFragment.add(new FragmentLamDep());
//        listFragment.add(new FragmentThoiTrang());
//        listFragment.add(new FragmentTheThaoVaDuLich());
//        listFragment.add(new FragmentThuongHieu());

        titleFragment.add("Nổi bật");
        titleFragment.add("Chương trình khuyến mãi");
        titleFragment.add("Điện tử");
//        titleFragment.add("Nhà cửa & đời sống");
//        titleFragment.add("Mẹ & bé");
//        titleFragment.add("Làm đẹp");
//        titleFragment.add("Thời trang");
//        titleFragment.add("Thể thao & du lịch");
//        titleFragment.add("Thương hiệu");
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titleFragment.get(position);
    }
}
