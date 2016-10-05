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

import com.mediatech.bmp.client.parameters.BMPDNSParameters;
import com.mediatech.bmp.client.parameters.BMPHeadersParameters;
import com.mediatech.bmp.client.parameters.BMPPageParameters;
import com.mediatech.bmp.client.parameters.BMPHarParameters;
import net.lightbody.bmp.core.har.Har;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Created by Ilia Rogozhin on 02.10.2016.
 */
public abstract class BMPProxy {

    private int port;
    private String address = "localhost";
    private String proxyManagerAddress = "localhost";
    private int proxyManagerPort = 8080;
    private String protocol = "http";
    private BMPProxyServices bmpProxyServices;
    private static String PATH_PROXY = "/proxy/";

    public BMPProxy() {
    }

    public BMPProxy(int port) {
        setPort(port);
    }

    public BMPProxy(int port, String proxyManagerAddress, int proxyManagerPort) {
        setPort(port);
        setProxyManagerAddress(proxyManagerAddress);
        setProxyManagerPort(proxyManagerPort);
    }

    public BMPProxy(int port, String address, String proxyManagerAddress, int proxyManagerPort) {
        setPort(port);
        setAddress(address);
        setProxyManagerAddress(proxyManagerAddress);
        setProxyManagerPort(proxyManagerPort);
    }

    public BMPProxy(int port, String address, String proxyManagerAddress, int proxyManagerPort, String protocol) {
        setPort(port);
        setAddress(address);
        setProxyManagerAddress(proxyManagerAddress);
        setProxyManagerPort(proxyManagerPort);
        setProtocol(protocol);
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
        if (!address.isEmpty()) {
            this.address = address;
        }
    }

    public String getProxyManagerAddress() {
        return proxyManagerAddress;
    }

    public void setProxyManagerAddress(String proxyManagerAddress) {
        if (proxyManagerAddress != null) {
            this.proxyManagerAddress = proxyManagerAddress;
        }
    }

    public int getProxyManagerPort() {
        return proxyManagerPort;
    }

    public void setProxyManagerPort(int proxyManagerPort) {
        this.proxyManagerPort = proxyManagerPort;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        if (!protocol.isEmpty()) {
            this.protocol = protocol;
        }
    }

    public BMPProxyServices getBmpProxyServices() {
        if (bmpProxyServices == null) {
            lazyInitializeBMPProxyServices();
        }
        return bmpProxyServices;
    }

    protected void lazyInitializeBMPProxyServices() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(String.format("%s://%s:%s%s", protocol, proxyManagerAddress, proxyManagerPort, PATH_PROXY))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        bmpProxyServices = retrofit.create(BMPProxyServices.class);
    }

    public abstract void createNewHar() throws IOException;

    public abstract void createNewHar(BMPHarParameters bmpHarParameters) throws IOException;

    public abstract void createNewPage() throws IOException;

    public abstract void createNewPage(BMPPageParameters bmpPageParameters) throws IOException;

    public abstract void destroy() throws IOException;

    public abstract void overridesDns(BMPDNSParameters bmpdnsParameters) throws IOException;

    public abstract void overridesHeaders(BMPHeadersParameters bmpHeadersParameters) throws IOException;

    public abstract Har getHar() throws IOException;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BMPProxy bmpProxy = (BMPProxy) o;

        if (port != bmpProxy.port) return false;
        if (proxyManagerPort != bmpProxy.proxyManagerPort) return false;
        if (address != null ? !address.equals(bmpProxy.address) : bmpProxy.address != null) return false;
        if (proxyManagerAddress != null ? !proxyManagerAddress.equals(bmpProxy.proxyManagerAddress) : bmpProxy.proxyManagerAddress != null)
            return false;
        return protocol != null ? protocol.equals(bmpProxy.protocol) : bmpProxy.protocol == null;

    }

    @Override
    public int hashCode() {
        int result = port;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (proxyManagerAddress != null ? proxyManagerAddress.hashCode() : 0);
        result = 31 * result + proxyManagerPort;
        result = 31 * result + (protocol != null ? protocol.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BMPProxy{" +
                "port=" + port +
                ", address='" + address + '\'' +
                ", proxyManagerAddress='" + proxyManagerAddress + '\'' +
                ", proxyManagerPort=" + proxyManagerPort +
                ", protocol='" + protocol + '\'' +
                '}';
    }
}
