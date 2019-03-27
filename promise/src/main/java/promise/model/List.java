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

package promise.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.core.util.Pair;
import promise.model.function.BIConsumer;
import promise.model.function.Combiner;
import promise.model.function.EachFunction;
import promise.model.function.FilterFunction2;
import promise.model.function.GroupFunction;
import promise.model.function.GroupFunction2;
import promise.model.function.GroupFunction3;
import promise.model.function.JoinFunction;
import promise.model.function.MapFunction;
import promise.model.function.ReduceFunction;
import promise.model.function.SearchFunction;
import promise.util.Conditions;

/**
 * Created on 5/13/18 by yoctopus.
 */
public class List<T> extends ArrayList<T> {
  /**
   * Constructs an empty list with the specified initial capacity.
   *
   * @param initialCapacity the initial capacity of the list
   * @throws IllegalArgumentException if the specified initial capacity is negative
   */
  public List(int initialCapacity) {
    super(initialCapacity);
  }

  /**
   * Constructs an empty list with an initial capacity of ten.
   */
  public List() {
  }

  /**
   * Constructs a list containing the elements of the specified collection, in the order they are
   * returned by the collection's iterator.
   *
   * @param c the collection whose elements are to be placed into this list
   * @throws NullPointerException if the specified collection is null
   */
  public List(@NonNull Collection<? extends T> c) {
    super(c);
  }

  @SafeVarargs
  public static <T> List<T> fromArray(T... t) {
    return new List<>(Arrays.asList(t));
  }

  public List<T> reverse() {
    Collections.reverse(this);
    return this;
  }

  public List<T> sample(int size) {
    List<T> list = new List<>();
    if (size() > size) for (int i = 0; i < size; i++) list.add(get(i));
    else list = this;
    return list;
  }

  @Nullable
  public T find(EachFunction<T> function) {
    return filter(function).first();
  }

  public int findIndex(EachFunction<T> function) {
    int match = 0;
    for (T t : this) {
      if (function.filter(t)) return match;
      match++;
    }
    return -1;
  }

  public List<T> shuffle() {
    Collections.shuffle(this);
    return this;
  }

  public <E> List<E> map(MapFunction<E, T> function) {
    List<E> list = new List<>();
    for (T t : this) list.add(function.from(t));
    return list;
  }

  public List<T> sorted(Comparator<T> comparator) {
    Collections.sort(this, comparator);
    return this;
  }

  public <K> List<T> arranged(final MapFunction<K, T> function, final Comparator<K> comparator) {
    return map(new MapFunction<Arrangeable<T, K>, T>() {
      @Override
      public Arrangeable<T, K> from(T t) {
        return new Arrangeable<T, K>().value(t).key(function.from(t));
      }
    })
        .sorted(
            new Comparator<Arrangeable<T, K>>() {
              @Override
              public int compare(Arrangeable<T, K> o1, Arrangeable<T, K> o2) {
                return comparator.compare(o1.key(), o2.key());
              }
            })
        .map(
            new MapFunction<T, Arrangeable<T, K>>() {
              @Override
              public T from(Arrangeable<T, K> arrangeable) {
                return arrangeable.value();
              }
            });
  }

  public <E> List<E> group(GroupFunction3<E, T> function) {
    return function.group(this);
  }

  public <K, E> List<E> group(GroupFunction2<K, E, T> function) {
    List<E> es = new List<>();
    Map<K, List<T>> map = new ArrayMap<>();
    for (int i = 0, size = this.size(); i < size; i++) {
      T t = this.get(i);
      K key = function.getKey(t);
      if (map.containsKey(key)) {
        List<T> list = Conditions.checkNotNull(map.get(key));
        list.add(t);
      } else {
        List<T> list = new List<>();
        list.add(t);
        map.put(key, list);
      }
    }
    for (Map.Entry<K, List<T>> entry : map.entrySet()) {
      E e = function.get(entry.getKey());
      function.apply(e, entry.getValue());
      es.add(e);
    }
    return es;
  }

  public <K> List<Category<K, T>> groupBy(final GroupFunction<K, T> function) {
    return group(
        new GroupFunction2<K, Category<K, T>, T>() {
          @Override
          public K getKey(T t) {
            return function.getKey(t);
          }

          @Override
          public Category<K, T> get(K k) {
            return new Category<>(k);
          }

          @Override
          public void apply(Category<K, T> category, List<T> list) {
            category.list(list);
          }
        });
  }

