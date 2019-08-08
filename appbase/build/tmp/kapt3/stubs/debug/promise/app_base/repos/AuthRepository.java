package promise.app_base.repos;

import java.lang.System;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0000\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0003J\"\u0010\n\u001a\u0004\u0018\u00010\u00022\u0016\u0010\u000b\u001a\u0012\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u000e\u0018\u00010\fH\u0016R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t\u00a8\u0006\u000f"}, d2 = {"Lpromise/app_base/repos/AuthRepository;", "Lpromise/repo/AbstractSyncIDataStore;", "Lpromise/app_base/auth/User;", "()V", "authApi", "Lpromise/app_base/data/net/AuthApi;", "getAuthApi", "()Lpromise/app_base/data/net/AuthApi;", "setAuthApi", "(Lpromise/app_base/data/net/AuthApi;)V", "one", "args", "", "", "", "appbase_debug"})
@promise.app_base.scopes.AppScope()
public final class AuthRepository extends promise.repo.AbstractSyncIDataStore<promise.app_base.auth.User> {
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Inject()
    public promise.app_base.data.net.AuthApi authApi;
    
    @org.jetbrains.annotations.NotNull()
    public final promise.app_base.data.net.AuthApi getAuthApi() {
        return null;
    }
    
    public final void setAuthApi(@org.jetbrains.annotations.NotNull()
    promise.app_base.data.net.AuthApi p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public promise.app_base.auth.User one(@org.jetbrains.annotations.Nullable()
    java.util.Map<java.lang.String, ? extends java.lang.Object> args) {
        return null;
    }
    
    @javax.inject.Inject()
    public AuthRepository() {
        super();
    }
}