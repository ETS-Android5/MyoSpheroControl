package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import de.nachregenkommtsonne.myospherocontrol.R;
import de.nachregenkommtsonne.myospherocontrol.activity.ControlActivity;

public class ServiceControllerNotificationUpdater implements IServiceControllerStatusChangedHandler
{
  private Context _context;
  private ServiceState _state;

  public ServiceControllerNotificationUpdater(Context context, ServiceState state)
  {
    _context = context;
    _state = state;
  }

  public void updateNotification()
  {
    if (_state.isRunning())
    {

      Intent intent = new Intent(_context, ControlActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      PendingIntent pIntent = PendingIntent.getActivity(_context, 0, intent, 0);

      Notification n = new Notification.Builder(_context)
          .setContentTitle(_context.getString(R.string.app_name))
          .setContentText(_context.getString(_state.getHint()))
          .setSmallIcon(R.drawable.ic_launcher)
          .setContentIntent(pIntent)
          .setAutoCancel(false)
          .setWhen(0)
          .setOngoing(true)
          .build();

      NotificationManager notificationManager = (NotificationManager) _context
          .getSystemService(Context.NOTIFICATION_SERVICE);

      notificationManager.notify(0, n);
    }
    else
    {
      NotificationManager notificationManager = (NotificationManager) _context
          .getSystemService(Context.NOTIFICATION_SERVICE);

      notificationManager.cancel(0);
    }
  }
}
