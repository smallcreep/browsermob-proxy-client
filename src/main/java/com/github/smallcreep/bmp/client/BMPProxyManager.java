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

import com.github.smallcreep.bmp.client.parameters.BMPProxyParameters;
import com.github.smallcreep.bmp.client.response.ProxyListDescriptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Created by Ilia Rogozhin on 02.10.2016.
 */
public abstract class BMPProxyManager {

    private int port = 8080;
    private String address = "localhost";
    private String protocol = "http";
    private BMPProxyManagerServices bmpProxyManagerServices;

    BMPProxyManager() {
    }

    BMPProxyManager(int port) {
        this.port = port;
    }

    public BMPProxyManager(int port, String address) {
        setPort(port);
        setAddress(address);
    }

    public BMPProxyManager(String address) {
        setAddress(address);
    }

    public BMPProxyManager(int port, String address, String protocol) {
        setPort(port);
        setAddress(address);
        setProtocol(protocol);
    }

    private void lazyInitializeBMPProxyManagerServices() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(String.format("%s://%s:%s", protocol, address, port))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        bmpProxyManagerServices = retrofit.create(BMPProxyManagerServices.class);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address != null) {
            this.address = address;
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        if (protocol != null) {
            this.protocol = protocol;
        }
    }

    protected BMPProxyManagerServices getBmpProxyManagerServices() {
        if (bmpProxyManagerServices == null) {
            lazyInitializeBMPProxyManagerServices();
        }
        return bmpProxyManagerServices;
    }

    public abstract ProxyListDescriptor getProxies() throws IOException;

    public abstract BMPLittleProxy start() throws IOException;

    public abstract BMPLittleProxy start(BMPProxyParameters bmpProxyParameters) throws IOException;

    public abstract BMPLittleProxy startWithProxyManager() throws IOException;

    public abstract BMPLittleProxy startWithProxyManager(int port) throws IOException;
}