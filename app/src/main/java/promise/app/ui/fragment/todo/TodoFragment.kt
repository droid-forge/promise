package promise.app.ui.fragment.todo

import android.app.Activity
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.todo_fragment.*
import promise.app.App
import promise.app.DaggerUIComponent
import promise.app.R
import promise.app.ui.DaggerListingComponent
import promise.app.ui.ListingDependencies
import promise.model.Searchable
import promise.view.PromiseAdapter
import promise.view.SearchableAdapter
import javax.inject.Inject

class TodoFragment : Fragment(), PromiseAdapter.Listener<Searchable> {

  @Inject lateinit var recyclerView: RecyclerView

  @Inject lateinit var searchableAdapter: SearchableAdapter<*>

  @Inject lateinit var todoViewModelFactory: TodoViewModelFactory

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

    val listingComponent = DaggerListingComponent.builder()
        .listingDependenciesModule(ListingDependencies(activity as Activity,
            this,
            todo_list))
        .dividerLine(true)
        .build()

    val app = activity!!.application as App

    val uiComponent = DaggerUIComponent.builder()
        .appComponent(app.appComponent)
        .build()

    listingComponent.inject(this)

    uiComponent.inject(this)

    viewModel = ViewModelProviders.of(this, todoViewModelFactory)
        .get(TodoViewModel::class.java)
  }

  override fun onClick(t: Searchable?, id: Int) {

  }

}
