package promise.app_base.repos

import promise.app_base.auth.User
import promise.app_base.data.net.AuthApi
import promise.app_base.scopes.AppScope
import promise.repo.AbstractSyncIDataStore
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */


@AppScope
class AuthRepository @Inject constructor() : AbstractSyncIDataStore<User>() {

  @Inject
  lateinit var authApi: AuthApi

  override fun one(args: Map<String, Any?>?): User? {
    if (args == null) throw IllegalArgumentException("arguments cant be null on one call")
    val email = args["email"] as String
    val password = args["password"] as String
    return authApi.login(email = email, password = password).execute().body()
  }

}
