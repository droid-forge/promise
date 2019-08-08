package promise.app_base.data.net;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\b\u0010\u0007\u001a\u00020\u0006H\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\n"}, d2 = {"Lpromise/app_base/data/net/BaseApi;", "", "()V", "provideAuthApi", "Lpromise/app_base/data/net/AuthApi;", "fastParserEngine", "Lpromise/data/net/net/FastParserEngine;", "provideFastParserEngine", "provideTodoApi", "Lpromise/app_base/data/net/TodoApi;", "appbase_debug"})
@dagger.Module()
public final class BaseApi {
    public static final promise.app_base.data.net.BaseApi INSTANCE = null;
    
    @org.jetbrains.annotations.NotNull()
    @promise.app_base.scopes.AppScope()
    @dagger.Provides()
    public static final promise.data.net.net.FastParserEngine provideFastParserEngine() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @promise.app_base.scopes.AppScope()
    @dagger.Provides()
    public static final promise.app_base.data.net.AuthApi provideAuthApi(@org.jetbrains.annotations.NotNull()
    promise.data.net.net.FastParserEngine fastParserEngine) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @promise.app_base.scopes.AppScope()
    @dagger.Provides()
    public static final promise.app_base.data.net.TodoApi provideTodoApi(@org.jetbrains.annotations.NotNull()
    promise.data.net.net.FastParserEngine fastParserEngine) {
        return null;
    }
    
    private BaseApi() {
        super();
    }
}