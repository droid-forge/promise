package promise.app.ui.fragment.weather

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import promise.app.R

class WeatherFragment : Fragment() {

  companion object {
    fun newInstance() = WeatherFragment()
  }

  private lateinit var viewModel: WeatherViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.weather_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
    // TODO: Use the ViewModel
  }

}
