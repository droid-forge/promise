package promise.data.utils

import promise.model.List
import java.util.concurrent.Callable

class SynchronousChainProcessor: Processor<Boolean> {

  private var callables: List<Callable<Boolean>> = List()

  fun registerCallables(vararg callables: Callable<Boolean>) {
    this.callables.addAll(callables)
  }

  override fun process(): Boolean {
    var executed = true
    callables.forEach {
      executed = executed && it.call()
    }
    return executed
  }
}