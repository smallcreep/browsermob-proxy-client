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

/**
 * Created by Ilia Rogozhin on 04.10.2016.
 */
public class BMPPageParameters {

    private boolean captureHeaders = false;
    private boolean captureContent = false;
    private boolean captureBinaryContent = false;
    private String initialPageRef = "Page 1";
    private String initialPageTitle = initialPageRef;

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

    public BMPPageParameters(boolean captureHeaders, boolean captureContent, boolean captureBinaryContent,
                             String initialPageRef, String initialPageTitle) {
        this.captureHeaders = captureHeaders;
        this.captureContent = captureContent;
        this.captureBinaryContent = captureBinaryContent;
        this.initialPageRef = initialPageRef;
        if (initialPageTitle.isEmpty()) {
            this.initialPageTitle = this.initialPageRef;
        } else {
            this.initialPageTitle = initialPageTitle;
        }
    }

    public BMPPageParameters(String initialPageRef, String initialPageTitle) {
        this(false, false, false, initialPageRef, initialPageTitle);
    }

    public BMPPageParameters(String initialPageRef) {
        this(initialPageRef, null);
    }

    public BMPPageParameters() {
    }
}
