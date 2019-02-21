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

package me.dev4vin.data.db;


import androidx.annotation.Nullable;
import me.dev4vin.model.List;
import me.dev4vin.model.S;
import me.dev4vin.model.SList;


public interface Table<T extends S, X> {
    boolean onCreate(X x) throws ModelError;
    boolean onUpgrade(X x, int v1, int v2) throws ModelError;

    Extras<T> read(X x);

    SList<T> onReadAll(X x, boolean close);

    SList<T> onReadAll(X x, Column column);

    SList<T> onReadAll(X x, Column... column);

    Extras<T> read(X x, Column... column);

    boolean onUpdate(T t, X x, Column column);

    boolean onUpdate(T t, X x);

    boolean onDelete(X x, Column column);

    boolean onDelete(T t, X x);

    boolean onDelete(X x);

    <C> boolean onDelete(X x, Column<C> column, List<C> list);

    long onSave(T t, X x);

    boolean onSave(SList<T> list, X x, boolean close);

    boolean onDrop(X x) throws ModelError;

    void backup(X x);
    void restore(X x);

    int onGetLastId(X x);

    String getName();

    interface Extras<T extends S> {
        @Nullable
        T first();
        @Nullable T last();
        SList<T> all();
        SList<T> limit(int limit);
        SList<T> between(Column<Integer> column, Integer a, Integer b);
        SList<T> where(Column... column);
        SList<T> notIn(Column<Integer> column, Integer a, Integer b);
        SList<T> like(Column... column);
        SList<T> orderBy(Column column);
        SList<T> groupBy(Column column);
        SList<T> groupAndOrderBy(Column column, Column column1);
    }
}
