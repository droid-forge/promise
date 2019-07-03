package promise.app.ui

import android.app.Activity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.Module
import dagger.Provides
import promise.app.scopes.UIScope
import promise.model.Searchable
import promise.view.AdapterDivider
import promise.view.PromiseAdapter
import promise.view.SearchableAdapter
import javax.inject.Named

/**
 * builds listing adapter
 *
 */
@Module
class ListingDependencies constructor(val activity: Activity,
                                      val listener: PromiseAdapter.Listener<Searchable>,
                                      val recyclerView: RecyclerView
) {
  companion object {
    const val DIVIDER_PARAM = "divider_param"
  }

  /**
   * builds an adapter with {@linkplain }
   *
   * @param listener for callback click item events
   * @return
   */
  @Provides
  @UIScope
  fun adapter(): SearchableAdapter<*> {
    return SearchableAdapter(listener)
  }

  /**
   * prepares a recycler view for displaying items
   *
   * @param activity
   * @param recyclerView
   * @param adapter
   * @param divider
   * @return prepared recyclerview
   */
  @Provides
  @UIScope
  fun recyclerView(adapter: SearchableAdapter<*>,
                   @Named(DIVIDER_PARAM) divider: Boolean): RecyclerView {
    recyclerView.layoutManager = LinearLayoutManager(activity)
    recyclerView.itemAnimator = DefaultItemAnimator()
    if (divider) recyclerView.addItemDecoration(AdapterDivider(activity, LinearLayoutManager.VERTICAL))
    recyclerView.adapter = adapter
    return recyclerView
  }
}