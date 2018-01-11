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
package com.eclard.exchangestream.util;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Milan.
 * On 1/10/18. 23 : 55
 */
public class ExchangeSecurity {
    private static final String HMAC_SHA256 = "HmacSHA256";

    public static String encodeHMACSHA256(String key, String data) {
        try {
            Mac hmac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secret_key
                = new SecretKeySpec(key.getBytes("UTF-8"), HMAC_SHA256);
            hmac.init(secret_key);
            return new String(Hex.encodeHex(hmac.doFinal(data.getBytes("UTF-8"))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getSignedRequestBody(String requestBody, String secretKey) {
        String signature = ExchangeSecurity.encodeHMACSHA256(secretKey, requestBody);
        requestBody += "&signature=" + signature;
        return requestBody;
    }
}