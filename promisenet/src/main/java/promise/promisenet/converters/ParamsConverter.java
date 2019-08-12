package promise.promisenet.converters;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

import promise.util.Conditions;

public class ParamsConverter {
  public static String toUrlParams(JSONObject data) {
    data = Conditions.checkNotNull(data);
    Iterator<String> iter = data.keys();
    StringBuilder params = new StringBuilder();
    while (iter.hasNext()) {
      String key = iter.next();
      data.opt(key);
      params.append(key).append("=").append(data.opt(key)).append("&");
    }
    return params.toString();
  }

  private static String toUrlParams(Map<String, Object> data) {
    data = Conditions.checkNotNull(data);
    StringBuilder params = new StringBuilder();
    for (Map.Entry<String, Object> entry : data.entrySet()) {
      String key = entry.getKey();
      Object object;
      if (entry.getValue() == null) object = "";
      else object = entry.getValue();
      params.append(key).append("=").append(object.toString()).append("&");
    }
    return params.toString();
  }
}
