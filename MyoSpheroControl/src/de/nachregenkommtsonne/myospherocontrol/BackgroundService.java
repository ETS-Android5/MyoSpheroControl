package de.nachregenkommtsonne.myospherocontrol;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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

  public ServiceBinder get_binder()
  {
    return _binder;
  }

  public ServiceController get_serviceController()
  {
    return _serviceController;
  }

  public void onCreate()
  {
    super.onCreate();
    _serviceState = new ServiceState();

    MyoController myoController = new MyoController(this);
    SpheroController spheroController = new SpheroController(this);

    _serviceControllerStatusChangedHandler = new ServiceControllerStatusChangedHandler(_binder, this, _serviceState);
    _serviceController = new ServiceController(myoController, spheroController, this, 
        _serviceControllerStatusChangedHandler, _serviceState);
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

  public interface IBinderEvents
  {
    void changed();
  }

  public void onTaskRemoved(Intent rootIntent)
  {
    super.onTaskRemoved(rootIntent);
    _serviceState.setRunning(false);
    _serviceControllerStatusChangedHandler.updateNotification();
  }
}
