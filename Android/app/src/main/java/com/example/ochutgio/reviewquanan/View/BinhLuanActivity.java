package com.example.ochutgio.reviewquanan.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ochutgio.reviewquanan.Adapter.AdapterHinhBinhLuanDuocChon;
import com.example.ochutgio.reviewquanan.Controller.BinhLuanController;
import com.example.ochutgio.reviewquanan.Model.BinhLuanModel;
import com.example.ochutgio.reviewquanan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ochutgio on 5/6/2018.
 */

public class BinhLuanActivity extends AppCompatActivity implements View.OnClickListener{

    final int REQUESTCODE_CHONHINHBINHLUAN = 11;

    TextView txtDangBinhLuan;
    TextView txtTenQuanAn;
    TextView txtDiaChi;
    TextView txtDiem;
    EditText edTieuDeBinhLuan;
    EditText edNoiDungBinhLuan;
    RecyclerView recyclerHinhDuocChon;
    ImageView btnChonHinh;
    Toolbar toolbar;
    SeekBar sbChamDiem;

    SharedPreferences sharedPreferences;
    BinhLuanController binhLuanController;
    String maquanan;
    int chamdiem = 5;

    List<String> listHinhDaChon;
    AdapterHinhBinhLuanDuocChon adapterHinhBinhLuanDuocChon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_binhluan);

        sharedPreferences = getSharedPreferences("LuuDangNhap", MODE_PRIVATE);

        maquanan = getIntent().getStringExtra("maquanan");
        String tenquan = getIntent().getStringExtra("tenquan");
        String diachi = getIntent().getStringExtra("diachi");

        binhLuanController = new BinhLuanController();
        listHinhDaChon = new ArrayList<>();

        recyclerHinhDuocChon = (RecyclerView) findViewById(R.id.recyclerHinhDuocChon);
        txtTenQuanAn = (TextView) findViewById(R.id.txtTenQuanAn);
        txtDiaChi = (TextView) findViewById(R.id.txtDiaChi);
        txtDangBinhLuan = (TextView) findViewById(R.id.txtDangBinhLuan);
        txtDiem = (TextView) findViewById(R.id.txtDiem);
        edTieuDeBinhLuan = (EditText) findViewById(R.id.edTieuDeBinhLuan);
        edNoiDungBinhLuan = (EditText) findViewById(R.id.edNoiDungBinhLuan);
        btnChonHinh = (ImageView) findViewById(R.id.btnChonHinh);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        sbChamDiem = (SeekBar) findViewById(R.id.sbChamDiem);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerHinhDuocChon.setLayoutManager(layoutManager);

        txtTenQuanAn.setText(tenquan);
        txtDiaChi.setText(diachi);

        btnChonHinh.setOnClickListener(this);
        txtDangBinhLuan.setOnClickListener(this);

        sbChamDiem.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                chamdiem = i;
                if(chamdiem > 0){
                    txtDiem.setText(chamdiem + " đ");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnChonHinh:
                Intent iChonHinhBinhLuan = new Intent(BinhLuanActivity.this, ChonHinhBinhLuanActivity.class);
                startActivityForResult(iChonHinhBinhLuan, REQUESTCODE_CHONHINHBINHLUAN );
                break;
            case R.id.txtDangBinhLuan:
                BinhLuanModel binhLuanModel = new BinhLuanModel();
                String tieude = edTieuDeBinhLuan.getText().toString();
                String noidung = edNoiDungBinhLuan.getText().toString();
                String mauser = sharedPreferences.getString("mauser", "");

                if(tieude.trim().length() == 0 | noidung.trim().length() == 0){
                    Toast.makeText(BinhLuanActivity.this, "vui lòng điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    binhLuanModel.setTieude(tieude);
                    binhLuanModel.setNoidung(noidung);
                    binhLuanModel.setChamdiem(chamdiem);
                    binhLuanModel.setLuotthich(0);
                    binhLuanModel.setMauser(mauser);
                    binhLuanController.ThemBinhLuan(binhLuanModel, listHinhDaChon, maquanan, this);
                }

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUESTCODE_CHONHINHBINHLUAN){
            if(resultCode == RESULT_OK){
                listHinhDaChon = data.getStringArrayListExtra("listhinhdachon");
                if(listHinhDaChon.size() > 12){
                    Toast.makeText(BinhLuanActivity.this, "Chỉ hỗ trợ tối da 12 hình ảnh", Toast.LENGTH_SHORT).show();
                }
                adapterHinhBinhLuanDuocChon = new AdapterHinhBinhLuanDuocChon(this, R.layout.custom_layout_hinhbinhluan_dachon, listHinhDaChon);
                recyclerHinhDuocChon.setAdapter(adapterHinhBinhLuanDuocChon);
                adapterHinhBinhLuanDuocChon.notifyDataSetChanged();
            }
        }
    }

    /// sự kiện nhấn nút Back
    @Override
    public void onBackPressed() {
        AlertDialog myAlertDialog = thoatAlertDialog();
        myAlertDialog.show();
    }

    /// tạo hộp thoại xác nhận thoát ứng dụng
    private AlertDialog thoatAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Xác Nhận!");
        builder.setMessage("Bạn có muốn hủy bình luận ?");
        builder.setCancelable(false);
        builder.setNegativeButton("Có",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.setNeutralButton("Không", null);


        AlertDialog dialog = builder.create();
        return dialog;
    }
}
