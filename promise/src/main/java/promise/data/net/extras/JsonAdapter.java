/*
 * Copyright 2017, Solutech RMS
 * Licensed under the Apache License, Version 2.0, "Solutech Limited".
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package promise.data.net.extras;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Map;

import promise.data.net.extras.CallAdapter;
import promise.data.utils.Converter;

/**
 * Created on 6/16/18 by yoctopus.
 */
public class JsonAdapter<T> extends CallAdapter<T, JSONObject, Map<String, Object>> {

    public JsonAdapter(Type type) {
        super(type);
    }

    @Override
    public Converter<T, JSONObject, Map<String, Object>> converter() {
        return new Converter<T, JSONObject, Map<String, Object>>() {
            @Override
            public Map<String, Object> get(T t) {
                try {
                    return promise.util.Converter.toMap(new JSONObject(new Gson().toJson(t)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public T from(JSONObject jsonObject) {
                return new Gson().fromJson(jsonObject.toString(), type());
            }
        };
    }
}
