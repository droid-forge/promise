/*
 *
 *  *
 *  *  * Copyright 2017, Solutech Limited
 *  *  * Licensed under the Apache License, Version 2.0, Solutech SAT.
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *  * Unless required by applicable law or agreed to in writing,
 *  *  * software distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *
 *
 */

package me.dev4vin.data.socket;

import android.content.Intent;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import me.dev4vin.Promise;
import me.dev4vin.data.log.LogUtil;
import me.dev4vin.data.net.Config;
import me.dev4vin.model.List;
import me.dev4vin.model.Message;
import me.dev4vin.util.Conditions;
import me.dev4vin.util.NetworkUtil;
import me.dev4vin.view.NetworkErrorActivity;

/** */
public abstract class FastParserSocketIO {
  public static final String SENDER = "fastparsersocketio";
  private Config config;
  private Socket socket;
  private String TAG = LogUtil.makeTag(FastParserSocketIO.class);

  public FastParserSocketIO(final Config config) throws URISyntaxException {
    this.config = Conditions.checkNotNull(config);
    socket = IO.socket(config.getMainUrl());
  }

  private void listenDefaultEvents() {
    final DefaultListener listener = Conditions.checkNotNull(defaultListener());
    socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
      @Override
      public void call(Object... args) {
        listener.onConnected(args);
      }
    });
    socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
      @Override
      public void call(Object... args) {
        listener.onDisconnected(args);
      }
    });
    socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
      @Override
      public void call(Object... args) {
        listener.onConnectionError(args);
      }
    });
    socket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
      @Override
      public void call(Object... args) {
        listener.onConnectionTimeOut(args);
      }
    });
  }

  public String listen(String... events) {
    listenDefaultEvents();
    for (final String event : events)
      socket.on(event, new Emitter.Listener() {
        @Override
        public void call(Object... args) {
          Promise.instance().send(new Message(SENDER, new SocketInterface()
              .event(event)
              .args(List.fromArray(args))
              .fastParserSocketIO(FastParserSocketIO.this)));
        }
      });
    socket.connect();
    return socket.id();
  }

  public void send(String event, Object... args) {
    socket.emit(event, args);
  }

  public void disconnect(String... events) {
    disconnect();
    for (String event : events) socket.off(event);
  }

  public void disconnect() {
    socket.disconnect();
  }

  public abstract DefaultListener defaultListener();

  public static FastParserSocketIO with(Config config, final DefaultListener listener, String... events) throws URISyntaxException {
    FastParserSocketIO fastParserSocketIO = new FastParserSocketIO(config) {
      @Override
      public DefaultListener defaultListener() {
        return listener;
      }
    };
    fastParserSocketIO.listen(events);
    return fastParserSocketIO;
  }

  public interface DefaultListener {
    void onConnected(Object... args);

    void onDisconnected(Object... args);

    void onConnectionError(Object... args);

    void onConnectionTimeOut(Object... args);
  }


  private boolean checkNetwork() {
    int status = NetworkUtil.getConnectivityStatus(config.getContext());
    return status != NetworkUtil.TYPE_NOT_CONNECTED;
  }

  private void startErrorActivity(String reason) {
    Intent intent = new Intent(config.getContext(), NetworkErrorActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.putExtra(NetworkErrorActivity.REASON, reason);
    config.getContext().startActivity(intent);
  }
}