  public <U> List<T> joinOn(List<U> uList, JoinFunction<T, U> function) {
    List<T> ts = new List<>();
    for (int i = 0, size = this.size(); i < size; i++) {
      T t = this.get(i);
      for (int i1 = 0, uSize = uList.size(); i1 < uSize; i1++) {
        U u = uList.get(i1);
        if (!function.joinBy(t, u)) continue;
        ts.add(t);
      }
    }
    return ts;
  }

  public <U> List<T> join(List<U> uList, Combiner<U, T> function) {
    if (this.size() != uList.size())
      throw new IllegalArgumentException("Samples must be of same size");
    List<T> ts = new List<>();
    for (int i = 0, size = this.size(); i < size; i++) {
      T t = this.get(i);
      U u = uList.get(i);
      ts.add(function.join(t, u));
    }
    return ts;
  }

  public List<T> uniques() {
    return new List<>(new HashSet<>(this));
  }

  public <K> List<T> uniques(MapFunction<K, T> function, JoinFunction<T, K> joinFunction) {
    return joinOn(map(function).uniques(), joinFunction);
  }

  public <U, K> List<T> reduce(
      List<U> uList, final FilterFunction2<K, U, T> function, final boolean reverse) {
    final Set<K> set = new HashSet<>();
    set.addAll(
        uList.map(
            new MapFunction<K, U>() {
              @Override
              public K from(U u) {
                return function.getKey(u);
              }
            }));
    return filter(
        new EachFunction<T>() {
          @Override
          public boolean filter(T t) {
            if (reverse) return set.contains(function.filterBy(t));
            else return !set.contains(function.filterBy(t));
          }
        });
  }

  public <K, U> List<T> merge(
      List<U> list,
      final boolean reverse,
      final FilterFunction2<K, U, T> function,
      Combiner<U, T> combiner) {
    final Set<K> set = new HashSet<>();
    set.addAll(
        list.map(
            new MapFunction<K, U>() {
              @Override
              public K from(U u) {
                return function.getKey(u);
              }
            }));
    return filter(
        new EachFunction<T>() {
          @Override
          public boolean filter(T t) {
            return reverse == set.contains(function.filterBy(t));
          }
        })
        .join(list, combiner);
  }

  public <K, MERGE> List<Pair<T, K>> mergeWith(List<K> list, final FilterFunction2<MERGE, K, T> function) {
    List<Pair<T, K>> list1 = new List<>();
    /*Set<Arrangeable<K, MERGE>> set = new HashSet<>(
        list.map(new MapFunction<Arrangeable<K, MERGE>, K>() {
          @Override
          public Arrangeable<K, MERGE> from(K k) {
            return new Arrangeable<K, MERGE>().key(function.getKey(k)).value(k);
          }
        })
    );*/
    for (T t : this)
      for (K k : list)
        if (function.getKey(k).equals(function.filterBy(t))) list1.add(new Pair<>(t, k));
    return list1;
  }

  public boolean anyMatch(EachFunction<T> function) {
    boolean match = false;
    for (T t : this)
      if (function.filter(t)) {
        match = true;
        break;
      }
    return match;
  }

  public boolean allMatch(EachFunction<T> function) {
    boolean match = true;
    for (T t : this)
      match = match && function.filter(t);
    return match;
  }

  public List<T> filter(EachFunction<T> function) {
    List<T> list = new List<>();
    for (T t : this) if (function.filter(t)) list.add(t);

    return list;
  }

  public <U> List<T> reduce(List<U> list, JoinFunction<T, U> function) {

    List<T> ts = new List<>();
    for (int i = 0; i < this.size(); i++) {
      T t = this.get(i);
      for (int j = 0; j < list.size(); j++)
        if (!function.joinBy(t, list.get(j))) {
          ts.add(t);
          break;
        }
    }

    return ts;
  }

  public void consume(BIConsumer<T, T> consumer) {
    if (isEmpty() || size() < 1) return;
    else if (size() < 2) {
      consumer.accept(get(0), get(1));
      return;
    }
    for (int i = 0; i < size(); i++) {
      consumer.accept(get(i), get(i + 1));
    }
  }

  public List<T> search(SearchFunction<T> function) {
    List<T> list = new List<>();
    for (T t : this) if (function.search(t)) list.add(t);
    return list;
  }

  @Nullable
  public T first() {
    return this.isEmpty() ? null : this.get(0);
  }

  @Nullable
  public T last() {
    return this.isEmpty() ? null : get(size() - 1);
  }

  public <K> K reduce(ReduceFunction<K, T> function) {
    return function.reduce(this);
  }
}
