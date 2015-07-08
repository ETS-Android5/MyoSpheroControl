package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ServiceBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ServiceBinderFactory;
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
	private ServiceState _serviceState;
  private NotifyingServiceState _notifyingServiceState;
  private ServiceBinder _serviceBinder;
  private SpheroController _spheroController;
  private MyoController _myoController;
  private BluetoothController _bluetoothController;
  
  public BackgroundService()
  {
    super();

    _serviceState = new ServiceState();
    _notifyingServiceState = new NotifyingServiceState(_serviceState);
    
    SpheroControllerFactory spheroControllerFactory = new SpheroControllerFactory();
    MyoControllerFactory myoControllerFactory = new MyoControllerFactory();
    BluetoothControllerFactory bluetoothControllerFactory = new BluetoothControllerFactory();
    ServiceBinderFactory serviceBinderFactory = new ServiceBinderFactory();

    _spheroController = spheroControllerFactory.create(this, _notifyingServiceState);
    _myoController = myoControllerFactory.create(this, _notifyingServiceState, _spheroController);
    _bluetoothController = bluetoothControllerFactory.create(_notifyingServiceState, _spheroController, _myoController);
    
    _serviceBinder = serviceBinderFactory.create(_serviceState, _spheroController, _myoController);

    GuiStateHinter guiStateHinter = new GuiStateHinter();
    INotificationUpdater notificationUpdater = new NotificationUpdater(this, _notifyingServiceState, guiStateHinter);
    ChangedNotifier changedNotifier = new ChangedNotifier(notificationUpdater, _serviceBinder);

    _notifyingServiceState.setChangedNotifier(changedNotifier);
  }

  public void onCreate()
  {
    super.onCreate();

    _spheroController.onCreate();
    _myoController.onCreate();
    _serviceState.onCreate();

    IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
    this.registerReceiver(_bluetoothController, filter);
  }

  public int onStartCommand(Intent intent, int flags, int startId)
  {
    return START_STICKY;
  }

  public IBinder onBind(Intent intent)
  {
    return _serviceBinder;
  }

  public void onTaskRemoved(Intent rootIntent)
  {
    super.onTaskRemoved(rootIntent);

    _notifyingServiceState.setRunning(false);
  }
}