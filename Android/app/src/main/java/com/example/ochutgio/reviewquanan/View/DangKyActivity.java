package com.example.ochutgio.reviewquanan.View;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ochutgio.reviewquanan.Controller.DangKyController;
import com.example.ochutgio.reviewquanan.Model.ThanhVienModel;
import com.example.ochutgio.reviewquanan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ochutgio on 4/4/2018.
 */

public class DangKyActivity extends AppCompatActivity {

    Button btnDangKy;
    EditText edEmail;
    EditText edPassword;
    EditText edRepeatPassword;

    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangky);

        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        edEmail = (EditText) findViewById(R.id.edEmail);
        edPassword = (EditText) findViewById(R.id.edPassword);
        edRepeatPassword = (EditText) findViewById(R.id.edRepeatPassword);

        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = edEmail.getText().toString();
                String matkhau1 = edPassword.getText().toString();
                String matkhau2 = edRepeatPassword.getText().toString();

                progressDialog.setMessage("Đang xử lý");
                progressDialog.setIndeterminate(true);

                if(email.trim().length() == 0 | matkhau1.trim().length() == 0){
                    Toast.makeText(DangKyActivity.this, "vui lòng nhập email hoặc password", Toast.LENGTH_SHORT).show();
                }else {
                    if(!isEmail(email)){
                        Toast.makeText(DangKyActivity.this, "địa chỉ mail không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                    else if(!matkhau1.equals(matkhau2)){
                        Toast.makeText(DangKyActivity.this, "Hai mật khẩu cần trùng nhau", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progressDialog.show();
                        firebaseAuth.createUserWithEmailAndPassword(email, matkhau1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){
                                    progressDialog.dismiss();

                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Toast.makeText(DangKyActivity.this, "Đăng ký thành công vui lòng xác thực email để đăng nhập", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }else {
                                    progressDialog.dismiss();
                                    Toast.makeText(DangKyActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                }

                            }



                        });
                    }
                }
            }
        });
    }

    private boolean isEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
