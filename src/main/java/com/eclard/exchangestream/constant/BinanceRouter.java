/*
 * Copyright 2018 Eclard
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
package com.eclard.exchangestream.constant;

/**
 * Created by Eclard.
 * User: Milan on 12/30/17. 20 : 50
 */
public interface BinanceRouter {
    String WEBSOCKET_TRADE_ADDRESS = "wss://stream.binance.com:9443/ws/%s@aggTrade";
    String WEBSOCKET_DEPTH_ADDRESS = "wss://stream.binance.com:9443/ws/%s@depth";
    String WEBSOCKET_KLINE_ADDRESS = "wss://stream.binance.com:9443/ws/%s@kline_%s";
    String WEBSOCKET_USERDATA_ADDRESS = "wss://stream.binance.com:9443/ws/%s";
}