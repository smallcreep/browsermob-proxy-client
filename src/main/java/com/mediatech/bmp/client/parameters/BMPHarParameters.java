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

package com.mediatech.bmp.client.parameters;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ilia Rogozhin on 04.10.2016.
 */
public class BMPHarParameters {

    private boolean captureHeaders = false;
    private boolean captureContent = false;
    private boolean captureBinaryContent = false;
    private String initialPageRef = "Page 0";
    private String initialPageTitle = null;

    public BMPHarParameters(boolean captureHeaders, boolean captureContent, boolean captureBinaryContent, String initialPageRef, String initialPageTitle) {
        this.captureHeaders = captureHeaders;
        this.captureContent = captureContent;
        this.captureBinaryContent = captureBinaryContent;
        this.initialPageRef = initialPageRef;
        this.initialPageTitle = initialPageTitle;
    }

    public BMPHarParameters(String initialPageRef, String initialPageTitle) {
        this(false, false, false, initialPageRef, initialPageTitle);
    }

    public BMPHarParameters(String initialPageRef) {
        this(initialPageRef, null);
    }

    public BMPHarParameters() {
    }

    public boolean isCaptureHeaders() {
        return captureHeaders;
    }

    public void setCaptureHeaders(boolean captureHeaders) {
        this.captureHeaders = captureHeaders;
    }

    public boolean isCaptureContent() {
        return captureContent;
    }

    public void setCaptureContent(boolean captureContent) {
        this.captureContent = captureContent;
    }

    public boolean isCaptureBinaryContent() {
        return captureBinaryContent;
    }

    public void setCaptureBinaryContent(boolean captureBinaryContent) {
        this.captureBinaryContent = captureBinaryContent;
    }

    public String getInitialPageRef() {
        return initialPageRef;
    }

    public void setInitialPageRef(String initialPageRef) {
        this.initialPageRef = initialPageRef;
    }

    public String getInitialPageTitle() {
        return initialPageTitle;
    }

    public void setInitialPageTitle(String initialPageTitle) {
        this.initialPageTitle = initialPageTitle;
    }

    public Map<String, String> getMapFields() {
        Map<String, String> result = new HashMap<>();
        result.put("captureHeaders", Boolean.toString(captureHeaders));
        result.put("captureContent", Boolean.toString(captureContent));
        result.put("captureBinaryContent", Boolean.toString(captureBinaryContent));
        if (initialPageRef != null) result.put("initialPageRef", initialPageRef);
        if (initialPageTitle != null) result.put("initialPageTitle", initialPageTitle);
        return result;
    }
}
