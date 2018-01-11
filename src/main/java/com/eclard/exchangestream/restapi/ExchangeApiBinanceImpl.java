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

import com.eclard.exchangestream.callback.ExchangeSuccessCallback;
import com.eclard.exchangestream.constant.BinanceRouter;
import com.eclard.exchangestream.constant.Currency;
import com.eclard.exchangestream.exception.ExchangeApiException;
import com.eclard.exchangestream.model.Account;
import com.eclard.exchangestream.model.Order;
import com.eclard.exchangestream.callback.ExchangeFailCallback;
import com.eclard.exchangestream.util.ExchangeSecurity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

import static com.eclard.exchangestream.CallbackMapper.getListOrderCallback;

/**
 * Created by Milan.
 * On 1/10/18. 23 : 10
 */
public class ExchangeApiBinanceImpl implements ExchangeApiBinance {
    private ExchangeApiBinanceService service;
    private String apiKey;
    private String secretKey;

    private ExchangeApiBinanceImpl(String apiKey, String secretKey) {
        this();
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    private ExchangeApiBinanceImpl() {
        this.service = getApiService();
    }

    /**
     * Creates new instance of ExchangeApiBinance object for communication with https Binance API.
     * This call requires api key and secret in order to use signed routes.
     *
     * @param apiKey API key.
     * @param secretKey Client Secret.
     */
    public static ExchangeApiBinance newInstance(String apiKey, String secretKey) {
        return new ExchangeApiBinanceImpl(apiKey, secretKey);
    }

    /**
     * Creates new instance of ExchangeApiBinance object for communication with https Binance API.
     * With this instance it is only possible to communicate with not
     * signed routes.
     */
    public static ExchangeApiBinance newInstance() {
        return new ExchangeApiBinanceImpl();
    }

    @Override
    public void order(Order order, ExchangeSuccessCallback<JsonObject> successCallback, ExchangeFailCallback failedCallback) {
        if (order == null)
            throw new ExchangeApiException("Order object cannot be null!");

        if (apiKey == null || secretKey == null)
            throw new ExchangeApiException("Api-Key or/and Secret cannot be null!");

        Call<JsonObject> call = this.service.setOrder(this.apiKey, getRequestBody(order));
        call.enqueue(getListOrderCallback(successCallback, failedCallback));
    }

    @Override
    public void setTestOrder(Order order, ExchangeSuccessCallback<JsonObject> successCallback, ExchangeFailCallback failedCallback) {
        if (order == null)
            throw new ExchangeApiException("Order object cannot be null!");

        if (apiKey == null || secretKey == null)
            throw new ExchangeApiException("Api-Key or/and Secret cannot be null!");

        Call<JsonObject> call = this.service.setTestOrder(this.apiKey, getRequestBody(order));
        call.enqueue(getListOrderCallback(successCallback, failedCallback));
    }

    @Override
    public void getOrderStatus(Order order, ExchangeSuccessCallback<Order> successCallback, ExchangeFailCallback failedCallback) {
        if (order == null)
            throw new ExchangeApiException("Order object cannot be null!");

        if (apiKey == null || secretKey == null)
            throw new ExchangeApiException("Api-Key or/and Secret cannot be null!");

        if (order.getOrigClientOrderId() == null && order.getOrderId() == 0)
            throw new ExchangeApiException("Either orderId or origClientOrderId must be sent.");

        String signature = ExchangeSecurity.encodeHMACSHA256(this.secretKey, order.toRequestBody());
        Call<Order> call = this.service.getOrderStatus(this.apiKey, order.getSymbol(),
                order.getOrderId(), order.getOrigClientOrderId(),
                order.getTimestamp(), signature);
        call.enqueue(getListOrderCallback(successCallback, failedCallback));
    }

    @Override
    public void cancelOrder(Order order, ExchangeSuccessCallback<JsonObject> successCallback, ExchangeFailCallback failedCallback) {
        if (order.getSymbol() == null)
            throw new ExchangeApiException("Symbol cannot be null!");

        if (order.getTimestamp() == 0)
            order.setTimestamp(System.currentTimeMillis());

        String signature = ExchangeSecurity.encodeHMACSHA256(this.secretKey, order.toRequestBody());
        Call<JsonObject> call = this.service.cancelOrder(this.apiKey, order.getSymbol(), order.getOrderId()
                , order.getTimestamp(), signature);
        call.enqueue(getListOrderCallback(successCallback, failedCallback));
    }

    @Override
    public void getCurrentOpenOrders(Order order, ExchangeSuccessCallback<List<Order>> successCallback, ExchangeFailCallback failedCallback) {
        if (order.getSymbol() == null)
            throw new ExchangeApiException("Symbol cannot be null!");

        if (order.getTimestamp() == 0)
            order.setTimestamp(System.currentTimeMillis());

        String signature = ExchangeSecurity.encodeHMACSHA256(this.secretKey, order.toRequestBody());
        Call<List<Order>> call = this.service.getCurrentOpenOrders(this.apiKey, order.getSymbol(),
                order.getTimestamp(), signature);
        call.enqueue(getListOrderCallback(successCallback, failedCallback));
    }

    @Override
    public void getAllOrders(Order order, int limit, ExchangeSuccessCallback<List<Order>> successCallback, ExchangeFailCallback failedCallback) {
        if (order.getSymbol() == null)
            throw new ExchangeApiException("Symbol cannot be null!");

        if (order.getTimestamp() == 0)
            order.setTimestamp(System.currentTimeMillis());

        // Default 500; max 500.
        if (limit > 500 || limit <= 0)
            limit = 500;

        String signature = ExchangeSecurity.encodeHMACSHA256(this.secretKey,
                order.toRequestBody() + "&limit=" + limit);
        Call<List<Order>> call = this.service.getAllOrders(this.apiKey, order.getSymbol(),
                order.getTimestamp(), limit, signature);
        call.enqueue(getListOrderCallback(successCallback, failedCallback));
    }

    @Override
    public void getAccount(ExchangeSuccessCallback<Account> successCallback, ExchangeFailCallback failedCallback) {
        long timestamp = System.currentTimeMillis();
        String signature = ExchangeSecurity.encodeHMACSHA256(this.secretKey, "timestamp=" + timestamp);
        Call<Account> call = this.service.getAccount(this.apiKey, timestamp, signature);
        call.enqueue(getListOrderCallback(successCallback, failedCallback));
    }

    @Override
    public void getAccountTradeList(Currency first, Currency second, int limit, ExchangeSuccessCallback<JsonArray> successCallback, ExchangeFailCallback failedCallback) {
        long timestamp = System.currentTimeMillis();
        if (limit > 500 || limit <= 0)
            limit = 500;

        if (!Currency.isValidPair(first, second))
            throw new ExchangeApiException("Quote currency has to be ETH, BTC, BNB or USDT!");

        StringBuilder sb = new StringBuilder();
        sb.append("symbol=").append(first.toString()).append(second.toString()).append("&");
        sb.append("limit=").append(limit).append("&");
        sb.append("timestamp=").append(timestamp);

        String signature = ExchangeSecurity.encodeHMACSHA256(this.secretKey, sb.toString());
        Call<JsonArray> call = this.service.getAccountTradeList(this.apiKey, first.toString()+ second.toString()
                , limit, timestamp, signature);
        call.enqueue(getListOrderCallback(successCallback, failedCallback));
    }

    @Override
    public void getAllPrices(ExchangeSuccessCallback<JsonArray> successCallback, ExchangeFailCallback failedCallback) {
        Call<JsonArray> call = this.service.getAllPrices();
        call.enqueue(getListOrderCallback(successCallback, failedCallback));
    }

    @Override
    public void get24hPrice(Currency first, Currency second, ExchangeSuccessCallback<JsonObject> successCallback, ExchangeFailCallback failedCallback) {
        if (!Currency.isValidPair(first, second))
            throw new ExchangeApiException("Quote currency has to be ETH, BTC, BNB or USDT!");

        Call<JsonObject> call = this.service.get24hPrice(first.toString() + second.toString());
        call.enqueue(getListOrderCallback(successCallback, failedCallback));
    }

    @Override
    public Response<JsonObject> getServerTime() throws IOException {
        Call<JsonObject> call = this.service.getServerTime();
        return call.execute();
    }

    private RequestBody getRequestBody(Order order) {
        String requestBody = ExchangeSecurity.getSignedRequestBody(order.toRequestBody()
                , this.secretKey);
        return RequestBody.create(MediaType.parse("text/plain"), requestBody);
    }

    private ExchangeApiBinanceService getApiService() {
        return new Retrofit.Builder()
            .baseUrl(BinanceRouter.API_BINANCE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeApiBinanceService.class);
    }
}