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

package me.yoctopus.view;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import me.yoctopus.R;
import me.yoctopus.util.NetworkUtil;

public class NetworkErrorActivity extends AppCompatActivity {

  public static final String NETWORK_ERROR = "network_error";
  public static final String SERVER_ERROR = "server_error";
  public static final String CONNECTION_TIMEOUT = "connection_timeout";
  public static String REASON = "reason";
  private static Action action;
  protected ImageView errorIcon;
  protected TextView statusTextView;
  private Button actionButton;
  private boolean isLaunchingNetwork = false;
  private View.OnClickListener
      retry =
          new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if (action != null) {
                action.onAction();
                finish();
              }
            }
          },
      turnOnData =
          new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              isLaunchingNetwork = true;
              startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
            }
          };

  public static void bind(Action action) {
    NetworkErrorActivity.action = action;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    super.setContentView(R.layout.activity_network_error);
    initView();
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (isLaunchingNetwork) {
      int status = NetworkUtil.getConnectivityStatus(this);
      if (status != NetworkUtil.TYPE_NOT_CONNECTED) {
        if (action != null) {
          action.onAction();
          finish();
        }
      } else statusTextView.setText("Network connection is still closed");
    } else {
      Intent intent = getIntent();
      if (intent.hasExtra(REASON))
        switch (intent.getStringExtra(REASON)) {
          case CONNECTION_TIMEOUT:
            {
              statusTextView.setText("Your connection has timed out");
              actionButton.setText("Retry");
              actionButton.setOnClickListener(retry);
              break;
            }
          case NETWORK_ERROR:
            {
              statusTextView.setText("You do not have an active network connection");
              actionButton.setOnClickListener(turnOnData);
              actionButton.setText("Turn on and Retry");
              break;
            }
          case SERVER_ERROR:
            {
              statusTextView.setText("An error occurred, That's all we know");
              actionButton.setOnClickListener(
                  new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      finish();
                    }
                  });
              errorIcon.setImageResource(R.drawable.server_error);
              actionButton.setText("Try some other time");
            }
        }
    }
  }

  private void initView() {
    actionButton = findViewById(R.id.action_button);
    statusTextView = findViewById(R.id.retry_textView);
    errorIcon = findViewById(R.id.error_icon);
  }

  public interface Action {
    void onAction();
  }
}
