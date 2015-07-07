package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceControllerFactory;

public class BackgroundService extends Service
{
  private ServiceController _serviceController;
  
  public BackgroundService()
  {
    super();

    GuiStateHinter guiStateHinter = new GuiStateHinter();
    ServiceState serviceState = new ServiceState(guiStateHinter);

    _serviceController = new ServiceControllerFactory().create(this, serviceState);
  }
  
  public void onCreate()
  {
    super.onCreate();

    _serviceController.start();
  }

  public int onStartCommand(Intent intent, int flags, int startId)
  {
    return START_STICKY;
  }

  public IBinder onBind(Intent intent)
  {
    return _serviceController.get_serviceBinder();
  }

  public void onTaskRemoved(Intent rootIntent)
  {
    super.onTaskRemoved(rootIntent);

    _serviceController.stop();
  }
}