/*
 * Copyright 2018 Eclard
 * Copyright Milan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.eclard.exchangestream.restapi;

import com.eclard.exchangestream.model.Account;
import com.eclard.exchangestream.model.Order;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by Milan.
 * On 1/10/18. 22 : 57
 */
public interface ExchangeApiBinanceService {
    String HEADER_X_MBX_APIKEY = "X-MBX-APIKEY";

    @POST("/api/v3/order")
    Call<JsonObject> setOrder(@Header(HEADER_X_MBX_APIKEY) String header, @Body RequestBody body);

    @POST("/api/v3/order/test")
    Call<JsonObject> setTestOrder(@Header(HEADER_X_MBX_APIKEY) String header, @Body RequestBody body);

    @GET("/api/v3/order")
    Call<Order> getOrderStatus(
            @Header(HEADER_X_MBX_APIKEY) String header,
            @Query("symbol") String symbol,
            @Query("orderId") long orderId,
            @Query("origClientOrderId") String origClientOrderId,
            @Query("timestamp") long timestamp,
            @Query("signature") String signature
    );

    @DELETE("/api/v3/order")
    Call<JsonObject> cancelOrder(
            @Header(HEADER_X_MBX_APIKEY) String header,
            @Query("symbol") String symbol,
            @Query("orderId") long orderId,
            @Query("timestamp") long timestamp,
            @Query("signature") String signature
    );

    @GET("/api/v3/openOrders")
    Call<List<Order>> getCurrentOpenOrders(
            @Header(HEADER_X_MBX_APIKEY) String header,
            @Query("symbol") String symbol,
            @Query("timestamp") long timestamp,
            @Query("signature") String signature
    );

    @GET("/api/v3/allOrders")
    Call<List<Order>> getAllOrders(
            @Header(HEADER_X_MBX_APIKEY) String header,
            @Query("symbol") String symbol,
            @Query("timestamp") long timestamp,
            @Query("limit") int limit,
            @Query("signature") String signature
    );

    @GET("/api/v3/account")
    Call<Account> getAccount(
            @Header(HEADER_X_MBX_APIKEY) String header,
            @Query("timestamp") long timestamp,
            @Query("signature") String signature
    );

    @GET("/api/v3/myTrades")
    Call<JsonArray> getAccountTradeList(
            @Header(HEADER_X_MBX_APIKEY) String header,
            @Query("symbol") String symbol,
            @Query("limit") int limit,
            @Query("timestamp") long timestamp,
            @Query("signature") String signature
    );

    @GET("/api/v1/time")
    Call<JsonObject> getServerTime();

    @GET("/api/v1/ticker/allPrices")
    Call<JsonArray> getAllPrices();

    @GET("/api/v1/ticker/24hr")
    Call<JsonObject> get24hPrice(@Query("symbol") String symbol);
}