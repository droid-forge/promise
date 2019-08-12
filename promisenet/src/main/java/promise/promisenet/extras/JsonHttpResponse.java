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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created on 2/14/18 by yoctopus.
 */

public class JsonHttpResponse<T> extends HttpResponse<String, T> {

    private Class<T> tClass;

    public JsonHttpResponse(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public HttpResponse<String, T> getResponse(String response) throws JSONException {
        if (tClass == JSONObject.class) return response((T) new JSONObject(response));
        else if (tClass == JSONArray.class) return response((T) new JSONArray(response));
        else throw new JSONException("Type must be either be JSonOnject or JSonArray");
    }
}
