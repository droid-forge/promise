package promise.app.ui.activity.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import promise.Promise
import promise.app.scopes.UIScope
import promise.app_base.repos.AuthRepository
import javax.inject.Inject

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
@UIScope
class LoginViewModelFactory @Inject constructor() : ViewModelProvider.Factory {

  @Inject lateinit var promise: Promise

  @Inject lateinit var authRepository: AuthRepository

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(LoginViewModel::class.java)) return LoginViewModel(
        authRepository = authRepository,
        promise = promise
    ) as T
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
