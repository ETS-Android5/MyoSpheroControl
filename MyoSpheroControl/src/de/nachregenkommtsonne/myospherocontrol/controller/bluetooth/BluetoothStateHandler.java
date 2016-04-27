package de.nachregenkommtsonne.myospherocontrol.controller.bluetooth;

import android.os.Handler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceControllerStarter;
import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroConnectionController;

public class BluetoothStateHandler implements IBluetoothStateHandler
{
  private IServiceState _serviceState;
  private IMyoController _myoController;
  private ISpheroConnectionController _spheroController;

  public BluetoothStateHandler(
      IServiceState serviceState,
      IMyoController myoController,
      ISpheroConnectionController spheroController)
  {
    _serviceState = serviceState;
    _myoController = myoController;
    _spheroController = spheroController;
  }

  public void activate()
  {
    // TODO: create Factory x2?
    Handler handler = new Handler();
    ServiceControllerStarter serviceControllerStarter = new ServiceControllerStarter(
        _serviceState,
        _myoController,
        _spheroController);
    handler.post(serviceControllerStarter);
  }

  public void deactivate()
  {
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