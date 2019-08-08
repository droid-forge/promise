package promise.app_base.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import java.lang.Deprecated;
import java.lang.Object;
import promise.app_base.models.Todo;

public abstract class TodoLayoutBinding extends ViewDataBinding {
  @NonNull
  public final AppCompatCheckBox checked;

  @NonNull
  public final AppCompatTextView dateTextView;

  @NonNull
  public final CheckedTextView titleText;

  @Bindable
  protected Todo mTodo;

  protected TodoLayoutBinding(Object _bindingComponent, View _root, int _localFieldCount,
      AppCompatCheckBox checked, AppCompatTextView dateTextView, CheckedTextView titleText) {
    super(_bindingComponent, _root, _localFieldCount);
    this.checked = checked;
    this.dateTextView = dateTextView;
    this.titleText = titleText;
  }

  public abstract void setTodo(@Nullable Todo todo);

  @Nullable
  public Todo getTodo() {
    return mTodo;
  }

  @NonNull
  public static TodoLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.todo_layout, root, attachToRoot, component)
   */
  @NonNull
  @Deprecated
  public static TodoLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable Object component) {
    return ViewDataBinding.<TodoLayoutBinding>inflateInternal(inflater, promise.app_base.R.layout.todo_layout, root, attachToRoot, component);
  }

  @NonNull
  public static TodoLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.inflate(inflater, R.layout.todo_layout, null, false, component)
   */
  @NonNull
  @Deprecated
  public static TodoLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable Object component) {
    return ViewDataBinding.<TodoLayoutBinding>inflateInternal(inflater, promise.app_base.R.layout.todo_layout, null, false, component);
  }

  public static TodoLayoutBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  /**
   * This method receives DataBindingComponent instance as type Object instead of
   * type DataBindingComponent to avoid causing too many compilation errors if
   * compilation fails for another reason.
   * https://issuetracker.google.com/issues/116541301
   * @Deprecated Use DataBindingUtil.bind(view, component)
   */
  @Deprecated
  public static TodoLayoutBinding bind(@NonNull View view, @Nullable Object component) {
    return (TodoLayoutBinding)bind(component, view, promise.app_base.R.layout.todo_layout);
  }
}
