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

package com.mediatech.bmp.client.parameters;

/**
 * Created by Ilia Rogozhin on 02.10.2016.
 */
public class BMPProxyParameters {

    private int port = -1;
    private String bindAddress = null;

    public BMPProxyParameters() {
    }

    public BMPProxyParameters(String bindAddress) {
        this.bindAddress = bindAddress;
    }

    public BMPProxyParameters(int port) {
        this.port = port;
    }

    public BMPProxyParameters(int port, String bindAddress) {
        this.port = port;
        this.bindAddress = bindAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getBindAddress() {
        return bindAddress;
    }

    public void setBindAddress(String bindAddress) {
        this.bindAddress = bindAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BMPProxyParameters that = (BMPProxyParameters) o;

        if (port != that.port) return false;
        return bindAddress != null ? bindAddress.equals(that.bindAddress) : that.bindAddress == null;

    }

    @Override
    public int hashCode() {
        int result = port;
        result = 31 * result + (bindAddress != null ? bindAddress.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BMPProxyParameters{" +
                "port=" + port +
                ", bindAddress='" + bindAddress + '\'' +
                '}';
    }

}
