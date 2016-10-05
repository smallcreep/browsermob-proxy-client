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

import com.mashape.unirest.http.Unirest;
import com.mediatech.bmp.client.BMPLittleProxy;
import com.mediatech.bmp.client.parameters.BMPDNSParameters;
import com.mediatech.bmp.client.parameters.BMPHarParameters;
import com.mediatech.bmp.client.parameters.BMPHeadersParameters;
import com.mediatech.bmp.client.parameters.BMPPageParameters;
import com.mediatech.bmp.client.response.ProxyDescriptor;
import com.mediatech.bmp.client.response.ProxyListDescriptor;
import com.mediatech.bmp.client.tests.util.ProxyTest;
import net.lightbody.bmp.core.har.Har;
import org.apache.http.HttpHost;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ilia Rogozhin on 04.10.2016.
 */
@RunWith(Theories.class)
public class TestProxyBMPClient extends ProxyTest {

    private static String EXPECTED_LOG_VERSION = "1.2";

    private static String EXPECTED_LOG_CREATOR_NAME = "BrowserMob Proxy";
    private static String EXPECTED_LOG_CREATOR_VERSION = "2.1.2";
    private static String EXPECTED_LOG_CREATOR_COMMENT = "";

    private static String EXPECTED_LOG_BROWSER_NAME = "unknown";
    private static String EXPECTED_LOG_BROWSER_VERSION = "";
    private static String EXPECTED_LOG_BROWSER_COMMENT = "";



    @DataPoints
    public static int[] testData = new int[]{
            0,
            1,
            5,
    };

    @Theory
    public void testDeleteProxy(final int testData) throws Throwable {
        ProxyListDescriptor proxyListDescriptorExpected = new ProxyListDescriptor();
        for (int i = 0; i < testData; i++) {
            BMPLittleProxy bmpLittleProxyActual = getBmpProxyManager().startWithProxyManager();
            int port = getPORT() + i + 2;
            BMPLittleProxy bmpLittleProxyExpected = new BMPLittleProxy(port, getADDRESS(), getADDRESS(), getPORT());
            assertEquals(bmpLittleProxyExpected, bmpLittleProxyActual);
            proxyListDescriptorExpected.getProxyList().add(new ProxyDescriptor(port));
        }
        getBmpLittleProxy().destroy();
        ProxyListDescriptor proxyListDescriptorActual = getBmpProxyManager().getProxies();
        assertEquals(proxyListDescriptorExpected, proxyListDescriptorActual);
    }

    @Test
    public void testHarWithoutParameters() throws Throwable {
        getBmpLittleProxy().createNewHar();
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        Unirest.get("http://google.com").asString();
        Har harActual = getBmpLittleProxy().getHar();

        assertEquals(EXPECTED_LOG_VERSION, harActual.getLog().getVersion());

        assertEquals(EXPECTED_LOG_CREATOR_NAME, harActual.getLog().getCreator().getName());
        assertEquals(EXPECTED_LOG_CREATOR_VERSION, harActual.getLog().getCreator().getVersion());
        assertEquals(EXPECTED_LOG_CREATOR_COMMENT, harActual.getLog().getCreator().getComment());

        assertEquals(EXPECTED_LOG_BROWSER_NAME, harActual.getLog().getBrowser().getName());
        assertEquals(EXPECTED_LOG_BROWSER_VERSION, harActual.getLog().getBrowser().getVersion());
        assertEquals(EXPECTED_LOG_BROWSER_COMMENT, harActual.getLog().getBrowser().getComment());

    }

    @Test
    public void testHarWithParameters() throws Throwable {
        BMPHarParameters bmpHarParameters = new BMPHarParameters(true, true, true, "Page 2", null);
        getBmpLittleProxy().createNewHar(bmpHarParameters);
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        Unirest.get("http://google.com").asString();
        Har harActual = getBmpLittleProxy().getHar();

        assertEquals(EXPECTED_LOG_VERSION, harActual.getLog().getVersion());

        assertEquals(EXPECTED_LOG_CREATOR_NAME, harActual.getLog().getCreator().getName());
        assertEquals(EXPECTED_LOG_CREATOR_VERSION, harActual.getLog().getCreator().getVersion());
        assertEquals(EXPECTED_LOG_CREATOR_COMMENT, harActual.getLog().getCreator().getComment());

        assertEquals(EXPECTED_LOG_BROWSER_NAME, harActual.getLog().getBrowser().getName());
        assertEquals(EXPECTED_LOG_BROWSER_VERSION, harActual.getLog().getBrowser().getVersion());
        assertEquals(EXPECTED_LOG_BROWSER_COMMENT, harActual.getLog().getBrowser().getComment());

    }

