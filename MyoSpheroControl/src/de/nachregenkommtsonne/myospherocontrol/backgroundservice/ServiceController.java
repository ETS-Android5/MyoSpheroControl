package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.IntentFilter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroEventHandler;

public class ServiceController implements IServiceController
{
  private IMyoController _myoController;
  private ISpheroController _spheroController;
  private ServiceState _state;
  private Context _context;
  private ChangedNotifier _changedNotifier;
  private IServiceControllerStatusChangedHandler _serviceControllerStatusChangedHandler;
  private ServiceBinder _binder;

  public ServiceController(
      IMyoController myoController,
      ISpheroController spheroController,
      Context context,
      ChangedNotifier changedNotifier,
      ServiceState serviceState,
      IServiceControllerStatusChangedHandler serviceControllerStatusChangedHandler,
      SpheroEventHandler eventListener,
      ServiceControllerBroadcastReceiver serviceControllerBroadcastReceiver)
  {
    _context = context;
    _myoController = myoController;
    _spheroController = spheroController;
    _changedNotifier = changedNotifier;

    _state = serviceState;
    _serviceControllerStatusChangedHandler = serviceControllerStatusChangedHandler;

    _spheroController.setEventListener(eventListener);

    IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
    
    _context.registerReceiver(serviceControllerBroadcastReceiver, filter);

    _myoController.updateDisabledState();

    _binder = new ServiceBinder(this, serviceState);
    _changedNotifier.setServiceBinder(_binder);
  }

  public ServiceBinder get_binder()
  {
    return _binder;
  }

  public ServiceState get_state()
  {
    return _state;
  }

  public void buttonClicked()
  {
    if (_state.isRunning())
    {
      if (_state.getBluetoothState() == BluetoothState.on)
      {
        _spheroController.stop();
      }
      _myoController.stop();
    }
    else
    {
      _myoController.start();
      if (_state.getBluetoothState() == BluetoothState.on)
      {
        _myoController.startConnecting();
        _spheroController.start();
      }
    }

    _state.setRunning(!_state.isRunning());
    _changedNotifier.onChanged();
  }

  public void unlinkClicked()
  {
    _myoController.connectAndUnlinkButtonClicked();
  }

  public void serviceStopped()
  {
    _state.setRunning(false);
    _serviceControllerStatusChangedHandler.updateNotification();
  }
}
