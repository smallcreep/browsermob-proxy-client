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

package com.mediatech.bmp.client.tests.util;

import com.mediatech.bmp.client.BMPLittleProxy;
import com.mediatech.bmp.client.BMPLittleProxyManager;
import com.mediatech.bmp.client.response.ProxyDescriptor;
import com.mediatech.bmp.client.response.ProxyListDescriptor;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Ilia Rogozhin on 02.10.2016.
 */
public abstract class ProxyManagerTest {

    private static int PORT = 8080;
    private static String ADDRESS = "127.0.0.1";
    private BMPLittleProxyManager bmpProxyManager;

    protected static int getPORT() {
        return PORT;
    }

    protected static String getADDRESS() {
        return ADDRESS;
    }

    @Before
    public void setUp() throws InterruptedException, IOException, URISyntaxException {
        bmpProxyManager = new BMPLittleProxyManager(PORT, ADDRESS);
    }

    @After
    public void tearDown() throws InterruptedException, IOException {
        ProxyListDescriptor proxyListDescriptor = bmpProxyManager.getProxies();
        for (ProxyDescriptor proxyListElement : proxyListDescriptor.getProxyList()) {
            new BMPLittleProxy(proxyListElement.getPort(), ADDRESS, ADDRESS, PORT).destroy();
        }

    }

    protected BMPLittleProxyManager getBmpProxyManager() {
        return bmpProxyManager;
    }
}
