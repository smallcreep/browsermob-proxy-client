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

package com.github.smallcreep.bmp.client;

import com.github.smallcreep.bmp.client.parameters.*;
import net.lightbody.bmp.core.har.Har;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ilia Rogozhin on 02.10.2016.
 */
public class BMPLittleProxy extends BMPProxy {

    /**
     * Instantiates a new Bmp little proxy.
     */
    public BMPLittleProxy() {
        super();
    }

    /**
     * Instantiates a new Bmp little proxy.
     *
     * @param port the port
     */
    public BMPLittleProxy(int port) {
        super(port);
    }

    /**
     * Instantiates a new Bmp little proxy.
     *
     * @param port                the port
     * @param proxyManagerAddress the proxy manager address
     * @param proxyManagerPort    the proxy manager port
     */
    public BMPLittleProxy(int port, String proxyManagerAddress, int proxyManagerPort) {
        super(port, proxyManagerAddress, proxyManagerPort);
    }

    /**
     * Instantiates a new Bmp little proxy.
     *
     * @param port                the port
     * @param address             the address
     * @param proxyManagerAddress the proxy manager address
     * @param proxyManagerPort    the proxy manager port
     */
    public BMPLittleProxy(int port, String address, String proxyManagerAddress, int proxyManagerPort) {
        super(port, address, proxyManagerAddress, proxyManagerPort);
    }

    /**
     * Instantiates a new Bmp little proxy.
     *
     * @param port                the port
     * @param address             the address
     * @param proxyManagerAddress the proxy manager address
     * @param proxyManagerPort    the proxy manager port
     * @param protocol            the protocol
     */
    public BMPLittleProxy(int port, String address, String proxyManagerAddress, int proxyManagerPort, String protocol) {
        super(port, address, proxyManagerAddress, proxyManagerPort, protocol);
    }

    @Override
    public void createNewHar() throws IOException {
        createNewHar(null);
    }

    @Override
    public void createNewHar(BMPHarParameters bmpHarParameters) throws IOException {
        if (bmpHarParameters == null) bmpHarParameters = new BMPHarParameters();
        getBmpProxyServices().startHar(getPort(), bmpHarParameters.getMapFields()).execute();
    }

    @Override
    public void createNewPage() throws IOException {
        createNewPage(null);
    }

    @Override
    public void createNewPage(BMPPageParameters bmpPageParameters) throws IOException {
        if (bmpPageParameters == null) bmpPageParameters = new BMPPageParameters();
        getBmpProxyServices().startNewPage(getPort(), bmpPageParameters.getMapFields()).execute();
    }

    @Override
    public void destroy() throws IOException {
        getBmpProxyServices().destroy(getPort()).execute();
    }

    @Override
    public Har getHar() throws IOException {
        return getBmpProxyServices().getHar(getPort()).execute().body();
    }

    @Override
    public void overridesDns(BMPDNSParameters bmpdnsParameters) throws IOException {
        if (bmpdnsParameters == null) bmpdnsParameters = new BMPDNSParameters(new HashMap<>());
        getBmpProxyServices().overridesDns(getPort(), bmpdnsParameters.getOverridesDNS()).execute();
    }

    @Override
    public void overridesHeaders(BMPHeadersParameters bmpHeadersParameters) throws IOException {
        if (bmpHeadersParameters == null) bmpHeadersParameters = new BMPHeadersParameters(new HashMap<>());
        getBmpProxyServices().overridesHeaders(getPort(), bmpHeadersParameters.getOverridesHeaders()).execute();
    }

    @Override
    public void resetDNSCache() throws IOException {
        getBmpProxyServices().resetDNSCache(getPort()).execute();
    }

    @Override
    public void setFilterResponse(String methodResponse) throws IOException {
        getBmpProxyServices().setFilterResponse(getPort(), methodResponse).execute();
    }

    @Override
    public void setFilterResponse(BMPResponseFilter bmpResponseFilter) throws IOException {
        setFilterResponse(bmpResponseFilter.toFilterString());
    }

    @Override
    public void setWhiteList(BMPWhiteListParameters bmpWhiteListParameters) throws IOException {
        if (bmpWhiteListParameters == null) bmpWhiteListParameters = new BMPWhiteListParameters();
        getBmpProxyServices().setWhiteList(getPort(), bmpWhiteListParameters.getMapFields()).execute();
    }

    @Override
    public List<String> getWhiteList() throws IOException {
        return getBmpProxyServices().getWhiteList(getPort()).execute().body();
    }

    @Override
    public void deleteWhiteList() throws IOException {
        getBmpProxyServices().deleteWhiteList(getPort()).execute();
    }
}