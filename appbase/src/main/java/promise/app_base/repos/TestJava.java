package promise.app_base.repos;

import java.util.List;

import promise.app_base.data.net.TodoApi;
import promise.app_base.models.Todo;
import promise.data.net.net.Call;
import promise.data.net.net.Callback;
import promise.data.net.net.Response;

public class TestJava {
  private void testFetchTodos(int limit, int skip, TodoApi todoApi) {
    /*todoApi.todos(skip, limit).enqueue(new Callback<List<Todo>>() {
      @Override
      public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {

      }

      @Override
      public void onFailure(Call<List<Todo>> call, Throwable t) {

      }
    });*/
  }
}
