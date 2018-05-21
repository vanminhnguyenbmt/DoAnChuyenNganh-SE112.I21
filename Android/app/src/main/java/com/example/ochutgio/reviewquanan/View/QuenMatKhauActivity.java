package com.example.ochutgio.reviewquanan.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ochutgio.reviewquanan.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

/**
 * Created by ochutgio on 4/4/2018.
 */

public class QuenMatKhauActivity extends AppCompatActivity {

    EditText edEmail;
    Button btnSendme;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quenmatkhau);

        edEmail = (EditText) findViewById(R.id.edEmailKP);
        btnSendme = (Button) findViewById(R.id.btnSendMe);
        firebaseAuth = FirebaseAuth.getInstance();

        btnSendme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString();
                if(isEmail(email)){
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(QuenMatKhauActivity.this, "Đã gửi email khôi phục mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(QuenMatKhauActivity.this, "Địa chỉ email không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
