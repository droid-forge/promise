package promise.repo

import promise.model.List
import promise.model.S

interface SyncIDataStore<T : S> {

  @Throws(Exception::class)
  fun all(args: Map<String, Any?>? = null): List<T>?

  @Throws(Exception::class)
  fun one(args: Map<String, Any?>? = null): T?

  @Throws(Exception::class)
  fun save(t: T, args: Map<String, Any?>? = null): Pair<T, Any?>

  @Throws(Exception::class)
  fun update(t: T, args: Map<String, Any?>? = null): Pair<T, Any?>

  @Throws(Exception::class)
  fun delete(t: T, args: Map<String, Any?>? = null): Any?

  @Throws(Exception::class)
  fun clear(args: Map<String, Any?>? = null): Any?

}