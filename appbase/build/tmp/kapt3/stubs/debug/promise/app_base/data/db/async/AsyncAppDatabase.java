package promise.app_base.data.db.async;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0007\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\n\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0016J(\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t2\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\fJ \u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0016J\u0018\u0010\u0015\u001a\u0012\u0012\u000e\u0012\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u00020\u00110\u00160\tH\u0016J0\u0010\b\u001a\u00020\u00072\u0006\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00132\u0018\u0010\u000b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0004\u0012\u00020\u000e0\fJ(\u0010\b\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u001a2\u0018\u0010\u000b\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t\u0012\u0004\u0012\u00020\u000e0\fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lpromise/app_base/data/db/async/AsyncAppDatabase;", "Lpromise/data/db/ReactiveFastDB;", "()V", "disposable", "Lio/reactivex/disposables/CompositeDisposable;", "onTerminate", "saveTodos", "", "todos", "Lpromise/model/List;", "Lpromise/app_base/models/Todo;", "responseCallBack", "Lpromise/model/ResponseCallBack;", "", "Lpromise/app_base/error/AppError;", "shouldUpgrade", "database", "Landroid/database/sqlite/SQLiteDatabase;", "oldVersion", "", "newVersion", "tables", "Lpromise/data/db/Table;", "skip", "limit", "category", "", "Companion", "appbase_debug"})
@promise.app_base.scopes.AppScope()
public final class AsyncAppDatabase extends promise.data.db.ReactiveFastDB {
    private final io.reactivex.disposables.CompositeDisposable disposable = null;
    private static final java.lang.String DB_NAME = "a";
    private static final int DB_VERSION = 1;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SENDER_TAG = "App_Database";
    private static final kotlin.Lazy todoTable$delegate = null;
    public static final promise.app_base.data.db.async.AsyncAppDatabase.Companion Companion = null;
    
    @java.lang.Override()
    public boolean shouldUpgrade(@org.jetbrains.annotations.NotNull()
    android.database.sqlite.SQLiteDatabase database, int oldVersion, int newVersion) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public promise.model.List<promise.data.db.Table<?, android.database.sqlite.SQLiteDatabase>> tables() {
        return null;
    }
    
    public final void todos(int skip, int limit, @org.jetbrains.annotations.NotNull()
    promise.model.ResponseCallBack<promise.model.List<promise.app_base.models.Todo>, promise.app_base.error.AppError> responseCallBack) {
    }
    
    /**
     * @param category
     */
    public final void todos(@org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    promise.model.ResponseCallBack<promise.model.List<promise.app_base.models.Todo>, promise.app_base.error.AppError> responseCallBack) {
    }
    
    /**
     * @param todos to be saved
     */
    public final void saveTodos(@org.jetbrains.annotations.NotNull()
    promise.model.List<promise.app_base.models.Todo> todos, @org.jetbrains.annotations.NotNull()
    promise.model.ResponseCallBack<java.lang.Boolean, promise.app_base.error.AppError> responseCallBack) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public io.reactivex.disposables.CompositeDisposable onTerminate() {
        return null;
    }
    
    @javax.inject.Inject()
    public AsyncAppDatabase() {
        super(null, 0, null, null);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2 = {"Lpromise/app_base/data/db/async/AsyncAppDatabase$Companion;", "", "()V", "DB_NAME", "", "DB_VERSION", "", "SENDER_TAG", "todoTable", "Lpromise/app_base/data/db/TodoTable;", "getTodoTable", "()Lpromise/app_base/data/db/TodoTable;", "todoTable$delegate", "Lkotlin/Lazy;", "appbase_debug"})
    public static final class Companion {
        
        private final promise.app_base.data.db.TodoTable getTodoTable() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}