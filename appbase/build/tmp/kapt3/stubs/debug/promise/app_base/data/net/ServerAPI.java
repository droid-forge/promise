package promise.app_base.data.net;

import java.lang.System;

/**
 * Add a response interceptor to intercept error codes 403
 */
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\bJ\"\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\t0\bJ\u0014\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000e0\rH\u0016J0\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0018\u0010\u0007\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0013\u0012\u0004\u0012\u00020\t0\bJ\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u0013H\u0002J\"\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\b\u00a8\u0006\u0017"}, d2 = {"Lpromise/app_base/data/net/ServerAPI;", "Lpromise/data/net/FastParser;", "()V", "addTodo", "", "todo", "Lpromise/app_base/models/Todo;", "responseCallBack", "Lpromise/model/ResponseCallBack;", "Lpromise/app_base/error/ServerError;", "deleteTodo", "", "getHeaders", "", "", "getTodos", "skip", "", "limit", "Lpromise/model/List;", "someTodos", "updateTodo", "Companion", "appbase_debug"})
public final class ServerAPI extends promise.data.net.FastParser {
    private static promise.app_base.data.net.ServerAPI instance;
    private static final java.lang.String UPSTREAM_URL = "https://jsonplaceholder.typicode.com";
    public static final promise.app_base.data.net.ServerAPI.Companion Companion = null;
    
    /**
     * returns default headers
     *
     * @return the headers to be used for every requests
     */
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.util.Map<java.lang.String, java.lang.String> getHeaders() {
        return null;
    }
    
    /**
     * get list of todos from server
     *
     * @param skip             the skip to start querying from, useful in pagination
     * @param limit            the number of todos we want
     * @param responseCallBack to return data to caller function
     */
    public final void getTodos(int skip, int limit, @org.jetbrains.annotations.NotNull()
    promise.model.ResponseCallBack<promise.model.List<promise.app_base.models.Todo>, promise.app_base.error.ServerError> responseCallBack) {
    }
    
    public final void addTodo(@org.jetbrains.annotations.NotNull()
    promise.app_base.models.Todo todo, @org.jetbrains.annotations.NotNull()
    promise.model.ResponseCallBack<promise.app_base.models.Todo, promise.app_base.error.ServerError> responseCallBack) {
    }
    
    public final void updateTodo(@org.jetbrains.annotations.NotNull()
    promise.app_base.models.Todo todo, @org.jetbrains.annotations.NotNull()
    promise.model.ResponseCallBack<promise.app_base.models.Todo, promise.app_base.error.ServerError> responseCallBack) {
    }
    
    public final void deleteTodo(@org.jetbrains.annotations.NotNull()
    promise.app_base.models.Todo todo, @org.jetbrains.annotations.NotNull()
    promise.model.ResponseCallBack<java.lang.Boolean, promise.app_base.error.ServerError> responseCallBack) {
    }
    
    private final promise.model.List<promise.app_base.models.Todo> someTodos() {
        return null;
    }
    
    private ServerAPI() {
        super(null);
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lpromise/app_base/data/net/ServerAPI$Companion;", "", "()V", "UPSTREAM_URL", "", "instance", "Lpromise/app_base/data/net/ServerAPI;", "appbase_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final promise.app_base.data.net.ServerAPI instance() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}