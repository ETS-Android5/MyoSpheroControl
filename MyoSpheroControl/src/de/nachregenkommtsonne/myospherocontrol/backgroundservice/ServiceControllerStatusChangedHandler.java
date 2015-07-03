package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import de.nachregenkommtsonne.myospherocontrol.R;
import de.nachregenkommtsonne.myospherocontrol.activity.ControlActivity;

public class ServiceControllerStatusChangedHandler implements IServiceControllerStatusChangedHandler
{
  private ServiceBinder _binder;
  private Context _context;
  private ServiceState _state;
  
  public ServiceControllerStatusChangedHandler(ServiceBinder binder, Context context, ServiceState state)
  {
    _binder = binder;
    _context = context;
    _state = state;
  }

  @Override
  public void onChanged()
  {
    _binder.onChanged();

    updateNotification();
  }
  
  @Override
  public void updateNotification()
  {
    if (_state.isRunning())
    {

      Intent intent = new Intent(_context, ControlActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      PendingIntent pIntent = PendingIntent.getActivity(_context, 0, intent, 0);

      Notification n = new Notification.Builder(_context).setContentTitle(_context.getString(R.string.app_name))
          .setContentText(_context.getString(_state.getHint())).setSmallIcon(R.drawable.ic_launcher)
          .setContentIntent(pIntent).setAutoCancel(false).setWhen(0).setOngoing(true).build();

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
