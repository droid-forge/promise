package promise.app_base.auth;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J*\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00042\u0012\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u001b0\u001aJ\u0010\u0010\u001c\u001a\u00020\u00162\u0006\u0010\u000e\u001a\u00020\u0004H\u0002J\u0010\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u0011\u001a\u00020\u0012H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\n \u0006*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0004\u0012\u00020\t\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000e\u001a\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\u00128F\u00a2\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001e"}, d2 = {"Lpromise/app_base/auth/Session;", "", "()V", "AUTH_TOKEN_KEY", "", "TAG", "kotlin.jvm.PlatformType", "rolePreferenceStore", "Lpromise/data/pref/PreferenceStore;", "Lpromise/app_base/auth/Role;", "serverAPI", "Lpromise/app_base/data/net/ServerAPI;", "sessionPreferences", "Lpromise/data/pref/Preferences;", "token", "getToken", "()Ljava/lang/String;", "user", "Lpromise/app_base/auth/User;", "getUser", "()Lpromise/app_base/auth/User;", "login", "", "email", "password", "responseCallBack", "Lpromise/model/ResponseCallBack;", "Lpromise/app_base/error/AuthError;", "storeToken", "storeUser", "appbase_debug"})
public final class Session {
    private static final java.lang.String TAG = null;
    
    /**
     * the namee of key in preference to store token in session preference
     */
    private static final java.lang.String AUTH_TOKEN_KEY = "auth_token_key";
    
    /**
     * preference object to easily store user info in shared preferences
     */
    private static promise.data.pref.Preferences sessionPreferences;
    
    /**
     * preference store to store multiple roles in shared preferences
     */
    private static promise.data.pref.PreferenceStore<promise.app_base.auth.Role> rolePreferenceStore;
    
    /**
     * server api tpo access upstream service
     */
    private static promise.app_base.data.net.ServerAPI serverAPI;
    public static final promise.app_base.auth.Session INSTANCE = null;
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getToken() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final promise.app_base.auth.User getUser() {
        return null;
    }
    
    public final void login(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    promise.model.ResponseCallBack<promise.app_base.auth.User, promise.app_base.error.AuthError> responseCallBack) {
    }
    
    private final void storeUser(promise.app_base.auth.User user) {
    }
    
    private final void storeToken(java.lang.String token) {
    }
    
    private Session() {
        super();
    }
}