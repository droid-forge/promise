package me.dev4vin.samplepromiseapp.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import me.dev4vin.samplepromiseapp.R;
import me.dev4vin.samplepromiseapp.common.DataRepository;
import me.dev4vin.samplepromiseapp.models.Todo;
import me.dev4vin.samplepromiseapp.ui.views.LoadableView;
import me.yoctopus.model.List;
import me.yoctopus.model.ResponseCallBack;
import me.yoctopus.view.AdapterDivider;
import me.yoctopus.view.PromiseAdapter;
import me.yoctopus.view.SearchableAdapter;
import me.yoctopus.view.loading.ProgressLayout;

public class MainActivity extends AppCompatActivity implements PromiseAdapter.Listener<Todo> {
  protected Toolbar toolbar;
  protected RecyclerView todosList;
  protected ProgressLayout loadingView;
  protected FloatingActionButton fab;
  private SearchableAdapter<Todo> searchableAdapter;
  private AdapterDivider divider;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    super.setContentView(R.layout.activity_main);
    initView();
    setSupportActionBar(toolbar);
  }

  @Override
  protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
      }
    });

    divider = new AdapterDivider(this, LinearLayout.VERTICAL);
    searchableAdapter = new SearchableAdapter<>(this);
    todosList.setLayoutManager(new LinearLayoutManager(this));
    todosList.setItemAnimator(new DefaultItemAnimator());
    todosList.addItemDecoration(divider);
    loadingView.showLoading(new LoadableView("Loading todos, please wait..."));

    searchableAdapter.swipe(new PromiseAdapter.Swipe<Todo>() {
      @Override
      public void onSwipeRight(Todo todo, PromiseAdapter.Response response) {

      }

      @Override
      public void onSwipeLeft(Todo todo, PromiseAdapter.Response response) {

      }
    });
    todosList.setAdapter(searchableAdapter);
    DataRepository.instance().getTodos(0, 10, new ResponseCallBack<List<Todo>, Exception>()
    .response(new ResponseCallBack.Response<List<Todo>, Exception>() {
      @Override
      public void onResponse(List<Todo> todos) throws Exception {
        searchableAdapter.add(todos);
      }
    }).error(new ResponseCallBack.Error<Exception>() {
          @Override
          public void onError(final Exception e) {
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                loadingView.showEmpty(0, "Could not load todos", e.getMessage());
              }
            });
          }
        }));
  }

  @Override
  public void onClick(Todo todo, int id) {

  }

  private void initView() {
    toolbar = findViewById(R.id.toolbar);
    todosList = findViewById(R.id.todos_list);
    loadingView = findViewById(R.id.loading_view);
    fab = findViewById(R.id.fab);
  }
}
