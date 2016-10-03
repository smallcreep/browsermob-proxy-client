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

package com.mediatech.bmp.client;

import com.mediatech.bmp.client.parameters.BMPProxyParameters;
import com.mediatech.bmp.client.response.ProxyListDescriptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Created by Ilia Rogozhin on 02.10.2016.
 */
public abstract class BMPProxyManager {

    private int port = 8080;
    private String adsress = "localhost";
    private String protocol = "http";
    private BMPProxyManagerServices bmpProxyManagerServices;

    BMPProxyManager() {
        setBMPProxyManagerServices();
    }

    private void setBMPProxyManagerServices() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(String.format("%s://%s:%s", protocol, adsress, port))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        bmpProxyManagerServices = retrofit.create(BMPProxyManagerServices.class);
    }

    BMPProxyManager(int port) {
        this.port = port;
        setBMPProxyManagerServices();
    }

    BMPProxyManager(int port, String adsress) {
        this.port = port;
        this.adsress = adsress;
        setBMPProxyManagerServices();
    }

    BMPProxyManager(String adsress) {
        this.adsress = adsress;
        setBMPProxyManagerServices();
    }

    public BMPProxyManager(int port, String adsress, String protocol) {
        this.port = port;
        this.adsress = adsress;
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public String getAdsress() {
        return adsress;
    }

    public String getProtocol() {
        return protocol;
    }

    BMPProxyManagerServices getBmpProxyManagerServices() {
        return bmpProxyManagerServices;
    }

    public abstract ProxyListDescriptor getProxies() throws IOException;

    public abstract BMPLittleProxy start() throws IOException;

    public abstract BMPLittleProxy start(BMPProxyParameters bmpProxyParameters) throws IOException;

    public abstract BMPLittleProxy startWithProxyManager(int port) throws IOException;
}
