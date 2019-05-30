package promise.app.ui.activity

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import promise.app.R
import promise.app.models.Todo
import promise.cac.notif.Notification
import promise.view.AdapterDivider
import promise.view.PromiseAdapter
import promise.view.SearchableAdapter

class MainActivity : AppCompatActivity(), PromiseAdapter.Listener<Todo> {

  private val searchableAdapter: SearchableAdapter<Todo> by lazy { SearchableAdapter<Todo>(this) }
  private val divider: AdapterDivider by lazy { AdapterDivider(this, LinearLayout.VERTICAL) }
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
    /*Handler().postDelayed( {
      notification.showDialog("Battery", " ${ApiHelper.batteryPercentage(this)}", null, null, null)
    }, 1000)*/
  }

  override fun onClick(todo: Todo, id: Int) {
    notification.showDialog("Todo clicked", todo.toString(),
        null, null, null)
  }

}
