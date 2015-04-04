package seven.more.queuesleuth.core;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import seven.more.queuesleuth.InfoActivity;
import seven.more.queuesleuth.R;

public class NotificationBuilder {

    public static void createAndShow(Context context, Department.Result.Group group) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setTicker(group.getCurrentNumber())
                        .setAutoCancel(true)
                        .setContentTitle("Aktualny numerek: " + group.getCurrentNumber());
        Intent resultIntent = new Intent(context, InfoActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }
}
