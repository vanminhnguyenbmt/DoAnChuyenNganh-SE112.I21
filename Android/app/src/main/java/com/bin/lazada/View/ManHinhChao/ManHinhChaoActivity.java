package com.bin.lazada.View.ManHinhChao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bin.lazada.R;
import com.bin.lazada.View.TrangChu.TrangChuActivity;

public class ManHinhChaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manhinhchao_layout);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //sau 3s thì bắt đầu chuyển màn hình sang trang chủ
                    Thread.sleep(2000);
                }catch (Exception e) {

                }finally {
                    //chuyển màn hình sang trang chủ
                    Intent iTrangChu = new Intent(ManHinhChaoActivity.this, TrangChuActivity.class);
                    startActivity(iTrangChu);
                }
            }
        });

        thread.start();
    }
}
