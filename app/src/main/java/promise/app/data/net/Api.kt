package promise.app.data.net

import promise.app.models.Todo
import promise.data.net.http.GET
import promise.data.net.http.Path
import promise.data.net.net.Call

interface TodoApi {

  @GET("/todos/{skip}/{limit}")
  fun todos(@Path("skip") skip: Int, @Path("limit") limit: Int): Call<List<Todo>>

}


