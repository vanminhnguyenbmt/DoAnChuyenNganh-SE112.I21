package com.bin.lazada.Presenter.TrangChu;

import com.bin.lazada.Model.TrangChu.DownloadDuLieu;
import com.bin.lazada.View.TrangChu.ViewDownLoadImp;

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
        }
    }
}
