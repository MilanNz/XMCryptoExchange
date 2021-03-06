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
package com.eclard.exchangestream;

import com.eclard.exchangestream.callback.ExchangeFailCallback;
import com.eclard.exchangestream.callback.ExchangeSuccessCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Milan.
 * On 1/14/18. 15 : 28
 *
 * Maps retrofit callback into our two callbacks in order to use lambda expression.
 */
public class CallbackMapper {

    public static<T> Callback<T> getListOrderCallback(final ExchangeSuccessCallback<T> successCallback
            , final ExchangeFailCallback failedCallback) {
        return new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                successCallback.success(response);
            }

            @Override
            public void onFailure(Call<T> call, Throwable throwable) {
                failedCallback.fail(throwable);
            }
        };
    }
}
