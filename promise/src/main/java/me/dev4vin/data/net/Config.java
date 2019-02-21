/*
 *
 *  * Copyright 2017, Peter Vincent
 *  * Licensed under the Apache License, Version 2.0, Promise.
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package me.dev4vin.data.net;


import android.content.Context;

import java.util.Map;

import me.dev4vin.Promise;

public class Config {
    private long timeOut = 0;
    private String mainUrl;
    private Context context;
    private Map<String, String> headers;
    private int retry = 0;
    private boolean allowSendErrors = false;

    public int retry() {
        return retry;
    }

    public Config retry(int retry) {
        this.retry = retry;
        return this;
    }

    public boolean sendMessages() {
        return allowSendErrors;
    }

    public Config sendMessages(boolean allowSendErrors) {
        this.allowSendErrors = allowSendErrors;
        return this;
    }

    public static Config create(String mainUrl) {
        return new Config(mainUrl, Promise.instance().context());
    }

    public Config timeOut(long timeUnit) {
        this.timeOut = timeUnit;
        return this;
    }

    private Config(String mainUrl, Context context) {
        this.mainUrl = mainUrl;
        this.context = context;
    }


    public long timeOut() {
        return timeOut;
    }

    public String getMainUrl() {
        return mainUrl;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }


    protected String getUrl(String endpoint) {
        if (mainUrl.endsWith("/") && endpoint.startsWith("/")) endpoint = endpoint.substring(1);
        else if (!mainUrl.endsWith("/") && !endpoint.startsWith("/")) mainUrl = mainUrl.concat("/");
        return mainUrl.concat(endpoint);
    }

    public Map<String, String> headers() {
        return headers;
    }

    public Config headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }
}
