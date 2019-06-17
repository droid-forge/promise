package promise.app.ui.fragment.di

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import promise.app.App
import promise.app.DaggerAppComponent
import promise.app.R
import promise.app.models.di.Car
import promise.app.models.di.DieselEngineModule
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DIFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DIFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DIFragment : Fragment() {

  @Inject
  lateinit var car: Car
  @Inject
  lateinit var car2: Car

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
    }
    val fragmentComponent = (activity?.application as App)
        .appComponent.getFragmentComponent(DieselEngineModule(100))
    fragmentComponent.inject(this)
    car.drive()
    car2.drive()
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_di, container, false)
  }

  // TODO: Rename method, update argument and hook method into UI event
  fun onButtonPressed(uri: Uri) {
  }
/*
  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is OnFragmentInteractionListener) {
      listener = context
    } else {
      throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
    }
  }

  override fun onDetach() {
    super.onDetach()
    listener = null
  }*/

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   *
   *
   * See the Android Training lesson [Communicating with Other Fragments]
   * (http://developer.android.com/training/basics/fragments/communicating.html)
   * for more information.
   */


  companion object {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DIFragment.
     */
    // TODO: Rename and change types and number of parameters

    fun newInstance() =
        DIFragment().apply {
          arguments = Bundle().apply {

          }
        }
  }
}
