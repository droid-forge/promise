package promise.app_base.data.net;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\"\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u00062\b\b\u0001\u0010\u0007\u001a\u00020\u0006H\'\u00a8\u0006\b"}, d2 = {"Lpromise/app_base/data/net/AuthApi;", "", "login", "Lpromise/data/net/net/Call;", "Lpromise/app_base/auth/User;", "email", "", "password", "appbase_debug"})
public abstract interface AuthApi {
    
    @org.jetbrains.annotations.NotNull()
    @promise.data.net.http.POST(value = "/login")
    @promise.data.net.http.FormUrlEncoded()
    public abstract promise.data.net.net.Call<promise.app_base.auth.User> login(@org.jetbrains.annotations.NotNull()
    @promise.data.net.http.Field(value = "email")
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    @promise.data.net.http.Field(value = "password")
    java.lang.String password);
}