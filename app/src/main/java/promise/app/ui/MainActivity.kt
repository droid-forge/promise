package promise.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.widget.LinearLayout

import promise.app.R
import promise.app.common.DataRepository
import promise.app.models.Todo
import promise.app.ui.views.LoadableView
import promise.model.List
import promise.model.ResponseCallBack
import promise.view.AdapterDivider
import promise.view.PromiseAdapter
import promise.view.SearchableAdapter
import promise.view.loading.ProgressLayout

class MainActivity : AppCompatActivity(), PromiseAdapter.Listener<Todo> {
  private lateinit var toolbar: Toolbar
  private lateinit var todosList: RecyclerView
  private lateinit var loadingView: ProgressLayout
  /*protected FloatingActionButton fab;*/
  private lateinit var searchableAdapter: SearchableAdapter<Todo>
  private lateinit var divider: AdapterDivider

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    super.setContentView(R.layout.activity_main)
    initView()
    setSupportActionBar(toolbar)
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    /*fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });*/

    divider = AdapterDivider(this, LinearLayout.VERTICAL)
    searchableAdapter = SearchableAdapter(this)
    todosList.layoutManager = LinearLayoutManager(this)
    todosList.itemAnimator = DefaultItemAnimator()
    todosList.addItemDecoration(divider!!)
    loadingView.showLoading(LoadableView("Loading todos, please wait..."))

    searchableAdapter!!.swipe(object : PromiseAdapter.Swipe<Todo> {
      override fun onSwipeRight(todo: Todo, response: PromiseAdapter.Response) {

      }

      override fun onSwipeLeft(todo: Todo, response: PromiseAdapter.Response) {

      }
    })
    todosList.adapter = searchableAdapter
    DataRepository.instance().getTodos(0, 10, ResponseCallBack<List<Todo>, Exception>()
        .response { todos -> searchableAdapter!!.add(todos) }.error { e -> runOnUiThread { loadingView.showEmpty(0, "Could not load todos", e.message) } })
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
