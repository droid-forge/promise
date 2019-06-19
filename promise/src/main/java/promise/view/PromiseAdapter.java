/*
 *
 *  * Copyright 2017, Peter Vincent
 *  * Licensed under the Apache License, Version 2.0, Promise.
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package promise.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import promise.cac.anim.Anim;
import promise.cac.anim.AnimDuration;
import promise.cac.anim.Animator;
import promise.data.log.LogUtil;
import promise.model.List;
import promise.model.Viewable;
import promise.model.function.MapFunction;
import promise.util.Conditions;

/**
 * Created by yoctopus on 11/6/17.
 */
public class PromiseAdapter<T extends Viewable>
    extends RecyclerView.Adapter<PromiseAdapter<T>.Holder> {
  private String TAG = LogUtil.makeTag(PromiseAdapter.class);
  private String AdapterItems = "__adapter_items__";
  private Indexer indexer;
  private List<T> list;
  private Listener<T> listener;
  private LongClickListener<T> longClickListener;
  private Swipe<T> swipeListener;
  private Anim anim;
  private AnimDuration animDuration;
  private AnimDuration waitDuration;
  private int alternatingColor = 0;
  private Handler handler;
  private RecyclerView recyclerView;
  private OnAfterInitListener onAfterInitListener;
  private SparseArray<T> sparseArray= new SparseArray<>();

  private Object args;

  public PromiseAdapter(@NonNull Listener<T> listener) {
    this(new List<T>(), listener);
  }

  public PromiseAdapter(@NonNull List<T> list, @NonNull Listener<T> listener) {
    this.list = Conditions.checkNotNull(list);
    this.listener = listener;
    this.handler = new Handler(Looper.getMainLooper());
    indexList();
  }

  public PromiseAdapter<T> args(Object args) {
    this.args = args;
    return this;
  }

  public void restoreViewState(Bundle instanceState) {
    List<Parcelable> items = new List<>(instanceState.getParcelableArrayList(AdapterItems));
    if (items.isEmpty()) return;
    this.list = items.map(parcelable -> (T) parcelable);
    setList(list);
  }

  public void backupViewState(Bundle instanceState) {
    instanceState.putParcelableArrayList(AdapterItems, new ArrayList<>(list.map(t -> (Parcelable) t)));
  }

  @Deprecated
  public void destroyViewState() {

  }

  public PromiseAdapter<T> swipe(Swipe<T> swipeListener) {
    this.swipeListener = swipeListener;
    return this;
  }

  public PromiseAdapter onAfterInitListener(OnAfterInitListener onAfterInitListener) {
    this.onAfterInitListener = onAfterInitListener;
    return this;
  }

  public PromiseAdapter<T> alternatingColor(int color) {
    this.alternatingColor = color;
    return this;
  }

  private void indexList() {
    indexer = new Indexer();
    indexer.index();
  }

  public void add(final T t) {
    indexer.add(Conditions.checkNotNull(t));
  }

  public void unshift(final T t) {
    indexer.unshift(Conditions.checkNotNull(t));
  }

  public void add(final List<T> list) {
    indexer.add(Conditions.checkNotNull(list));
  }

  public void remove(final T t) {
    indexer.remove(Conditions.checkNotNull(t));
  }

  public void updateAll() {
    indexer.updateAll();
  }

  public void update(final T t) {
    indexer.update(Conditions.checkNotNull(t));
  }

  public void clear() {
    indexer.clear();
  }

  @Override
  public int getItemViewType(int position) {
    T t = getList().get(position);
    int viewType = t.layout();
    Conditions.checkState(viewType != 0, "The layout resource for " + t + " is not provided");
    return viewType;
  }

  @NonNull
  @Override
  public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
    if (onAfterInitListener != null) onAfterInitListener.onAfterInit(view);
    return new Holder(view);
  }

  @Override
  public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
    this.recyclerView = recyclerView;
    RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
    if (manager instanceof GridLayoutManager) {
      GridLayoutManager manager1 = (GridLayoutManager) manager;
      recyclerView.setLayoutManager(new WrapContentGridLayoutManager(recyclerView.getContext(), manager1.getSpanCount()));
    } else if (manager instanceof LinearLayoutManager)
      recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(recyclerView.getContext()));
    /*if (recyclerView.getItemAnimator() != null) {
      recyclerView.setItemAnimator(new CustomItemAnimator());
    }*/
    if (swipeListener != null) {
      ItemTouchHelper.SimpleCallback simpleCallback =
          new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(
                RecyclerView recyclerView,
                RecyclerView.ViewHolder viewHolder,
                RecyclerView.ViewHolder target) {
              return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
              if (viewHolder instanceof PromiseAdapter.Holder) {
                final Holder holder = (Holder) viewHolder;
                Response response =
                    new Response() {
                      @Override
                      public void call() {
                        update(holder.t);
                      }
                    };
                switch (direction) {
                  case ItemTouchHelper.RIGHT:
                    swipeListener.onSwipeRight(holder.t, response);
                    break;
                  case ItemTouchHelper.LEFT:
                    swipeListener.onSwipeLeft(holder.t, response);
                    break;
                }
              }
            }
          };
      new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }
  }

  @Override
  public void onBindViewHolder(@NonNull Holder holder, int position) {
    T t = getList().get(position);
    if (alternatingColor != 0)
      if (position % 2 == 1) holder.view.setBackgroundColor(alternatingColor);
    holder.bind(t, args);
    holder.bindListener(t);
    holder.bindLongClickListener(t);
    if (anim != null) {
      Animator animator = new Animator(holder.view, anim);
      animator.setAnimDuration(animDuration == null ? AnimDuration.standard() : animDuration);
      animator.setWaitDuration(waitDuration == null ? AnimDuration.noDuration() : waitDuration);
      animator.animate();
    }
  }

  @Override
  public int getItemCount() {
    return indexer.size();
  }

  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.indexer.setList(list);
  }

  public Listener<T> getListener() {
    return listener;
  }

  public void setListener(Listener<T> listener) {
    this.listener = listener;
  }

  public boolean isReverse() {
    return indexer.reverse;
  }

  private void setReverse(boolean reverse) {
    this.indexer.reverse(reverse);
  }

  public LongClickListener<T> getLongClickListener() {
    return longClickListener;
  }

  public void setLongClickListener(LongClickListener<T> longClickListener) {
    this.longClickListener = longClickListener;
  }

  public void reverse() {
    indexer.reverse(true);
  }

  public void reverse(boolean reverse) {
    indexer.reverse(reverse);
  }

  public Anim getAnim() {
    return anim;
  }

  public void setAnim(Anim anim) {
    this.anim = anim;
  }

  public AnimDuration getAnimDuration() {
    return animDuration;
  }

  public void setAnimDuration(AnimDuration animDuration) {
    this.animDuration = animDuration;
  }

  public AnimDuration getWaitDuration() {
    return waitDuration;
  }

  public void setWaitDuration(AnimDuration waitDuration) {
    this.waitDuration = waitDuration;
  }

  public interface Listener<T> {
    void onClick(T t, @IdRes int id);
  }

  public interface OnAfterInitListener {
    void onAfterInit(View view);
  }

  public interface Response {
    void call();
  }

  public interface Swipe<T> {
    void onSwipeRight(T t, Response response);

    void onSwipeLeft(T t, Response response);
  }

  public interface LongClickListener<T> {
    void onLongClick(T t, @IdRes int id);
  }

  public class Holder extends RecyclerView.ViewHolder {
    public View view;
    T t;

    public Holder(View itemView) {
      super(itemView);
      view = itemView;
    }

    void bind(T t, Object args) {
      this.t = t;
      t.init(view);
      t.bind(view, args);
    }

    void bindListener(final T t) {
      if (listener == null) return;
      List<Field> fields = new List<>(Arrays.asList(t.getClass().getDeclaredFields()));
      for (Field field : fields)
        try {
          Object view = field.get(t);
          if (view instanceof View)
            ((View) view)
                .setOnClickListener(
                    new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                        listener.onClick(t, v.getId());
                      }
                    });
        } catch (IllegalAccessException ignored) {
          /*LogUtil.e(TAG, "illegal access ", ignored);*/
        }
    }

    void bindLongClickListener(final T t) {
      if (longClickListener == null) return;
      List<Field> fields = new List<>(Arrays.asList(t.getClass().getDeclaredFields()));
      for (Field field : fields)
        try {
          Object view = field.get(t);
          if (view instanceof View)
            ((View) view)
                .setOnLongClickListener(
                    new View.OnLongClickListener() {
                      @Override
                      public boolean onLongClick(View v) {

                        longClickListener.onLongClick(t, v.getId());
                        return true;
                      }
                    });
        } catch (IllegalAccessException ignored) {
        }
    }
  }

  private class Indexer {
    boolean reverse = false;

    void index() {
      for (int i = 0; i < list.size(); i++) list.get(i).index(i);
    }

    void add(T t) {
      if (list == null) list = new List<>();
      if (!list.isEmpty()) {
        if (reverse) list.reverse();
        list.add(t);
        if (reverse) list.reverse();
        index();
        handler.post(
            new Runnable() {
              @Override
              public void run() {
                notifyDataSetChanged();
              }
            });
      } else {
        list.add(t);
        t.index(0);
        handler.post(
            new Runnable() {
              @Override
              public void run() {
                notifyItemInserted(0);
              }
            });
      }
    }

    void unshift(T t) {
      if (list == null) list = new List<>();
      if (!list.isEmpty()) {
        List<T> list1 = new List<>();
        list1.add(t);
        list1.addAll(list);
        setList(list1);
        index();
        handler.post(
            new Runnable() {
              @Override
              public void run() {
                notifyDataSetChanged();
              }
            });
      } else {
        list.add(t);
        t.index(0);
        handler.post(
            new Runnable() {
              @Override
              public void run() {
                notifyItemInserted(0);
              }
            });
      }
    }

    void setList(List<T> list) {
      PromiseAdapter.this.list = list;
      index();
      handler.post(
          new Runnable() {
            @Override
            public void run() {
              notifyDataSetChanged();
            }
          });
    }

    void remove(final T t) {
      if (list == null) return;
      list.remove(t.index());
      index();
      handler.post(
          new Runnable() {
            @Override
            public void run() {
              notifyItemRemoved(t.index());
            }
          });
    }

    void update(final T t) {
      if (list == null) return;
      if (t.index() >= list.size()) return;
      list.set(t.index(), t);
      handler.post(
          new Runnable() {
            @Override
            public void run() {
              notifyDataSetChanged();
              /*notifyItemChanged(t.index());*/
            }
          });
    }

    void updateAll() {
      handler.post(
          new Runnable() {
            @Override
            public void run() {
              notifyDataSetChanged();
            }
          });
    }

    void add(List<T> list) {
      for (T t : list) add(t);
    }

    void clear() {
      if (list == null || list.isEmpty()) return;
      list.clear();
      handler.post(
          new Runnable() {
            @Override
            public void run() {
              notifyDataSetChanged();
            }
          });
    }

    int size() {
      return list == null || list.isEmpty() ? 0 : list.size();
    }

    void reverse(boolean reverse) {
      this.reverse = reverse;
    }
  }

  public class WrapContentLinearLayoutManager extends LinearLayoutManager {
    public WrapContentLinearLayoutManager(Context context) {
      super(context);
    }

    public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
      super(context, orientation, reverseLayout);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
      try {
        super.onLayoutChildren(recycler, state);
      } catch (IndexOutOfBoundsException e) {
        LogUtil.e(TAG, "meet a Bug in RecyclerView");
      }
    }
  }

  public class WrapContentGridLayoutManager extends GridLayoutManager {

    public WrapContentGridLayoutManager(Context context, int spanCount) {
      super(context, spanCount);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
      try {
        super.onLayoutChildren(recycler, state);
      } catch (IndexOutOfBoundsException e) {
        LogUtil.e(TAG, "meet a Bug in RecyclerView");
      }
    }
  }

  public class CustomItemAnimator extends DefaultItemAnimator {
    @Override
    public boolean animateChange(
        RecyclerView.ViewHolder oldHolder,
        RecyclerView.ViewHolder newHolder,
        int fromX,
        int fromY,
        int toX,
        int toY) {
      if (getSupportsChangeAnimations()) {
        return super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY);
      } else {
        if (oldHolder == newHolder) {
          if (oldHolder != null) {
            // if the two holders are equal, call dispatch change only once
            dispatchChangeFinished(oldHolder, /*ignored*/ true);
          }
        } else {
          // else call dispatch change once for every non-null holder
          if (oldHolder != null) {
            dispatchChangeFinished(oldHolder, true);
          }
          if (newHolder != null) {
            dispatchChangeFinished(newHolder, false);
          }
        }
        // we don't need a call to requestPendingTransactions after this, return false.
        return false;
      }
    }
  }
}