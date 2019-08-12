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

import java.lang.reflect.Type;

/**
 * Created on 6/16/18 by yoctopus.
 */
public abstract class CallAdapter<T, D, U> {

    public abstract Converter<T, D, U> converter();

    private Type type;

    public CallAdapter(Type type) {
        this.type = type;
    }

    public Type type() {
        return type;
    }

    public T parse(HttpResponse<?, D> response){
        return converter().from(response.response());
    }

    public U parse(T t) {
        return converter().get(t);
    }

}
