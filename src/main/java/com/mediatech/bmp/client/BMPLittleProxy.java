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

import com.mediatech.bmp.client.parameters.BMPHarParameters;
import com.mediatech.bmp.client.parameters.BMPPageParameters;
import net.lightbody.bmp.core.har.Har;

import java.io.IOException;

/**
 * Created by Ilia Rogozhin on 02.10.2016.
 */
public class BMPLittleProxy extends BMPProxy {

    public BMPLittleProxy() {
        super();
    }

    public BMPLittleProxy(int port) {
        super(port);
    }

    public BMPLittleProxy(int port, String proxyManagerAddress, int proxyManagerPort) {
        super(port, proxyManagerAddress, proxyManagerPort);
    }

    public BMPLittleProxy(int port, String address, String proxyManagerAddress, int proxyManagerPort) {
        super(port, address, proxyManagerAddress, proxyManagerPort);
    }

    public BMPLittleProxy(int port, String address, String proxyManagerAddress, int proxyManagerPort, String protocol) {
        super(port, address, proxyManagerAddress, proxyManagerPort, protocol);
    }

    @Override
    public void createNewHar() throws IOException {
        getBmpProxyServices().startHar(getPort()).execute();
    }

    @Override
    public void createNewHar(BMPHarParameters bmpHarParameters) {

    }

    @Override
    public void createNewPage() {

    }

    @Override
    public void createNewPage(BMPPageParameters bmpPageParameters) {

    }

    @Override
    public void destroy() throws IOException {
        getBmpProxyServices().destroy(getPort()).execute();
    }

    @Override
    public Har getHar() throws IOException {
        return getBmpProxyServices().getHar(getPort()).execute().body();
    }
}