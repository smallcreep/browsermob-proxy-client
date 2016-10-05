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

package com.github.smallcreep.bmp.client.parameters;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ilia Rogozhin on 04.10.2016.
 */
public class BMPPageParameters {

    private String pageRef = "Page 0";
    private String pageTitle = null;

    public BMPPageParameters(String pageRef, String pageTitle) {
        this.pageRef = pageRef;
        this.pageTitle = pageTitle;
    }

    public BMPPageParameters(String pageRef) {
        this(pageRef, null);
    }

    public BMPPageParameters() {
    }

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

    public Map<String, String> getMapFields() {
        Map<String, String> result = new HashMap<>();
        if (pageRef != null) result.put("pageRef", pageRef);
        if (pageTitle != null) result.put("pageTitle", pageTitle);
        return result;
    }
}
