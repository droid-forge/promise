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

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created on 5/14/18 by yoctopus.
 */

public class JsonArrayHttpResponse extends HttpResponse<String, JSONArray> {

    @Override
    public HttpResponse<String, JSONArray> getResponse(String response) throws JSONException {
        return response(new JSONArray(response));
    }
}
