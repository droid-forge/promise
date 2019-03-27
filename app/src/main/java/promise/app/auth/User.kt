package promise.app.auth

import promise.model.List

class User {
  private var email: String? = null
  private var password: String? = null
  private var names: String? = null
  private var roles: List<Role>? = null

  fun email(): String? {
    return email
  }

  fun email(email: String): User {
    this.email = email
    return this
  }

  fun password(): String? {
    return password
  }

  fun password(password: String): User {
    this.password = password
    return this
  }

  fun names(): String? {
    return names
  }

  fun names(names: String): User {
    this.names = names
    return this
  }

  fun roles(): List<Role>? {
    return roles
  }

  fun roles(roles: List<Role>): User {
    this.roles = roles
    return this
  }
}
