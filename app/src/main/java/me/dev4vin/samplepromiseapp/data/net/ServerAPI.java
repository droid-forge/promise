package me.dev4vin.samplepromiseapp.data.net;

import androidx.collection.ArrayMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import me.dev4vin.samplepromiseapp.auth.Session;
import me.dev4vin.samplepromiseapp.error.ServerError;
import me.dev4vin.samplepromiseapp.models.Todo;
import me.dev4vin.data.log.LogUtil;
import me.dev4vin.data.net.Config;
import me.dev4vin.data.net.EndPoint;
import me.dev4vin.data.net.FastParser;
import me.dev4vin.data.net.Interceptor;
import me.dev4vin.data.net.extras.HttpPayload;
import me.dev4vin.data.net.extras.HttpResponse;
import me.dev4vin.model.List;
import me.dev4vin.model.ResponseCallBack;

public class ServerAPI extends FastParser {
  private static ServerAPI instance;
  private static final String UPSTREAM_URL = "https://somelink.com/api/";
  private String TAG = LogUtil.makeTag(ServerAPI.class);

  /**
   * Add a response interceptor to intercept error codes 403
   */
  private ServerAPI() {
    super(Config.create(UPSTREAM_URL).retry(5));
    responseInterceptor(new Interceptor<HttpResponse<?, ?>>() {
      @Override
      public void intercept(HttpResponse<?, ?> httpResponse, ResponseCallBack<HttpResponse<?, ?>, Throwable> callBack) {
        int status = httpResponse.status();
        /// check for 403 and cancel response
        if (status == 403) callBack.error(new ServerError("Request not authorized"));
        /// complete the response
        else callBack.response(httpResponse);
      }
    });
  }

  /**
   * returns default headers
   *
   * @return the headers to be used for every requests
   */
  @Override
  public Map<String, String> getHeaders() {
    Map<String, String> map = new ArrayMap<>();
    map.put("ConsumerAPIKEY", "some value for your gateway to know this app as a consumer");
    map.put("Authorization", "Bearer " + Session.getToken());
    return map;
  }

  /**
   * get list of todos from server
   *
   * @param skip             the skip to start querying from, useful in pagination
   * @param limit            the number of todos we want
   * @param responseCallBack to return data to caller function
   */
  public void getTodos(int skip, int limit, final ResponseCallBack<List<Todo>, ServerError> responseCallBack) {
    Map<String, String> parts = new ArrayMap<>();
    parts.put("from", String.valueOf(skip));
    parts.put("to", String.valueOf(limit));
    get(new EndPoint("todos/:from/-/:to").params(parts), HttpPayload.get(), new ResponseCallBack<HttpResponse<String, JSONObject>, JSONException>()
        .response(new ResponseCallBack.Response<HttpResponse<String, JSONObject>, JSONException>() {
          @Override
          public void onResponse(HttpResponse<String, JSONObject> stringJSONObjectHttpResponse) throws JSONException {
            int status = stringJSONObjectHttpResponse.status();
            if (status == 200) {
              JSONArray array = stringJSONObjectHttpResponse.response().getJSONArray("payload");
              List<Todo> todos = new List<>();
              for (int i = 0; i < array.hashCode(); i++) {
                JSONObject todoObject = array.getJSONObject(i);
                Todo todo = new Todo().category(todoObject.getString("category")).name("name").completed(todoObject.getBoolean("completed"));
                todos.add(todo);
              }
              responseCallBack.response(todos);
            } else if (status == 404) responseCallBack.error(new ServerError("Todos not found"));
          }
        }).error(new ResponseCallBack.Error<JSONException>() {
          @Override
          public void onError(JSONException e) {
              LogUtil.e(TAG, "get-todos: "+ e);
          }
        }));
  }

  public void addTodo(Todo todo, ResponseCallBack<Todo, ServerError> responseCallBack) {

  }

  public void updateTodo(Todo todo, ResponseCallBack<Todo, ServerError> responseCallBack) {

  }

  public void deleteTodo(Todo todo, ResponseCallBack<Boolean, ServerError> responseCallBack) {

  }

  public static ServerAPI instance() {
    if (instance == null) instance = new ServerAPI();
    return instance;
  }
}
