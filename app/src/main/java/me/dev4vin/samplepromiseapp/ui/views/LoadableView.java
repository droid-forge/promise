package me.dev4vin.samplepromiseapp.ui.views;

import android.view.View;

import me.yoctopus.model.Viewable;

public class LoadableView implements Viewable {
  private String status;

  public LoadableView(String status) {
    this.status = status;
  }

  @Override
  public int layout() {
    return 0;
  }

  @Override
  public void init(View view) {

  }

  @Override
  public void bind(View view) {

  }

  @Override
  public void index(int index) {

  }

  @Override
  public int index() {
    return 0;
  }
}
