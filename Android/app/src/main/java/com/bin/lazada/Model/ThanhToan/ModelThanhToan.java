package com.bin.lazada.Model.ThanhToan;

import android.util.Log;

import com.bin.lazada.ConnectInternet.DownloadJSON;
import com.bin.lazada.ObjectClass.ChiTietHoaDon;
import com.bin.lazada.ObjectClass.DanhGia;
import com.bin.lazada.ObjectClass.HoaDon;
import com.bin.lazada.View.TrangChu.TrangChuActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ModelThanhToan {
    public Boolean ThemHoaDon(HoaDon hoaDon) {

        String duongdan = TrangChuActivity.SERVER_NAME;
        Boolean kiemtra = false;

        List<HashMap<String, String>> attrs = new ArrayList<>();

        HashMap<String, String> hsHam = new HashMap<>();
        hsHam.put("ham", "ThemHoaDon");

        List<ChiTietHoaDon> chiTietHoaDonList = hoaDon.getChiTietHoaDonList();

        String chuoijson = "{\"DANHSACHSANPHAM\": [ ";
        for(int i = 0; i < chiTietHoaDonList.size(); i++) {
            chuoijson += "{";
            chuoijson += "\"masp\": " + chiTietHoaDonList.get(i).getMASP() + ",";
            chuoijson += "\"soluong\": " + chiTietHoaDonList.get(i).getSOLUONG();

            if(i == chiTietHoaDonList.size() - 1) {
                chuoijson += "}";
            }else {
                chuoijson += "},";
            }
        }
        chuoijson += "]}";

        HashMap<String, String> hsDanhSachSanPham = new HashMap<>();
        hsDanhSachSanPham.put("danhsachsanpham", chuoijson);

        HashMap<String, String> hsTenNguoiNhan = new HashMap<>();
        hsTenNguoiNhan.put("tennguoinhan", hoaDon.getTENNGUOINHAN());

        HashMap<String, String> hsSoDT = new HashMap<>();
        hsSoDT.put("sodt", String.valueOf(hoaDon.getSODT()));

        HashMap<String, String> hsDiaChi = new HashMap<>();
        hsDiaChi.put("diachi", hoaDon.getDIACHI());

        HashMap<String, String> hsChuyenKhoan = new HashMap<>();
        hsChuyenKhoan.put("chuyenkhoan", String.valueOf(hoaDon.getCHUYENKHOAN()));

        attrs.add(hsHam);
        attrs.add(hsDanhSachSanPham);
        attrs.add(hsTenNguoiNhan);
        attrs.add(hsSoDT);
        attrs.add(hsDiaChi);
        attrs.add(hsChuyenKhoan);

        DownloadJSON downloadJSON = new DownloadJSON(duongdan, attrs);
        downloadJSON.execute();

        try {
            String dulieuJSON = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dulieuJSON);
            String ketqua = jsonObject.getString("ketqua");

            if (ketqua.equals("true")) {
                kiemtra = true;
            } else {
                kiemtra = false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return kiemtra;
    }
}
