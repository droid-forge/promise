package promise.app.ui.fragment.todo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import promise.app_base.models.Todo
import promise.repo.StoreRepository

class TodoViewModel(private val todoRepository: StoreRepository<Todo>) : ViewModel() {

   val data = MutableLiveData<List<Todo>>()
/*
  private val _loginResult = MutableLiveData<LoginResult>()
  val loginResult: LiveData<LoginResult> = _loginResult

  fun login(username: String, password: String) = promise.execute {
    val user = authRepository.one(ArrayMap<String, String>().apply {
      put("email", username)
      put("password", password)
    })
    if (user == null) _loginResult.value = LoginResult(error = R.string.login_failed)
    else _loginResult.value = LoginResult(success = LoggedInUserView(displayName = user.names()!!))
  }

  fun loginDataChanged(username: String, password: String) =
      if (!isUserNameValid(username)) _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
      else if (!isPasswordValid(password)) _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
      else _loginForm.value = LoginFormState(isDataValid = true)

  private fun isUserNameValid(username: String): Boolean =
      if (username.contains('@')) Patterns.EMAIL_ADDRESS.matcher(username).matches()
      else username.isNotBlank()

  private fun isPasswordValid(password: String): Boolean = password.length > 5*/

}
