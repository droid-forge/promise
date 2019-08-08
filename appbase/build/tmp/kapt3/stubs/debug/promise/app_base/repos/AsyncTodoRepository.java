package promise.app_base.repos;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0000\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0007JT\u0010\b\u001a\u00020\t2\u0018\u0010\n\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\f\u0012\u0004\u0012\u00020\t0\u000b2\u0018\u0010\r\u001a\u0014\u0012\b\u0012\u00060\u000ej\u0002`\u000f\u0012\u0004\u0012\u00020\t\u0018\u00010\u000b2\u0016\u0010\u0010\u001a\u0012\u0012\u0004\u0012\u00020\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0013\u0018\u00010\u0011H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lpromise/app_base/repos/AsyncTodoRepository;", "Lpromise/repo/AbstractAsyncIDataStore;", "Lpromise/app_base/models/Todo;", "asyncAppDatabase", "Lpromise/app_base/data/db/async/AsyncAppDatabase;", "todoApi", "Lpromise/app_base/data/net/TodoApi;", "(Lpromise/app_base/data/db/async/AsyncAppDatabase;Lpromise/app_base/data/net/TodoApi;)V", "all", "", "res", "Lkotlin/Function1;", "Lpromise/model/List;", "err", "Ljava/lang/Exception;", "Lkotlin/Exception;", "args", "", "", "", "appbase_debug"})
@promise.app_base.scopes.AppScope()
public final class AsyncTodoRepository extends promise.repo.AbstractAsyncIDataStore<promise.app_base.models.Todo> {
    private final promise.app_base.data.db.async.AsyncAppDatabase asyncAppDatabase = null;
    private final promise.app_base.data.net.TodoApi todoApi = null;
    
    @java.lang.Override()
    public void all(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super promise.model.List<promise.app_base.models.Todo>, kotlin.Unit> res, @org.jetbrains.annotations.Nullable()
    kotlin.jvm.functions.Function1<? super java.lang.Exception, kotlin.Unit> err, @org.jetbrains.annotations.Nullable()
    java.util.Map<java.lang.String, ? extends java.lang.Object> args) {
    }
    
    @javax.inject.Inject()
    public AsyncTodoRepository(@org.jetbrains.annotations.NotNull()
    promise.app_base.data.db.async.AsyncAppDatabase asyncAppDatabase, @org.jetbrains.annotations.NotNull()
    promise.app_base.data.net.TodoApi todoApi) {
        super();
    }
}