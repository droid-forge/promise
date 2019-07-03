package promise.app.ui.activity.login

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_login.*
import promise.app.DaggerUIComponent
import promise.app.R
import promise.app.ui.activity.BaseActivity
import javax.inject.Inject

class LoginActivity : BaseActivity() {

  private lateinit var loginViewModel: LoginViewModel

  @Inject
  lateinit var loginViewModelFactory: LoginViewModelFactory

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_login)

    val uiComponent = DaggerUIComponent.builder()
        .appComponent(app.appComponent)
        .build()

    uiComponent.inject(this)

    loginViewModel = ViewModelProviders.of(this, loginViewModelFactory)
        .get(LoginViewModel::class.java)

    loginViewModel.loginFormState.observe(this, Observer {
      val loginState = it ?: return@Observer
      // disable login button unless both username / password is valid
      sign_in_btn.isEnabled = loginState.isDataValid
      if (loginState.usernameError != null) email_input_editText.error = getString(loginState.usernameError)
      if (loginState.passwordError != null) password_input_editText.error = getString(loginState.passwordError)
    })

    loginViewModel.loginResult.observe(this, Observer {
      val loginResult = it ?: return@Observer
      loading.visibility = View.GONE
      if (loginResult.error != null) showLoginFailed(loginResult.error)
      if (loginResult.success != null) updateUiWithUser(loginResult.success)
      setResult(Activity.RESULT_OK)
      finish()
    })

    email_input_editText.afterTextChanged {
      loginViewModel.loginDataChanged(
          it,
          password_input_editText.text.toString()
      )
    }

    password_input_editText.apply {
      afterTextChanged {
        loginViewModel.loginDataChanged(
            email_input_editText.text.toString(),
            it
        )
      }

      setOnEditorActionListener { _, actionId, _ ->
        when (actionId) {
          EditorInfo.IME_ACTION_DONE ->
            loginViewModel.login(
                email_input_editText.text.toString(),
                password_input_editText.text.toString()
            )
        }
        false
      }

      sign_in_btn.setOnClickListener {
        loading.visibility = View.VISIBLE
        loginViewModel.login(email_input_editText.text.toString(),
            password_input_editText.text.toString())
      }
    }
  }

  private fun updateUiWithUser(model: LoggedInUserView) {
    val welcome = getString(R.string.welcome)
    val displayName = model.displayName
    // TODO : initiate successful logged in experience
    Toast.makeText(
        applicationContext,
        "$welcome $displayName",
        Toast.LENGTH_LONG
    ).show()
  }

  private fun showLoginFailed(@StringRes errorString: Int) {
    Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
  }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
  this.addTextChangedListener(object : TextWatcher {
    override fun afterTextChanged(editable: Editable?) {
      afterTextChanged(editable.toString())
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
  })
}
