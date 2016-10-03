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

/**
 * Created by Ilia Rogozhin on 02.10.2016.
 */
public abstract class BMPProxy {

    private int port;
    private String adsress = "localhost";
    private String protocol = "http";

    public BMPProxy() {
    }

    public BMPProxy(int port) {
        this.port = port;
    }

    public BMPProxy(int port, String adsress) {
        this.port = port;
        this.adsress = adsress;
    }

    public BMPProxy(int port, String adsress, String protocol) {
        this.port = port;
        this.adsress = adsress;
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAdsress() {
        return adsress;
    }

    public void setAdsress(String adsress) {
        this.adsress = adsress;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BMPProxy bmpProxy = (BMPProxy) o;

        if (port != bmpProxy.port) return false;
        if (adsress != null ? !adsress.equals(bmpProxy.adsress) : bmpProxy.adsress != null) return false;
        return protocol != null ? protocol.equals(bmpProxy.protocol) : bmpProxy.protocol == null;

    }

    @Override
    public int hashCode() {
        int result = port;
        result = 31 * result + (adsress != null ? adsress.hashCode() : 0);
        result = 31 * result + (protocol != null ? protocol.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BMPProxy{" +
                "port=" + port +
                ", adsress='" + adsress + '\'' +
                ", protocol='" + protocol + '\'' +
                '}';
    }
}
