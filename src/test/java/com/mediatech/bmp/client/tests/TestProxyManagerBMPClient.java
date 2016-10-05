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
            BMPLittleProxy bmpLittleProxyActual = getBmpProxyManager().start();
            BMPLittleProxy bmpLittleProxyExpected = new BMPLittleProxy(bmpLittleProxyActual.getPort(),
                    getADDRESS(), getPORT());
            assertEquals(bmpLittleProxyExpected, bmpLittleProxyActual);
            proxyListDescriptorExpected.getProxyList().add(new ProxyDescriptor(bmpLittleProxyActual.getPort()));
        }
        ProxyListDescriptor proxyListDescriptorActual = getBmpProxyManager().getProxies();
        assertEquals(proxyListDescriptorExpected, proxyListDescriptorActual);
    }

    @Test
    public void testGetProxiesWithPort() throws Throwable {
        ProxyListDescriptor proxyListDescriptorExpected = new ProxyListDescriptor();
        int port = getPORT() + 10;
        BMPProxyParameters bmpProxyParameters = new BMPProxyParameters(port);
        BMPLittleProxy bmpLittleProxyActual = getBmpProxyManager().start(bmpProxyParameters);
        BMPLittleProxy bmpLittleProxyExpected = new BMPLittleProxy(port, getADDRESS(), getPORT());
        assertEquals(bmpLittleProxyExpected, bmpLittleProxyActual);
        proxyListDescriptorExpected.getProxyList().add(new ProxyDescriptor(port));
        ProxyListDescriptor proxyListDescriptorActual = getBmpProxyManager().getProxies();
        assertEquals(proxyListDescriptorExpected, proxyListDescriptorActual);
    }

    @Test
    public void testGetProxiesWithPortAndBindAddress() throws Throwable {
        ProxyListDescriptor proxyListDescriptorExpected = new ProxyListDescriptor();
        int port = getPORT() + 10;
        BMPProxyParameters bmpProxyParameters = new BMPProxyParameters(port, getADDRESS());
        BMPLittleProxy bmpLittleProxyActual = getBmpProxyManager().start(bmpProxyParameters);
        BMPLittleProxy bmpLittleProxyExpected = new BMPLittleProxy(port, getADDRESS(), getADDRESS(), getPORT());
        assertEquals(bmpLittleProxyExpected, bmpLittleProxyActual);
        proxyListDescriptorExpected.getProxyList().add(new ProxyDescriptor(port));
        ProxyListDescriptor proxyListDescriptorActual = getBmpProxyManager().getProxies();
        assertEquals(proxyListDescriptorExpected, proxyListDescriptorActual);
    }

    @Test
    public void testGetProxiesWithBindAddress() throws Throwable {
        ProxyListDescriptor proxyListDescriptorExpected = new ProxyListDescriptor();
        BMPLittleProxy bmpLittleProxyActual = getBmpProxyManager().start(new BMPProxyParameters(getADDRESS()));
        BMPLittleProxy bmpLittleProxyExpected = new BMPLittleProxy(bmpLittleProxyActual.getPort(), getADDRESS(),
                getADDRESS(), getPORT());
        assertEquals(bmpLittleProxyExpected, bmpLittleProxyActual);
        proxyListDescriptorExpected.getProxyList().add(new ProxyDescriptor(bmpLittleProxyActual.getPort()));
        ProxyListDescriptor proxyListDescriptorActual = getBmpProxyManager().getProxies();
        assertEquals(proxyListDescriptorExpected, proxyListDescriptorActual);
    }

    @Test
    public void testGetProxiesWithAddressProxyManager() throws Throwable {
        ProxyListDescriptor proxyListDescriptorExpected = new ProxyListDescriptor();
        BMPLittleProxy bmpLittleProxyActual = getBmpProxyManager().startWithProxyManager();
        BMPLittleProxy bmpLittleProxyExpected = new BMPLittleProxy(bmpLittleProxyActual.getPort(), getADDRESS(),
                getADDRESS(), getPORT());
        assertEquals(bmpLittleProxyExpected, bmpLittleProxyActual);
        proxyListDescriptorExpected.getProxyList().add(new ProxyDescriptor(bmpLittleProxyActual.getPort()));
        ProxyListDescriptor proxyListDescriptorActual = getBmpProxyManager().getProxies();
        assertEquals(proxyListDescriptorExpected, proxyListDescriptorActual);
    }

    @Test
    public void testGetProxiesWithAddressProxyManagerAndPort() throws Throwable {
        ProxyListDescriptor proxyListDescriptorExpected = new ProxyListDescriptor();
        int port = getPORT() + 10;
        BMPLittleProxy bmpLittleProxyActual = getBmpProxyManager().startWithProxyManager(port);
        BMPLittleProxy bmpLittleProxyExpected = new BMPLittleProxy(port, getADDRESS(), getADDRESS(), getPORT());
        assertEquals(bmpLittleProxyExpected, bmpLittleProxyActual);
        proxyListDescriptorExpected.getProxyList().add(new ProxyDescriptor(port));
        ProxyListDescriptor proxyListDescriptorActual = getBmpProxyManager().getProxies();
        assertEquals(proxyListDescriptorExpected, proxyListDescriptorActual);
    }
}
