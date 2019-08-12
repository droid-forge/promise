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

package promise.promisenet.extras;

import androidx.annotation.NonNull;

import org.json.JSONException;

import java.util.Map;

/**
 * Created on 2/7/18 by yoctopus.
 */

public abstract class HttpResponse<K, T>  {
    private T response;

    private Map<String, String> headers;

    private int status;

    public Map<String, String> headers() {
        return headers;
    }

    public HttpResponse<K, T> headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public int status() {
        return status;
    }

    public HttpResponse<K, T> status(int status) {
        this.status = status;
        return this;
    }

    public HttpResponse<K, T> response(T response) {
        this.response = response;
        return this;
    }

    public  T response() {
        return response;
    }

    public abstract HttpResponse<K, T> getResponse(K response) throws JSONException;

    @NonNull
    @Override
    public String toString() {
        return "HttpResponse{" +
                "response=" + response() +
                ", headers=" + headers +
                ", status=" + status +
                '}';
    }
}
