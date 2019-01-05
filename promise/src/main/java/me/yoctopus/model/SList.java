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

package me.yoctopus.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import androidx.annotation.Nullable;

public class SList<T extends S> extends List<T> {
    public SList(Collection<? extends T> c) {
        super(c);
    }

    public SList() {
        super();
    }


    public SList(int initialCapacity) {
        super(initialCapacity);
    }

    @Nullable
    public T getWithID(int key) {
        if (key < 1) throw new IllegalArgumentException("key less than 1 is not allowed");
        for (T t : this) if (t.id() == key) return t;
        return null;
    }

    public int getID(T t) {
        if (getWithID(t.id()) != null) return t.id();
        else return -1;
    }

    public int getIndex(int key) {
        int index = 0;
        for (T t : this) {
            if (t.id() == key) return index;
            index++;
        }
        return -1;
    }

    public int getIndex(T t) {
        return getIndex(t.id());
    }

    public boolean removeWithID(int id) {
        int index = getIndex(id);
        if (index != -1) {
            super.remove(index);
            return true;
        }
        return false;
    }

     public boolean remove(T t) {
        super.remove(getIndex(t));
        return true;
     }

    public void delete(T t) {
        removeWithID(t.id());
    }

    public void update(T t) {
        super.set(getIndex(t), t);
    }

    public SList<T> reverseWithID() {
        sortWithID();
        reverse();
        return this;
    }

    public SList<T> sortWithID() {
        Collections.sort(this,
                new Comparator<T>() {
                    @Override
                    public int compare(T o1, T o2) {
                        return o1.id() < o2.id() ? 1 :
                                o1.id() > o2.id() ? -1 : 0;

                    }
                });
        return this;
    }
}
