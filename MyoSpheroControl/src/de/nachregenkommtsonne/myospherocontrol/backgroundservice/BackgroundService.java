package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ServiceBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ServiceBinderFactory;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceController;
import de.nachregenkommtsonne.myospherocontrol.controller.NotifyingServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.bluetooth.BluetoothController;
import de.nachregenkommtsonne.myospherocontrol.controller.bluetooth.BluetoothControllerFactory;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.MyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.MyoControllerFactory;
import de.nachregenkommtsonne.myospherocontrol.controller.notification.INotificationUpdater;
import de.nachregenkommtsonne.myospherocontrol.controller.notification.NotificationUpdater;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.SpheroController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.SpheroControllerFactory;

public class BackgroundService extends Service
{
  private ServiceController _serviceController;

  public BackgroundService()
  {
    super();

    ServiceState serviceState = new ServiceState();
    NotifyingServiceState notifyingServiceState = new NotifyingServiceState(serviceState);
    
    
    SpheroControllerFactory spheroControllerFactory = new SpheroControllerFactory();
    MyoControllerFactory myoControllerFactory = new MyoControllerFactory();
    BluetoothControllerFactory bluetoothControllerFactory = new BluetoothControllerFactory();
    ServiceBinderFactory serviceBinderFactory = new ServiceBinderFactory();

    SpheroController spheroController = spheroControllerFactory.create(this, notifyingServiceState);
    MyoController myoController = myoControllerFactory.create(this, notifyingServiceState, spheroController);
    BluetoothController bluetoothController = bluetoothControllerFactory.create(notifyingServiceState, spheroController, myoController);
    ServiceBinder serviceBinder = serviceBinderFactory.create(serviceState, spheroController, myoController);

    ServiceController serviceController = new ServiceController(this, serviceState, spheroController, myoController, bluetoothController, serviceBinder);

    GuiStateHinter guiStateHinter = new GuiStateHinter();
    INotificationUpdater notificationUpdater = new NotificationUpdater(this, notifyingServiceState, guiStateHinter);
    ChangedNotifier changedNotifier = new ChangedNotifier(notificationUpdater, serviceBinder);

    notifyingServiceState.setChangedNotifier(changedNotifier);
       
    _serviceController = serviceController;
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
    return _serviceController.getServiceBinder();
  }

  public void onTaskRemoved(Intent rootIntent)
  {
    super.onTaskRemoved(rootIntent);

    _serviceController.stop();
  }
}