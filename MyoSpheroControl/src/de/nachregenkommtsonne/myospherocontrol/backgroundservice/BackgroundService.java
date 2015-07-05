package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceControllerFactory;

public class BackgroundService extends Service
{
  private ServiceControllerFactory _serviceControllerFactory;
  private ServiceController _serviceController;

  public BackgroundService()
  {
    super();

    _serviceControllerFactory = new ServiceControllerFactory(this);
 
    GuiStateHinter guiStateHinter = new GuiStateHinter();
    ServiceState serviceState = new ServiceState(guiStateHinter);
    INotificationUpdater notificationUpdater = new NotificationUpdater(this, serviceState);
    IChangedNotifier changedNotifier = new ChangedNotifier(notificationUpdater);

    _serviceController = _serviceControllerFactory.createServiceController(serviceState, changedNotifier);
  }
  
  public void onCreate()
  {
    super.onCreate();

    _serviceController.onCreate(this);
  }

  public int onStartCommand(Intent intent, int flags, int startId)
  {
    return START_STICKY;
  }

  public IBinder onBind(Intent intent)
  {
    return _serviceController.get_binder();
  }

  public void onTaskRemoved(Intent rootIntent)
  {
    super.onTaskRemoved(rootIntent);

    _serviceController.serviceStopped();
  }
}
