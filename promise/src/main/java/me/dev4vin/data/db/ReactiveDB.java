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

import android.database.sqlite.SQLiteDatabase;



import io.reactivex.Flowable;
import me.dev4vin.model.S;
import me.dev4vin.model.SList;

/**
 * Created on 12/6/17 by yoctopus.
 */

public abstract class ReactiveDB extends FastDB {
    public ReactiveDB(String name, int version, Corrupt listener) {
        super(name, version, listener);
    }

    public ReactiveDB(int version) {
        super(version);
    }

    public <T extends S> Flowable<SList<T>> readMany(Table<T, SQLiteDatabase> table, Column column) {
        return Flowable.fromArray(readAll(table, column));
    }

    public <T extends S> Flowable<SList<T>> readMany(Table<T, SQLiteDatabase> table, Column[] column) {
        return Flowable.fromArray(readAll(table, column));
    }
}
