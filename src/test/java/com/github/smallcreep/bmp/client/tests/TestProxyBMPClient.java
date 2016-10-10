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

package com.github.smallcreep.bmp.client.tests;

import com.github.smallcreep.bmp.client.BMPLittleProxy;
import com.github.smallcreep.bmp.client.parameters.BMPDNSParameters;
import com.github.smallcreep.bmp.client.parameters.BMPHarParameters;
import com.github.smallcreep.bmp.client.parameters.BMPHeadersParameters;
import com.github.smallcreep.bmp.client.parameters.BMPPageParameters;
import com.github.smallcreep.bmp.client.response.ProxyDescriptor;
import com.github.smallcreep.bmp.client.response.ProxyListDescriptor;
import com.github.smallcreep.bmp.client.tests.util.ProxyTest;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarNameValuePair;
import org.apache.http.HttpHost;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ilia Rogozhin on 04.10.2016.
 */
@RunWith(Theories.class)
public class TestProxyBMPClient extends ProxyTest {

    public static final String HEADER_NAME_1 = "User-Agent";
    public static final String HEADER_VALUE_1 = "BrowserMob-Agent";
    public static final String HEADER_NAME_2 = "Test";
    public static final String HEADER_VALUE_2 = "Test";
    public static final String PAGE_ID_FOR_TEST = "Page 2";
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
            int port = getPORT() + i + 2;
            BMPLittleProxy bmpLittleProxyActual = getBmpProxyManager().startWithProxyManager(port);
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
        Har harExpected = getHarExpected();
        getBmpLittleProxy().createNewHar();
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        Unirest.get(URL_PROTOCOL + URL_FOR_TEST).asString();
        harExpected.getLog().addPage(getHarPage(null, null));
        harExpected.getLog().addEntry(getHarEntry(null));
        assertEqualsHar(harExpected, getBmpLittleProxy().getHar());
    }

    @Test
    public void testHarWithParameters() throws Throwable {
        Har harExpected = getHarExpected();
        BMPHarParameters bmpHarParameters = new BMPHarParameters(true, true, true, PAGE_ID_FOR_TEST, null);
        getBmpLittleProxy().createNewHar(bmpHarParameters);
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        Unirest.get(URL_PROTOCOL + URL_FOR_TEST).asString();
        harExpected.getLog().addPage(getHarPage(PAGE_ID_FOR_TEST, PAGE_ID_FOR_TEST));
        harExpected.getLog().addEntry(getHarEntry(PAGE_ID_FOR_TEST));
        assertEqualsHar(harExpected, getBmpLittleProxy().getHar());
    }

    @Test
    public void testHarWithParametersAndNewPage() throws Throwable {
        Har harExpected = getHarExpected();
        BMPHarParameters bmpHarParameters = new BMPHarParameters(true, true, true, PAGE_ID_FOR_TEST, null);
        getBmpLittleProxy().createNewHar(bmpHarParameters);
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        Unirest.get(URL_PROTOCOL + URL_FOR_TEST).asString();
        harExpected.getLog().addPage(getHarPage(PAGE_ID_FOR_TEST, PAGE_ID_FOR_TEST));
        harExpected.getLog().addEntry(getHarEntry(PAGE_ID_FOR_TEST));
        getBmpLittleProxy().createNewPage();
        Unirest.get(URL_PROTOCOL + URL_FOR_TEST).asString();
        harExpected.getLog().addPage(getHarPage(null, null));
        harExpected.getLog().addEntry(getHarEntry(null));
        assertEqualsHar(harExpected, getBmpLittleProxy().getHar());
    }

    @Test
    public void testHarWithParametersAndNewPageWithParameters() throws Throwable {
        Har harExpected = getHarExpected();
        String pageRef = "Page 3";
        String pageTitle = "Page 333";
        BMPHarParameters bmpHarParameters = new BMPHarParameters(true, true, true, PAGE_ID_FOR_TEST, null);
        getBmpLittleProxy().createNewHar(bmpHarParameters);
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        Unirest.get(URL_PROTOCOL + URL_FOR_TEST).asString();
        harExpected.getLog().addPage(getHarPage(PAGE_ID_FOR_TEST, PAGE_ID_FOR_TEST));
        harExpected.getLog().addEntry(getHarEntry(PAGE_ID_FOR_TEST));
        BMPPageParameters bmpPageParameters = new BMPPageParameters(pageRef, pageTitle);
        getBmpLittleProxy().createNewPage(bmpPageParameters);
        Unirest.get(URL_PROTOCOL + URL_FOR_TEST).asString();
        harExpected.getLog().addPage(getHarPage(pageRef, pageTitle));
        harExpected.getLog().addEntry(getHarEntry(pageRef));
        assertEqualsHar(harExpected, getBmpLittleProxy().getHar());
    }

    @Test
    public void testOverridesDNS() throws Throwable {
        Har harExpected = getHarExpected();
        BMPHarParameters bmpHarParameters = new BMPHarParameters(true, true, true, PAGE_ID_FOR_TEST, null);
        getBmpLittleProxy().createNewHar(bmpHarParameters);
        Map<String, String> overridesDNS = new HashMap<>();
        overridesDNS.put(URL_FOR_TEST, getADDRESS());
        BMPDNSParameters bmpdnsParameters = new BMPDNSParameters(overridesDNS);
        getBmpLittleProxy().overridesDns(bmpdnsParameters);
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        Unirest.get(URL_PROTOCOL + URL_FOR_TEST).asString();
        harExpected.getLog().addPage(getHarPage(PAGE_ID_FOR_TEST, PAGE_ID_FOR_TEST));
        HarEntry harEntryExpected = getHarEntry(PAGE_ID_FOR_TEST);
        harEntryExpected.setServerIPAddress(getADDRESS());
        harExpected.getLog().addEntry(harEntryExpected);
        assertEqualsHar(harExpected, getBmpLittleProxy().getHar());
    }

    @Test
    public void testOverridesHeaders() throws Throwable {
        Har harExpected = getHarExpected();
        BMPHarParameters bmpHarParameters = new BMPHarParameters(true, true, true, PAGE_ID_FOR_TEST, null);
        getBmpLittleProxy().createNewHar(bmpHarParameters);
        Map<String, String> overridesHeaders = new HashMap<>();
        overridesHeaders.put(HEADER_NAME_1, HEADER_VALUE_1);
        overridesHeaders.put(HEADER_NAME_2, HEADER_VALUE_2);
        BMPHeadersParameters bmpHeadersParameters = new BMPHeadersParameters(overridesHeaders);
        getBmpLittleProxy().overridesHeaders(bmpHeadersParameters);
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        Unirest.get(URL_PROTOCOL + URL_FOR_TEST).asString();
        harExpected.getLog().addPage(getHarPage(PAGE_ID_FOR_TEST, PAGE_ID_FOR_TEST));
        harExpected.getLog().addEntry(getHarEntry(PAGE_ID_FOR_TEST));
        assertEqualsHar(harExpected, getBmpLittleProxy().getHar());
        assertTrue(getBmpLittleProxy().getHar().getLog().getEntries().get(0).getRequest().getHeaders()
                .contains(new HarNameValuePair(HEADER_NAME_1, HEADER_VALUE_1)));
        assertTrue(getBmpLittleProxy().getHar().getLog().getEntries().get(0).getRequest().getHeaders()
                .contains(new HarNameValuePair(HEADER_NAME_2, HEADER_VALUE_2)));
    }

    @Test
    public void testOverridesResponse() throws Throwable {
        getBmpLittleProxy().setFilterResponse("contents.setTextContents('<html><body>Response successfully intercepted</body></html>');");
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        HttpResponse<String> response = Unirest.get(URL_PROTOCOL + URL_FOR_TEST).asString();
        assertEquals(response.getBody(), "<html><body>Response successfully intercepted</body></html>");

    }
}
