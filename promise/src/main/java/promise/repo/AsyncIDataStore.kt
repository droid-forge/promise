package promise.repo

import promise.model.List
import promise.model.S

interface AsyncIDataStore<T : S> {

  fun all(res: (List<T>) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)
  fun one(res: (T) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)
  fun save(t: T, res: (T, Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)
  fun update(t: T, res: (T, Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)
  fun delete(t: T, res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)
  fun clear(res: (Any?) -> Unit, err: ((Exception) -> Unit)?, args: Map<String, Any?>? = null)

}