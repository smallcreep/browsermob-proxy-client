/*
 * Copyright 2016 Ilia Rogozhin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.smallcreep.bmp.client.parameters;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;

/**
 * Created by Ilia Rogozhin on 11.10.2016.
 */
public class FilterUrls {

    private String regexpUrl = null;
    private HttpMethod urlMethod = null;
    private HttpHeaders httpHeaders = null;

    public FilterUrls() {
    }

    public FilterUrls(String regexpUrl) {
        this.regexpUrl = regexpUrl;
    }

    public FilterUrls(String regexpUrl, HttpMethod urlMethod) {
        this.regexpUrl = regexpUrl;
        this.urlMethod = urlMethod;
    }

    public FilterUrls(String regexpUrl, HttpMethod urlMethod, HttpHeaders httpHeaders) {
        this.regexpUrl = regexpUrl;
        this.urlMethod = urlMethod;
        this.httpHeaders = httpHeaders;
    }

    public String getRegexpUrl() {
        return regexpUrl;
    }

    public void setRegexpUrl(String regexpUrl) {
        this.regexpUrl = regexpUrl;
    }

    public HttpMethod getUrlMethod() {
        return urlMethod;
    }

    public void setUrlMethod(HttpMethod urlMethod) {
        this.urlMethod = urlMethod;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterUrls that = (FilterUrls) o;

        if (regexpUrl != null ? !regexpUrl.equals(that.regexpUrl) : that.regexpUrl != null) return false;
        return urlMethod != null ? urlMethod.equals(that.urlMethod) : that.urlMethod == null;

    }

    @Override
    public int hashCode() {
        int result = regexpUrl != null ? regexpUrl.hashCode() : 0;
        result = 31 * result + (urlMethod != null ? urlMethod.hashCode() : 0);
        result = 31 * result + (httpHeaders != null ? httpHeaders.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FilterUrls{" +
                "regexpUrl='" + regexpUrl + '\'' +
                ", urlMethod=" + urlMethod +
                ", httpHeaders=" + httpHeaders +
                '}';
    }
}
