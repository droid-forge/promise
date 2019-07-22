package promise.app.ui.views

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import promise.app.R

import promise.model.Viewable

class LoadableView(private val status: String) : Viewable {

  private lateinit var textView: AppCompatTextView

  override fun layout(): Int = R.layout.loadable_view_layout

  override fun init(view: View) {
    textView = view.findViewById(R.id.loading_text_textView)
  }

  override fun bind(view: View) {
    textView.text = status
  }

  override fun index(index: Int) {

  }

  override fun index(): Int = 0
}
