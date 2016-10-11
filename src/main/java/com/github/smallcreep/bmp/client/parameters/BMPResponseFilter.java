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

import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.List;

/**
 * Created by Ilia Rogozhin on 10.10.2016.
 */
public class BMPResponseFilter {

    private HttpResponse response = null;
    private HttpMessageContents contents = null;
    private HttpMessageInfo messageInfo = null;
    private List<String> overridesUrl = null;

    public BMPResponseFilter() {
    }

    public BMPResponseFilter(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
        this.response = response;
        this.contents = contents;
        this.messageInfo = messageInfo;
    }

    public BMPResponseFilter(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo, List<String> overridesUrl) {
        this.response = response;
        this.contents = contents;
        this.messageInfo = messageInfo;
        this.overridesUrl = overridesUrl;
    }

    public HttpResponse getResponse() {
        return response;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    public HttpMessageContents getContents() {
        return contents;
    }

    public void setContents(HttpMessageContents contents) {
        this.contents = contents;
    }

    public HttpMessageInfo getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(HttpMessageInfo messageInfo) {
        this.messageInfo = messageInfo;
    }

    public List<String> getOverridesUrl() {
        return overridesUrl;
    }

    public void setOverridesUrl(List<String> overridesUrl) {
        this.overridesUrl = overridesUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BMPResponseFilter that = (BMPResponseFilter) o;

        if (response != null ? !response.equals(that.response) : that.response != null) return false;
        if (contents != null ? !contents.equals(that.contents) : that.contents != null) return false;
        if (messageInfo != null ? !messageInfo.equals(that.messageInfo) : that.messageInfo != null) return false;
        return overridesUrl != null ? overridesUrl.equals(that.overridesUrl) : that.overridesUrl == null;

    }

    @Override
    public int hashCode() {
        int result = response != null ? response.hashCode() : 0;
        result = 31 * result + (contents != null ? contents.hashCode() : 0);
        result = 31 * result + (messageInfo != null ? messageInfo.hashCode() : 0);
        result = 31 * result + (overridesUrl != null ? overridesUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BMPResponseFilter{" +
                "response=" + response +
                ", contents=" + contents +
                ", messageInfo=" + messageInfo +
                ", overridesUrl=" + overridesUrl +
                '}';
    }

    public String toFilterString() {
        String result = "var flag = true;";
        if (overridesUrl != null && overridesUrl.size() > 0) {
            result += "flag = false;";
            result += "var PatternClass = Java.type('java.util.regex.Pattern');";
            result += "var MatcherClass = Java.type('java.util.regex.Matcher');";
            result += "var pattern; var matcher;";
            for (String overridesUrlRegexp : overridesUrl) {
                result += String.format("pattern = PatternClass.compile('%s');", overridesUrlRegexp);
                result += "matcher = pattern.matcher(messageInfo.getOriginalUrl());";
                result += "if (matcher.find()) { flag = true }";
            }
        }
        result += "if (flag) {";
        if (contents != null) {
            if (contents.getTextContents() != null) {
                result += String.format("contents.setTextContents('%s');", contents.getTextContents());
            }
        }
        if (response != null) {
            if (response.getStatus() != null) {
                result += "var HttpResponseStatusClass = Java.type('io.netty.handler.codec.http.HttpResponseStatus');";
                result += String.format("var status = new HttpResponseStatusClass(%d, '%s');", response.getStatus().code(),
                        response.getStatus().reasonPhrase());
                result += "response.setStatus(status);";
            }
            if (response.headers() != null) {
                result += "response.headers().clear();";
                result += "var HttpHeadersClass = Java.type('io.netty.handler.codec.http.HttpHeaders');";
                result += "var DefaultHttpHeadersClass = Java.type('io.netty.handler.codec.http.DefaultHttpHeaders');";
                result += "var httpHeaders = new DefaultHttpHeadersClass();";
                for (String headerName: response.headers().names()) {
                    for (String headersValue: response.headers().getAll(headerName)) {
                        result += String.format("httpHeaders.add('%s', '%s');", headerName, headersValue);
                    }
                }
                result += "response.headers().set(httpHeaders);";
            }
        }
        result += "}";
        return result;
    }
}
