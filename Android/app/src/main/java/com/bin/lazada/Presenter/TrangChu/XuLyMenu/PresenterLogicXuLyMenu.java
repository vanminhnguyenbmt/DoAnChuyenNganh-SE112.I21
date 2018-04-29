package com.bin.lazada.Presenter.TrangChu.XuLyMenu;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.bin.lazada.ConnectInternet.DownloadJSON;
import com.bin.lazada.Model.DangNhap.ModelDangNhap;
import com.bin.lazada.Model.TrangChu.XuLyMenu.XuLyJSONMenu;
import com.bin.lazada.ObjectClass.LoaiSanPham;
import com.bin.lazada.View.TrangChu.ViewXuLyMenu;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PresenterLogicXuLyMenu implements IPresenterXuLyMenu {

    ViewXuLyMenu viewXuLyMenu;
    String tennguoidung = "";

    public PresenterLogicXuLyMenu(ViewXuLyMenu viewXuLyMenu)
    {
        this.viewXuLyMenu = viewXuLyMenu;
    }

    @Override
    public void LayDanhSachMenu() {
        List<LoaiSanPham> loaiSanPhamList;
        String dataJSON = "";
        List<HashMap<String, String>> attrs = new ArrayList<>();

        //Lấy dữ liệu bằng phương thức get
//        String duongdan = "http://192.168.137.1/weblazada/loaisanpham.php?maloaicha=0";

//        DownloadJSON downloadJSON = new DownloadJSON(duongdan);

        //Lấy bằng phương thức post
        String duongdan = "http://192.168.137.1/weblazada/loaisanpham.php";
        HashMap<String, String> hsMaLoaiCha = new HashMap<>();
        hsMaLoaiCha.put("maloaicha", "0");
        attrs.add(hsMaLoaiCha);

        DownloadJSON downloadJSON = new DownloadJSON(duongdan, attrs);
        downloadJSON.execute();

        try {
            dataJSON = downloadJSON.get();
            XuLyJSONMenu xuLyJSONMenu = new XuLyJSONMenu();
            loaiSanPhamList = xuLyJSONMenu.ParseJSONMenu(dataJSON);
            viewXuLyMenu.HienThiDanhSachMenu(loaiSanPhamList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public AccessToken LayTokenNguoiDungFacebook() {
        ModelDangNhap modelDangNhap = new ModelDangNhap();
        AccessToken accessToken = modelDangNhap.LayTokenFacebookHienTai();

        return accessToken;
    }
}
