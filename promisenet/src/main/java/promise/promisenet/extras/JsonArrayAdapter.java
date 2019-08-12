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

package promise.promisenet.extras;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Type;

import promise.data.log.LogUtil;
import promise.model.List;
import promise.util.DoubleConverter;

/**
 * Created on 6/16/18 by yoctopus.
 */
public class JsonArrayAdapter<T> extends CallAdapter<List<T>, JSONArray, JSONArray> {
    private String TAG = LogUtil.makeTag(JsonArrayAdapter.class);
    private Gson gson;

    public JsonArrayAdapter(Type type) {
        super(type);
        gson = new Gson();
    }

    @Override
    public DoubleConverter<List<T>, JSONArray, JSONArray> converter() {
        return new DoubleConverter<List<T>, JSONArray, JSONArray>() {

            @Override
            public JSONArray serialize(List<T> list) {
                JSONArray array = new JSONArray();
                for (T t: list) array.put(gson.toJson(t));
                return array;
            }

            @Override
            public List<T> deserialize(JSONArray jsonArray) {
                List<T> list = new List<>();
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        T t = gson.fromJson(jsonArray.getJSONObject(i).toString(), type());
                        list.add(t);
                    }
                } catch (JSONException e) {
                    LogUtil.e(TAG, e);
                }
                return list;
            }
        };
    }
}
