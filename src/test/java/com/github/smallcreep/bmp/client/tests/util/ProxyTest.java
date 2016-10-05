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
import org.junit.Before;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Ilia Rogozhin on 04.10.2016.
 */
public abstract class ProxyTest extends ProxyManagerTest {

    private BMPLittleProxy bmpLittleProxy;

    @Before
    @Override
    public void setUp() throws InterruptedException, IOException, URISyntaxException {
        super.setUp();
        bmpLittleProxy = getBmpProxyManager().startWithProxyManager();
    }

    protected BMPLittleProxy getBmpLittleProxy() {
        return bmpLittleProxy;
    }
}
