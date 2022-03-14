package com.guru.paytmdevelopertest;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class Paytm {

    @SerializedName("MID")
    String mId;

    @SerializedName("ORDER_ID")
    String orderId;

    @SerializedName("CUST_ID")
    String custId;

    @SerializedName("TXN_AMOUNT")
    String txnAmount;



    public Paytm(String mId, String txnAmount) {
        this.mId = mId;
        this.orderId = generateString();
        this.custId = generateString();
        this.txnAmount = txnAmount;

        Log.d("orderId", orderId);
        Log.d("customerId", custId);
    }

    public String getmId() {
        return mId;
    }
    public String getOrderId() {
        return orderId;
    }
    public String getCustId() {
        return custId;
    }
    public String getTxnAmount() {
        return txnAmount;
    }
    private String generateString() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}
