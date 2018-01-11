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
package com.eclard.exchangestream.model;

import com.eclard.exchangestream.constant.Currency;
import com.eclard.exchangestream.constant.OrderSide;
import com.eclard.exchangestream.constant.OrderType;
import com.eclard.exchangestream.constant.TimeInForce;
import com.eclard.exchangestream.exception.ExchangeApiException;

/**
 * Created by Milan.
 * On 1/10/18. 23 : 01
 */
public class Order {
    private String symbol;
    private String side;
    private String type;
    private String timeInForce;
    private String quantity;
    private String price;
    private String origClientOrderId;
    private long orderId;
    private long recvWindow;
    private long timestamp;

    public void setSymbol(Currency symbolFirst, Currency symbolSecond) {
        if (!Currency.isValidPair(symbolFirst, symbolSecond)) {
            throw new ExchangeApiException("Quote currency has to be ETH, BTC, BNB or USDT!");
        }
        this.symbol = symbolFirst.toString() + symbolSecond.toString();
    }

    public void setSide(OrderSide side) {
        this.side = side.toString();
    }

    public void setType(OrderType type) {
        this.type = type.toString();
    }

    public void setTimeInForce(TimeInForce timeInForce) {
        this.timeInForce = timeInForce.toString();
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setRecvWindow(long recvWindow) {
        this.recvWindow = recvWindow;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSide() {
        return side;
    }

    public String getType() {
        return type;
    }

    public String getTimeInForce() {
        return timeInForce;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }

    public long getRecvWindow() {
        return recvWindow;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getOrigClientOrderId() {
        return origClientOrderId;
    }

    public void setOrigClientOrderId(String origClientOrderId) {
        this.origClientOrderId = origClientOrderId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String toRequestBody() {
        StringBuilder sb = new StringBuilder();
        sb.append("symbol=").append(this.symbol).append("&");

        if (this.side != null) {
            sb.append("side=").append(this.side).append("&");
        }

        if (this.type != null) {
            sb.append("type=").append(this.type).append("&");
        }

        if (this.timeInForce != null) {
            sb.append("timeInForce=").append(this.timeInForce).append("&");
        }

        if (this.quantity != null) {
            sb.append("quantity=").append(this.quantity).append("&");
        }

        if (this.price != null) {
            sb.append("price=").append(this.price).append("&");
        }

        if (this.recvWindow != 0) {
            sb.append("recvWindow=").append(this.recvWindow).append("&");
        }

        if (this.orderId != 0) {
            sb.append("orderId=").append(this.orderId).append("&");
        }

        if (this.origClientOrderId != null) {
            sb.append("origClientOrderId=").append(this.origClientOrderId).append("&");
        }

        sb.append("timestamp=").append(this.timestamp);
        return sb.toString();
    }
}