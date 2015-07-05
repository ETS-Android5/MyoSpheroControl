package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.IChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroEvents;

public class ServiceController implements IServiceController
{
  private IMyoController _myoController;
  private ISpheroController _spheroController;
  private ServiceState _state;
  private IChangedNotifier _changedNotifier;
  private BroadcastReceiver _serviceControllerBroadcastReceiver;
  private ServiceBinder _binder;

  public ServiceController(
      IMyoController myoController,
      ISpheroController spheroController,
      IChangedNotifier changedNotifier,
      ServiceState serviceState,
      ISpheroEvents eventListener,
      BroadcastReceiver serviceControllerBroadcastReceiver,
      ServiceBinder binder)
  {
    _myoController = myoController;
    _spheroController = spheroController;
    _changedNotifier = changedNotifier;
    _state = serviceState;
    _serviceControllerBroadcastReceiver = serviceControllerBroadcastReceiver;
    _spheroController.setEventListener(eventListener);

    _binder = binder;
    _changedNotifier.setServiceBinder(_binder);
  }

  public void onCreate(Context context)
  {
    _spheroController.onCreate(context);
    _myoController.onCreate(context);
    _state.onCreate(context);
    
    IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
    context.registerReceiver(_serviceControllerBroadcastReceiver, filter);
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
    _changedNotifier.onChanged();
  }
}