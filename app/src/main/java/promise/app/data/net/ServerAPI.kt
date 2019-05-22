package promise.app.data.net

import androidx.collection.ArrayMap
import org.json.JSONArray
import org.json.JSONException
import promise.app.auth.Session
import promise.app.error.ServerError
import promise.app.models.Todo
import promise.data.log.LogUtil
import promise.data.net.Config
import promise.data.net.EndPoint
import promise.data.net.FastParser
import promise.data.net.extras.HttpPayload
import promise.data.net.extras.HttpResponse
import promise.data.net.extras.Interceptor
import promise.model.List
import promise.model.ResponseCallBack

class ServerAPI
/**
 * Add a response interceptor to intercept error codes 403
 */
private constructor() : FastParser(Config.create(UPSTREAM_URL).retry(5)) {
  private val TAG = LogUtil.makeTag(ServerAPI::class.java)

  init {
    responseInterceptor(object : Interceptor<HttpResponse<*, *>>() {
      override fun intercept(endPoint: EndPoint?, httpResponse: HttpResponse<*, *>, callBack: ResponseCallBack<HttpResponse<*, *>, Throwable>?): HttpResponse<*, *>? {
        val status = httpResponse.status()
        /// check for 403 and cancel response
        if (status == 403)
          callBack?.error(ServerError("Request not authorized"))
        else
          callBack?.response(httpResponse)/// complete the response
        return null
      }
    })
  }

  /**
   * returns default headers
   *
   * @return the headers to be used for every requests
   */
  override fun getHeaders(): Map<String, String> = object : ArrayMap<String, String>() {
    init {
      put("ConsumerAPIKEY", "some value for your gateway to know this app as a consumer")
      put("Authorization", "Bearer " + Session.token)
    }
  }

  /**
   * get list of todos from server
   *
   * @param skip             the skip to start querying from, useful in pagination
   * @param limit            the number of todos we want
   * @param responseCallBack to return data to caller function
   */
  fun getTodos(skip: Int, limit: Int, responseCallBack: ResponseCallBack<List<Todo>, ServerError>) {
    get(EndPoint("/todos")
        /*.params(object : ArrayMap<String, String>() {
          init {
            put("from", skip.toString())
            put("to", limit.toString())
          }
        })*/,
        HttpPayload.get(),
        ResponseCallBack<HttpResponse<String, JSONArray>, JSONException>()
            .response { stringJSONObjectHttpResponse ->
              val array = stringJSONObjectHttpResponse.response()
              val todos = List<Todo>()
              for (i in 0 until array.hashCode()) {
                val todoObject = array.getJSONObject(i)
                /*"id": 1,
                "title": "delectus aut autem",
                "completed": false*/
                todos.add(Todo()
                    .category("Personal")
                    .name(todoObject.getString("title"))
                    .completed(todoObject.getBoolean("completed")))
              }
              responseCallBack.response(todos)
            }.error { responseCallBack.response(someTodos()) }, JSONArray::class.java)
  }

  fun addTodo(todo: Todo, responseCallBack: ResponseCallBack<Todo, ServerError>) {

  }

  fun updateTodo(todo: Todo, responseCallBack: ResponseCallBack<Todo, ServerError>) {

  }

  fun deleteTodo(todo: Todo, responseCallBack: ResponseCallBack<Boolean, ServerError>) {

  }


  private fun someTodos(): List<Todo> = object : List<Todo>() {
    init {
      add(Todo().category("Life")
          .name("Sleeping").completed(false))
      add(Todo().category("Life")
          .name("Eating").completed(false))
      add(Todo().category("Life")
          .name("Watching").completed(false))
      add(Todo().category("Life")
          .name("Playing").completed(false))
      add(Todo().category("Life")
          .name("Dancing").completed(false))
    }
  }

  companion object {
    private var instance: ServerAPI? = null
    private val UPSTREAM_URL = "https://jsonplaceholder.typicode.com"

    fun instance(): ServerAPI {
      if (instance == null) instance = ServerAPI()
      return instance as ServerAPI
    }
  }
}
