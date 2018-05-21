package com.example.ochutgio.reviewquanan.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.ochutgio.reviewquanan.Model.ChiNhanhQuanAnModel;
import com.example.ochutgio.reviewquanan.Model.MonAnModel;
import com.example.ochutgio.reviewquanan.Model.QuanAnModel;
import com.example.ochutgio.reviewquanan.Model.ThemThucDonModel;
import com.example.ochutgio.reviewquanan.Model.ThucDonModel;
import com.example.ochutgio.reviewquanan.Model.TienIchModel;
import com.example.ochutgio.reviewquanan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by ochutgio on 5/8/2018.
 */

public class ThemQuanAnActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    final int REQUEST_IMVVIDDEO = 222;
    final int REQUEST_IMVTHUCDON = 333;
    final int REQUEST_IMV1 = 111;

    Button btnThemQuanAn;
    Button btnGioMoCua;
    Button btnGioDongCua;
    Spinner spinerKhuVuc;
    ImageView imvTam;

    LinearLayout khungTienIch;
    LinearLayout khungChiNhanh;
    LinearLayout containerChiNhanh;
    LinearLayout containerThucDon;

    EditText edTenQuanAn;
    EditText edGiaToiThieu;
    EditText edGiaToiDa;
    EditText edTenChiNhanh;

    ImageView imvHinhQuanAn1;
    ImageView imvHinhQuanAn2;
    ImageView imvHinhQuanAn3;
    ImageView imvHinhQuanAn4;
    ImageView imvHinhQuanAn5;
    ImageView imvHinhQuanAn6;


    Toolbar toolbar;

    String giomocua = "09:00";
    String giodongcua = "21:00";

    List<Bitmap> hinhmonanList;
    List<String> tienichList;
    List<String> khuvucList;
    List<String> thucdonList;

    List<ThucDonModel> thucDonModelList;
    List<ThemThucDonModel> themThucDonModelList;

    Uri hinhquanan;
    String khuvuc;
    String maquanan;

    ProgressDialog progress;
    ArrayAdapter<String> adapterKhuVuc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_themquanan);
        progress = new ProgressDialog(this);
        progress.setMessage("Đang xử lý...");

        btnThemQuanAn = (Button) findViewById(R.id.btnThemQuanAn);
        btnGioDongCua = (Button) findViewById(R.id.btnGioDongCua);
        btnGioMoCua = (Button) findViewById(R.id.btnGioMoCua);
        spinerKhuVuc = (Spinner) findViewById(R.id.spinerKhuVuc);
        khungTienIch = (LinearLayout) findViewById(R.id.khungTienIch);

        containerThucDon = (LinearLayout) findViewById(R.id.containerThucDon);

        imvHinhQuanAn1 = (ImageView) findViewById(R.id.imvHinhQuanAn1);

        edTenQuanAn = (EditText) findViewById(R.id.edTenQuanAn);
        edGiaToiThieu = (EditText) findViewById(R.id.edGiaToiThieu);
        edGiaToiDa = (EditText) findViewById(R.id.edGiaToiDa);
        edTenChiNhanh = (EditText) findViewById(R.id.edTenChiNhanh);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        /// set toolbar
        toolbar.setTitle("");
        setSupportActionBar(toolbar);



        hinhmonanList = new ArrayList<>();
        thucDonModelList = new ArrayList<>();
        themThucDonModelList = new ArrayList<>();
        tienichList = new ArrayList<>();
        khuvucList = new ArrayList<>();
        thucdonList = new ArrayList<>();

        adapterKhuVuc = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, khuvucList);
        spinerKhuVuc.setAdapter(adapterKhuVuc);
        adapterKhuVuc.notifyDataSetChanged();


        CloneThucDon();

        btnThemQuanAn.setOnClickListener(this);
        btnGioMoCua.setOnClickListener(this);
        btnGioDongCua.setOnClickListener(this);
        spinerKhuVuc.setOnItemSelectedListener(this);
        imvHinhQuanAn1.setOnClickListener(this);
        //imvVideo.setOnClickListener(this);

        ///
        LayDanhSachTienIch();
        LayDanhSachKhuVuc();

    }


    private  void ThemQuanAn(){

        String tenquanan = edTenQuanAn.getText().toString();
        String chinhanh = edTenChiNhanh.getText().toString();
        String giatoithieuinput = edGiaToiThieu.getText().toString();
        String giatoidainput = edGiaToiDa.getText().toString();

        if(tenquanan.trim().length() == 0 | chinhanh.trim().length() == 0 | giatoithieuinput.trim().length() == 0 | giatoidainput.trim().length() == 0){
            Toast.makeText(ThemQuanAnActivity.this, "vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        }else {
            long giatoithieu = Long.parseLong(giatoithieuinput);
            long giatoida = Long.parseLong(giatoidainput);

            final DatabaseReference noteRoot = FirebaseDatabase.getInstance().getReference();
            DatabaseReference noteQuanAn = noteRoot.child("quanans");
            final DatabaseReference noteKhuVuc = noteRoot.child("khuvucs");
            DatabaseReference noteChiNhanh = noteRoot.child("chinhanhquanans");

            maquanan = noteQuanAn.push().getKey();

            String urlGeocodingApi = "https://maps.googleapis.com/maps/api/geocode/json?address=" + chinhanh.replace(" ", "%20") + "&KEY=AIzaSyBkbgUbHNvH_6drCmpx6btkv7B8PoQjaIU";
            DownloadToaDo downloadToaDo = new DownloadToaDo();
            downloadToaDo.execute(urlGeocodingApi);


            QuanAnModel quanAnModel = new QuanAnModel();
            quanAnModel.setLuotthich(0);
            quanAnModel.setTenquanan(tenquanan);
            quanAnModel.setGiatoithieu(giatoithieu);
            quanAnModel.setGiatoida(giatoida);
            quanAnModel.setGiomocua(giomocua);
            quanAnModel.setGiodongcua(giodongcua);
            quanAnModel.setGiaohang(false);
            quanAnModel.setTienich(tienichList);
            quanAnModel.setVideogioithieu("");

            if(maquanan != null){
                /// thêm quán ăn
                progress.show();
                noteQuanAn.child(maquanan).setValue(quanAnModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        noteKhuVuc.child(khuvuc).push().setValue(maquanan);
                    }
                });

                /// upload hình ảnh quán ăn
                if(hinhquanan != null){
                    FirebaseStorage.getInstance().getReference().child("Photo/" + hinhquanan.getLastPathSegment()).putFile(hinhquanan).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            noteRoot.child("hinhanhquanans").child(maquanan).push().setValue(hinhquanan.getLastPathSegment());
                        }
                    });
                }

                /// thêm và upload thực đơn quán ăn
                if(themThucDonModelList.size() > 0){
                    for(int i = 0; i < themThucDonModelList.size(); i++){
                        noteRoot.child("thucdonquanans").child(maquanan).child(themThucDonModelList.get(i).getMathucdon()).push().setValue(themThucDonModelList.get(i).getMonAnModel());
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        Bitmap bitmap = hinhmonanList.get(i);
                        Bitmap b = Bitmap.createScaledBitmap(bitmap, 480, 640, false);
                        b.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] data = stream.toByteArray();
                        FirebaseStorage.getInstance().getReference().child("Photo/" + themThucDonModelList.get(i).getMonAnModel().getHinhanh()).putBytes(data);
                    }
                    progress.dismiss();
                    Toast.makeText(ThemQuanAnActivity.this, "thêm quán ăn thành công", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(ThemQuanAnActivity.this, "thêm quán ăn thất bại,\nvui lòng kiểm tra lại kết nối internet", Toast.LENGTH_SHORT).show();
            }

        }

    }

    class DownloadToaDo extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line + "\n");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("kiemtra", s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonResult = jsonObject.getJSONArray("results");

                if(jsonResult != null){
                    for(int i = 0; i < jsonResult.length(); i++){
                        JSONObject object = jsonResult.getJSONObject(i);

                        if(object != null){
                            String address = object.getString("formatted_address");
                            JSONObject geometry = object.getJSONObject("geometry");
                            if(geometry != null){
                                JSONObject location = geometry.getJSONObject("location");
                                double latitude = (double) location.get("lat");
                                double longitude = (double) location.get("lng");
                                Log.d("kiemtra", "" + latitude + " - " + longitude);

                                ChiNhanhQuanAnModel chiNhanhQuanAnModel = new ChiNhanhQuanAnModel();
                                chiNhanhQuanAnModel.setDiachi(address);
                                chiNhanhQuanAnModel.setLatitude(latitude);
                                chiNhanhQuanAnModel.setLongitude(longitude);
                                FirebaseDatabase.getInstance().getReference().child("chinhanhquanans").child(maquanan).push().setValue(chiNhanhQuanAnModel);
                            }
                        }else {
                            Toast.makeText(ThemQuanAnActivity.this, "địa chỉ quán ăn không tồn tại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(ThemQuanAnActivity.this, "địa chỉ quán ăn không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e){
                e.printStackTrace();
                Toast.makeText(ThemQuanAnActivity.this, "địa chỉ quán ăn không tồn tại", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(final View view) {

        Calendar calendar = Calendar.getInstance();
        final int gio = calendar.get(Calendar.HOUR_OF_DAY);
        int phut = calendar.get(Calendar.MINUTE);

        switch (view.getId()){
            case R.id.btnThemQuanAn:
                ThemQuanAn();
                break;
            case R.id.btnGioMoCua:
                TimePickerDialog timePickerDialogMoCua = new TimePickerDialog(ThemQuanAnActivity.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        giomocua = i + ":" + i1;
                        ((Button)view).setText(giomocua);
                    }
                }, gio, phut, true);

                timePickerDialogMoCua.show();
                break;
            case  R.id.btnGioDongCua:
                TimePickerDialog timePickerDialogDongCua = new TimePickerDialog(ThemQuanAnActivity.this, new TimePickerDialog.OnTimeSetListener(){
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        giodongcua = i + ":" + i1;
                        ((Button)view).setText(giodongcua);
                    }
                }, gio, phut, true);

                timePickerDialogDongCua.show();
                break;

            case R.id.imvHinhQuanAn1:
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Chọn hình"), REQUEST_IMV1);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_IMVTHUCDON:
                if(data != null){
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imvTam.setImageBitmap(bitmap);
                    hinhmonanList.add(bitmap);
                }
                break;
            case REQUEST_IMV1:
                if( resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    imvHinhQuanAn1.setImageURI(uri);
                    hinhquanan = uri;
                }
                break;
        }
    }

    private void CloneThucDon(){

        View v = LayoutInflater.from(ThemQuanAnActivity.this).inflate(R.layout.layout_clone_thucdon, null);

        final Spinner spinnerThucDon = (Spinner) v.findViewById(R.id.spinerThucDon);
        Button btnThemThucDon = (Button) v.findViewById(R.id.btnThemThucDon);
        final EditText edTenMonAn = (EditText) v.findViewById(R.id.edTenMonAn);
        final EditText edGiaTien = (EditText) v.findViewById(R.id.edGiaTien);
        ImageView imvChupHinh = (ImageView) v.findViewById(R.id.imvChupHinh);
        imvTam = imvChupHinh;

        ArrayAdapter<String> adapterThucDon = new ArrayAdapter<String>(ThemQuanAnActivity.this, android.R.layout.simple_list_item_1, thucdonList);
        spinnerThucDon.setAdapter(adapterThucDon);
        adapterThucDon.notifyDataSetChanged();

        if(thucDonModelList.size() == 0){
            LayDanhSachThucDon(adapterThucDon);
        }

        imvChupHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMVTHUCDON);
            }
        });

        btnThemThucDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tenhinh = String.valueOf(Calendar.getInstance().getTimeInMillis()) + ".jpg" ;
                String tenmonan = edTenMonAn.getText().toString();
                String giatien = edGiaTien.getText().toString();
                int position = spinnerThucDon.getSelectedItemPosition();
                String mathucdon = thucDonModelList.get(position).getMathucdon();

                if(tenmonan.trim().length() > 0 & giatien.trim().length() > 0){
                    MonAnModel monAnModel = new MonAnModel();
                    monAnModel.setTenmon(tenmonan);
                    monAnModel.setGiatien(Long.parseLong(giatien));
                    monAnModel.setHinhanh(tenhinh);

                    ThemThucDonModel themThucDonModel = new ThemThucDonModel();
                    themThucDonModel.setMathucdon(mathucdon);
                    themThucDonModel.setMonAnModel(monAnModel);
                    themThucDonModelList.add(themThucDonModel);
                    view.setVisibility(View.GONE);
                    CloneThucDon();
                }else {
                    Toast.makeText(ThemQuanAnActivity.this, "vui lòng nhập thông tin món ăn", Toast.LENGTH_SHORT).show();
                }
            }
        });

        containerThucDon.addView(v);
    }

    private void LayDanhSachTienIch(){
        FirebaseDatabase.getInstance().getReference().child("quanlytienichs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot valueTienIch : dataSnapshot.getChildren()){
                    final String matienich = valueTienIch.getKey();
                    TienIchModel tienIchModel = valueTienIch.getValue(TienIchModel.class);
                    tienIchModel.setMatienich(matienich);

                    CheckBox checkBox = new CheckBox(ThemQuanAnActivity.this);
                    checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    checkBox.setTag(tienIchModel.getMatienich());
                    checkBox.setText(tienIchModel.getTentienich());
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b == true){
                                tienichList.add(matienich);
                            }else {
                                tienichList.remove(matienich);
                            }
                        }
                    });
                    khungTienIch.addView(checkBox);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void LayDanhSachKhuVuc(){
        FirebaseDatabase.getInstance().getReference().child("khuvucs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot valueKhuVuc : dataSnapshot.getChildren()){
                    String tenkhuvuc = valueKhuVuc.getKey();
                    khuvucList.add(tenkhuvuc);
                }
                adapterKhuVuc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void LayDanhSachThucDon(final ArrayAdapter<String> adapter){
        FirebaseDatabase.getInstance().getReference().child("thucdons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot valueThucDon : dataSnapshot.getChildren()){
                    ThucDonModel thucDonModel = new  ThucDonModel();
                    String tenthucdon = valueThucDon.getValue(String.class);
                    String mathucdon = valueThucDon.getKey();

                    thucdonList.add(tenthucdon);
                    thucDonModel.setMathucdon(mathucdon);
                    thucDonModel.setTenthucdon(tenthucdon);
                    thucDonModelList.add(thucDonModel);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.spinerKhuVuc:
                khuvuc = khuvucList.get(i);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
        builder.setMessage("Bạn có muốn thoát màn hình thêm quán ăn ?");
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
