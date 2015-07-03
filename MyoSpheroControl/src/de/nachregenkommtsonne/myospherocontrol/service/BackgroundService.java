package de.nachregenkommtsonne.myospherocontrol.service;

import de.nachregenkommtsonne.myospherocontrol.myo.MyoController;
import de.nachregenkommtsonne.myospherocontrol.sphero.SpheroController;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class BackgroundService extends Service
{
  public ServiceController _serviceController;
  public ServiceBinder _binder;
  private IServiceControllerEvents _serviceControllerEvents;

  public BackgroundService()
  {
    super();
    
    _binder = new ServiceBinder(this);
    _serviceControllerEvents = new BackgroundServiceChangedListener(this);
  }

  public void onCreate()
  {
    super.onCreate();
    _serviceController = new ServiceController(new MyoController(this), new SpheroController(this), this);
    _serviceController.setEventListener(_serviceControllerEvents);
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
