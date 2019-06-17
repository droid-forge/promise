package promise.app.repos

import promise.app.auth.User
import promise.app.models.Result
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

  fun login(username: String, password: String): Result<User> {
    return try {
      // TODO: handle loggedInUser authentication
      val fakeUser = User()
      Result.Success(fakeUser)
    } catch (e: Throwable) {
      Result.Error(IOException("Error logging in", e))
    }
  }

  fun logout() {
    // TODO: revoke authentication
  }
}

