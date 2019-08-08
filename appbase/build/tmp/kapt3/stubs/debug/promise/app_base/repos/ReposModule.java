package promise.app_base.repos;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lpromise/app_base/repos/ReposModule;", "", "()V", "TODO_REPOSITORY", "", "provideStoreRepository", "Lpromise/repo/StoreRepository;", "Lpromise/app_base/models/Todo;", "asyncTodoRepository", "Lpromise/app_base/repos/AsyncTodoRepository;", "syncTodoRepository", "Lpromise/app_base/repos/SyncTodoRepository;", "appbase_debug"})
@dagger.Module()
public final class ReposModule {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String TODO_REPOSITORY = "todo_repository";
    public static final promise.app_base.repos.ReposModule INSTANCE = null;
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Named(value = "todo_repository")
    @promise.app_base.scopes.AppScope()
    @dagger.Provides()
    public static final promise.repo.StoreRepository<promise.app_base.models.Todo> provideStoreRepository(@org.jetbrains.annotations.NotNull()
    promise.app_base.repos.AsyncTodoRepository asyncTodoRepository, @org.jetbrains.annotations.NotNull()
    promise.app_base.repos.SyncTodoRepository syncTodoRepository) {
        return null;
    }
    
    private ReposModule() {
        super();
    }
}