package promise.app_base.auth

import androidx.collection.ArrayMap

import org.json.JSONException
import org.json.JSONObject
import promise.app_base.data.net.ServerAPI
import promise.app_base.error.AuthError

import promise.data.log.LogUtil
import promise.data.net.EndPoint
import promise.data.net.extras.HttpPayload
import promise.data.net.extras.HttpResponse
import promise.data.pref.PreferenceStore
import promise.data.pref.Preferences
import promise.data.utils.Converter
import promise.model.ResponseCallBack
import promise.model.function.EachFunction

object Session {
  private val TAG = LogUtil.makeTag(Session::class.java)
  /**
   * the namee of key in preference to store token in session preference
   */
  private val AUTH_TOKEN_KEY = "auth_token_key"
  /**
   * preference object to easily store user info in shared preferences
   */
  private var sessionPreferences: Preferences? = null
  /**
   * preference store to store multiple roles in shared preferences
   */
  private var rolePreferenceStore: PreferenceStore<Role>? = null
  /**
   * server api tpo access upstream service
   */
  private var serverAPI: ServerAPI? = null

  val token: String
    get() = sessionPreferences!!.getString(AUTH_TOKEN_KEY)

  val user: User
    get() = User().email(sessionPreferences!!.getString("email")).names(sessionPreferences!!.getString("names"))

  init {
    serverAPI = ServerAPI.instance()
    sessionPreferences = Preferences("session")
    rolePreferenceStore = object : PreferenceStore<Role>("session",
        object : Converter<Role, JSONObject, JSONObject> {
      override fun get(role: Role): JSONObject =// convert a role to a json object
          object : JSONObject() {
            init {
              try {
                put("namee", role.name())
                put("allowed", role.allowed())
              } catch (e: JSONException) {
                e.printStackTrace()
              }
            }
          }

      override fun from(jsonObject: JSONObject): Role? =// get a role from the json object
          try {
            Role()
                .name(jsonObject.getString("namee"))
                .allowed(jsonObject.getBoolean("allowed"))
          } catch (e: JSONException) {
            e.printStackTrace()
            null
          }
    }) {
      override fun findIndexFunction(role: Role): EachFunction<JSONObject> =
          EachFunction { jsonObject ->
            /// do not save a role that already exists in the array
            try {
              jsonObject.getString("namee") == role.name()
            } catch (e: JSONException) {
              e.printStackTrace()
              false
            }
          }
    }
  }

  fun login(email: String, password: String, responseCallBack: ResponseCallBack<User, AuthError>) {
    val data = ArrayMap<String, Any>()
    data["email"] = email
    data["password"] = password
    serverAPI!!.post(EndPoint("auth/login"), HttpPayload.get().jsonPayload(data),
        ResponseCallBack<HttpResponse<String, JSONObject>, JSONException>()
            .response { stringJSONObjectHttpResponse ->
              val status = stringJSONObjectHttpResponse.status()
              if (status == 200) {
                val payload = stringJSONObjectHttpResponse.response().getJSONObject("user")
                val token = stringJSONObjectHttpResponse.response().getString("token")
                val user = User().email(payload.getString("email"))
                    .names(payload.getString("names"))
                ///TODO read the roles from the payload and store in user object
                storeUser(user)
                storeToken(token)
                responseCallBack.response(user)
              } else
                responseCallBack.error(AuthError().code(status))
            }.error { e -> LogUtil.e(TAG, "session error ", e) })
  }

  private fun storeUser(user: User) {
    val map = ArrayMap<String, Any>()
    map["email"] = user.email()
    map["names"] = user.names()
    sessionPreferences!!.save(map)
    if (user.roles() == null) return
    if (!user.roles()!!.isEmpty())
      for (role in user.roles()!!)
        rolePreferenceStore!!.save("roles", role, ResponseCallBack())
  }

  private fun storeToken(token: String) {
    sessionPreferences!!.save(AUTH_TOKEN_KEY, token)
  }
}
