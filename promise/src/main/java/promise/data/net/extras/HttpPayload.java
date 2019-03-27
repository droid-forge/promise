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

package promise.data.net.extras;


import java.io.File;
import java.util.Map;

import androidx.collection.ArrayMap;
import promise.data.log.LogUtil;

/**
 * Created on 2/9/18 by yoctopus.
 */

public class HttpPayload {
    private String TAG = LogUtil.makeTag(HttpPayload.class);
    private Map<String, Object> payload;
    private Map<String, String> headers;
    private boolean shouldBeJson, shouldBeParams;
    private Map<String, File> files;

    private HttpPayload() {
        shouldBeParams = true;
    }

    public Map<String, File> files() {
        return files;
    }
    public Map<String, Object> payload() {
        if (payload == null) {
            payload = new ArrayMap<>();
        }
        return payload;
    }

    public HttpPayload file(String name, File file) {
        if (files == null) files = new ArrayMap<>();
        files.put(name, file);
        return this;
    }

    public HttpPayload file(String name, String file) {
        return file(name, new File(file));
    }

    public HttpPayload jsonPayload(Map<String, Object> payload) {
        this.payload = payload;
        this.shouldBeJson = true;
        this.shouldBeParams = false;
        return this;
    }
    public HttpPayload paramsPayload(Map<String, Object> payload) {
        this.payload = payload;
        this.shouldBeParams = true;
        this.shouldBeJson = false;
        return this;
    }
    public Map<String, String> headers() {
        if (headers == null) {
            headers = new ArrayMap<>();
        }
        return headers;
    }

    public static HttpPayload headers(Map<String, String> headers) {
        HttpPayload payload = get();
        payload.headers = headers;
        return payload;
    }
    public static HttpPayload get() {

        return new HttpPayload();
    }

    public boolean shouldBeJson() {
        return shouldBeJson;
    }

    public boolean shouldBeParams() {
        return shouldBeParams;
    }

}
