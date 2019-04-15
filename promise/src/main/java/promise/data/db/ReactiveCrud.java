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

package promise.data.db;


import io.reactivex.Maybe;
import io.reactivex.Single;
import promise.model.List;
import promise.model.S;
import promise.model.SList;


interface ReactiveCrud<X> {

    <T extends S> ReactiveTable.Extras<T> read(Table<T, X> table) throws ModelError;

    <T extends S> Maybe<SList<T>> readAll(Table<T, X> table);

    <T extends S> Maybe<SList<T>> readAll(Table<T, X> table, Column column);

    <T extends S> Maybe<Boolean> update(T t, Table<T, X> table, Column column);

    <T extends S> Maybe<Boolean> update(T t, Table<T, X> table);

    <T extends S> Maybe<SList<T>> readAll(Table<T, X> table, Column[] columns);

    <T extends S> ReactiveTable.Extras<T> read(Table<T, X> table, Column... columns) throws ModelError;

    Maybe<Boolean> delete(Table<?, X> table, Column column);

    <T extends S> Maybe<Boolean> delete(Table<T, X> table, T t);

    Maybe<Boolean> delete(Table<?, X> table);

    <T> Maybe<Boolean> delete(Table<?, X> table, Column<T> column, List<T> list);

    <T extends S> Single<Long> save(T t, Table<T, X> table);

    <T extends S> Single<Boolean> save(SList<T> list, Table<T, X> table);

    Maybe<Boolean> deleteAll();

    Maybe<Integer> getLastId(Table<?, X> table);
}