    @Test
    public void testHarWithParametersAndNewPage() throws Throwable {
        BMPHarParameters bmpHarParameters = new BMPHarParameters(true, true, true, "Page 2", null);
        getBmpLittleProxy().createNewHar(bmpHarParameters);
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        Unirest.get("http://google.com").asString();
        getBmpLittleProxy().createNewPage();
        Unirest.get("http://google.com").asString();
        Har harActual = getBmpLittleProxy().getHar();

        assertEquals(EXPECTED_LOG_VERSION, harActual.getLog().getVersion());

        assertEquals(EXPECTED_LOG_CREATOR_NAME, harActual.getLog().getCreator().getName());
        assertEquals(EXPECTED_LOG_CREATOR_VERSION, harActual.getLog().getCreator().getVersion());
        assertEquals(EXPECTED_LOG_CREATOR_COMMENT, harActual.getLog().getCreator().getComment());

        assertEquals(EXPECTED_LOG_BROWSER_NAME, harActual.getLog().getBrowser().getName());
        assertEquals(EXPECTED_LOG_BROWSER_VERSION, harActual.getLog().getBrowser().getVersion());
        assertEquals(EXPECTED_LOG_BROWSER_COMMENT, harActual.getLog().getBrowser().getComment());

    }

    @Test
    public void testHarWithParametersAndNewPageWithParameters() throws Throwable {
        BMPHarParameters bmpHarParameters = new BMPHarParameters(true, true, true, "Page 2", null);
        getBmpLittleProxy().createNewHar(bmpHarParameters);
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        Unirest.get("http://google.com").asString();
        BMPPageParameters bmpPageParameters = new BMPPageParameters("Page 3", "Page 333");
        getBmpLittleProxy().createNewPage(bmpPageParameters);
        Unirest.get("http://google.com").asString();
        Har harActual = getBmpLittleProxy().getHar();

        assertEquals(EXPECTED_LOG_VERSION, harActual.getLog().getVersion());

        assertEquals(EXPECTED_LOG_CREATOR_NAME, harActual.getLog().getCreator().getName());
        assertEquals(EXPECTED_LOG_CREATOR_VERSION, harActual.getLog().getCreator().getVersion());
        assertEquals(EXPECTED_LOG_CREATOR_COMMENT, harActual.getLog().getCreator().getComment());

        assertEquals(EXPECTED_LOG_BROWSER_NAME, harActual.getLog().getBrowser().getName());
        assertEquals(EXPECTED_LOG_BROWSER_VERSION, harActual.getLog().getBrowser().getVersion());
        assertEquals(EXPECTED_LOG_BROWSER_COMMENT, harActual.getLog().getBrowser().getComment());

    }

    @Test
    public void testOverridesDNS() throws Throwable {
        BMPHarParameters bmpHarParameters = new BMPHarParameters(true, true, true, "Page 2", null);
        getBmpLittleProxy().createNewHar(bmpHarParameters);
        Map<String, String> overridesDNS = new HashMap<>();
        overridesDNS.put("local.terraclicks.com","192.168.118.36");
        BMPDNSParameters bmpdnsParameters = new BMPDNSParameters(overridesDNS);
        getBmpLittleProxy().overridesDns(bmpdnsParameters);
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        Unirest.get("http://local.terraclicks.com").asString();

        Har harActual = getBmpLittleProxy().getHar();

        assertEquals(EXPECTED_LOG_VERSION, harActual.getLog().getVersion());

        assertEquals(EXPECTED_LOG_CREATOR_NAME, harActual.getLog().getCreator().getName());
        assertEquals(EXPECTED_LOG_CREATOR_VERSION, harActual.getLog().getCreator().getVersion());
        assertEquals(EXPECTED_LOG_CREATOR_COMMENT, harActual.getLog().getCreator().getComment());

        assertEquals(EXPECTED_LOG_BROWSER_NAME, harActual.getLog().getBrowser().getName());
        assertEquals(EXPECTED_LOG_BROWSER_VERSION, harActual.getLog().getBrowser().getVersion());
        assertEquals(EXPECTED_LOG_BROWSER_COMMENT, harActual.getLog().getBrowser().getComment());

    }

    @Test
    public void testOverridesHeaders() throws Throwable {
        BMPHarParameters bmpHarParameters = new BMPHarParameters(true, true, true, "Page 2", null);
        getBmpLittleProxy().createNewHar(bmpHarParameters);
        Map<String, String> overridesHeaders = new HashMap<>();
        overridesHeaders.put("User-Agent","BrowserMob-Agent");
        BMPHeadersParameters bmpHeadersParameters = new BMPHeadersParameters(overridesHeaders);
        getBmpLittleProxy().overridesHeaders(bmpHeadersParameters);
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        Unirest.get("http://local.terraclicks.com").asString();

        Har harActual = getBmpLittleProxy().getHar();

        assertEquals(EXPECTED_LOG_VERSION, harActual.getLog().getVersion());

        assertEquals(EXPECTED_LOG_CREATOR_NAME, harActual.getLog().getCreator().getName());
        assertEquals(EXPECTED_LOG_CREATOR_VERSION, harActual.getLog().getCreator().getVersion());
        assertEquals(EXPECTED_LOG_CREATOR_COMMENT, harActual.getLog().getCreator().getComment());

        assertEquals(EXPECTED_LOG_BROWSER_NAME, harActual.getLog().getBrowser().getName());
        assertEquals(EXPECTED_LOG_BROWSER_VERSION, harActual.getLog().getBrowser().getVersion());
        assertEquals(EXPECTED_LOG_BROWSER_COMMENT, harActual.getLog().getBrowser().getComment());

    }
}
