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

package me.yoctopus.data.socket;

import android.content.Intent;

import me.yoctopus.data.log.LogUtil;
import me.yoctopus.data.net.Config;
import me.yoctopus.model.ResponseCallBack;
import me.yoctopus.util.Conditions;
import me.yoctopus.util.NetworkUtil;
import me.yoctopus.view.NetworkErrorActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/** */
public abstract class FastParserWebSocket {
  public static final String SENDER = "fastParserwebsocket";
  private Config config;
  private OkHttpClient client;
  private String TAG = LogUtil.makeTag(FastParserWebSocket.class);

  public FastParserWebSocket(final Config config) {
    this.config = Conditions.checkNotNull(config);
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    client = builder.build();
  }

  public void connect(ResponseCallBack<WebSocket, Throwable> responseCallBack) {
    Request request = new Request.Builder().url(config.getMainUrl()).build();
    WebSocket ws = client.newWebSocket(request, webSocketListener());
    client.dispatcher().executorService().shutdown();
    responseCallBack.response(ws);
  }

  public abstract WebSocketListener webSocketListener();

  public static FastParserWebSocket with(Config config, final WebSocketListener listener) {
    return new FastParserWebSocket(config) {
      @Override
      public WebSocketListener webSocketListener() {
        return listener;
      }
    };
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
