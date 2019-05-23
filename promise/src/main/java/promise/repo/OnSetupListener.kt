package promise.repo

interface OnSetupListener {
  fun onPrepArgs(args: MutableMap<String, Any?>?)
}