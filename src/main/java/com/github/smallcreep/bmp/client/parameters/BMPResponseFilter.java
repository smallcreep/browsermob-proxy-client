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
import java.util.Set;

/**
 * Created by Ilia Rogozhin on 10.10.2016.
 */
public class BMPResponseFilter {

    private HttpResponse response = null;
    private HttpMessageContents contents = null;
    private HttpMessageInfo messageInfo = null;
    private List<FilterUrls> overridesUrls = null;
    private Long delayTime = null;

    public BMPResponseFilter() {
    }

    public BMPResponseFilter(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
        this.response = response;
        this.contents = contents;
        this.messageInfo = messageInfo;
    }

    public BMPResponseFilter(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo,
                             List<FilterUrls> overridesUrls) {
        this.response = response;
        this.contents = contents;
        this.messageInfo = messageInfo;
        this.overridesUrls = overridesUrls;
    }

    public BMPResponseFilter(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo,
                             List<FilterUrls> overridesUrls, Long delayTime) {
        this.response = response;
        this.contents = contents;
        this.messageInfo = messageInfo;
        this.overridesUrls = overridesUrls;
        this.delayTime = delayTime;
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

    public List<FilterUrls> getOverridesUrls() {
        return overridesUrls;
    }

    public void setOverridesUrls(List<FilterUrls> overridesUrls) {
        this.overridesUrls = overridesUrls;
    }

    public Long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Long delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BMPResponseFilter that = (BMPResponseFilter) o;

        if (response != null ? !response.equals(that.response) : that.response != null) return false;
        if (contents != null ? !contents.equals(that.contents) : that.contents != null) return false;
        if (messageInfo != null ? !messageInfo.equals(that.messageInfo) : that.messageInfo != null) return false;
        if (overridesUrls != null ? !overridesUrls.equals(that.overridesUrls) : that.overridesUrls != null)
            return false;
        return delayTime != null ? delayTime.equals(that.delayTime) : that.delayTime == null;

    }

    @Override
    public int hashCode() {
        int result = response != null ? response.hashCode() : 0;
        result = 31 * result + (contents != null ? contents.hashCode() : 0);
        result = 31 * result + (messageInfo != null ? messageInfo.hashCode() : 0);
        result = 31 * result + (overridesUrls != null ? overridesUrls.hashCode() : 0);
        result = 31 * result + (delayTime != null ? delayTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BMPResponseFilter{" +
                "response=" + response +
                ", contents=" + contents +
                ", messageInfo=" + messageInfo +
                ", overridesUrls=" + overridesUrls +
                ", delayTime=" + delayTime +
                '}';
    }

    public String toFilterString() {
        String result = "var flag = true;";
        if (overridesUrls != null && overridesUrls.size() > 0) {
            result += "flag = false;";
            result += "var PatternClass = Java.type('java.util.regex.Pattern');";
            result += "var MatcherClass = Java.type('java.util.regex.Matcher');";
            result += "var pattern; var matcher;";
            for (FilterUrls overridesUrl : overridesUrls) {
                if (overridesUrl.getRegexpUrl() != null) {
                    result += String.format("pattern = PatternClass.compile('%s');", overridesUrl.getRegexpUrl());
                    result += "matcher = pattern.matcher(messageInfo.getOriginalUrl());";
                    result += "if (matcher.find()";
                    if (overridesUrl.getUrlMethod() != null) {
                        result += String.format(" && messageInfo.getOriginalRequest().getMethod().toString() == '%s'",
                                overridesUrl.getUrlMethod().toString());
                    }
                    if (overridesUrl.getHttpHeaders() != null) {
                        Set<String> httpHeadersNames = overridesUrl.getHttpHeaders().names();
                        for (String httpHeadersName: httpHeadersNames) {
                            result += String.format(" && response.headers().contains('%s')", httpHeadersName);
                            if (overridesUrl.getHttpHeaders().getAll(httpHeadersName).size() > 0) {
                                result += String.format(" && response.headers().getAll('%s').size() == %s",
                                        httpHeadersName, overridesUrl.getHttpHeaders().getAll(httpHeadersName).size());
                                for (String httpHeadersValue : overridesUrl.getHttpHeaders().getAll(httpHeadersName)) {
                                    result += String.format(" && response.headers().getAll('%s').contains('%s')",
                                            httpHeadersName, httpHeadersValue);
                                }
                            }
                        }

                    }
                    result += ") { flag = true }";
                }
            }
        }
        result += "if (flag) {";
        if (contents != null) {
            if (contents.getTextContents() != null) {
                result += String.format("contents.setTextContents('%s');", contents.getTextContents());
            }
        }
        if (delayTime != null) {
            result += "var threadClass = Java.type('java.lang.Thread');";
            result += String.format("threadClass.sleep(%s);", delayTime);
        }
        if (response != null) {
            if (response.getStatus() != null) {
                result += "var HttpResponseStatusClass = Java.type('io.netty.handler.codec.http.HttpResponseStatus');";
                result += String.format("var status = new HttpResponseStatusClass(%d, '%s');",
                        response.getStatus().code(), response.getStatus().reasonPhrase());
                result += "response.setStatus(status);";
            }
            if (response.headers() != null) {
                result += "response.headers().clear();";
                result += "var HttpHeadersClass = Java.type('io.netty.handler.codec.http.HttpHeaders');";
                result += "var DefaultHttpHeadersClass = Java.type('io.netty.handler.codec.http.DefaultHttpHeaders');";
                result += "var httpHeaders = new DefaultHttpHeadersClass();";
                for (String headerName : response.headers().names()) {
                    for (String headersValue : response.headers().getAll(headerName)) {
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
