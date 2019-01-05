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

package me.yoctopus.cac.notif;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.DrawableRes;
import androidx.core.app.NotificationCompat;

class NBar {
    private static final String NOTIFICATION_TAG = "NewMessage";
    static int id_normal;
    static void notify(final Context context,
                       String title,
                       final String message,
                       Bitmap bitmap,
                       @DrawableRes int small,
                       PendingIntent pendingIntent) {
        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(small)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setLargeIcon(bitmap)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        notify(context, builder.build());
    }
    static void notifyWithProgress(final Context context,
                                   String title,
                                   final String message,
                                   Bitmap bitmap,
                                   @DrawableRes int small,
                                   int progress) {
        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setDefaults(Notification.FLAG_ONGOING_EVENT)
                        .setSmallIcon(small)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setLargeIcon(bitmap)
                        .setProgress(100, progress, false)
                        .setAutoCancel(false);
        notify(0, context, builder.build());
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context,
                               final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG,
                    id_normal,
                    notification);
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(),
                    notification);
        }
        id_normal++;
    }
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(int id, final Context context,
                               final Notification notification) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            nm.notify(NOTIFICATION_TAG,
                    id,
                    notification);
        } else {
            nm.notify(NOTIFICATION_TAG.hashCode(),
                    notification);
        }
    }



}
