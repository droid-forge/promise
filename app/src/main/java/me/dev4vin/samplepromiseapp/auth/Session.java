package me.dev4vin.samplepromiseapp.auth;

import androidx.collection.ArrayMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import me.dev4vin.samplepromiseapp.data.net.ServerAPI;
import me.dev4vin.samplepromiseapp.error.AuthError;
import me.yoctopus.data.log.LogUtil;
import me.yoctopus.data.net.EndPoint;
import me.yoctopus.data.net.extras.HttpPayload;
import me.yoctopus.data.net.extras.HttpResponse;
import me.yoctopus.data.pref.PreferenceStore;
import me.yoctopus.data.pref.Preferences;
import me.yoctopus.data.utils.Converter;
import me.yoctopus.model.ResponseCallBack;
import me.yoctopus.model.function.FilterFunction;

public class Session {
  private static String TAG = LogUtil.makeTag(Session.class);
  /**
   * the name of key in preference to store token in session preference
   */
  private static final String AUTH_TOKEN_KEY = "auth_token_key";
  /**
   * preference object to easily store user info in shared preferences
   */
  private static Preferences sessionPreferences;
  /**
   * preference store to store multiple roles in shared preferences
   */
  private static PreferenceStore<Role> rolePreferenceStore;
  /**
   * server api tpo access upstream service
   */
  private static ServerAPI serverAPI;
  static {
    serverAPI = ServerAPI.instance();
    sessionPreferences = new Preferences("session");
    rolePreferenceStore = new PreferenceStore<Role>("session", new Converter<Role, JSONObject, JSONObject>() {
      @Override
      public JSONObject get(Role role) {
        // convert a role to a json object
        JSONObject object = new JSONObject();
        try {
          object.put("name", role.name());
          object.put("allowed", role.allowed());
        } catch (JSONException e) {
          e.printStackTrace();
        }
        return object;
      }

      @Override
      public Role from(JSONObject jsonObject) {
        // get a role from the json object
        try {
          return new Role()
              .name(jsonObject.getString("name"))
              .allowed(jsonObject.getBoolean("allowed"));
        } catch (JSONException e) {
          e.printStackTrace();
          return null;
        }
      }
    }) {
      @Override
      public FilterFunction<JSONObject> findIndexFunction(final Role role) {
        return new FilterFunction<JSONObject>() {
          @Override
          public boolean filter(JSONObject jsonObject) {
            /// do not save a role that already exists in the array
            try {
              return jsonObject.getString("name").equals(role.name());
            } catch (JSONException e) {
              e.printStackTrace();
              return false;
            }
          }
        };
      }
    };
  }
  public static void login(String email, final String password, final ResponseCallBack<User, AuthError> responseCallBack) {
    Map<String, Object> data = new ArrayMap<>();
    data.put("email", email);
    data.put("password", password);
    serverAPI.post(new EndPoint("auth/login"), HttpPayload.get().jsonPayload(data),
        new ResponseCallBack<HttpResponse<String, JSONObject>, JSONException>()
    .response(new ResponseCallBack.Response<HttpResponse<String, JSONObject>, JSONException>() {
      @Override
      public void onResponse(HttpResponse<String, JSONObject> stringJSONObjectHttpResponse) throws JSONException {
        int status = stringJSONObjectHttpResponse.status();
        if (status == 200) {
            JSONObject payload = stringJSONObjectHttpResponse.response().getJSONObject("user");
            String token = stringJSONObjectHttpResponse.response().getString("token");
            User user = new User().email(payload.getString("email"))
                .names(payload.getString("names"));
            ///TODO read the roles from the payload and store in user object
            storeUser(user);
            storeToken(token);
            responseCallBack.response(user);
        } else responseCallBack.error(new AuthError().code(status));
      }
    }).error(new ResponseCallBack.Error<JSONException>() {
          @Override
          public void onError(JSONException e) {
              LogUtil.e(TAG, "session error ", e);
          }
        }));
  }

  private static void storeUser(User user) {
    Map<String, Object> map = new ArrayMap<>();
    map.put("email", user.email());
    map.put("names", user.names());
    if (sessionPreferences.save(map)) for (Role role : user.roles())
      rolePreferenceStore.save("roles", role, new ResponseCallBack<Boolean, Throwable>());
  }

  private static void storeToken(String token) {
    sessionPreferences.save(AUTH_TOKEN_KEY, token);
  }

  public static String getToken() {
    return sessionPreferences.getString(AUTH_TOKEN_KEY);
  }

  public static User getUser() {
    return new User().email(sessionPreferences.getString("email")).names(sessionPreferences.getString("names"));
  }
}
