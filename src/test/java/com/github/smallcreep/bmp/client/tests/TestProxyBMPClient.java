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
import com.github.smallcreep.bmp.client.parameters.*;
import com.github.smallcreep.bmp.client.response.ProxyDescriptor;
import com.github.smallcreep.bmp.client.response.ProxyListDescriptor;
import com.github.smallcreep.bmp.client.tests.util.ProxyTest;
import com.mashape.unirest.http.Headers;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarNameValuePair;
import net.lightbody.bmp.util.HttpMessageContents;
import org.apache.http.HttpHost;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.*;

import static io.netty.handler.codec.http.HttpHeaders.Names.ACCESS_CONTROL_ALLOW_CREDENTIALS;
import static io.netty.handler.codec.http.HttpHeaders.Names.ACCESS_CONTROL_MAX_AGE;
import static org.junit.Assert.*;

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
        harEntryExpected.getResponse().setStatusText("");
        harEntryExpected.getResponse().setHttpVersion("unknown");
        harEntryExpected.getResponse().setStatus(0);
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
    public void testOverridesResponseAsString() throws Throwable {
        getBmpLittleProxy()
                .setFilterResponse("contents.setTextContents('<html><body>Response successfully intercepted</body></html>'); " +
                        "var HttpResponseStatusClass = Java.type('io.netty.handler.codec.http.HttpResponseStatus'); " +
                        "var st = HttpResponseStatusClass.FORBIDDEN;response.setStatus(st);");
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        HttpResponse<String> response = Unirest.get(URL_PROTOCOL + URL_FOR_TEST).asString();
        assertEquals("<html><body>Response successfully intercepted</body></html>", response.getBody());
        assertEquals(HttpResponseStatus.FORBIDDEN.code(), response.getStatus());
    }

    @Test
    public void testOverridesResponseAsResponseFilter() throws Throwable {
        Headers headersExpected = new Headers();
        List<String> accessControlAllowCredentialsList = new ArrayList<>();
        accessControlAllowCredentialsList.add("test");
        accessControlAllowCredentialsList.add("test2");
        headersExpected.put(ACCESS_CONTROL_ALLOW_CREDENTIALS, accessControlAllowCredentialsList);
        List<String> accessControlMaxAgeList = new ArrayList<>();
        accessControlMaxAgeList.add("test3");
        headersExpected.put(ACCESS_CONTROL_MAX_AGE, accessControlMaxAgeList);
        io.netty.handler.codec.http.HttpResponse responseOverrides = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.FORBIDDEN);
        for (String headers : headersExpected.keySet()) {
            for (String headersValue : headersExpected.get(headers)) {
                responseOverrides.headers().add(headers, headersValue);
            }
        }
        HttpMessageContents contents = new HttpMessageContents(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.FORBIDDEN));
        contents.setTextContents("<html><body>Response successfully intercepted</body></html>");
        BMPResponseFilter bmpResponseFilter = new BMPResponseFilter(responseOverrides, contents, null);
        getBmpLittleProxy().setFilterResponse(bmpResponseFilter);
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        HttpResponse<String> response = Unirest.get(URL_PROTOCOL + URL_FOR_TEST).asString();
        assertOverrideResponseEquals(accessControlAllowCredentialsList, accessControlMaxAgeList, response);
    }

    @Test
    public void testOverridesResponseAsResponseFilterAndListUrl() throws Throwable {
        Headers headersExpected = new Headers();
        List<String> accessControlAllowCredentialsList = new ArrayList<>();
        accessControlAllowCredentialsList.add("test");
        accessControlAllowCredentialsList.add("test2");
        headersExpected.put(ACCESS_CONTROL_ALLOW_CREDENTIALS, accessControlAllowCredentialsList);
        List<String> accessControlMaxAgeList = new ArrayList<>();
        accessControlMaxAgeList.add("test3");
        headersExpected.put(ACCESS_CONTROL_MAX_AGE, accessControlMaxAgeList);
        io.netty.handler.codec.http.HttpResponse responseOverrides = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.FORBIDDEN);
        for (String headers : headersExpected.keySet()) {
            for (String headersValue : headersExpected.get(headers)) {
                responseOverrides.headers().add(headers, headersValue);
            }
        }
        HttpMessageContents contents = new HttpMessageContents(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.FORBIDDEN));
        contents.setTextContents("<html><body>Response successfully intercepted</body></html>");
        List<FilterUrls> filterUrls = new ArrayList<>();
        filterUrls.add(new FilterUrls("(.*)index\\.html(.*)"));
        filterUrls.add(new FilterUrls("^http:\\/\\/search\\.maven\\.org\\/$", HttpMethod.GET));
        filterUrls.add(new FilterUrls("(.*)test\\.html(.*)", HttpMethod.POST));
        BMPResponseFilter bmpResponseFilter = new BMPResponseFilter(responseOverrides, contents, null, filterUrls);
        getBmpLittleProxy().setFilterResponse(bmpResponseFilter);
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));

        HttpResponse<String> response = Unirest.get(URL_PROTOCOL + URL_FOR_TEST).asString();
        assertOverrideResponseEquals(accessControlAllowCredentialsList, accessControlMaxAgeList, response);
        response = Unirest.post(URL_PROTOCOL + URL_FOR_TEST).asString();
        assertOverrideResponseNotEquals(accessControlAllowCredentialsList, accessControlMaxAgeList, response);

        response = Unirest.get("http://search.maven.org/index.html").asString();
        assertOverrideResponseEquals(accessControlAllowCredentialsList, accessControlMaxAgeList, response);
        response = Unirest.post("http://search.maven.org/index.html").asString();
        assertOverrideResponseEquals(accessControlAllowCredentialsList, accessControlMaxAgeList, response);

        response = Unirest.get("http://search.maven.org/test.html").asString();
        assertOverrideResponseNotEquals(accessControlAllowCredentialsList, accessControlMaxAgeList, response);
        response = Unirest.post("http://search.maven.org/test.html").asString();
        assertOverrideResponseEquals(accessControlAllowCredentialsList, accessControlMaxAgeList, response);

        response = Unirest.get("http://search.maven.org/abracadabra.alibaba").asString();
        assertOverrideResponseNotEquals(accessControlAllowCredentialsList, accessControlMaxAgeList, response);
        response = Unirest.post("http://search.maven.org/abracadabra.alibaba").asString();
        assertOverrideResponseNotEquals(accessControlAllowCredentialsList, accessControlMaxAgeList, response);
    }

    @Test
    public void testWhiteList() throws Throwable {
        Har harExpected = getHarExpected();
        getBmpLittleProxy().createNewHar();
        BMPWhiteListParameters bmpWhiteListParameters = new BMPWhiteListParameters("(.*)index\\.html(.*),^http:\\/\\/search\\.maven\\.org\\/$", 500);
        getBmpLittleProxy().setWhiteList(bmpWhiteListParameters);

        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));

        Unirest.get("http://search.maven.org/index.html").asString();
        Unirest.post("http://search.maven.org/index.html").asString();

        Unirest.get(URL_PROTOCOL + URL_FOR_TEST).asString();
        Unirest.post(URL_PROTOCOL + URL_FOR_TEST).asString();

        HttpResponse<String> response =  Unirest.get("http://search.maven.org/test.html").asString();
        assertEquals(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), response.getStatus());
        response = Unirest.post("http://search.maven.org/test.html").asString();
        assertEquals(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), response.getStatus());

        harExpected.getLog().addPage(getHarPage(null, null));

        HarEntry harEntryExpected = getHarEntry(null);
        harEntryExpected.getRequest().setUrl("http://search.maven.org/index.html");
        harEntryExpected.getResponse().setStatusText(HttpResponseStatus.NOT_FOUND.reasonPhrase());
        harEntryExpected.getResponse().setStatus(HttpResponseStatus.NOT_FOUND.code());
        harExpected.getLog().addEntry(harEntryExpected);

        HarEntry harEntryExpected2 = getHarEntry(null);
        harEntryExpected2.getRequest().setUrl("http://search.maven.org/index.html");
        harEntryExpected2.getRequest().setMethod("POST");
        harEntryExpected2.getResponse().setStatusText(HttpResponseStatus.NOT_FOUND.reasonPhrase());
        harEntryExpected2.getResponse().setStatus(HttpResponseStatus.NOT_FOUND.code());
        harExpected.getLog().addEntry(harEntryExpected2);

        HarEntry harEntryExpected3 = getHarEntry(null);
        harExpected.getLog().addEntry(harEntryExpected3);

        HarEntry harEntryExpected4 = getHarEntry(null);
        harEntryExpected4.getRequest().setMethod("POST");
        harEntryExpected4.getResponse().setStatusText(HttpResponseStatus.NOT_FOUND.reasonPhrase());
        harEntryExpected4.getResponse().setStatus(HttpResponseStatus.NOT_FOUND.code());
        harExpected.getLog().addEntry(harEntryExpected4);

        assertEqualsHar(harExpected, getBmpLittleProxy().getHar());
    }

    @Test
    public void testOverridesWhiteList() throws Throwable {
        Har harExpected = getHarExpected();
        getBmpLittleProxy().createNewHar();
        BMPWhiteListParameters bmpWhiteListParameters = new BMPWhiteListParameters("(.*)index\\.html(.*)", 500);
        getBmpLittleProxy().setWhiteList(bmpWhiteListParameters);

        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));

        Unirest.get("http://search.maven.org/index.html").asString();
        Unirest.post("http://search.maven.org/index.html").asString();

        HttpResponse<String> response = Unirest.get(URL_PROTOCOL + URL_FOR_TEST).asString();
        assertEquals(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), response.getStatus());
        response = Unirest.post(URL_PROTOCOL + URL_FOR_TEST).asString();
        assertEquals(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), response.getStatus());

        bmpWhiteListParameters = new BMPWhiteListParameters("^http:\\/\\/search\\.maven\\.org\\/$", 500);
        getBmpLittleProxy().setWhiteList(bmpWhiteListParameters);

        response = Unirest.get("http://search.maven.org/index.html").asString();
        assertEquals(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), response.getStatus());
        response = Unirest.post("http://search.maven.org/index.html").asString();
        assertEquals(HttpResponseStatus.INTERNAL_SERVER_ERROR.code(), response.getStatus());

        Unirest.get(URL_PROTOCOL + URL_FOR_TEST).asString();
        Unirest.post(URL_PROTOCOL + URL_FOR_TEST).asString();

        harExpected.getLog().addPage(getHarPage(null, null));

        HarEntry harEntryExpected = getHarEntry(null);
        harEntryExpected.getRequest().setUrl("http://search.maven.org/index.html");
        harEntryExpected.getResponse().setStatusText(HttpResponseStatus.NOT_FOUND.reasonPhrase());
        harEntryExpected.getResponse().setStatus(HttpResponseStatus.NOT_FOUND.code());
        harExpected.getLog().addEntry(harEntryExpected);

        HarEntry harEntryExpected2 = getHarEntry(null);
        harEntryExpected2.getRequest().setUrl("http://search.maven.org/index.html");
        harEntryExpected2.getRequest().setMethod("POST");
        harEntryExpected2.getResponse().setStatusText(HttpResponseStatus.NOT_FOUND.reasonPhrase());
        harEntryExpected2.getResponse().setStatus(HttpResponseStatus.NOT_FOUND.code());
        harExpected.getLog().addEntry(harEntryExpected2);

        HarEntry harEntryExpected3 = getHarEntry(null);
        harExpected.getLog().addEntry(harEntryExpected3);

        HarEntry harEntryExpected4 = getHarEntry(null);
        harEntryExpected4.getRequest().setMethod("POST");
        harEntryExpected4.getResponse().setStatusText(HttpResponseStatus.NOT_FOUND.reasonPhrase());
        harEntryExpected4.getResponse().setStatus(HttpResponseStatus.NOT_FOUND.code());
        harExpected.getLog().addEntry(harEntryExpected4);

        assertEqualsHar(harExpected, getBmpLittleProxy().getHar());
    }

    @Test
    public void testGetWhiteList() throws Throwable {
        BMPWhiteListParameters bmpWhiteListParametersExpected = new BMPWhiteListParameters("(.*)index\\.html(.*),^http:\\/\\/search\\.maven\\.org\\/$", null);
        getBmpLittleProxy().setWhiteList(bmpWhiteListParametersExpected);
        List<String> bmpWhiteListsParametersActual = getBmpLittleProxy().getWhiteList();
        assertEquals(Arrays.asList(bmpWhiteListParametersExpected.getRegex().split(",")), bmpWhiteListsParametersActual);
        bmpWhiteListParametersExpected = new BMPWhiteListParameters("(.*)index\\.html(.*),^http:\\/\\/search\\.maven\\.org\\/$,test", null);
        getBmpLittleProxy().setWhiteList(bmpWhiteListParametersExpected);
        bmpWhiteListsParametersActual = getBmpLittleProxy().getWhiteList();
        assertEquals(Arrays.asList(bmpWhiteListParametersExpected.getRegex().split(",")), bmpWhiteListsParametersActual);
    }

    @Test
    public void testDeleteWhiteList() throws Throwable {
        BMPWhiteListParameters bmpWhiteListParametersExpected = new BMPWhiteListParameters("(.*)test\\.html(.*),^http:\\/\\/search\\.maven\\.org\\/$", 500);
        getBmpLittleProxy().setWhiteList(bmpWhiteListParametersExpected);
        getBmpLittleProxy().deleteWhiteList();
        List<String> bmpWhiteListsParametersActual = getBmpLittleProxy().getWhiteList();
        List<String> bmpWhiteListsExpected = new ArrayList<>();
        assertEquals(bmpWhiteListsExpected, bmpWhiteListsParametersActual);
        Unirest.setProxy(new HttpHost(getBmpLittleProxy().getAddress(), getBmpLittleProxy().getPort()));
        HttpResponse<String> response = Unirest.get("http://search.maven.org/index.html").asString();
        assertEquals(HttpResponseStatus.NOT_FOUND.code(), response.getStatus());
    }

    private void assertOverrideResponseNotEquals(List<String> accessControlAllowCredentialsList,
                                                 List<String> accessControlMaxAgeList,
                                                 HttpResponse<String> response) {
        assertNotEquals("<html><body>Response successfully intercepted</body></html>", response.getBody());
        assertNotEquals(HttpResponseStatus.FORBIDDEN.code(), response.getStatus());
        assertNotEquals(HttpResponseStatus.FORBIDDEN.reasonPhrase(), response.getStatusText());
        assertNotEquals(accessControlAllowCredentialsList, response.getHeaders().get(ACCESS_CONTROL_ALLOW_CREDENTIALS));
        assertNotEquals(accessControlMaxAgeList, response.getHeaders().get(ACCESS_CONTROL_MAX_AGE));
    }

    private void assertOverrideResponseEquals(List<String> accessControlAllowCredentialsList,
                                              List<String> accessControlMaxAgeList,
                                              HttpResponse<String> response) {
        assertEquals("<html><body>Response successfully intercepted</body></html>", response.getBody());
        assertEquals(HttpResponseStatus.FORBIDDEN.code(), response.getStatus());
        assertEquals(HttpResponseStatus.FORBIDDEN.reasonPhrase(), response.getStatusText());
        assertEquals(accessControlAllowCredentialsList, response.getHeaders().get(ACCESS_CONTROL_ALLOW_CREDENTIALS));
        assertEquals(accessControlMaxAgeList, response.getHeaders().get(ACCESS_CONTROL_MAX_AGE));
    }
}
