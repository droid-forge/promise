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

import java.util.LinkedList;


public class TxManager {
    private static LinkedList<Tx> queue;
    private boolean running = false;
    private static TxManager instance;

    public TxManager() {
        if (queue == null) queue = new LinkedList<>();
    }

    public static TxManager getInstance() {
        if (instance == null) instance = new TxManager();
        return instance;
    }

    public void execute(Tx tx) throws NoCallBacksError {
        if (queue == null) return;
        if (tx.complete.isEmpty()) throw new NoCallBacksError("No complete callbacks found");
        queue.add(tx);
        if (running) return;
        cycle();
    }
    private void cycle() {
        if (queue.isEmpty()) {
            running = false;
            return;
        }
        running = true;
        queue.peekFirst().complete(new Tx.Complete() {
            @Override
            public void onComplete( Object o) {
                queue.removeFirst();
                if (!queue.isEmpty()) cycle();
            }
        });
        queue.peekFirst().execute();
    }
}
