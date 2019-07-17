package promise.app.ui.fragment.todo

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.todo_fragment.*
import promise.app.App
import promise.app.DaggerUIComponent
import promise.app.R
import promise.app.ui.ListAdapterModule
import promise.app_base.models.Result
import promise.model.Searchable
import promise.view.PromiseAdapter
import promise.view.SearchableAdapter
import javax.inject.Inject

class TodoFragment : Fragment(), PromiseAdapter.Listener<Searchable> {

  @Inject
  lateinit var todoViewModelFactory: TodoViewModelFactory

  @Inject
  lateinit var searchableAdapter: SearchableAdapter<Searchable>

  companion object {
    fun newInstance() = TodoFragment()
  }

  private lateinit var viewModel: TodoViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.todo_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val app = activity!!.application as App

    val uiComponent = DaggerUIComponent.builder()
        .appComponent(app.appComponent)
        .listAdapterModule(ListAdapterModule(activity as Activity, todo_list, this))
        .build()

    uiComponent.inject(this)

    viewModel = ViewModelProviders.of(this, todoViewModelFactory)
        .get(TodoViewModel::class.java)

    viewModel.data.observe(this, Observer {
      if (it is Result.Success) {
        progress_layout.showContent()
        searchableAdapter.clear()
        searchableAdapter.add(it.data)
      } else if (it is Result.Error) {
        progress_layout.showEmpty(R.drawable.navigation_empty_icon, "You do not have any todos now", it.exception.message)
      }
    })
    progress_layout.showLoading()
    viewModel.fetchTodos(0, 10)
  }

  override fun onClick(t: Searchable, id: Int) {

  }
}
