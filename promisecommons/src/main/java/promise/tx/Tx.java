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

package promise.tx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public abstract class Tx<T, X> implements Future {
    private T[] params;
    private Task task;
    private long millis = 0;
    private final ThreadLocal<Runnable> thread =
            new ThreadLocal<Runnable>() {
                @Override
                protected Runnable initialValue() {
                    return () -> {
                        if (task != null) return;
                        task = new Task();
                        if (params != null) task.execute(params);
                        else task.execute((T[]) null);
                    };
                }
            };
    private boolean completed = false;
    private CallBack<T, X> callBack;
    private Progress<T, X> progress;
    List<Complete<T>> complete;

    public Tx() {
        callBack = getCallBack();
        progress = getProgress();
        complete = new ArrayList<>();
    }

    public void execute() {
        execute(0);
    }

    public void execute(long millis) {
        try {
            checkCallBacks();
            this.millis = millis;
            thread.get().run();
        } catch (NoCallBacksError error) {
            error.show();
        }
    }

    @Override
    public void cancel() {
        if (task != null) {
            task.cancel(true);
            task = null;
        }
    }

    @Override
    public boolean isCancelled() {
        return task == null;
    }

    @Override
    public boolean isDone() {
        return completed;
    }

    public void setParams(T[] params) {
        this.params = params;
    }

    public abstract Progress<T, X> getProgress();

    private void checkCallBacks() throws NoCallBacksError {
        if (callBack == null) {
            throw new NoCallBacksError();
        }
    }

    private void finalize(T t) {
        if (complete != null && !complete.isEmpty())
            for (Complete<T> complete1 : complete) complete1.onComplete(t);
    }

    public abstract CallBack<T, X> getCallBack();


    public Tx complete(Complete<T> complete) {
        if (this.complete == null) this.complete = new ArrayList<>();
        this.complete.add(complete);
        return this;
    }

    public interface CallBack<T, X> {
        /**
         * callbacks to initialize this task
         */
        void onStart();

        /**
         * @return the result of the task
         */
        T onExecute();

        /**
         * @param x show progress while the task is executing
         */
        void onProgress(X... x);

        /**
         * @param t denote the end of this task
         */
        void onEnd(T t);
    }

    public interface Complete<T> {
        void onComplete(T t);
    }

    public interface Progress<T, X> {
        X[] onProgress(T... t);
    }

    public interface Builder<B> {
        Context getContext();

        int getId();

        B get();
    }

    @SuppressLint("StaticFieldLeak")
    protected class Task extends AsyncTask<T, X, T> {
        @SafeVarargs
        @Override
        protected final T doInBackground(T... params) {
            if (millis > 0) try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (progress != null && params != null) publishProgress(progress.onProgress(params));
            T t = callBack.onExecute();
            completed = true;
            return t;
        }

        @Override
        protected void onPreExecute() {
            callBack.onStart();
        }

        @SafeVarargs
        @Override
        protected final void onProgressUpdate(X... values) {
            super.onProgressUpdate(values);
            callBack.onProgress(values);
        }

        @Override
        protected void onPostExecute(T t) {
            super.onPostExecute(t);
            callBack.onEnd(t);
            Tx.this.finalize(t);
        }
    }
}
