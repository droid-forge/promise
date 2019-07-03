package promise.app_base.repos

import javax.inject.Named

import dagger.Module
import dagger.Provides
import promise.app_base.models.Todo
import promise.app_base.scopes.AppScope
import promise.repo.StoreRepository

@Module
object ReposModule {

  const val TODO_REPOSITORY = "todo_repository"

  @Provides
  @AppScope
  @Named(TODO_REPOSITORY)
  fun provideStoreRepository(asyncTodoRepository: AsyncTodoRepository,
                             syncTodoRepository: SyncTodoRepository): StoreRepository<Todo> {
    return StoreRepository.create(syncTodoRepository, asyncTodoRepository)
  }
}
