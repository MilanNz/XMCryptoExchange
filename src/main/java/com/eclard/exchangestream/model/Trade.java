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

import com.google.gson.annotations.SerializedName;

/**
 * Created by Milan.
 * On 1/9/18. 21 : 53
 */
public class Trade {

    @SerializedName("e")
    private String eventType;

    @SerializedName("E")
    private long eventTime;

    @SerializedName("s")
    private String pair;

    @SerializedName("a")
    private long aggregatedTradeId;

    @SerializedName("p")
    private String price;

    @SerializedName("q")
    private String quantity;

    @SerializedName("f")
    private long firstBreakdownTradeId;

    @SerializedName("l")
    private long lastBreakdownTradeId;

    @SerializedName("T")
    private long tradeTime;

    @SerializedName("m")
    private boolean isBuyerMaker;

    public String getEventType() {
        return eventType;
    }

    public long getEventTime() {
        return eventTime;
    }

    public String getPair() {
        return pair;
    }

    public long getAggregatedTradeId() {
        return aggregatedTradeId;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public long getFirstBreakdownTradeId() {
        return firstBreakdownTradeId;
    }

    public long getLastBreakdownTradeId() {
        return lastBreakdownTradeId;
    }

    public long getTradeTime() {
        return tradeTime;
    }

    public boolean isBuyerMaker() {
        return isBuyerMaker;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("event: ").append(this.getEventType()).append(System.lineSeparator());
        sb.append("eventTime: ").append(this.getEventTime()).append(System.lineSeparator());
        sb.append("symbol/pair: ").append(this.getPair()).append(System.lineSeparator());
        sb.append("aggregated tradeid: ").append(this.getAggregatedTradeId()).append(System.lineSeparator());
        sb.append("price: ").append(this.getPrice()).append(System.lineSeparator());
        sb.append("quantity: ").append(this.getQuantity()).append(System.lineSeparator());
        sb.append("first breakdown trade id: ").append(this.getFirstBreakdownTradeId()).append(System.lineSeparator());
        sb.append("last breakdown trade id: ").append(this.getLastBreakdownTradeId()).append(System.lineSeparator());
        sb.append("trade time: ").append(this.getTradeTime()).append(System.lineSeparator());
        sb.append("buyer is a maker: ").append(this.isBuyerMaker()).append(System.lineSeparator());
        return sb.toString();
    }
}