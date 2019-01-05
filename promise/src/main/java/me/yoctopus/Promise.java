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

package me.yoctopus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import me.yoctopus.data.log.LogUtil;
import me.yoctopus.data.net.FastParser;
import me.yoctopus.model.Action;
import me.yoctopus.model.AsyncAction;
import me.yoctopus.model.List;
import me.yoctopus.model.Message;
import me.yoctopus.model.ResponseCallBack;
import me.yoctopus.model.function.MapFunction;
import me.yoctopus.util.Conditions;

/** Created on 2/13/18 by yoctopus. */
public class Promise {
  public static final String TAG = LogUtil.makeTag(Promise.class);
  private static Promise instance;
  private Context context;
  private ExecutorService executor;
  private PublishSubject<Message> bus;
  public final BroadcastReceiver networkChangeReceiver =
      new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
          if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)) {
              LogUtil.e(TAG, "network connection gone");
            instance().send(new Message(TAG, "Network shut down"));
          }
          if (intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, true)) {
              LogUtil.e(TAG, "network connection back");
            instance().send(new Message(TAG, FastParser.NETWORK_IS_BACK));
          }
        }
      };
  private CompositeDisposable disposable;
  private List<Disposable> disposables;

  private Promise(Context context) {
    this.context = context;
    disposable = new CompositeDisposable();
    context.registerReceiver(
        networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
  }

  public static Promise init(Context context) {
    if (instance != null) throw new IllegalStateException("Promise can only be instantiated once");
    instance = new Promise(context);
    return instance;
  }

  public static Promise instance() {
    if (instance == null) throw new RuntimeException("Initialize promise first");
    return instance;
  }

  public void send(Message object) {
    if (bus == null) bus = PublishSubject.create();
    bus.onNext(object);
  }

  public int listen(final String sender, final ResponseCallBack<Object, Throwable> callBack) {
    if (bus == null) bus = PublishSubject.create();
    if (disposables == null) disposables = new List<>();
    disposables.add(
        bus.subscribeOn(Schedulers.from(executor))
            .observeOn(Schedulers.from(executor))
            .subscribe(
                new Consumer<Message>() {
                  @Override
                  public void accept(Message object)
                  {
                    if (sender.equals(object.sender())) callBack.response(object);
                  }
                },
                new Consumer<Throwable>() {
                  @Override
                  public void accept(Throwable throwable) {
                    callBack.error(throwable);
                  }
                }));
    disposable.add(Conditions.checkNotNull(disposables.last()));
    return disposables.size() - 1;
  }

  public void stopListening(int id) {
    if (bus == null) return;
    if (disposables == null || disposables.isEmpty()) return;
    disposable.remove(disposables.get(id));
  }

  public Context context() {
    return context;
  }

  public void context(Context context) {
    this.context = context;
  }

  public ExecutorService executor() {
    if (executor == null) return Executors.newSingleThreadExecutor();
    return executor;
  }

  public Promise threads(int threads) {
    if (executor == null) executor = Executors.newFixedThreadPool(threads);
    return this;
  }

  public Promise disableErrors() {
    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
      @Override
      public void uncaughtException(Thread t, Throwable e) {

      }
    });
    return this;
  }

  public void execute(Runnable runnable) {
    instance().executor().execute(runnable);
  }

  public <T> void execute(
      final Action<T> action, final ResponseCallBack<T, Throwable> responseCallBack) {
    if (disposables == null) disposables = new List<>();
    disposables.add(
        Observable.fromCallable(
                new Callable<T>() {
                  @Override
                  public T call() throws Exception {
                    return action.execute();
                  }
                })
            .observeOn(Schedulers.from(instance().executor))
            .subscribeOn(Schedulers.from(instance().executor))
            .subscribe(
                new Consumer<T>() {
                  @Override
                  public void accept(T t) {
                    responseCallBack.response(t);
                  }
                },
                new Consumer<Throwable>() {
                  @Override
                  public void accept(Throwable throwable) {
                    responseCallBack.error(throwable);
                  }
                }));
    disposable.add(Conditions.checkNotNull(disposables.last()));
  }

 /* public <T> void executeAsync(
    final AsyncAction<T> action, final ResponseCallBack<T, Throwable> responseCallBack) {
    if (disposables == null) disposables = new List<>();
    disposables.add(
      Observable.fromCallable(
        new Callable<T>() {
          @Override
          public T call() throws Exception {
            return action.execute();
          }
        })
        .observeOn(Schedulers.from(instance().executor))
        .subscribeOn(Schedulers.from(instance().executor))
        .subscribe(
          new Consumer<T>() {
            @Override
            public void accept(T t) {
              responseCallBack.response(t);
            }
          },
          new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
              responseCallBack.error(throwable);
            }
          }));
    disposable.add(Conditions.checkNotNull(disposables.last()));
  }*/

  public void execute(
      final List<Action<?>> actions, final ResponseCallBack<List<?>, Throwable> responseCallBack) {
    if (disposables == null) disposables = new List<>();
    disposables.add(
        Observable.zip(
                actions.map(
                    new MapFunction<ObservableSource<?>, Action<?>>() {
                      @Override
                      public ObservableSource<?> from(final Action<?> action) {
                        return new ObservableSource<Object>() {
                          @Override
                          public void subscribe(Observer<? super Object> observer) {
                            try {
                              observer.onNext(action.execute());
                            } catch (Exception e) {
                              observer.onError(e);
                            }
                          }
                        };
                      }
                    }),
                new Function<Object[], List<Object>>() {
                  @Override
                  public List<Object> apply(Object[] objects) {
                    return List.fromArray(objects);
                  }
                })
            .observeOn(Schedulers.from(instance().executor))
            .subscribeOn(Schedulers.from(instance().executor))
            .subscribe(
                new Consumer<List<Object>>() {
                  @Override
                  public void accept(List<Object> objects) {
                    responseCallBack.response(objects);
                  }
                },
                new Consumer<Throwable>() {
                  @Override
                  public void accept(Throwable throwable) {
                    responseCallBack.error(throwable);
                  }
                }));
    disposable.add(Conditions.checkNotNull(disposables.last()));
  }

  public boolean terminate() {
    context.unregisterReceiver(networkChangeReceiver);
    context = null;
    disposable.dispose();
    disposables.clear();
    bus = null;
    executor().shutdownNow();
    return executor().isShutdown();
  }
}
