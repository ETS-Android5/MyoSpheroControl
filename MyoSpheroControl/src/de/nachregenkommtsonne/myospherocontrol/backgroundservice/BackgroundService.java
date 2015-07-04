package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoEventHandler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroController;
import de.nachregenkommtsonne.myospherocontrol.movement.MovementCalculator;

public class BackgroundService extends Service
{
  private ServiceBinder _binder;
  private IServiceControllerStatusChangedHandler _serviceControllerStatusChangedHandler;
  private ServiceState _serviceState;
  private ServiceController _serviceController;
  
  public BackgroundService()
  {
    super();
  }

  public void onCreate()
  {
    super.onCreate();

    _serviceState = new ServiceState();

    Context context = this;
    
    SpheroController spheroController = new SpheroController(context);

    MovementCalculator mMovementCalculator = new MovementCalculator();

    _serviceControllerStatusChangedHandler = new ServiceControllerNotificationUpdater(context, _serviceState);
    
    ChangedNotifier changedNotifier = new ChangedNotifier(_serviceControllerStatusChangedHandler);
 
    MyoEventHandler myoEventHandler = new MyoEventHandler(_serviceState, mMovementCalculator, spheroController, changedNotifier);
    MyoController myoController = new MyoController(context, myoEventHandler);
    
    _serviceController = new ServiceController(myoController, spheroController, context, changedNotifier, _serviceState, _serviceControllerStatusChangedHandler);
    
    _binder = new ServiceBinder(_serviceController, _serviceState);
    
    changedNotifier.setServiceBinder(_binder);
  }

  public int onStartCommand(Intent intent, int flags, int startId)
  {
    return START_STICKY;
  }

  public IBinder onBind(Intent intent)
  {
    return _binder;
  }

  public void onTaskRemoved(Intent rootIntent)
  {
    super.onTaskRemoved(rootIntent);

    _serviceController.serviceStopped();
   }
}
