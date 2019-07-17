package promise.app_base.data.net

import promise.app_base.models.Todo
import promise.data.net.http.GET
import promise.data.net.http.Path
import promise.data.net.net.Call

interface TodoApi {

  @GET("/todos")
  fun todos(): Call<List<Todo>>

}


