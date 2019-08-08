package promise.app_base.data.db;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u000f2\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u000fB\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0011\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0002H\u0096\u0002J\u0012\u0010\n\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\f0\u000bH\u0016J\b\u0010\r\u001a\u00020\u000eH\u0016\u00a8\u0006\u0010"}, d2 = {"Lpromise/app_base/data/db/TaskTable;", "Lpromise/data/db/Model;", "Lpromise/app_base/models/Task;", "()V", "from", "cursor", "Landroid/database/Cursor;", "get", "Landroid/content/ContentValues;", "todo", "getColumns", "Lpromise/model/List;", "Lpromise/data/db/Column;", "getName", "", "Companion", "appbase_debug"})
public final class TaskTable extends promise.data.db.Model<promise.app_base.models.Task> {
    private static final java.lang.String tableName = "b";
    @org.jetbrains.annotations.NotNull()
    private static promise.data.db.Column<java.lang.String> category;
    @org.jetbrains.annotations.NotNull()
    private static promise.data.db.Column<java.lang.String> namee;
    @org.jetbrains.annotations.NotNull()
    private static promise.data.db.Column<java.lang.Integer> completed;
    public static final promise.app_base.data.db.TaskTable.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public promise.model.List<promise.data.db.Column<?>> getColumns() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public android.content.ContentValues get(@org.jetbrains.annotations.NotNull()
    promise.app_base.models.Task todo) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public promise.app_base.models.Task from(@org.jetbrains.annotations.NotNull()
    android.database.Cursor cursor) {
        return null;
    }
    
    public TaskTable() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R \u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR \u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u0007\"\u0004\b\r\u0010\tR \u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0007\"\u0004\b\u0010\u0010\tR\u000e\u0010\u0011\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lpromise/app_base/data/db/TaskTable$Companion;", "", "()V", "category", "Lpromise/data/db/Column;", "", "getCategory", "()Lpromise/data/db/Column;", "setCategory", "(Lpromise/data/db/Column;)V", "completed", "", "getCompleted", "setCompleted", "namee", "getNamee", "setNamee", "tableName", "appbase_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final promise.data.db.Column<java.lang.String> getCategory() {
            return null;
        }
        
        public final void setCategory(@org.jetbrains.annotations.NotNull()
        promise.data.db.Column<java.lang.String> p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final promise.data.db.Column<java.lang.String> getNamee() {
            return null;
        }
        
        public final void setNamee(@org.jetbrains.annotations.NotNull()
        promise.data.db.Column<java.lang.String> p0) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final promise.data.db.Column<java.lang.Integer> getCompleted() {
            return null;
        }
        
        public final void setCompleted(@org.jetbrains.annotations.NotNull()
        promise.data.db.Column<java.lang.Integer> p0) {
        }
        
        private Companion() {
            super();
        }
    }
}