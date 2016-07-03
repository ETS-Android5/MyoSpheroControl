package de.nachregenkommtsonne.myospherocontrolC.backgroundservice;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import de.nachregenkommtsonne.myospherocontrolC.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrolC.backgroundservice.binder.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrolC.backgroundservice.binder.ServiceBinder;
import de.nachregenkommtsonne.myospherocontrolC.backgroundservice.binder.ServiceBinderFactory;
import de.nachregenkommtsonne.myospherocontrolC.controller.NotifyingServiceState;
import de.nachregenkommtsonne.myospherocontrolC.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrolC.controller.bluetooth.BluetoothStateReceiver;
import de.nachregenkommtsonne.myospherocontrolC.controller.bluetooth.BluetoothStateReceiverFactory;
import de.nachregenkommtsonne.myospherocontrolC.controller.myo.MyoController;
import de.nachregenkommtsonne.myospherocontrolC.controller.myo.MyoControllerFactory;
import de.nachregenkommtsonne.myospherocontrolC.controller.notification.INotificationUpdater;
import de.nachregenkommtsonne.myospherocontrolC.controller.notification.NotificationUpdater;
import de.nachregenkommtsonne.myospherocontrolC.controller.sphero.SpheroController;
import de.nachregenkommtsonne.myospherocontrolC.controller.sphero.SpheroManager;
import de.nachregenkommtsonne.myospherocontrolC.controller.sphero.SpheroMovementController;

public class BackgroundService extends Service
{
  private ServiceState _serviceState;
  private NotifyingServiceState _notifyingServiceState;
  private ServiceBinder _serviceBinder;
  private SpheroController _spheroController;
  private MyoController _myoController;
  private BluetoothStateReceiver _bluetoothController;

  public BackgroundService()
  {
    super();

    _serviceState = new ServiceState();
    _notifyingServiceState = new NotifyingServiceState(_serviceState);

    MyoControllerFactory myoControllerFactory = new MyoControllerFactory();
    BluetoothStateReceiverFactory bluetoothControllerFactory = new BluetoothStateReceiverFactory();
    ServiceBinderFactory serviceBinderFactory = new ServiceBinderFactory();
    SpheroManager spheroManager = new SpheroManager();
    SpheroMovementController spheroMovementController = new SpheroMovementController(spheroManager);

    _spheroController = new SpheroController(this, spheroManager, _notifyingServiceState);
    _myoController = myoControllerFactory.create(this, _notifyingServiceState, spheroMovementController);
    _bluetoothController = bluetoothControllerFactory.create(_notifyingServiceState, _spheroController, _myoController);
    _serviceBinder = serviceBinderFactory.create(_serviceState, _notifyingServiceState, _spheroController, _myoController);

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
    
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    boolean isBluetoothEnabled = bluetoothAdapter != null && bluetoothAdapter.isEnabled();
    
    _serviceState.onCreate(isBluetoothEnabled);

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