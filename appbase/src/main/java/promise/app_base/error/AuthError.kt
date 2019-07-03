package promise.app_base.error

class AuthError : Exception() {
  private var code: Int = 0

  fun code(): Int = code

  fun code(code: Int): AuthError {
    this.code = code
    return this
  }
}
