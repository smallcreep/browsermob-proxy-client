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
public class BMPHarParameters {

    private String pageRef = "Page 1";
    private String pageTitle = pageRef;

    public String getPageRef() {
        return pageRef;
    }

    public void setPageRef(String pageRef) {
        this.pageRef = pageRef;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public BMPHarParameters(String pageRef, String pageTitle) {
        this.pageRef = pageRef;
        if (pageTitle.isEmpty()) {
            this.pageTitle = this.pageRef;
        } else {
            this.pageTitle = pageTitle;
        }
    }

    public BMPHarParameters(String pageRef) {
        this(pageRef, null);
    }

    public BMPHarParameters() {
    }
}
