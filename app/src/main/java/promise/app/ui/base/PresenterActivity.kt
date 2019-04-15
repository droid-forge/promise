package promise.app.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

interface BaseView

abstract class BasePresenter<View : BaseView> constructor(view: View) {

  var view: View? = null
    protected set

  init {
    this.view = view
  }

  fun destroyView() {
    view = null
  }
}

abstract class PresenterActivity<Presenter: BasePresenter<*>>: AppCompatActivity() {

   val presenter: Presenter = presenter()


  abstract fun presenter(): Presenter

  override fun onDestroy() {
    presenter.destroyView()
    super.onDestroy()
  }
}