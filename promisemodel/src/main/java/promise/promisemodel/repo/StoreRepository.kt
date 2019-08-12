package promise.promisemodel.repo

import promise.model.List
import promise.model.S
import java.util.*

/**
 *
 *
 * @param T
 * @property store
 */
class StoreRepository<T : S>(private val store: StoreHelper<T>) {
  /**
   *
   *
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun all(args: MutableMap<String, Any?>?, res: ((List<T>) -> Unit)? = null, err: ((Exception) -> Unit)? = null): List<T>? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      if (res == null) throw IllegalArgumentException("response must be provided to together with err")
      store.asyncStore().all(res, err, args)
    } else return store.syncStore().all(args)
    return null
  }

  /**
   *
   *
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun one(args: MutableMap<String, Any?>?, res: ((T) -> Unit)? = null, err: ((Exception) -> Unit)? = null): T? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      if (res == null) throw IllegalArgumentException("response must be provided to together with err")
      store.asyncStore().one(res, err, args)
    } else return store.syncStore().one(args)
    return null
  }

  /**
   *
   *
   * @param t
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun save(t: T, args: MutableMap<String, Any?>?, res: ((T, Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Pair<T, Any?>? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      if (res == null) throw IllegalArgumentException("response must be provided to together with err")
      store.asyncStore().save(t, res, err, args)
    } else return store.syncStore().save(t, args)
    return null
  }

  /**
   *
   *
   * @param t
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun save(t: List<T>, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Any? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      if (res == null) throw IllegalArgumentException("response must be provided to together with err")
      store.asyncStore().save(t, res, err, args)
    } else return store.syncStore().save(t, args)
    return null
  }

  /**
   *
   *
   * @param t
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun update(t: T, args: MutableMap<String, Any?>?, res: ((T, Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Pair<T, Any?>? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      if (res == null) throw IllegalArgumentException("response must be provided to together with err")
      store.asyncStore().update(t, res, err, args)
    } else return store.syncStore().update(t, args)
    return null
  }

  /**
   *
   *
   * @param t
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun update(t: List<T>, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Any? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      if (res == null) throw IllegalArgumentException("response must be provided to together with err")
      store.asyncStore().update(t, res, err, args)
    } else return store.syncStore().update(t, args)
    return null
  }

  /**
   *
   *
   * @param t
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun delete(t: T, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Any? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      if (res == null) throw IllegalArgumentException("response must be provided to together with err")
      store.asyncStore().delete(t, res, err, args)
    } else return store.syncStore().delete(t, args)
    return null
  }

  /**
   *
   *
   * @param t
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun delete(t: List<T>, args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Any? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      if (res == null) throw IllegalArgumentException("response must be provided to together with err")
      store.asyncStore().delete(t, res, err, args)
    } else return store.syncStore().delete(t, args)
    return null
  }

  /**
   *
   *
   * @param args
   * @param res
   * @param err
   * @return
   */
  @JvmOverloads
  @Throws(Exception::class)
  fun clear(args: MutableMap<String, Any?>?, res: ((Any?) -> Unit)? = null, err: ((Exception) -> Unit)? = null): Any? {
    onSetupListener?.onPrepArgs(args)
    if (checkCallbacksNotNull(res, err)) {
      if (res == null) throw IllegalArgumentException("response must be provided to together with err")
      store.asyncStore().clear(res, err, args)
    } else return store.syncStore().clear(args)
    return null
  }

  /**
   *
   *
   * @param args
   * @return
   */
  private fun checkCallbacksNotNull(vararg args: Any?): Boolean {
    for (arg in args) if (arg != null) return true
    return false
  }

  companion object {
    /**
     *
     */
    private var onSetupListener: OnSetupListener? = null

    /**
     *
     *
     * @param setup
     */
    @JvmStatic
    @JvmOverloads
    fun setup(setup: OnSetupListener? = null) {
      onSetupListener = setup
      repos = WeakHashMap()
    }

    /**
     *
     */
    var repos: WeakHashMap<String, StoreRepository<*>>? = null

    /**
     *
     *
     * @param key
     * @return
     */
    fun instance(key: String): StoreRepository<*>? = try {
      if (repos == null) setup()
      repos!![key] as StoreRepository<*>
    } catch (e: Exception) {
      null
    }

    /**
     *
     *
     * @param T
     * @param syncStore
     * @param asyncStore
     * @return
     */
    inline fun <reified T : S> create(syncStore: SyncIDataStore<T>, asyncStore: AsyncIDataStore<T>): StoreRepository<T> {
      var repo = instance(T::class.java.simpleName)
      if (repo != null) return repo as StoreRepository<T>
      repo = StoreRepository(object : StoreHelper<T> {
        override fun syncStore(): SyncIDataStore<T> = syncStore
        override fun asyncStore(): AsyncIDataStore<T> = asyncStore
      })
      repos?.put(T::class.java.simpleName, repo)
      return repo
    }

    /**
     *
     *
     */
    fun clear() {
      repos?.clear()
    }
  }
}