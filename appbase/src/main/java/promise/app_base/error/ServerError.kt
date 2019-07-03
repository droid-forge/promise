package promise.app_base.error

class ServerError(message: String) : Exception(message) {
  private var code: Int = 0

  fun code(): Int = code

  fun code(code: Int): ServerError {
    this.code = code
    return this
  }
}
