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


import io.reactivex.Maybe;
import io.reactivex.Single;
import promise.model.List;
import promise.model.S;
import promise.model.SList;
import promise.model.SModel;

interface ReactiveCrud<X> {

    <T extends SModel> ReactiveTable.Extras<T> read(Table<T, X> table) throws ModelError;

    <T extends SModel> Maybe<SList<T>> readAll(Table<T, X> table);

    <T extends SModel> Maybe<SList<T>> readAll(Table<T, X> table, Column... column);

    <T extends SModel> Maybe<Boolean> update(T t, Table<T, X> table, Column column);

    <T extends SModel> Maybe<Boolean> update(T t, Table<T, X> table);

    <T extends SModel> Maybe<Boolean> delete(Table<T, X> table, Column column);

    <T extends SModel> Maybe<Boolean> delete(Table<T, X> table, T t);

    Maybe<Boolean> delete(Table<?, X> table);

    <C> Maybe<Boolean> delete(Table<?, X> table, Column<C> column, List<C> list);

    <T extends SModel> Single<Long> save(T t, Table<T, X> table);

    <T extends SModel> Single<Boolean> save(SList<T> list, Table<T, X> table);

    Maybe<Boolean> deleteAll();

    <T extends SModel> Maybe<Integer> getLastId(Table<T, X> table);
}
