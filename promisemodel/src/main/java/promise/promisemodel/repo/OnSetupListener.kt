package promise.promisemodel.repo

interface OnSetupListener {
  fun onPrepArgs(args: MutableMap<String, Any?>?)
}