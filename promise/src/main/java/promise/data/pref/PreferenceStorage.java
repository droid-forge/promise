package promise.data.pref;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.util.Map;

import kotlin.Pair;
import promise.model.List;
import promise.model.S;
import promise.repo.SyncIDataStore;

/**
 * Created on 7/17/18 by yoctopus.
 */
public class PreferenceStorage<T extends S> implements SyncIDataStore<T> {
  public static final int SAVE_MODE_REPLACE = 1;
  private Preferences preferences;
  private Converter<T, JSONObject, JSONObject> converter;


  public PreferenceStorage(String name, Converter<T, JSONObject, JSONObject> converter) {
    this.preferences = new Preferences(name);
    this.converter = converter;
  }

  @Nullable
  @Override
  @Deprecated
  public List<T> all(@Nullable Map<String, ?> args) throws Exception {
    List<T> list = new List<>();
    for (Map.Entry<String, ?> key: preferences.getAll().entrySet()) {
      String val = (String) key.getValue();
      list.add(converter.from(new JSONObject(val)));
    }
    return list;
  }

  @Nullable
  public List<T> all(@Nullable Map<String, ?> args, MapFilter<T> mapFilter) throws Exception {
    List<T> list = new List<>();
    for (Map.Entry<String, ?> entry: preferences.getAll().entrySet()) {
      String val = (String) entry.getValue();
      list.add(converter.from(new JSONObject(val)));
    }
    return list.filter(t -> mapFilter.filter(t, args));
  }

  @Nullable
  @Override
  public T one(@Nullable Map<String, ?> args) throws Exception {
    return null;
  }

  @NotNull
  @Override
  public Pair<T, Object> save(@NotNull T t, @Nullable Map<String, ?> args) throws Exception {
    return null;
  }

  @NotNull
  @Override
  public Pair<T, Object> update(@NotNull T t, @Nullable Map<String, ?> args) throws Exception {
    return null;
  }

  @Nullable
  @Override
  public Object delete(@NotNull T t, @Nullable Map<String, ?> args) throws Exception {
    return null;
  }

  @Nullable
  @Override
  public Object clear(@Nullable Map<String, ?> args) throws Exception {
    return null;
  }

  public interface MapFilter<T extends S> {
    boolean filter(T t, Map<String, ?> args);
  }


 /*@Override
 public void get(String s, ResponseCallBack<Extras<T>, Throwable> callBack) {
  try {
   String k = preferences.getString(s);
   JSONArray array = new JSONArray(k);
   if (array.length() == 0) callBack.error(new Throwable("Not available"));
   List<JSONObject> objects = new List<>();
   for (int i = 0; i < array.length(); i++) objects.add(array.getJSONObject(i));
   new StoreExtra<T, Throwable>() {
    @Override
    public <Y> List<T> filter(List<T> list, Y... y) {
     return list;
    }
   }.getExtras(
     objects.map(
         jsonObject -> converter.from(jsonObject)),
     callBack);
  } catch (JSONException e) {
   callBack.error(e);
  }
 }

 @Override
 public void delete(String s, T t, ResponseCallBack<Boolean, Throwable> callBack) {
  try {
   JSONArray array = new JSONArray(preferences.getString(s));
  } catch (JSONException e) {
   callBack.error(e);
  }
 }

 @Override
 public void update(String s, T t, ResponseCallBack<Boolean, Throwable> callBack) {
  try {
   JSONArray array = new JSONArray(preferences.getString(s));
   array.put(converter.get(t));
   preferences.save(s, array.toString());
  } catch (JSONException e) {
   callBack.error(e);
  }
 }

 @Override
 public void save(String s, final T t, ResponseCallBack<Boolean, Throwable> callBack) {
  try {
   JSONArray array = new JSONArray(preferences.getString(s));
   List<JSONObject> objects = new List<>();
   for (int i = 0; i < array.length(); i++) objects.add(array.getJSONObject(i));
   int index =
     objects.findIndex(
         jsonObject -> findIndexFunction(t).filter(jsonObject));
   if (index != -1) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
     array.remove(index);
     array.put(converter.get(t));
    } else {
     array = new JSONArray();
     objects.add(converter.get(t));
     for (JSONObject object : objects) array.put(object);
    }
   } else array.put(converter.get(t));
   preferences.save(s, array.toString());
   callBack.response(true);
  } catch (JSONException e) {
   JSONArray jsonArray = new JSONArray();
   jsonArray.put(converter.get(t));
   preferences.save(s, jsonArray.toString());
   callBack.response(true);
  }
 }

 @Override
 public void clear(String s, ResponseCallBack<Boolean, Throwable> callBack) {
  preferences.clear(s);
  callBack.response(true);
 }

 @Override
 public void clear(ResponseCallBack<Boolean, Throwable> callBack) {
  preferences.clearAll();
  callBack.response(true);
 }*/


}
