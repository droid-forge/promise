package promise.app_base.auth;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u000e\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004J\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006J\u000e\u0010\u0005\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lpromise/app_base/auth/Role;", "", "()V", "allowed", "", "name", "", "Companion", "appbase_debug"})
public final class Role {
    private java.lang.String name;
    private boolean allowed;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CAN_ADD_TODO = "can_add_todo";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CAN_UPDATE_TODO = "can_update_todo";
    public static final promise.app_base.auth.Role.Companion Companion = null;
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String name() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final promise.app_base.auth.Role name(@org.jetbrains.annotations.NotNull()
    java.lang.String name) {
        return null;
    }
    
    public final boolean allowed() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final promise.app_base.auth.Role allowed(boolean allowed) {
        return null;
    }
    
    public Role() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\u0004X\u0086D\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006\u00a8\u0006\t"}, d2 = {"Lpromise/app_base/auth/Role$Companion;", "", "()V", "CAN_ADD_TODO", "", "getCAN_ADD_TODO", "()Ljava/lang/String;", "CAN_UPDATE_TODO", "getCAN_UPDATE_TODO", "appbase_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getCAN_ADD_TODO() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getCAN_UPDATE_TODO() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}