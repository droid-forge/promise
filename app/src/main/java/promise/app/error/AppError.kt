package promise.app.error

class AppError : Exception {
  constructor(message: String) : super(message)

  constructor(cause: Throwable) : super(cause)
}
