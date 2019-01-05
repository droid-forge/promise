package me.yoctopus.data;


import androidx.annotation.Nullable;
import me.yoctopus.model.List;

/**
 * Created on 6/27/18 by yoctopus.
 */
public interface Extras<T> {
    @Nullable
    T first();
    @Nullable T last();
    List<T> all();
    List<T> limit(int limit);
    <X> List<T> where(X... x);
}
