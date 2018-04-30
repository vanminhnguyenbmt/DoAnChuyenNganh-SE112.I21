package com.bin.lazada.Model.DangNhap_DangKy;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.bin.lazada.ConnectInternet.DownloadJSON;
import com.bin.lazada.View.TrangChu.TrangChuActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ModelDangNhap {
    AccessToken accessToken;
    AccessTokenTracker accessTokenTracker;

    public AccessToken LayTokenFacebookHienTai() {

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
            }
        };

        accessToken = AccessToken.getCurrentAccessToken();

        return accessToken;
    }

    public String LayCacheDangNhap(Context context) {
        SharedPreferences cacheDangNhap = context.getSharedPreferences("dangnhap", Context.MODE_PRIVATE);
        String tennv = cacheDangNhap.getString("tennv", "");

        return tennv;
    }

    public void CapNhapCacheDangNhap(Context context, String tennv) {
        SharedPreferences cacheDangNhap = context.getSharedPreferences("dangnhap", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = cacheDangNhap.edit();
        editor.putString("tennv", tennv);
        editor.commit();
    }

    //hàm kiểm tra đăng nhập trả về true nếu thành công và ngược lại
    public Boolean KiemTraDangNhap(Context context, String tendangnhap, String matkhau) {
        Boolean kiemtra = false;
        String duongdan = TrangChuActivity.SERVER_NAME;
        List<HashMap<String, String>> attrs = new ArrayList<>();

        //gửi tham số ham lên PHP service
        HashMap<String, String> hsHam = new HashMap<>();
        hsHam.put("ham", "KiemTraDangNhap");

        //gửi tên đăng nhập lên PHP service
        HashMap<String, String> hsTenDN = new HashMap<>();
        hsTenDN.put("tendangnhap", tendangnhap);

        //gửi mật khẩu lên PHP service
        HashMap<String, String> hsMatKhau = new HashMap<>();
        hsMatKhau.put("matkhau", matkhau);

        attrs.add(hsHam);
        attrs.add(hsTenDN);
        attrs.add(hsMatKhau);

        DownloadJSON downloadJSON = new DownloadJSON(duongdan, attrs);
        downloadJSON.execute();

        try {
            String dulieu = downloadJSON.get();
            JSONObject jsonObject = new JSONObject(dulieu);
            String jsonKetQua = jsonObject.getString("ketqua");

            if(jsonKetQua.equals("true")) {
                kiemtra = true;
                String tennv = jsonObject.getString("tennv");

                CapNhapCacheDangNhap(context, tennv);


            }else {
                kiemtra = false;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return kiemtra;
    }

    public GoogleApiClient LayGoogleApiClient(Context context, GoogleApiClient.OnConnectionFailedListener failedListener) {
        GoogleApiClient mGoogleApiClient;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage((AppCompatActivity)context, failedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        return mGoogleApiClient;
    }

    public GoogleSignInResult LayThongTinDangNhapGoogle(GoogleApiClient googleApiClient) {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()) {
            return opr.get();
        }else {
            return null;
        }
    }

    public void HuyTokenTracker() {
        accessTokenTracker.stopTracking();
    }
}
