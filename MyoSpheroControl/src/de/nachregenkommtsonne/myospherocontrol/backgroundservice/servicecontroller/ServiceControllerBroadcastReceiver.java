package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.IChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.BluetoothState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroController;

public class ServiceControllerBroadcastReceiver extends BroadcastReceiver
{
  private IChangedNotifier _changedNotifier;
  private ServiceState _serviceState;
  private IMyoController _myoController;
  private ISpheroController _spheroController;

  public ServiceControllerBroadcastReceiver(
      IChangedNotifier changedNotifier, ServiceState serviceState,
      IMyoController myoController, ISpheroController spheroController)
  {
    _changedNotifier = changedNotifier;
    _serviceState = serviceState;
    _myoController = myoController;
    _spheroController = spheroController;
  }

  public void onReceive(Context context, Intent intent)
  {
    String action = intent.getAction();

    if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED))
    {
      int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
      switch (state)
      {
      case BluetoothAdapter.STATE_OFF:
        _serviceState.setBluetoothState(BluetoothState.off);
        break;

      case BluetoothAdapter.STATE_TURNING_OFF:
        _serviceState.setControlMode(false);
        _serviceState.setBluetoothState(BluetoothState.turningOff);

        if (_serviceState.isRunning())
        {
          _myoController.stopConnecting();
          _spheroController.stopForBluetooth();
        }

        break;

      case BluetoothAdapter.STATE_ON:
        Handler handler = new Handler();
        handler.post(new ServiceControllerStarter(_changedNotifier, _serviceState, _myoController,
            _spheroController));
        break;

      case BluetoothAdapter.STATE_TURNING_ON:
        _serviceState.setBluetoothState(BluetoothState.turningOn);
        break;
      }
      _changedNotifier.onChanged();
    }
  }
}