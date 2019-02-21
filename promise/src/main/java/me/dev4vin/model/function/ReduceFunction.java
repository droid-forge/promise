package me.dev4vin.model.function;

import me.dev4vin.model.List;

/**
 * Created on 7/5/18 by yoctopus.
 */
public interface ReduceFunction<K, T> {
    K reduce(List<T> list);
}
