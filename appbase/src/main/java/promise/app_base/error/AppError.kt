package promise.app_base.error

class AppError(cause: String) : Exception(cause) {
  constructor(cause: Throwable) : this(cause.message!!)
}
