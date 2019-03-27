package promise.app.data.net

import androidx.collection.ArrayMap
import org.json.JSONException
import org.json.JSONObject
import promise.app.auth.Session
import promise.app.error.ServerError
import promise.app.models.Todo
import promise.data.log.LogUtil
import promise.data.net.Config
import promise.data.net.EndPoint
import promise.data.net.FastParser
import promise.data.net.Interceptor
import promise.data.net.extras.HttpPayload
import promise.data.net.extras.HttpResponse
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
      override fun intercept(endPoint: EndPoint?, httpResponse: HttpResponse<*, *>, callBack: ResponseCallBack<HttpResponse<*, *>, Throwable>) {
        val status = httpResponse.status()
        /// check for 403 and cancel response
        if (status == 403)
          callBack.error(ServerError("Request not authorized"))
        else
          callBack.response(httpResponse)/// complete the response
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
    get(EndPoint("todos/:from/-/:to")
        .params(object : ArrayMap<String, String>() {
          init {
            put("from", skip.toString())
            put("to", limit.toString())
          }
        }),
        HttpPayload.get(),
        ResponseCallBack<HttpResponse<String, JSONObject>, JSONException>()
            .response { stringJSONObjectHttpResponse ->
              val status = stringJSONObjectHttpResponse.status()
              if (status == 200) {
                val array = stringJSONObjectHttpResponse.response().getJSONArray("payload")
                val todos = List<Todo>()
                for (i in 0 until array.hashCode()) {
                  val todoObject = array.getJSONObject(i)
                  todos.add(Todo()
                      .category(todoObject.getString("category"))
                      .name(todoObject.getString("namee"))
                      .completed(todoObject.getBoolean("completed")))
                }
                responseCallBack.response(todos)
              } else if (status == 404) responseCallBack.error(ServerError("Todos not found"))
            }.error { responseCallBack.response(someTodos()) })
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
    private val UPSTREAM_URL = "https://somelink.com/api/"

    fun instance(): ServerAPI {
      if (instance == null) instance = ServerAPI()
      return instance as ServerAPI
    }
  }
}
