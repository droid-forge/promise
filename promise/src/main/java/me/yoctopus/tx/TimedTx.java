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

package me.yoctopus.tx;

import android.content.Intent;
import android.os.Handler;

import me.yoctopus.data.net.Config;
import me.yoctopus.tx.Tx;
import me.yoctopus.util.Conditions;
import me.yoctopus.view.NetworkErrorActivity;

/**
 * Created on 1/25/18 by yoctopus.
 */
public abstract class TimedTx<T, X> extends Tx<T, X> {

    private long  millis;
    private Cancel cancel;

    public TimedTx(long millis, Cancel cancel) {
        this.millis = millis;
        this.cancel = Conditions.checkNotNull(cancel);
    }

    @Override
    public void execute() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isDone()) {
                    cancel();
                    if (isCancelled()) {
                        cancel.onCancelled();
                    }
                }
            }
        }, millis);
        super.execute();
    }
    public interface Cancel {
        void onCancelled();
    }
}
