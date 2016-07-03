package de.nachregenkommtsonne.myospherocontrol.controller.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.R;
import de.nachregenkommtsonne.myospherocontrol.activity.ControlActivity;
import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;

public class NotificationUpdater implements INotificationUpdater
{
  private static final int NOTIFICATION_ID = 0;
  
  private Context _context;
  private IServiceState _state;
  private GuiStateHinter _guiStateHinter;

  public NotificationUpdater(Context context, IServiceState state, GuiStateHinter guiStateHinter)
  {
    _context = context;
    _state = state;
    _guiStateHinter = guiStateHinter;
  }

  public void updateNotification()
  {
    if (_state.isRunning())
    {
      Intent intent = new Intent(_context, ControlActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

      PendingIntent pIntent = PendingIntent.getActivity(_context, 0, intent, 0);

      Notification notification = new Notification.Builder(_context)
          .setContentTitle(_context.getString(R.string.app_name))
          .setContentText(_context.getString(_guiStateHinter.getHint(_state)))
          .setSmallIcon(getNotificationIcon())

      .setContentIntent(pIntent)
          .setAutoCancel(false)
          .setWhen(0)
          .setOngoing(true)
          .build();

      NotificationManager notificationManager = (NotificationManager) _context
          .getSystemService(Context.NOTIFICATION_SERVICE);

      notificationManager.notify(NOTIFICATION_ID, notification);
    } else
    {
      NotificationManager notificationManager = (NotificationManager) _context
          .getSystemService(Context.NOTIFICATION_SERVICE);

      notificationManager.cancel(NOTIFICATION_ID);
    }
  }

  private int getNotificationIcon()
  {
    boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
    return useWhiteIcon ? R.drawable.icon_silhouette : R.drawable.ic_launcher;
  }
}
