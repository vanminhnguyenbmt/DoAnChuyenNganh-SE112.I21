package com.example.ochutgio.reviewquanan.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ochutgio.reviewquanan.Model.ThanhVienModel;
import com.example.ochutgio.reviewquanan.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;


/**
 * Created by ochutgio on 4/3/2018.
 */

public class DangNhapActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, FirebaseAuth.AuthStateListener {

    Button btnDangNhapGoogle;
    Button btnDangNhapFacebook;
    TextView tvDangKy;
    TextView tvQuenMatKhau;
    Button btnDangNhap;
    EditText edEmail;
    EditText edPassword;

    GoogleApiClient apiClient;
    CallbackManager mCallbackFacebook;
    FirebaseAuth firebaseAuth;
    LoginManager loginManager;
    SharedPreferences sharedPreferences;

    List<String> permissionFacebook = Arrays.asList("email","public_profile");

    ProgressDialog progressDialog;

    public static int RequestGoogleCode = 99;
    public static int SignIn_Flat = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dangnhap);

        firebaseAuth = FirebaseAuth.getInstance();
        mCallbackFacebook = CallbackManager.Factory.create();
        loginManager = LoginManager.getInstance();

//        firebaseAuth.signOut();
        loginManager.logOut();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang đăng nhập");
        progressDialog.setIndeterminate(true);
        //progressDialog.setCancelable(false);

        btnDangNhapGoogle = (Button) findViewById(R.id.btnDangNhapGoogle);
        btnDangNhapFacebook = (Button) findViewById(R.id.btnDangNhapFacebook);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        tvDangKy = (TextView) findViewById(R.id.tvDangKy);
        tvQuenMatKhau =(TextView) findViewById(R.id.tvQuenMatKhau);
        edEmail = (EditText) findViewById(R.id.edEmailDN);
        edPassword = (EditText) findViewById(R.id.edPasswordDN);

        tvDangKy.setOnClickListener((View.OnClickListener) this);
        tvQuenMatKhau.setOnClickListener((View.OnClickListener) this);
        btnDangNhapGoogle.setOnClickListener((View.OnClickListener) this);
        btnDangNhapFacebook.setOnClickListener((View.OnClickListener) this);
        btnDangNhap.setOnClickListener((View.OnClickListener) this);

        khoiTaoGoogleClient();

        sharedPreferences = getSharedPreferences("LuuDangNhap", MODE_PRIVATE);
    }

    /// Khởi tạo google signin và google api client
    private void khoiTaoGoogleClient(){

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    /// đăng nhập facebook
    private void DangNhapFacebook(){
        loginManager.logInWithReadPermissions(this, permissionFacebook);
        loginManager.registerCallback(mCallbackFacebook, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                SignIn_Flat = 2;
                String tokenID = loginResult.getAccessToken().getToken();
                chungThucDangNhapFireBase(tokenID);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }


    /// Mở popup đăng nhập google với apiClient đã tạo
    private void dangNhapGoogle(GoogleApiClient apiClient){
        SignIn_Flat = 1;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(signInIntent, RequestGoogleCode);
    }

    /// gọi hàm đăng nhập
    private void DangNhap(){
        String email =  edEmail.getText().toString();
        String password = edPassword.getText().toString();

        if(email.trim().length() == 0 | password.trim().length() == 0){
            Toast.makeText(DangNhapActivity.this, "vui lòng nhập email hoặc password", Toast.LENGTH_SHORT).show();
        }else {
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(DangNhapActivity.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

    /// kết quả trả về sau khi hoàn thành các bước trong  popup đăng nhập google
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RequestGoogleCode){
            if(resultCode == RESULT_OK){
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount signInAccount = signInResult.getSignInAccount();
                String TokenId = signInAccount.getIdToken();
                chungThucDangNhapFireBase(TokenId);
            }
        }else {
            mCallbackFacebook.onActivityResult(requestCode, resultCode, data);
            Toast.makeText(this, "dang nhap facebook", Toast.LENGTH_SHORT).show();
        }
    }

    /// Hàm chứng thực đăng nhập firebase với TokenID(đại diện cho tài khoản của người dùng)
    private void chungThucDangNhapFireBase(String tokenid){
        if(SignIn_Flat == 1){
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenid, null);
            progressDialog.show();
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(DangNhapActivity.this, "Đăng nhập google thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else if (SignIn_Flat == 2){
            AuthCredential authCredential = FacebookAuthProvider.getCredential(tokenid);
            Log.d("token", tokenid+"");
            progressDialog.show();
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(DangNhapActivity.this, "Đăng nhập facebook thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }


    @Override
    public void onClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.btnDangNhapGoogle:
                dangNhapGoogle(apiClient);
                break;

            case R.id.btnDangNhapFacebook:
                DangNhapFacebook();
                break;
            case R.id.tvDangKy:
                Intent iDangKy = new Intent(DangNhapActivity.this, DangKyActivity.class);
                startActivity(iDangKy);
                break;
            case R.id.tvQuenMatKhau:
                Intent iQuenMatKhau = new Intent(DangNhapActivity.this, QuenMatKhauActivity.class);
                startActivity(iQuenMatKhau);
                break;
            case R.id.btnDangNhap:
                DangNhap();
                break;
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        if( user != null){
            if(user.isEmailVerified() == true){

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("mauser", user.getUid());
                editor.commit();

                final DatabaseReference dataUser = FirebaseDatabase.getInstance().getReference().child("thanhviens");
                progressDialog.show();
                dataUser.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.getValue() == null){
                            ThanhVienModel thanhVienModel = new ThanhVienModel();
                            if(user.getDisplayName() != null){

                                thanhVienModel.setHoten(user.getDisplayName());
                                thanhVienModel.setHinhanh(user.getUid() + ".jpg");
                            }else {

                                thanhVienModel.setHoten(user.getEmail());
                                thanhVienModel.setHinhanh("avatar.png");
                            }

                            new DownLoadImageTask(user.getUid()+".jpg").execute(user.getPhotoUrl().toString());
                            dataUser.child(user.getUid()).setValue(thanhVienModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                        Intent iTrangChu = new Intent(DangNhapActivity.this, TrangChuActivity.class);
                                        startActivity(iTrangChu);
                                        finish();
                                    }
                                }
                            });

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                ///
                Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                Intent iTrangChu = new Intent(DangNhapActivity.this, TrangChuActivity.class);
                startActivity(iTrangChu);
                finish();
            }else {
                Toast.makeText(DangNhapActivity.this, "Vui lòng xác thực email để thực hiện đăng nhập", Toast.LENGTH_SHORT).show();
            }

        }else {

        }
    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {

        String tenhinh;

        public DownLoadImageTask(String tenhinh){
            this.tenhinh = tenhinh;
        }

        /*
            doInBackground(Params... params)
            Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result){
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            //Bitmap b = Bitmap.createScaledBitmap(result, 480, 640, false);
            result.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] data = stream.toByteArray();
            FirebaseStorage.getInstance().getReference().child("User/" + tenhinh).putBytes(data);
        }
    }
}
