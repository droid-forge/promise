package promise.app_base.auth;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004B\u0005\u00a2\u0006\u0002\u0010\u0005J\b\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007J\u000e\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0006\u001a\u00020\u0007J\b\u0010\b\u001a\u0004\u0018\u00010\u0007J\u000e\u0010\b\u001a\u00020\u00002\u0006\u0010\b\u001a\u00020\u0007J\b\u0010\t\u001a\u0004\u0018\u00010\u0007J\u000e\u0010\t\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0007J\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bJ\u0014\u0010\n\u001a\u00020\u00002\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bJ\u0018\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0011\u001a\u00020\u000eH\u0016R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lpromise/app_base/auth/User;", "Lpromise/model/SModel;", "parcel", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "()V", "email", "", "names", "password", "roles", "Lpromise/model/List;", "Lpromise/app_base/auth/Role;", "describeContents", "", "writeToParcel", "", "flags", "CREATOR", "appbase_debug"})
public final class User extends promise.model.SModel {
    private java.lang.String email;
    private java.lang.String password;
    private java.lang.String names;
    private promise.model.List<promise.app_base.auth.Role> roles;
    public static final promise.app_base.auth.User.CREATOR CREATOR = null;
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String email() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final promise.app_base.auth.User email(@org.jetbrains.annotations.NotNull()
    java.lang.String email) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String password() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final promise.app_base.auth.User password(@org.jetbrains.annotations.NotNull()
    java.lang.String password) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String names() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final promise.app_base.auth.User names(@org.jetbrains.annotations.NotNull()
    java.lang.String names) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final promise.model.List<promise.app_base.auth.Role> roles() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final promise.app_base.auth.User roles(@org.jetbrains.annotations.NotNull()
    promise.model.List<promise.app_base.auth.Role> roles) {
        return null;
    }
    
    @java.lang.Override()
    public void writeToParcel(@org.jetbrains.annotations.NotNull()
    android.os.Parcel parcel, int flags) {
    }
    
    @java.lang.Override()
    public int describeContents() {
        return 0;
    }
    
    public User() {
        super();
    }
    
    public User(@org.jetbrains.annotations.NotNull()
    android.os.Parcel parcel) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001d\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2 = {"Lpromise/app_base/auth/User$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lpromise/app_base/auth/User;", "()V", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lpromise/app_base/auth/User;", "appbase_debug"})
    public static final class CREATOR implements android.os.Parcelable.Creator<promise.app_base.auth.User> {
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public promise.app_base.auth.User createFromParcel(@org.jetbrains.annotations.NotNull()
        android.os.Parcel parcel) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public promise.app_base.auth.User[] newArray(int size) {
            return null;
        }
        
        private CREATOR() {
            super();
        }
    }
}