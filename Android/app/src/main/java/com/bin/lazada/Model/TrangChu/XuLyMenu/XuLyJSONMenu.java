package com.bin.lazada.Model.TrangChu.XuLyMenu;

import android.util.Log;

import com.bin.lazada.ObjectClass.LoaiSanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class XuLyJSONMenu {
    public List<LoaiSanPham> ParseJSONMenu(String dulieujson)
    {
        List<LoaiSanPham> loaiSanPhamList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(dulieujson);
            JSONArray loaisanpham = jsonObject.getJSONArray("LOAISANPHAM");
            int count = loaisanpham.length();
            //duyệt mảng json object
            for(int i = 0; i < count; i++)
            {
                //lấy từng phần tử object của mảng json
                JSONObject value = loaisanpham.getJSONObject(i);

                //gán từng phần tử của object cho dataloaisanpham sau đó thêm vào list LoaiSanPham
                LoaiSanPham dataloaiSanPham = new LoaiSanPham();
                dataloaiSanPham.setMALOAISP(Integer.parseInt(value.getString("MALOAISP")));
                dataloaiSanPham.setMALOAICHA(Integer.parseInt(value.getString("MALOAI_CHA")));
                dataloaiSanPham.setTENLOAISP(value.getString("TENLOAISP"));

                loaiSanPhamList.add(dataloaiSanPham);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return loaiSanPhamList;
    }
}
