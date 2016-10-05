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

import com.mediatech.bmp.client.BMPLittleProxyManager;
import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Before;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ilia Rogozhin on 02.10.2016.
 */
public abstract class ProxyManagerTest {

    private Process proc = null;
    private static int PORT = 8080;
    private static String ADDRESS = "127.0.0.1";
    private String commandLine;
    private List<Integer> startJavaPIDs;
    private BMPLittleProxyManager bmpProxyManager;

    @Before
    public void setUp() throws InterruptedException, IOException, URISyntaxException {
        URL url;
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            url = getClass().getResource("/browsermob-proxy-2.1.2/bin/browsermob-proxy.bat");
            startJavaPIDs = getJavaPids();
        } else {
            url = getClass().getResource("/browsermob-proxy-2.1.2/bin/browsermob-proxy");
            String commandLine = getComandLineChmod(url);
            proc = Runtime.getRuntime().exec(commandLine);
        }
        String commandLine = getComandLineString(url);
        proc = Runtime.getRuntime().exec(commandLine);
        Thread.sleep(2000);
        bmpProxyManager = new BMPLittleProxyManager(PORT, ADDRESS);
    }

    private List<Integer> getJavaPids() throws IOException {
        List<Integer> result = new ArrayList<>();
        Runtime rt = Runtime.getRuntime();
        Process process = rt.exec("tasklist /FI \"imagename eq java.exe\"");
        InputStream stdout = process.getInputStream();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdout));
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("java.exe")) {
                Pattern pattern = Pattern.compile("^java.exe(\\D*)(\\d+)(.*)Console(.*)$");
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    result.add(Integer.valueOf(matcher.group(2)));
                }
            }
        }
        return result;
    }

    @NotNull
    private String getComandLineString(URL url) throws URISyntaxException {
        Path resPath = getPath(url);
        this.commandLine = resPath.toString();
        return resPath.toString() + " -port " + PORT + " -address " + ADDRESS;
    }

    @NotNull
    private String getComandLineChmod(URL url) throws URISyntaxException {
        Path resPath = getPath(url);
        this.commandLine = resPath.toString();
        return "chmod +x " + resPath.toString();
    }

    private Path getPath(URL url) throws URISyntaxException {
        return java.nio.file.Paths.get(url.toURI());
    }

    @After
    public void tearDown() throws InterruptedException, IOException {
        Runtime rt = Runtime.getRuntime();
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            List<Integer> endJavaPIDs = getJavaPids();
            for (Integer endJavaPID : endJavaPIDs)
                if (!startJavaPIDs.contains(endJavaPID)) {
                    rt.exec("taskkill /F /PID " + endJavaPID);
                }
        } else
            proc.destroy();
    }

    protected BMPLittleProxyManager getBmpProxyManager() {
        return bmpProxyManager;
    }

    protected static int getPORT() {
        return PORT;
    }

    protected static String getADDRESS() {
        return ADDRESS;
    }
}
