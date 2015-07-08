package de.nachregenkommtsonne.myospherocontrol.controller.bluetooth;

import android.os.Handler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.IChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceControllerStarter;
import de.nachregenkommtsonne.myospherocontrol.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroController;

public class BluetoothStateHandler implements IBluetoothStateHandler
{
  private IChangedNotifier _changedNotifier;
  private ServiceState _serviceState;
  private IMyoController _myoController;
  private ISpheroController _spheroController;

  public BluetoothStateHandler(
      ServiceState serviceState,
      IMyoController myoController,
      ISpheroController spheroController)
  {
    _serviceState = serviceState;
    _myoController = myoController;
    _spheroController = spheroController;
  }

  public void setChangedNotifier(ChangedNotifier changedNotifier)
  {
    _changedNotifier = changedNotifier;
  }

  public void activate()
  {
    // TODO: create Factory x2?
    Handler handler = new Handler();
    ServiceControllerStarter serviceControllerStarter = new ServiceControllerStarter(
        _changedNotifier,
        _serviceState,
        _myoController,
        _spheroController);
    handler.post(serviceControllerStarter);
  }

  public void deactivate()
  {
    _serviceState.setControlMode(false);

    if (_serviceState.isRunning())
    {
      _myoController.stopConnecting();
      _spheroController.stopForBluetooth();
    }
  }

  public void updateBluetoothState(BluetoothStatus bluetoothState)
  {
    _serviceState.setBluetoothState(bluetoothState);
  }
}