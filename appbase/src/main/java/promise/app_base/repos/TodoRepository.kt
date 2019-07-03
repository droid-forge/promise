package promise.app_base.repos

import promise.app_base.models.Todo
import promise.repo.AbstractAsyncIDataStore
import promise.repo.AbstractSyncIDataStore

class SyncTodoRepository: AbstractSyncIDataStore<Todo>()

class AsyncTodoRepository: AbstractAsyncIDataStore<Todo>()