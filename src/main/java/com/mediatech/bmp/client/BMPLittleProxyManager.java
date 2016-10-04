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

import java.io.IOException;

/**
 * Created by Ilia Rogozhin on 02.10.2016.
 */
public class BMPLittleProxyManager extends BMPProxyManager {

    public BMPLittleProxyManager() {
        super();
    }

    public BMPLittleProxyManager(int port) {
        super(port);
    }

    public BMPLittleProxyManager(int port, String adsress) {
        super(port, adsress);
    }

    public BMPLittleProxyManager(String adsress) {
        super(adsress);
    }

    @Override
    public ProxyListDescriptor getProxies() throws IOException {
        return getBmpProxyManagerServices().proxyGet().execute().body();
    }

    @Override
    public BMPLittleProxy start(BMPProxyParameters bmpProxyParameters) throws IOException {
        BMPLittleProxy result;
        if (bmpProxyParameters != null) {
            if (bmpProxyParameters.getPort() != -1) {
                result = getBmpProxyManagerServices()
                        .proxyStart(bmpProxyParameters.getPort(), bmpProxyParameters.getBindAddress())
                        .execute().body();
            }
            else {
                result = getBmpProxyManagerServices()
                        .proxyStart(bmpProxyParameters.getBindAddress())
                        .execute().body();
            }
            if (bmpProxyParameters.getBindAddress() != null) {
                result.setAdsress(bmpProxyParameters.getBindAddress());
            }
        } else {
            result = getBmpProxyManagerServices().proxyStart().execute().body();
        }
        return result;
    }

    @Override
    public BMPLittleProxy start() throws IOException {
        return start(null);
    }

    @Override
    public BMPLittleProxy startWithProxyManager() throws IOException {
        return start(new BMPProxyParameters(getAdsress()));
    }

    @Override
    public BMPLittleProxy startWithProxyManager(int port) throws IOException {
        return start(new BMPProxyParameters(port, getAdsress()));
    }
}
