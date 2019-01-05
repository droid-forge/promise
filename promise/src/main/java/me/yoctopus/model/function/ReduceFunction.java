package me.yoctopus.model.function;

import me.yoctopus.model.List;

/**
 * Created on 7/5/18 by yoctopus.
 */
public interface ReduceFunction<K, T> {
    K reduce(List<T> list);
}
