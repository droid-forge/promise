package promise.app_base.data.net

import promise.app_base.auth.User
import promise.data.net.http.Field
import promise.data.net.http.FormUrlEncoded
import promise.data.net.http.POST
import promise.data.net.net.Call

interface AuthApi {

  @FormUrlEncoded
  @POST("/login")
  fun login(@Field("email") email: String,
            @Field("password") password: String): Call<User>
}