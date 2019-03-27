package promise.app.auth

class Role {

  private var name: String? = null
  private var allowed: Boolean = false

  fun name(): String? {
    return name
  }

  fun name(name: String): Role {
    this.name = name
    return this
  }

  fun allowed(): Boolean {
    return allowed
  }

  fun allowed(allowed: Boolean): Role {
    this.allowed = allowed
    return this
  }

  companion object {
    val CAN_ADD_TODO = "can_add_todo"
    val CAN_UPDATE_TODO = "can_update_todo"
  }
}
