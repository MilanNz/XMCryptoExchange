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

import com.eclard.exchangestream.model.Trade;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * Created by Milan.
 * On 1/9/18. 19 : 05
 */
public class ExchangeWebSocketListener extends WebSocketListener {
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private StreamListener streamListener;
    private Gson gson;

    public ExchangeWebSocketListener(StreamListener listener) {
        this.streamListener = listener;
        this.gson = new Gson();
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        this.streamListener.onConnected(response);
    }

    @Override
    public void onMessage(WebSocket webSocket, String msg) {
        if (msg == null) {
            return;
        }
        JsonObject json = new JsonParser().parse(msg).getAsJsonObject();
        if (json.getAsJsonPrimitive("e").getAsString().equals("aggTrade")) {
            this.streamListener.onMessage(gson.fromJson(msg, Trade.class));
        } else {
            this.streamListener.onMessage(json);
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        this.streamListener.onClosed(code, reason);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        this.streamListener.onClosed(code, reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        this.streamListener.onFailure(t, response);
    }

    public interface StreamListener<T> {
        void onMessage(T t);
        void onClosed(int code, String reason);
        void onConnected(Response response);
        void onFailure(Throwable throwable, Response response);
    }
}