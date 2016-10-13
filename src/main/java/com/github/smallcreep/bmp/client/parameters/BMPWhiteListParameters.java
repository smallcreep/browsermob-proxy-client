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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ilia Rogozhin on 13.10.2016.
 */
public class BMPWhiteListParameters {

    private String regex;
    private Integer status;

    public BMPWhiteListParameters() {
    }

    public BMPWhiteListParameters(String regex, Integer status) {
        this.regex = regex;
        this.status = status;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BMPWhiteListParameters that = (BMPWhiteListParameters) o;

        if (regex != null ? !regex.equals(that.regex) : that.regex != null) return false;
        return status != null ? status.equals(that.status) : that.status == null;

    }

    @Override
    public int hashCode() {
        int result = regex != null ? regex.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BMPWhiteListParameters{" +
                "regex='" + regex + '\'' +
                ", status=" + status +
                '}';
    }

    public Map<String, String> getMapFields() {
        Map<String, String> result = new HashMap<>();
        if (regex != null) result.put("regex", regex);
        if (status != null) result.put("status", status.toString());
        return result;
    }
}
