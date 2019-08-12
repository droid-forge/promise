package promisemodel.repo

import promise.model.List
import promise.model.S

/**
 *
 *
 * @param T
 */
interface AsyncIDataStore<T : S> {
  /**
   *
   *
   * @param res
   * @param err
   * @param args
   */
  fun all(res: (List<T>) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param res
   * @param err
   * @param args
   */
  fun one(res: (T) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  fun save(t: T, res: (T, Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  fun save(t: List<T>, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  fun update(t: T, res: (T, Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  fun update(t: List<T>, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  fun delete(t: T, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  fun delete(t: List<T>, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

  /**
   *
   *
   * @param res
   * @param err
   * @param args
   */
  fun clear(res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)
}

/**
 *
 *
 * @param T
 */
open class AbstractAsyncIDataStore<T : S> : AsyncIDataStore<T> {
  /**
   *
   *
   * @param res
   * @param err
   * @param args
   */
  override fun all(res: (List<T>) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) =
      res(List())

  /**
   *
   *
   * @param res
   * @param err
   * @param args
   */
  override fun one(res: (T) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) {
    err?.invoke(Exception())
  }

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  override fun save(t: T, res: (T, Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) =
      res(t, Any())

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  override fun save(t: List<T>, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) {
    res(Any())
  }

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  override fun update(t: T, res: (T, Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) =
      res(t, Any())

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  override fun update(t: List<T>, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) {
    res(Any())
  }

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  override fun delete(t: T, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) =
      res(Any())

  /**
   *
   *
   * @param t
   * @param res
   * @param err
   * @param args
   */
  override fun delete(t: List<T>, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) {
    res(Any())
  }

  /**
   *
   *
   * @param res
   * @param err
   * @param args
   */
  override fun clear(res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>?) =
      res(Any())
}