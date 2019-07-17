package promise.app.ui

import android.app.Activity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.Module
import dagger.Provides
import promise.app.scopes.UIScope
import promise.model.Searchable
import promise.view.AdapterDivider
import promise.view.PromiseAdapter
import promise.view.SearchableAdapter

@Module
class ListAdapterModule(private val activity: Activity? = null,
                        private val recyclerView: RecyclerView? = null,
                        private val listener: PromiseAdapter.Listener<Searchable>? = null,
                        private var layoutManager: RecyclerView.LayoutManager? = null,
                        private val withDivider: Boolean = true,
                        private val swipe: PromiseAdapter.Swipe<Searchable>? = null) {
  @Provides
  @UIScope
  fun searchableAdapter(): SearchableAdapter<Searchable> {
    val adapter = SearchableAdapter(listener)
    if (layoutManager == null) layoutManager = LinearLayoutManager(activity)
    recyclerView?.layoutManager = layoutManager
    if (withDivider) recyclerView?.addItemDecoration(AdapterDivider(activity, LinearLayoutManager.VERTICAL))
    if (swipe != null) adapter.swipe(swipe)
    recyclerView?.adapter = adapter
    return adapter
  }
}