package promise.app.data.net

import promise.app.error.AppError
import promise.app.models.Todo
import promise.data.net.converters.GsonConverterFactory
import promise.data.net.http.GET
import promise.data.net.net.Call
import promise.data.net.net.Callback
import promise.data.net.net.FastParserEngine
import promise.data.net.net.Response
import promise.model.ResponseCallBack


interface TodoApi {
  @GET("/todos")
  fun todos(): Call<List<Todo>>
}


fun getTodos(response: ResponseCallBack<promise.model.List<Todo>, AppError>) {
  val engine: FastParserEngine = FastParserEngine.Builder()
      .baseUrl("https://jsonplaceholder.typicode.com")
      .addConverterFactory(GsonConverterFactory.create())
      .build()
  val api = engine.create(TodoApi::class.java)
  api.todos().enqueue(object : Callback<List<Todo>> {
    override fun onResponse(call: Call<List<Todo>>?, res: Response<List<Todo>>?) =
        response.response(res?.body()?.let { promise.model.List(it) })

    override fun onFailure(call: Call<List<Todo>>?, t: Throwable?) =
        response.error(t?.let { AppError(it) })

  })
}

