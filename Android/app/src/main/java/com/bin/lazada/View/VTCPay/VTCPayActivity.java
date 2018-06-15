package com.bin.lazada.View.VTCPay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bin.lazada.R;
import com.bin.lazada.View.ThanhToan.ThanhToanActivity;
import com.lib.vtcpay.sdk.ICallBackPayment;
import com.lib.vtcpay.sdk.InitModel;
import com.lib.vtcpay.sdk.PaymentModel;
import com.lib.vtcpay.sdk.VTCPaySDK;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class VTCPayActivity extends AppCompatActivity {
    private EditText edAmount, edOrderCode, edAppID, edSecreteKey, edReceiveAccount, edDescription;
    private RadioButton rbVND, rbUSD;
    private InitModel initModel;
    String madoitac, tennguoinhan, diachi, sodt;
    float tongtien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_vtcpay);
        initView();
        //init model
        initModel = new InitModel();
        rbVND.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    initModel.setCurrency(VTCPaySDK.VND);
                }
            }
        });
        rbUSD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    initModel.setCurrency(VTCPaySDK.USD);
                }
            }
        });
        madoitac = getIntent().getStringExtra("madoitac");
        tongtien = getIntent().getFloatExtra("tongtien", 0);
        tennguoinhan = getIntent().getStringExtra("tennguoinhan");
        diachi = getIntent().getStringExtra("diachi");
        sodt = getIntent().getStringExtra("sodt");

        edAmount.setText(String.valueOf((int) tongtien));
        edOrderCode.setText(madoitac);
        edAppID.setText("500014025");
        edSecreteKey.setText("123456aA@");
        edReceiveAccount.setText("0963465816");
        edDescription.setText("Vui lòng bấm PAYMENT để tiếp tục");

//        cbIsHiddenPaymentVTCPay = (CheckBox) findViewById(R.id.cbIsHiddenPaymentVTCPay);
//        cbIsHiddenPaymentForeignBank = (CheckBox) findViewById(R.id.cbIsHiddenPaymentForeignBank);
//        cbIsHiddenPaymentDomesticBank = (CheckBox) findViewById(R.id.cbIsHiddenPaymentDomesticBank);
    }

    private void initView(){
        edAmount = (EditText) findViewById(R.id.edAmount);
        edOrderCode = (EditText) findViewById(R.id.edOrderCode);
        edAppID = (EditText) findViewById(R.id.edAppID);
        edSecreteKey = (EditText) findViewById(R.id.edSecreteKey);
        edReceiveAccount = (EditText) findViewById(R.id.edReceiveAccount);
        edDescription = (EditText) findViewById(R.id.edDescription);
        rbVND = (RadioButton) findViewById(R.id.rbVND);
        rbUSD = (RadioButton) findViewById(R.id.rbUSD);
    }

    public void onClickPayment(View view) {
        if (checkParamsNull()) return;
        initModel.setSandbox(true);//[Required] set enviroment test, default is true
        initModel.setAmount(Long.parseLong(edAmount.getText().toString())); //[Required] your amount
        initModel.setOrderCode(edOrderCode.getText().toString());//[Required] your order code
        initModel.setAppID(Long.parseLong(edAppID.getText().toString())); //[Required] your AppID that registered with VTC
        initModel.setSecretKey(edSecreteKey.getText().toString()); //[Required] your secret key that registered with VTC
        initModel.setReceiverAccount(edReceiveAccount.getText().toString());//[Required] your account
        initModel.setDescription(edDescription.getText().toString()); //[Option] description
        //initModel.setCurrency(VTCPaySDK.VND);//[Option] set currency, default is VND
        initModel.setDrawableLogoMerchant(R.mipmap.ic_launcher); //[Option] Your logo
//        initModel.setHiddenForeignBank(cbIsHiddenPaymentForeignBank.isChecked());//[Option] hidden foreign bank
//        initModel.setHiddenPayVTC(cbIsHiddenPaymentVTCPay.isChecked());//[Option] hidden pay vtc
//        initModel.setHiddenDomesticBank(cbIsHiddenPaymentDomesticBank.isChecked());//[Option] hidden domestic bank
        VTCPaySDK.getInstance().setInitModel(initModel); //init model

        VTCPaySDK.getInstance().payment(this,
                new ICallBackPayment() {
                    @Override
                    public void onPaymentSuccess(PaymentModel paymentModel) {
                        Toast.makeText(
                                VTCPayActivity.this,
                                "payment success transctionID "
                                        + paymentModel.getTransactionID(),
                                Toast.LENGTH_SHORT).show();
                        Log.d("vtcpay", "Payment success transctionID " + paymentModel.getTransactionID());
                        Intent iThanhToan = new Intent(VTCPayActivity.this, ThanhToanActivity.class);
                        iThanhToan.putExtra("machuyenkhoan", paymentModel.getTransactionID());
                        iThanhToan.putExtra("chonhinhthuc", 1);
                        iThanhToan.putExtra("madoitac", madoitac);
                        iThanhToan.putExtra("checkpayment", true);
                        iThanhToan.putExtra("tennguoinhan", tennguoinhan);
                        iThanhToan.putExtra("diachi", diachi);
                        iThanhToan.putExtra("sodt", sodt);
                        startActivity(iThanhToan);
                    }

                    @Override
                    public void onPaymentError(int errorCode, String errorMessage, String bankName) {
                        Toast.makeText(VTCPayActivity.this,
                                "Payment error " + errorMessage, Toast.LENGTH_SHORT)
                                .show();
                        Log.d("vtcpay", "Payment error " + errorMessage);
                    }

                    @Override
                    public void onPaymentCancel() {
                        // TODO Auto-generated method stub
                        Toast.makeText(VTCPayActivity.this, "Payment cancel ",
                                Toast.LENGTH_SHORT).show();
                    }

//					@Override
//					public void onPaymentError(String error) {
//						// TODO Auto-generated method stub
//						Toast.makeText(MainActivity.this,
//								"Payment error " + error, Toast.LENGTH_SHORT)
//								.show();
//					}
                });
    }

    private boolean checkParamsNull() {
        if (TextUtils.isEmpty(edAmount.getText().toString())) {
            Toast.makeText(VTCPayActivity.this, "Please input amount", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(edOrderCode.getText().toString())) {
            Toast.makeText(VTCPayActivity.this, "Please input OrderCode", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(edAppID.getText().toString())) {
            Toast.makeText(VTCPayActivity.this, "Please input AppID", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(edSecreteKey.getText().toString())) {
            Toast.makeText(VTCPayActivity.this, "Please input SecreteKey", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(edReceiveAccount.getText().toString())) {
            Toast.makeText(VTCPayActivity.this, "Please input ReceiveAccount", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
}
