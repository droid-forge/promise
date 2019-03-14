/*
 * Copyright 2017, Peter Vincent
 * Licensed under the Apache License, Version 2.0, Promise.
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.dev4vin.models;

public class ResponseCallBack<T, E extends Throwable> {

    private Response<T, E> response;
    private Error<E> error;

    public void response(T t) {
        if (response != null) {
            try {
                response.onResponse(t);
            } catch (Throwable e) {
                error((E) e);
            }
        }
    }

    public ResponseCallBack<T, E> response(Response<T, E> response) {
        this.response = response;
        return this;
    }

    public void error(E e) {
        if (error != null) error.onError(e);
    }

    public ResponseCallBack<T, E> error(Error<E> error) {
        this.error = error;
        return this;
    }

    public interface Response<T, E extends Throwable> {
        void onResponse(T t) throws E;
    }

    public interface Error<E> {
        void onError(E e);
    }
}
