package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoEventHandler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroController;
import de.nachregenkommtsonne.myospherocontrol.movement.MovementCalculator;

public class BackgroundService extends Service
{
  private ServiceController _serviceController;
  private ServiceBinder _binder;
  private IServiceControllerStatusChangedHandler _serviceControllerStatusChangedHandler;
  private ServiceState _serviceState;

  public BackgroundService()
  {
    super();
  }

  public ServiceController get_serviceController()
  {
    return _serviceController;
  }

  public void onCreate()
  {
    super.onCreate();

    _serviceState = new ServiceState();

    ChangedNotifier changedNotifier = new ChangedNotifier(_serviceControllerStatusChangedHandler, _binder);

    SpheroController spheroController = new SpheroController(this);
    MyoEventHandler myoEventHandler = new MyoEventHandler(_serviceState, new MovementCalculator(), spheroController,
        changedNotifier);

    MyoController myoController = new MyoController(this, myoEventHandler);

    _serviceControllerStatusChangedHandler = new ServiceControllerNotificationUpdater(this, _serviceState);

    _serviceController = new ServiceController(myoController, spheroController, this,
        changedNotifier, _serviceState);

    _binder = new ServiceBinder(_serviceController);
  }

  public int onStartCommand(Intent intent, int flags, int startId)
  {
    return START_STICKY;
  }

  public IBinder onBind(Intent intent)
  {
    return _binder;
  }

  public void onDestroy()
  {
    super.onDestroy();
  }

  public void onTaskRemoved(Intent rootIntent)
  {
    super.onTaskRemoved(rootIntent);
    _serviceState.setRunning(false);
    _serviceControllerStatusChangedHandler.updateNotification();
  }
}
