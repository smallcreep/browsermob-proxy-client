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

package com.github.smallcreep.bmp.client.tests.util;

import com.github.smallcreep.bmp.client.BMPLittleProxy;
import net.lightbody.bmp.core.har.*;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ilia Rogozhin on 04.10.2016.
 */
public abstract class ProxyTest extends ProxyManagerTest {

    public static final String URL_FOR_TEST = "search.maven.org";
    public static final String URL_PROTOCOL = "http://";
    public static final String DEFAULT_PAGE_ID = "Page 0";

    private BMPLittleProxy bmpLittleProxy;

    public static void assertEqualsHar(Har harExpected, Har harActual) {
        assertEqualsHarLog(harExpected.getLog(), harActual.getLog());
    }

    private static void assertEqualsHarLog(HarLog harLogExpected, HarLog harLogActual) {
        assertEquals(harLogExpected.getComment(), harLogActual.getComment());
        assertEquals(harLogExpected.getVersion(), harLogActual.getVersion());
        assertEqualsHarNameVersion(harLogExpected.getCreator(), harLogActual.getCreator());
        assertEqualsHarNameVersion(harLogExpected.getBrowser(), harLogActual.getBrowser());
        assertEqualsHarPages(harLogExpected.getPages(), harLogActual.getPages());
        assertEqualsHarEntries(harLogExpected.getEntries(), harLogActual.getEntries());
    }

    private static void assertEqualsHarNameVersion(HarNameVersion harNameVersionExpected,
                                                   HarNameVersion harNameVersionActual) {
        assertEquals(harNameVersionExpected.getComment(), harNameVersionActual.getComment());
        assertEquals(harNameVersionExpected.getVersion(), harNameVersionActual.getVersion());
        assertEquals(harNameVersionExpected.getName(), harNameVersionActual.getName());
    }

    private static void assertEqualsHarPages(List<HarPage> harPagesExpected, List<HarPage> harPagesActual) {
        assertEquals(harPagesExpected.size(), harPagesActual.size());
        for (int i = 0; i < harPagesExpected.size(); i++) {
            assertEquals(harPagesExpected.get(i).getId(), harPagesActual.get(i).getId());
            assertEquals(harPagesExpected.get(i).getTitle(), harPagesActual.get(i).getTitle());
            assertEquals(harPagesExpected.get(i).getComment(), harPagesActual.get(i).getComment());
        }
    }

    private static void assertEqualsHarEntries(List<HarEntry> harEntriesExpected, List<HarEntry> harEntriesActual) {
        assertEquals(harEntriesExpected.size(), harEntriesActual.size());
        for (int i = 0; i < harEntriesExpected.size(); i++) {
            assertEquals(harEntriesExpected.get(i).getPageref(), harEntriesActual.get(i).getPageref());
            assertEquals(harEntriesExpected.get(i).getServerIPAddress(), harEntriesActual.get(i).getServerIPAddress());
            assertEquals(harEntriesExpected.get(i).getComment(), harEntriesActual.get(i).getComment());
            assertEqualsHarRequest(harEntriesExpected.get(i).getRequest(), harEntriesActual.get(i).getRequest());
            assertEqualsHarResponse(harEntriesExpected.get(i).getResponse(), harEntriesActual.get(i).getResponse());
        }
    }

    private static void assertEqualsHarRequest(HarRequest harRequestExpected, HarRequest harRequestActual) {
        assertEquals(harRequestExpected.getComment(), harRequestActual.getComment());
        assertEquals(harRequestExpected.getMethod(), harRequestActual.getMethod());
        assertEquals(harRequestExpected.getHttpVersion(), harRequestActual.getHttpVersion());
        assertEquals(harRequestExpected.getUrl(), harRequestActual.getUrl());
    }

    private static void assertEqualsHarResponse(HarResponse harResponseExpected, HarResponse harResponseActual) {
        assertEquals(harResponseExpected.getComment(), harResponseActual.getComment());
        assertEquals(harResponseExpected.getStatusText(), harResponseActual.getStatusText());
        assertEquals(harResponseExpected.getHttpVersion(), harResponseActual.getHttpVersion());
        assertEquals(harResponseExpected.getStatus(), harResponseActual.getStatus());
    }

    @Before
    @Override
    public void setUp() throws InterruptedException, IOException, URISyntaxException {
        super.setUp();
        bmpLittleProxy = getBmpProxyManager().startWithProxyManager();
    }

    @NotNull
    private HarLog getHarLogWithDefaultParameters() {
        HarLog harLog = new HarLog();
        HarNameVersion harNameCreator = new HarNameVersion("BrowserMob Proxy", "2.1.2");
        harLog.setCreator(harNameCreator);
        HarNameVersion harNameBrowser = new HarNameVersion("unknown", "");
        harLog.setBrowser(harNameBrowser);
        return harLog;
    }

    @NotNull
    public Har getHarExpected() {
        Har harExpected = new Har();
        HarLog harLog = getHarLogWithDefaultParameters();
        harExpected.setLog(harLog);
        return harExpected;
    }

    @NotNull
    public HarPage getHarPage(String pageId, String pageTitile) {
        if (pageId == null) pageId = DEFAULT_PAGE_ID;
        if (pageTitile == null) pageTitile = pageId;
        return new HarPage(pageId, pageTitile);
    }

    @NotNull
    public HarEntry getHarEntry(String pageId) {
        if (pageId == null) pageId = DEFAULT_PAGE_ID;
        HarEntry harEntry = new HarEntry(pageId);
        HarRequest harRequest = new HarRequest("GET", URL_PROTOCOL + URL_FOR_TEST + "/", "HTTP/1.1");
        harEntry.setRequest(harRequest);
        HarResponse harResponse = new HarResponse(200, "OK", "HTTP/1.1");
        harEntry.setResponse(harResponse);
        harEntry.setServerIPAddress("207.223.241.72");
        return harEntry;
    }

    protected BMPLittleProxy getBmpLittleProxy() {
        return bmpLittleProxy;
    }
}
