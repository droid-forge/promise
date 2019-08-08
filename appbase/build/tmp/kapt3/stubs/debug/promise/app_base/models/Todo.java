package promise.app_base.models;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u0000 !2\u00020\u00012\u00020\u0002:\u0001!B\u0007\b\u0016\u00a2\u0006\u0002\u0010\u0003B\u000f\b\u0012\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0012\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\u0007\u001a\u0004\u0018\u00010\bJ\u000e\u0010\u0007\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\t\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u0016\u001a\u00020\bJ\b\u0010\u0017\u001a\u00020\fH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\u000b\u001a\u00020\u00132\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0016J\b\u0010\u0019\u001a\u00020\fH\u0016J\b\u0010\u001a\u001a\u0004\u0018\u00010\bJ\u000e\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001a\u001a\u00020\bJ\u0010\u0010\u001b\u001a\u00020\n2\u0006\u0010\u001c\u001a\u00020\bH\u0016J\b\u0010\u001d\u001a\u00020\bH\u0016J\u0018\u0010\u001e\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\fH\u0016R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lpromise/app_base/models/Todo;", "Lpromise/model/SModel;", "Lpromise/model/Searchable;", "()V", "in", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "category", "", "completed", "", "index", "", "switchCompat", "Landroidx/appcompat/widget/AppCompatCheckBox;", "textView", "Landroid/widget/CheckedTextView;", "title", "bind", "", "view", "Landroid/view/View;", "date", "describeContents", "init", "layout", "name", "onSearch", "query", "toString", "writeToParcel", "dest", "flags", "CREATOR", "appbase_debug"})
public final class Todo extends promise.model.SModel implements promise.model.Searchable {
    private java.lang.String category;
    private java.lang.String title;
    private boolean completed;
    private transient int index;
    private transient androidx.appcompat.widget.AppCompatCheckBox switchCompat;
    private transient android.widget.CheckedTextView textView;
    public static final promise.app_base.models.Todo.CREATOR CREATOR = null;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String date() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String category() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final promise.app_base.models.Todo category(@org.jetbrains.annotations.NotNull()
    java.lang.String category) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String name() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final promise.app_base.models.Todo name(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
        return null;
    }
    
    public final boolean completed() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final promise.app_base.models.Todo completed(boolean completed) {
        return null;
    }
    
    @java.lang.Override()
    public boolean onSearch(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
        return false;
    }
    
    @java.lang.Override()
    public int layout() {
        return 0;
    }
    
    @java.lang.Override()
    public void init(@org.jetbrains.annotations.NotNull()
    android.view.View view) {
    }
    
    @java.lang.Override()
    public void bind(@org.jetbrains.annotations.Nullable()
    android.view.View view) {
    }
    
    @java.lang.Override()
    public void index(int index) {
    }
    
    @java.lang.Override()
    public int index() {
        return 0;
    }
    
    @java.lang.Override()
    public int describeContents() {
        return 0;
    }
    
    @java.lang.Override()
    public void writeToParcel(@org.jetbrains.annotations.NotNull()
    android.os.Parcel dest, int flags) {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public Todo() {
        super();
    }
    
    private Todo(android.os.Parcel in) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001d\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2 = {"Lpromise/app_base/models/Todo$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lpromise/app_base/models/Todo;", "()V", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lpromise/app_base/models/Todo;", "appbase_debug"})
    public static final class CREATOR implements android.os.Parcelable.Creator<promise.app_base.models.Todo> {
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public promise.app_base.models.Todo createFromParcel(@org.jetbrains.annotations.NotNull()
        android.os.Parcel parcel) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public promise.app_base.models.Todo[] newArray(int size) {
            return null;
        }
        
        private CREATOR() {
            super();
        }
    }
}