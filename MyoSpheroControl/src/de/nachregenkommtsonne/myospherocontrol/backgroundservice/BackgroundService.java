package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ServiceBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ServiceBinderFactory;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.bluetooth.BluetoothController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.bluetooth.BluetoothControllerFactory;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoControllerFactory;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.notification.INotificationUpdater;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.notification.NotificationUpdater;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroControllerFactory;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceController;

public class BackgroundService extends Service
{
  private ServiceController _serviceController;

  public BackgroundService()
  {
    super();

    GuiStateHinter guiStateHinter = new GuiStateHinter();
    ServiceState serviceState = new ServiceState(guiStateHinter);

    SpheroControllerFactory spheroControllerFactory = new SpheroControllerFactory();
    MyoControllerFactory myoControllerFactory = new MyoControllerFactory();
    BluetoothControllerFactory bluetoothControllerFactory = new BluetoothControllerFactory();
    ServiceBinderFactory serviceBinderFactory = new ServiceBinderFactory();

    SpheroController spheroController = spheroControllerFactory.create(this, serviceState);
    MyoController myoController = myoControllerFactory.create(this, serviceState, spheroController);
    BluetoothController bluetoothController = bluetoothControllerFactory
        .create(serviceState, spheroController, myoController);
    ServiceBinder serviceBinder = serviceBinderFactory.create(serviceState, spheroController, myoController);

    ServiceController serviceController = new ServiceController(
        this,
        serviceState,
        spheroController,
        myoController,
        bluetoothController,
        serviceBinder);

    INotificationUpdater notificationUpdater = new NotificationUpdater(this, serviceState);
    ChangedNotifier changedNotifier = new ChangedNotifier(notificationUpdater, serviceBinder);

    spheroController.setChangedNotifier(changedNotifier);
    myoController.setChangedNotifier(changedNotifier);
    bluetoothController.setChangedNotifier(changedNotifier);

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