package promise.promisemodel.repo

import promise.model.S

/**
 *
 *
 * @param T
 */
interface StoreHelper<T : S> {
  /**
   *
   *
   * @return
   */
  fun syncStore(): SyncIDataStore<T>

  /**
   *
   *
   * @return
   */
  fun asyncStore(): AsyncIDataStore<T>
}