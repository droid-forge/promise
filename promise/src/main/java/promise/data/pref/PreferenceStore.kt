package promise.data.pref

import android.os.Build

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import promise.data.Extras
import promise.data.Store
import promise.model.List
import promise.model.ResponseCallBack
import promise.model.function.EachFunction

/**
 * Created on 7/17/18 by yoctopus.
 */

abstract class PreferenceStore<T>(name: String, private val converter: Converter<T, JSONObject, JSONObject>) : Store<T, String, Throwable> {
  private val preferences: Preferences = Preferences(name)

  abstract fun findIndexFunction(t: T): EachFunction<JSONObject>

  override fun get(s: String, callBack: ResponseCallBack<Extras<T>, Throwable>) {
    try {
      val k = preferences.getString(s)
      val array = JSONArray(k)
      if (array.length() == 0) callBack.error(Throwable("Not available"))
      val objects = List<JSONObject>()
      for (i in 0 until array.length()) objects.add(array.getJSONObject(i))
      object : Store.StoreExtra<T, Throwable>() {
        override fun <Y> filter(list: List<T>, vararg y: Y): List<T> {
          return list
        }
      }.getExtras(
          objects.map { jsonObject -> converter.from(jsonObject) },
          callBack)
    } catch (e: JSONException) {
      callBack.error(e)
    }

  }

  override fun delete(s: String, t: T, callBack: ResponseCallBack<Boolean, Throwable>) =
      get(s, ResponseCallBack<Extras<T>, Throwable>()
          .response { tExtras ->
            val list = tExtras.all()
            val index = list.findIndex { t == it }
            if (index != -1) {
              list.removeAt(index)
              clear(s, ResponseCallBack<Boolean, Throwable>()
                  .response { _ ->
                    list.forEach {
                      save(s, it, ResponseCallBack())
                    }
                  })
            }
          }
          .error { callBack.error(it) })

  override fun update(s: String, t: T, callBack: ResponseCallBack<Boolean, Throwable>) =
      get(s, ResponseCallBack<Extras<T>, Throwable>()
          .response { tExtras ->
            val list = tExtras.all()
            val index = list.findIndex { t == it }
            if (index != -1) {
              list[index] = t
              clear(s, ResponseCallBack<Boolean, Throwable>()
                  .response { _ ->
                    list.forEach {
                      save(s, it, ResponseCallBack())
                    }
                  })
            }
          }
          .error { callBack.error(it) })

  override fun save(s: String, t: T, callBack: ResponseCallBack<Boolean, Throwable>) {
    try {
      var array = JSONArray(preferences.getString(s))
      val objects = List<JSONObject>()
      for (i in 0 until array.length()) objects.add(array.getJSONObject(i))
      val index = objects.findIndex { jsonObject -> findIndexFunction(t).filter(jsonObject) }
      if (index != -1) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
          array.remove(index)
          array.put(converter.get(t))
        } else {
          array = JSONArray()
          objects.add(converter.get(t))
          for (`object` in objects) array.put(`object`)
        }
      } else
        array.put(converter.get(t))
      preferences.save(s, array.toString())
      callBack.response(true)
    } catch (e: JSONException) {
      val jsonArray = JSONArray()
      jsonArray.put(converter.get(t))
      preferences.save(s, jsonArray.toString())
      callBack.response(true)
    }
  }

  fun save(s: String, list: List<T>, callBack: ResponseCallBack<Boolean, Throwable>) {
    try {
      val array = JSONArray(preferences.getString(s))
      val objects = List<JSONObject>()
      for (i in 0 until array.length()) objects.add(array.getJSONObject(i))
      list.forEach {
        array.put(converter.get(it))
      }
      preferences.save(s, array.toString())
      callBack.response(true)
    } catch (e: JSONException) {
      val jsonArray = JSONArray()
      list.forEach {
        jsonArray.put(converter.get(it))
      }
      preferences.save(s, jsonArray.toString())
      callBack.response(true)
    }
  }

  override fun clear(s: String, callBack: ResponseCallBack<Boolean, Throwable>) {
    preferences.clear(s)
    callBack.response(true)
  }

  override fun clear(callBack: ResponseCallBack<Boolean, Throwable>) {
    preferences.clearAll()
    callBack.response(true)
  }
}
