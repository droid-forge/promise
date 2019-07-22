package promise.app.ui.activity.login

import android.util.Patterns
import androidx.collection.ArrayMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import promise.Promise
import promise.app.R
import promise.app_base.repos.AuthRepository

class LoginViewModel(private val authRepository: AuthRepository, private val promise: Promise) : ViewModel() {

  private val _loginForm = MutableLiveData<LoginFormState>()
  val loginFormState: LiveData<LoginFormState> = _loginForm

  private val _loginResult = MutableLiveData<LoginResult>()
  val loginResult: LiveData<LoginResult> = _loginResult

  fun login(username: String, password: String) = promise.execute {
    val user = authRepository.one(ArrayMap<String, String>().apply {
      put("email", username)
      put("password", password)
    })
    promise.executeOnUi {
      if (user == null) _loginResult.value = LoginResult(error = R.string.login_failed)
      else _loginResult.value = LoginResult(success = LoggedInUserView(displayName = user.names()!!))
    }
  }

  fun loginDataChanged(username: String, password: String) =
      if (!isUserNameValid(username)) _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
      else if (!isPasswordValid(password)) _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
      else _loginForm.value = LoginFormState(isDataValid = true)

  private fun isUserNameValid(username: String): Boolean =
      if (username.contains('@')) Patterns.EMAIL_ADDRESS.matcher(username).matches()
      else username.isNotBlank()

  private fun isPasswordValid(password: String): Boolean = password.length > 5
}
