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

package promise.promisedb;


import androidx.annotation.Nullable;

import promise.model.List;
import promise.model.Result;

public interface Store<T, K, X extends Throwable> {

  void get(K k, Result<Extras<T>, X> callBack);

  void delete(K k, T t, Result<Boolean, X> callBack);

  void update(K k, T t, Result<Boolean, X> callBack);

  void save(K k, T t, Result<Boolean, X> callBack);

  void clear(K k, Result<Boolean, X> callBack);

  void clear(Result<Boolean, X> callBack);

  abstract class StoreExtra<T, E extends Throwable> {
    public void getExtras(final List<T> list, Result<Extras<T>, E> callBack) {
      callBack.response(
          new Extras<T>() {
            @Nullable
            @Override
            public T first() {
              return list.first();
            }

            @Nullable
            @Override
            public T last() {
              return list.last();
            }

            @Override
            public List<T> all() {
              return list;
            }

            @Override
            public List<T> limit(int limit) {
              return list.sample(limit);
            }

            @SafeVarargs
            @Override
            public final <X> List<T> where(X... x) {
              return filter(list, x);
            }
          });
    }

    public abstract <Y> List<T> filter(List<T> list, Y... y);
  }
}
