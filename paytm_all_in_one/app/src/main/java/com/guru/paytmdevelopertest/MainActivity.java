package com.guru.paytmdevelopertest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.paytm.pgsdk.TransactionManager;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressBar progressBar;
    String txnAmountString;
    Button btnPayNow;
    Integer ActivityRequestCode = 2;
    CheckBox environment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnPayNow = (Button) findViewById(R.id.txnProcessBtn);
        btnPayNow.setOnClickListener(MainActivity.this);
        TextView txnAmount = (TextView) findViewById(R.id.txnAmountId);

        txnAmountString = txnAmount.getText().toString();
        environment = (CheckBox) findViewById(R.id.environmentCheckbox);
        environment.setChecked(true);
    }

    private String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
    //Paytm Integration method
    private void generateCheckSum() {
        Double totalAmountPayment = Double.parseDouble(txnAmountString);
        Log.d("SSSSSS", "GGGG" + String.format("%.2f", totalAmountPayment));
        //creating a retrofit object.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.PAYTM_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api apiService = retrofit.create(Api.class);
        final Paytm paytm = new Paytm(
                Constant.M_ID,
                String.format("%.2f", totalAmountPayment)
               // CallbackURL
        );

        //creating a call object from the apiService
        Call<Checksum> call = apiService.getChecksum(
                // paytm.getmId(),
                paytm.getOrderId(),
                paytm.getCustId(),
                paytm.getTxnAmount()
        );
        Log.d("dsdsd","jhjhjhj"+call.toString());
        //making the call to generate checksum
        call.enqueue(new Callback<Checksum>() {
            @Override
            public void onResponse(Call<Checksum> call, Response<Checksum> response) {
                Log.d("SSDDDD", "DDDDDDKK" + response.body().getChecksumHash() + "gg" + response.body().getPaytStatus() + "ggl" + response.body().getOrderId());
                TranstionProcess(response.body().getChecksumHash(),response.body().getOrderId(),paytm.getTxnAmount());
            }
            @Override
            public void onFailure(Call<Checksum> call, Throwable t) {
                Log.d("SSDDDD", "DDDDDDFailer" + t.getMessage());
            }
        });
    }
    
    public void TranstionProcess(String txnTokenString,String orderId,String txnAmountString){
        progressBar.setVisibility(View.VISIBLE);
        String host = "https://securegw.paytm.in/";
        if(environment.isChecked()){
            host = "https://securegw-stage.paytm.in/";
        }
        progressBar.setVisibility(View.GONE);
            String callBackUrl = host + "theia/paytmCallback?ORDER_ID="+orderId;
            PaytmOrder paytmOrder = new PaytmOrder(orderId, Constant.M_ID, txnTokenString, txnAmountString, callBackUrl);
            TransactionManager transactionManager = new TransactionManager(paytmOrder, new PaytmPaymentTransactionCallback(){

                @Override
                public void onTransactionResponse(Bundle bundle) {
                    Toast.makeText(MainActivity.this, "Response (onTransactionResponse) : "+bundle.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void networkNotAvailable() {

                }

                @Override
                public void onErrorProceed(String s) {

                }

                @Override
                public void clientAuthenticationFailed(String s) {

                }

                @Override
                public void someUIErrorOccurred(String s) {

                }

                @Override
                public void onErrorLoadingWebPage(int i, String s, String s1) {

                }

                @Override
                public void onBackPressedCancelTransaction() {

                }

                @Override
                public void onTransactionCancel(String s, Bundle bundle) {

                }
            });
            transactionManager.setAppInvokeEnabled(false);
            transactionManager.setShowPaymentUrl(host + "theia/api/v1/showPaymentPage");
            transactionManager.startTransaction(this, ActivityRequestCode);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityRequestCode && data != null) {
            Toast.makeText(this, data.getStringExtra("nativeSdkForMerchantMessage") + data.getStringExtra("response"), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txnProcessBtn :
                generateCheckSum();
                break;
        }
    }
}
