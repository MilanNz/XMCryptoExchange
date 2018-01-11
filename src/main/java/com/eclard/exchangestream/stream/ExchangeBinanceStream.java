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
package com.eclard.exchangestream.stream;

import com.eclard.exchangestream.constant.BinanceRouter;
import com.eclard.exchangestream.constant.Currency;
import com.eclard.exchangestream.exception.ExchangeStreamException;
import com.eclard.exchangestream.restapi.ExchangeApiBinance;
import com.eclard.exchangestream.restapi.ExchangeApiBinanceImpl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

/**
 * Created by Milan.
 * On 1/9/18. 19 : 16
 */
public class ExchangeBinanceStream {
    private OkHttpClient client;
    private WebSocket webSocket;
    private String interval;
    private String listenKey;
    private String pair;

    public ExchangeBinanceStream() {
        this.client = new OkHttpClient();
    }

    /**
     * Connects on Binance trade exchange stream.
     */
    public void stream(ExchangeWebSocketListener.StreamListener listener) {
        checkParameters(listener);
        this.webSocket = this.client.newWebSocket(getRequest(BinanceRouter.WEBSOCKET_TRADE_ADDRESS),
                new ExchangeWebSocketListener(listener));
    }

    /**
     * Connects on Binance depth stream.
     */
    public void klineStream(ExchangeWebSocketListener.StreamListener listener) {
        checkParameters(listener);
        if (this.interval == null) {
            throw new ExchangeStreamException("Interval cannot be null!");
        }
        String address = String.format(BinanceRouter.WEBSOCKET_KLINE_ADDRESS, this.pair);
        this.webSocket = this.client.newWebSocket(
                new Request.Builder().url(address).build(),
                new ExchangeWebSocketListener(listener));
    }

    /**
     * Connects on Depth stream.
     */
    public void depthStream(ExchangeWebSocketListener.StreamListener listener) {
        checkParameters(listener);
        this.webSocket = this.client.newWebSocket(getRequest(BinanceRouter.WEBSOCKET_DEPTH_ADDRESS),
                new ExchangeWebSocketListener(listener));
    }

    /**
     * Connects on User Data stream.
     */
    public void userDataStream(ExchangeWebSocketListener.StreamListener listener) {
        if (listener == null) {
            throw new ExchangeStreamException("Stream listener cannot be null!");
        }
        if (this.listenKey == null) {
            throw new ExchangeStreamException("Listen key cannot be null!");
        }
        String address = String.format(BinanceRouter.WEBSOCKET_USERDATA_ADDRESS, this.listenKey);
        this.webSocket = this.client.newWebSocket(
                new Request.Builder().url(address).build(),
                new ExchangeWebSocketListener(listener));
    }

    /**
     * Gets implemented ExchangeApiBinance object for communication with https Binance API.
     * This call requires api key and secret in order to use signed routes.
     *
     * @param apiKey API key.
     * @param secretKey Client Secret.
     */
    public ExchangeApiBinance getApiBinance(String apiKey, String secretKey) {
        return ExchangeApiBinanceImpl.newInstance(apiKey, secretKey);
    }

    /**
     * Gets implemented ExchangeApiBinance object for communication with https Binance API.
     * With this instance it is only possible to communicate with not
     * signed routes.
     */
    public ExchangeApiBinance getApiBinance() {
        return ExchangeApiBinanceImpl.newInstance();
    }

    private void checkParameters(ExchangeWebSocketListener.StreamListener listener) {
        if (listener == null) {
            throw new ExchangeStreamException("Stream listener cannot be null!");
        }
        if (this.pair == null) {
            throw new ExchangeStreamException("Trade pair cannot be null!");
        }
    }

    private Request getRequest(String formatAddress) {
        String address = String.format(formatAddress, this.pair);
        return new Request.Builder().url(address).build();
    }

    /**
     * Sets crypto currency pair.
     */
    public ExchangeBinanceStream setPair(Currency firstSymbol, Currency secondSymbol) {
        this.pair = firstSymbol.toString() + secondSymbol.toString();
        return this;
    }

    public ExchangeBinanceStream setInterval(String interval) {
        this.interval = interval;
        return this;
    }

    public ExchangeBinanceStream setListenKey(String listenKey) {
        this.listenKey = listenKey;
        return this;
    }

    /**
     * Closes Binance exchange stream.
     */
    public void closeStream() {
        this.client.dispatcher().executorService().shutdown();
        this.webSocket.cancel();
    }
}
