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
import com.eclard.exchangestream.constant.Currency;
import com.eclard.exchangestream.model.Account;
import com.eclard.exchangestream.model.Order;
import com.eclard.exchangestream.callback.ExchangeFailCallback;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

/**
 * Created by Milan.
 * On 1/11/18. 23 : 21
 */
public interface ExchangeApiBinance {
    /**
     * Creates new order.
     *
     * Required fields:
     * - Symbol
     * - Side
     * - Type
     * - Time in Force
     * - Quantity
     * - Price
     * - Timestamp
     *
     * @param order New order.
     * @param successCallback Success callback.
     * @param failCallback Fail callback.
     */
    void order(Order order, ExchangeSuccessCallback<JsonObject> successCallback, ExchangeFailCallback failCallback);

    /**
     * Creates test new order creation and signature/recvWindow long. Creates and validates
     * a new order but does not send it into the matching engine.
     *
     * Required fields:
     * - Symbol
     * - Side
     * - Type
     * - Time in Force
     * - Quantity
     * - Price
     * - RecvWindow
     * - Timestamp
     *
     * @param order Test order.
     * @param successCallback Success callback.
     * @param failCallback Fail callback.
     */
    void setTestOrder(Order order, ExchangeSuccessCallback<JsonObject> successCallback, ExchangeFailCallback failCallback);

    /**
     * Gets an order's status.
     *
     * Required fields:
     * - Symbol
     * - Order id
     * - Orig Client Order Id
     * - Timestamp.
     *
     * @param order Order details.
     * @param successCallback Success callback.
     * @param failCallback Fail callback.
     */
    void getOrderStatus(Order order, ExchangeSuccessCallback<Order> successCallback, ExchangeFailCallback failCallback);

    /**
     * Cancels an active order.
     *
     * Required fields:
     * - Symbol
     * - Order Id
     * - Timestemp
     *
     * @param order Order to cancel.
     * @param successCallback Success callback.
     * @param failCallback Fail callback.
     */
    void cancelOrder(Order order, ExchangeSuccessCallback<JsonObject> successCallback, ExchangeFailCallback failCallback);

    /**
     * Gets all current open orders on a symbol.
     *
     * Required fields:
     * - Symbol
     * - Timestemp
     *
     * @param order Order details.
     * @param successCallback Success callback.
     * @param failCallback Fail callback.
     */
    void getCurrentOpenOrders(Order order, ExchangeSuccessCallback<List<Order>> successCallback, ExchangeFailCallback failCallback);

    /**
     * Gets all account orders; active, canceled, or filled.
     *
     * Required fields:
     * - Symbol
     * - Limit (Default 500, max 500)
     * - Timestemp
     *
     * @param order Order details.
     * @param successCallback Success callback.
     * @param failCallback Fail callback.
     */
    void getAllOrders(Order order, int limit, ExchangeSuccessCallback<List<Order>> successCallback, ExchangeFailCallback failCallback);

    /**
     * Gets current account information.
     *
     * @param successCallback Success callback.
     * @param failCallback Fail callback.
     */
    void getAccount(ExchangeSuccessCallback<Account> successCallback, ExchangeFailCallback failCallback);

    /**
     * Gets trades for a specific account and symbol.
     *
     * @param first First currency.
     * @param second Second currency (base).
     * @param successCallback Success callback.
     * @param failCallback Fail callback.
     */
    void getAccountTradeList(Currency first, Currency second, int limit, ExchangeSuccessCallback<JsonArray> successCallback, ExchangeFailCallback failCallback);

    /**
     * Gets latest price for all symbols.
     *
     * @param successCallback Success callback.
     * @param failCallback Fail callback.
     */
    void getAllPrices(ExchangeSuccessCallback<JsonArray> successCallback, ExchangeFailCallback failCallback);

    /**
     * Gets 24 hour price change statistics.
     *
     * @param first First currency.
     * @param second Second currency (base).
     * @param successCallback Success callback.
     * @param failCallback Fail callback.
     */
    void get24hPrice(Currency first, Currency second, ExchangeSuccessCallback<JsonObject> successCallback, ExchangeFailCallback failCallback);

    /**
     * Gets server time.
     * <p>
     * Note: This call is synchronous and it is only used for testing.
     * </p>
     */
    Response<JsonObject> getServerTime() throws IOException;
}