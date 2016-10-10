/*
 *    Copyright 2016 Ilia Rogozhin
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.smallcreep.bmp.client;

import net.lightbody.bmp.core.har.Har;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

/**
 * Created by Ilia Rogozhin on 02.10.2016.
 */
public interface BMPProxyServices {

    @DELETE("{port}")
    Call<Void> destroy(@Path("port") int port);

    @FormUrlEncoded
    @PUT("{port}/har")
    Call<Void> startHar(@Path("port") int port, @FieldMap Map<String, String> fields);

    @GET("{port}/har")
    Call<Har> getHar(@Path("port") int port);

    @FormUrlEncoded
    @PUT("{port}/har/pageRef")
    Call<Void> startNewPage(@Path("port") int port, @FieldMap Map<String, String> fields);

    @POST("{port}/hosts")
    Call<Void> overridesDns(@Path("port") int port, @Body Map<String, String> body);

    @POST("{port}/headers")
    Call<Void> overridesHeaders(@Path("port") int port, @Body Map<String, String> body);

    @DELETE("{port}/dns/cache")
    Call<Void> resetDNSCache(@Path("port") int port);

    @Headers("Content-Type: text/plain")
    @POST("{port}/filter/response")
    Call<Void> setFilterResponse(@Path("port") int port, @Body String content);
}
