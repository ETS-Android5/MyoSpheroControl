package de.nachregenkommtsonne.myospherocontrol;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BackgroundService extends Service
{
  private ServiceController _serviceController;
  private ServiceBinder _binder;

  public BackgroundService()
  {
    super();
    
    _binder = new ServiceBinder(this);
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
    
    _serviceController = new ServiceController(new MyoController(this), new SpheroController(this), this, _binder);
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
    _serviceController.getState().setRunning(false);
    _serviceController.updateNotification();
  }
}
