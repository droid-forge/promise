package promise.app.ui

import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import promise.app.R
import promise.app.common.DataRepository
import promise.app.error.AppError
import promise.app.models.Todo
import promise.app.ui.base.BasePresenter
import promise.app.ui.base.BaseView
import promise.app.ui.base.PresenterActivity
import promise.cac.notif.Notification
import promise.model.List
import promise.model.ResponseCallBack
import promise.view.AdapterDivider
import promise.view.PromiseAdapter
import promise.view.SearchableAdapter

interface MainView : BaseView {
  fun onGetTodos(todos: List<Todo>)
  fun onError(error: AppError)
}

class MainPresenter(view: MainView) : BasePresenter<MainView>(view) {
  private val dataRepository: DataRepository by lazy { DataRepository.instance() }
  fun getTodos(skip: Int, take: Int) {
    dataRepository.getTodos(skip, take, ResponseCallBack<List<Todo>, Exception>()
        .response { view?.onGetTodos(it) }
        .error { view?.onError(AppError(it)) })
  }
}

class MainActivity : PresenterActivity<MainPresenter>(), PromiseAdapter.Listener<Todo>, MainView {

  override fun presenter(): MainPresenter = MainPresenter(this)

  override fun onGetTodos(todos: List<Todo>) {
    runOnUiThread {
      loading_view.showContent()
      notification.showDialog("Loaded todos", "they are: ${todos.size}", null, null, null)
    }
    searchableAdapter.add(todos)
  }

  override fun onError(error: AppError) {
    runOnUiThread {
      loading_view.showEmpty(0, "Could not load todos", error.message)
    }
  }

  private val searchableAdapter: SearchableAdapter<Todo> by lazy { SearchableAdapter<Todo>(this) }
  private  val divider: AdapterDivider by lazy { AdapterDivider(this, LinearLayout.VERTICAL) }
  private val notification: Notification by lazy { Notification(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    super.setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    todos_list.layoutManager = LinearLayoutManager(this)
    todos_list.itemAnimator = DefaultItemAnimator()
    todos_list.addItemDecoration(divider)
    loading_view.showLoading(/*LoadableView("Loading todos, please wait...")*/)

    searchableAdapter.swipe(object : PromiseAdapter.Swipe<Todo> {
      override fun onSwipeRight(todo: Todo, response: PromiseAdapter.Response) {

      }

      override fun onSwipeLeft(todo: Todo, response: PromiseAdapter.Response) {

      }
    })
    todos_list.adapter = searchableAdapter
    presenter.getTodos(0, 10)
    /*Handler().postDelayed( {
      notification.showDialog("Battery", " ${ApiHelper.batteryPercentage(this)}", null, null, null)
    }, 1000)*/
  }

  override fun onClick(todo: Todo, id: Int) {
      notification.showDialog("Todo clicked", todo.toString(),
          null, null, null)
  }

}
