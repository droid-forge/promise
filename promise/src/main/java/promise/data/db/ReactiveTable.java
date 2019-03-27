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


import androidx.annotation.Nullable;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import promise.model.List;
import promise.model.S;
import promise.model.SList;


public interface ReactiveTable<T extends S, X> {

    boolean onCreate(X x) throws ModelError;
    boolean onUpgrade(X x, int v1, int v2) throws ModelError;

    Extras<T> read(X x);

    Maybe<SList<T>> onReadAll(X x, boolean close);

    Maybe<SList<T>> onReadAll(X x, Column column);

    Maybe<SList<T>> onReadAll(X x, Column... column);

    Extras<T> read(X x, Column... column);

    Maybe<Boolean> onUpdate(T t, X x, Column column);

    Maybe<Boolean> onUpdate(T t, X x);

    Maybe<Boolean> onDelete(X x, Column column);

    Maybe<Boolean> onDelete(T t, X x);

    Maybe<Boolean> onDelete(X x);

    <C> Maybe<Boolean> onDelete(X x, Column<C> column, List<C> list);

    Single<Long> onSave(T t, X x);

    Single<Boolean> onSave(SList<T> list, X x, boolean close);

    Single<Boolean> onDrop(X x);

    Completable backup(X x);
    Completable restore(X x);

    Maybe<Integer> onGetLastId(X x);

    String getName();

    interface Extras<T extends S> {
        @Nullable
        Maybe<T> first();
        @Nullable Maybe<T> last();
        Maybe<SList<T>> all();
        Maybe<SList<T>> limit(int limit);
        Maybe<SList<T>> between(Column<Integer> column, Integer a, Integer b);
        Maybe<SList<T>> where(Column... column);
        Maybe<SList<T>> notIn(Column<Integer> column, Integer a, Integer b);
        Maybe<SList<T>> like(Column... column);
        Maybe<SList<T>> orderBy(Column column);
        Maybe<SList<T>> groupBy(Column column);
        Maybe<SList<T>> groupAndOrderBy(Column column, Column column1);
    }
}
