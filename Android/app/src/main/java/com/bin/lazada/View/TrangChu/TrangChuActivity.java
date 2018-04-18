package com.bin.lazada.View.TrangChu;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bin.lazada.Model.TrangChu.DownloadDuLieu;
import com.bin.lazada.Presenter.TrangChu.PresenterDownloadLogic;
import com.bin.lazada.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class TrangChuActivity extends AppCompatActivity implements View.OnClickListener, ViewDownLoadImp{
    EditText editMaLoaiCha;
    Button btnLayDuLieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trangchu_layout);

        editMaLoaiCha = (EditText) findViewById(R.id.edMaLoaiCha);
        btnLayDuLieu = (Button) findViewById(R.id.btnLayDuLieu);
        btnLayDuLieu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String maloaicha = editMaLoaiCha.getText().toString();
        //địa chỉ localhost genymotion 10.0.3.2
        //địa chỉ localhost android studio 127.0.0.1
//        String duongdan = "http://192.168.137.1/weblazada/loaisanpham.php?maloaicha="+maloaicha; đường dẫn dạng get
        String duongdan = "http://192.168.137.1/weblazada/loaisanpham.php";
        PresenterDownloadLogic presenterDownloadLogic = new PresenterDownloadLogic(TrangChuActivity.this, duongdan, maloaicha);
        presenterDownloadLogic.downloaddulieu();
    }

    @Override
    public void downloadthanhcong(String dulieu) {
        Toast.makeText(this, dulieu, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void downloadthatbai(String dulieu) {
        Toast.makeText(this, dulieu, Toast.LENGTH_SHORT).show();
    }
}
