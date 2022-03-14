package com.guru.paytmdevelopertest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {
    @FormUrlEncoded
   // @POST("live")
    @POST("index")
    Call<Checksum> getChecksum(
            // @Field("MID") String mId,
            @Field("ORDER_ID") String orderId,
            @Field("CUST_ID") String custId,
            @Field("TXN_AMOUNT") String txnAmount
    );

}