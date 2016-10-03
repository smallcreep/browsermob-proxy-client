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

package com.mediatech.bmp.client;

/**
 * Created by Ilia Rogozhin on 02.10.2016.
 */
public class BMPLittleProxy extends BMPProxy {

    public BMPLittleProxy() {
        super();
    }

    public BMPLittleProxy(int port) {
        super(port);
    }

    public BMPLittleProxy(int port, String adsress) {
        super(port, adsress);
    }

    public BMPLittleProxy(int port, String adsress, String protocol) {
        super(port, adsress, protocol);
    }
}