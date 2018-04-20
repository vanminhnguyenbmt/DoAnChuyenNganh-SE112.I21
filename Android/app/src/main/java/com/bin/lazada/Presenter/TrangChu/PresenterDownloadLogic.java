package com.bin.lazada.Presenter.TrangChu;

import android.util.Log;

import com.bin.lazada.Model.TrangChu.DownloadDuLieu;
import com.bin.lazada.View.TrangChu.ViewDownLoadImp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class PresenterDownloadLogic implements PresenterDownloadImp {
    ViewDownLoadImp viewDownLoadImp;
    String duongdan = "";
    String maloaicha = "";

    public PresenterDownloadLogic(ViewDownLoadImp viewDownLoadImp, String duongdan, String maloaicha) {
        this.viewDownLoadImp = viewDownLoadImp;
        this.duongdan = duongdan;
        this.maloaicha = maloaicha;
    }

    @Override
    public void downloaddulieu() {
        DownloadDuLieu TaiDuLieu = new DownloadDuLieu();
        TaiDuLieu.execute(duongdan, maloaicha);

        try {
            String dulieuparse = TaiDuLieu.get();
            JSONObject jsonObject = new JSONObject(dulieuparse);
            JSONArray jsloaisanpham = jsonObject.getJSONArray("LOAISANPHAM");
            for(int i = 0; i < jsloaisanpham.length(); i++)
            {
                JSONObject dulieu = jsloaisanpham.getJSONObject(i);
                String tensanpham = dulieu.getString("TENLOAISP");
                Log.d("json", tensanpham);
            }

            String dulieu = TaiDuLieu.get();
            if(dulieu == null || dulieu.equals(""))
            {
                dulieu = "Kiểm tra lại kết nối";
                viewDownLoadImp.downloadthatbai(dulieu);
            }else {
                viewDownLoadImp.downloadthanhcong(dulieu);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
