package de.nachregenkommtsonne.myospherocontrol.controller.bluetooth;

import android.bluetooth.BluetoothAdapter;
import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroController;

public class BluetoothStateHandler implements IBluetoothStateHandler
{
  private IServiceState _serviceState;
  private IMyoController _myoController;
  private ISpheroController _spheroController;

  public BluetoothStateHandler(IServiceState serviceState, IMyoController myoController, ISpheroController spheroController)
  {
    _serviceState = serviceState;
    _myoController = myoController;
    _spheroController = spheroController;
  }

  public IServiceState get_serviceState()
  {
    return _serviceState;
  }

  public IMyoController get_myoController()
  {
    return _myoController;
  }

  public ISpheroController get_spheroController()
  {
    return _spheroController;
  }

  public void handleState(int bluetoothAdapterState)
  {
    switch (bluetoothAdapterState)
      {
      case BluetoothAdapter.STATE_TURNING_OFF:
        _serviceState.setBluetoothState(BluetoothState.turningOff);

        if (_serviceState.isRunning())
        {
          _myoController.stopConnecting();
          _spheroController.stopForBluetooth();
        }
        break;

      case BluetoothAdapter.STATE_OFF:
        _serviceState.setBluetoothState(BluetoothState.off);
        break;

      case BluetoothAdapter.STATE_TURNING_ON:
        _serviceState.setBluetoothState(BluetoothState.turningOn);
        break;

      case BluetoothAdapter.STATE_ON:
        _serviceState.setBluetoothState(BluetoothState.on);

        if (_serviceState.isRunning())
        {
          _myoController.startConnecting();
          _spheroController.start();
        }
        break;
      }
  }
}
