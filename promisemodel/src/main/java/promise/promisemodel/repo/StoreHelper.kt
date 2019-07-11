package promise.promisemodel.repo

import promise.model.S

interface StoreHelper<T : S> {
  fun syncStore(): SyncIDataStore<T>
  fun asyncStore(): AsyncIDataStore<T>
}