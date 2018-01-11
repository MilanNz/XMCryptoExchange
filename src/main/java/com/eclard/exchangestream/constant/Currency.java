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
 * User: Milan on 1/9/18. 00 : 07
 */
public enum Currency {
    ETH("Ethereum"),
    BTC("Bitcoin"),
    USDT("Tether USD"),
    BNB("Binance Coin"),
    IOTA("MIOTA"),
    XRP("Ripple"),
    XVG("Verge"),
    XMR("Monero"),
    TRX("TRON"),
    LTC("Litecoin"),
    MCO("Monaco"),
    SUB("Substratum"),
    QSP("Quantstamp"),
    NEO("NEO"),
    BQX("Ethos"),
    EOS("EOS"),
    QTUM("Qtum"),
    BNT("Bancor"),
    ZRX("ZRX"),
    FUN("FunFair"),
    DASH("Dash"),
    CNC("CHNCoin"),
    ETC("Ethereum Classic");

    private String fullName;
    Currency(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public static boolean isValidPair(Currency first, Currency second) {
        return first != second && (second == Currency.ETH
                || second == Currency.BTC
                || second == Currency.BNB
                || second == Currency.USDT);
    }
}