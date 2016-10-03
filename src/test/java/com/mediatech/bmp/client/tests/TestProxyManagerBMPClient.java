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

package com.mediatech.bmp.client.tests;

import com.mediatech.bmp.client.BMPLittleProxy;
import com.mediatech.bmp.client.BMPLittleProxyManager;
import com.mediatech.bmp.client.parameters.BMPProxyParameters;
import com.mediatech.bmp.client.response.ProxyDescriptor;
import com.mediatech.bmp.client.response.ProxyListDescriptor;
import com.mediatech.bmp.client.tests.util.ProxyManagerTest;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Ilia Rogozhin on 01.10.2016.
 */
@RunWith(Theories.class)
public class TestProxyManagerBMPClient extends ProxyManagerTest {

    @DataPoints
    public static int[] testData = new int[]{
            0,
            1,
            5,
    };

    @Theory
    public void testGetProxiesList(final int testData) throws Throwable {
        ProxyListDescriptor proxyListDescriptorExpected = new ProxyListDescriptor();
        for (int i = 0; i < testData; i++) {
            BMPLittleProxy bmpLittleProxyActual = bmpProxyManager.start();
            int port = PORT + i + 1;
            BMPLittleProxy bmpLittleProxyExpected = new BMPLittleProxy(port);
            assertEquals(bmpLittleProxyExpected, bmpLittleProxyActual);
            proxyListDescriptorExpected.getProxyList().add(new ProxyDescriptor(port));
        }
        ProxyListDescriptor proxyListDescriptorActual = bmpProxyManager.getProxies();
        assertEquals(proxyListDescriptorExpected, proxyListDescriptorActual);
    }

    @Test
    public void testGetProxiesWithPort() throws Throwable {
        ProxyListDescriptor proxyListDescriptorExpected = new ProxyListDescriptor();
        int port = PORT + 10;
        BMPProxyParameters bmpProxyParameters = new BMPProxyParameters(port);
        BMPLittleProxy bmpLittleProxyActual = bmpProxyManager.start(bmpProxyParameters);
        BMPLittleProxy bmpLittleProxyExpected = new BMPLittleProxy(port);
        assertEquals(bmpLittleProxyExpected, bmpLittleProxyActual);
        proxyListDescriptorExpected.getProxyList().add(new ProxyDescriptor(port));
        ProxyListDescriptor proxyListDescriptorActual = bmpProxyManager.getProxies();
        assertEquals(proxyListDescriptorExpected, proxyListDescriptorActual);
    }

    @Test
    public void testGetProxiesWithPortAndBindAdress() throws Throwable {
        ProxyListDescriptor proxyListDescriptorExpected = new ProxyListDescriptor();
        int port = PORT + 10;
        BMPProxyParameters bmpProxyParameters = new BMPProxyParameters(port, ADDRESS);
        BMPLittleProxy bmpLittleProxyActual = bmpProxyManager.start(bmpProxyParameters);
        BMPLittleProxy bmpLittleProxyExpected = new BMPLittleProxy(port, ADDRESS);
        assertEquals(bmpLittleProxyExpected, bmpLittleProxyActual);
        proxyListDescriptorExpected.getProxyList().add(new ProxyDescriptor(port));
        ProxyListDescriptor proxyListDescriptorActual = bmpProxyManager.getProxies();
        assertEquals(proxyListDescriptorExpected, proxyListDescriptorActual);
    }

    @Test
    public void testGetProxiesWithPortAndAddressProxyManager() throws Throwable {
        ProxyListDescriptor proxyListDescriptorExpected = new ProxyListDescriptor();
        int port = PORT + 10;
        BMPLittleProxy bmpLittleProxyActual = bmpProxyManager.startWithProxyManager(port);
        BMPLittleProxy bmpLittleProxyExpected = new BMPLittleProxy(port, ADDRESS);
        assertEquals(bmpLittleProxyExpected, bmpLittleProxyActual);
        proxyListDescriptorExpected.getProxyList().add(new ProxyDescriptor(port));
        ProxyListDescriptor proxyListDescriptorActual = bmpProxyManager.getProxies();
        assertEquals(proxyListDescriptorExpected, proxyListDescriptorActual);
    }

}
