package promise.app_base.data.db;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0007\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tJ\u0014\u0010\r\u001a\u00020\u000b2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000fJ \u0010\u0010\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0016J\u0018\u0010\u0016\u001a\u0012\u0012\u000e\u0012\f\u0012\u0002\b\u0003\u0012\u0004\u0012\u00020\u00120\u00170\u000fH\u0016J\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u000f2\u0006\u0010\u001a\u001a\u00020\u0004J\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f2\u0006\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u001c\u001a\u00020\u0014J\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\t0\u000f2\u0006\u0010\u001a\u001a\u00020\u0004J\u000e\u0010\u001d\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lpromise/app_base/data/db/AppDatabase;", "Lpromise/data/db/FastDB;", "()V", "TAG", "", "kotlin.jvm.PlatformType", "addTodo", "", "todo", "Lpromise/app_base/models/Todo;", "clearTodos", "", "deleteTodo", "saveTodos", "todos", "Lpromise/model/List;", "shouldUpgrade", "database", "Landroid/database/sqlite/SQLiteDatabase;", "oldVersion", "", "newVersion", "tables", "Lpromise/data/db/Table;", "tasks", "Lpromise/app_base/models/Task;", "category", "skip", "limit", "updateTodo", "Companion", "appbase_debug"})
@promise.app_base.scopes.AppScope()
public final class AppDatabase extends promise.data.db.FastDB {
    private final java.lang.String TAG = null;
    private static final java.lang.String DB_NAME = "a";
    private static final int DB_VERSION = 2;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String SENDER_TAG = "App_Database";
    private static final kotlin.Lazy todoTable$delegate = null;
    private static final kotlin.Lazy taskTable$delegate = null;
    public static final promise.app_base.data.db.AppDatabase.Companion Companion = null;
    
    /**
     * check if the database should be upgraded
     *
     * @param database   to be upgraded
     * @param oldVersion current database version
     * @param newVersion new database version
     * @return true if to be upgraded or false
     */
    @java.lang.Override()
    public boolean shouldUpgrade(@org.jetbrains.annotations.NotNull()
    android.database.sqlite.SQLiteDatabase database, int oldVersion, int newVersion) {
        return false;
    }
    
    /**
     * All tables must be registered here
     *
     * @return the list of tables to be created by fast db
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public promise.model.List<promise.data.db.Table<?, android.database.sqlite.SQLiteDatabase>> tables() {
        return null;
    }
    
    /**
     * @param skip  records to skip
     * @param limit records to take
     * @return a list of todos
     */
    @org.jetbrains.annotations.NotNull()
    public final promise.model.List<promise.app_base.models.Todo> todos(int skip, int limit) {
        return null;
    }
    
    /**
     * @param category todo category
     * @return a list of todos matching this category
     */
    @org.jetbrains.annotations.NotNull()
    public final promise.model.List<promise.app_base.models.Todo> todos(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final promise.model.List<promise.app_base.models.Task> tasks(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
        return null;
    }
    
    /**
     * @param todos to be saved
     * @return true if all todos are saved
     */
    public final boolean saveTodos(@org.jetbrains.annotations.NotNull()
    promise.model.List<promise.app_base.models.Todo> todos) {
        return false;
    }
    
    public final long addTodo(@org.jetbrains.annotations.NotNull()
    promise.app_base.models.Todo todo) {
        return 0L;
    }
    
    public final boolean updateTodo(@org.jetbrains.annotations.NotNull()
    promise.app_base.models.Todo todo) {
        return false;
    }
    
    public final boolean deleteTodo(@org.jetbrains.annotations.NotNull()
    promise.app_base.models.Todo todo) {
        return false;
    }
    
    public final boolean clearTodos() {
        return false;
    }
    
    @javax.inject.Inject()
    public AppDatabase() {
        super(null, 0, null, null);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u001b\u0010\u000e\u001a\u00020\u000f8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0012\u0010\r\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0013"}, d2 = {"Lpromise/app_base/data/db/AppDatabase$Companion;", "", "()V", "DB_NAME", "", "DB_VERSION", "", "SENDER_TAG", "taskTable", "Lpromise/app_base/data/db/TaskTable;", "getTaskTable", "()Lpromise/app_base/data/db/TaskTable;", "taskTable$delegate", "Lkotlin/Lazy;", "todoTable", "Lpromise/app_base/data/db/TodoTable;", "getTodoTable", "()Lpromise/app_base/data/db/TodoTable;", "todoTable$delegate", "appbase_debug"})
    public static final class Companion {
        
        private final promise.app_base.data.db.TodoTable getTodoTable() {
            return null;
        }
        
        private final promise.app_base.data.db.TaskTable getTaskTable() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}