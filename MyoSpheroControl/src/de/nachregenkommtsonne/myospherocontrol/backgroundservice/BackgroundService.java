package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceControllerFactory;

public class BackgroundService extends Service
{
  private ServiceController _serviceController;

  public void onCreate()
  {
    super.onCreate();

    Context context = this;
    
    _serviceController = new ServiceControllerFactory().createServiceController(context);
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
