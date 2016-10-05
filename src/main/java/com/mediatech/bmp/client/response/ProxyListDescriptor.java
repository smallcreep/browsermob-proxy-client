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

package com.mediatech.bmp.client.response;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Ilia Rogozhin on 02.10.2016.
 */
public class ProxyListDescriptor {

    private Collection<ProxyDescriptor> proxyList = new ArrayList<>();

    public ProxyListDescriptor() {
    }

    public ProxyListDescriptor(Collection<ProxyDescriptor> proxyList) {
        this.proxyList = proxyList;
    }

    public Collection<ProxyDescriptor> getProxyList() {
        return proxyList;
    }

    public void setProxyList(Collection<ProxyDescriptor> proxyList) {
        this.proxyList = proxyList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProxyListDescriptor that = (ProxyListDescriptor) o;


        return proxyList != null ? (proxyList.containsAll(that.proxyList) && that.proxyList.containsAll(proxyList))
                : that.proxyList == null;

    }

    @Override
    public int hashCode() {
        return proxyList != null ? proxyList.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ProxyListDescriptor{" +
                "proxyList=" + proxyList +
                '}';
    }
}
