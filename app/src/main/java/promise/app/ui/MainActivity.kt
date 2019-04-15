package promise.app.ui

import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import promise.util.ApiHelper
import promise.view.AdapterDivider
import promise.view.PromiseAdapter
import promise.view.SearchableAdapter
import promise.view.loading.ProgressLayout

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
    searchableAdapter.add(todos)
  }

  override fun onError(error: AppError) {
    runOnUiThread {
      loadingView.showEmpty(0, "Could not load todos", error.message)
    }
  }

  private lateinit var toolbar: Toolbar
  private lateinit var todosList: RecyclerView
  private lateinit var loadingView: ProgressLayout
  /*protected FloatingActionButton fab;*/
  private val searchableAdapter: SearchableAdapter<Todo> by lazy { SearchableAdapter<Todo>(this) }
  private lateinit var divider: AdapterDivider
  private val notification: Notification by lazy { Notification(this) }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    super.setContentView(R.layout.activity_main)
    initView()
    setSupportActionBar(toolbar)
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    divider = AdapterDivider(this, LinearLayout.VERTICAL)
    todosList.layoutManager = LinearLayoutManager(this)
    todosList.itemAnimator = DefaultItemAnimator()
    todosList.addItemDecoration(divider!!)
    loadingView.showLoading(/*LoadableView("Loading todos, please wait...")*/)

    searchableAdapter.swipe(object : PromiseAdapter.Swipe<Todo> {
      override fun onSwipeRight(todo: Todo, response: PromiseAdapter.Response) {

      }

      override fun onSwipeLeft(todo: Todo, response: PromiseAdapter.Response) {

      }
    })
    todosList.adapter = searchableAdapter
    presenter.getTodos(0, 10)
    /*Handler().postDelayed( {
      notification.showDialog("Battery", " ${ApiHelper.batteryPercentage(this)}", null, null, null)
    }, 1000)*/
  }

  override fun onClick(todo: Todo, id: Int) {

  }

  private fun initView() {
    toolbar = findViewById(R.id.toolbar)
    todosList = findViewById(R.id.todos_list)
    loadingView = findViewById(R.id.loading_view)
    /*fab = findViewById(R.id.fab);*/
  }
}
