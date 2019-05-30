package promise.app.ui.fragment.todo

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import promise.app.R

class TodoFragment : Fragment() {

  companion object {
    fun newInstance() = TodoFragment()
  }

  private lateinit var viewModel: TodoViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {
    return inflater.inflate(R.layout.todo_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(TodoViewModel::class.java)
  }

}
