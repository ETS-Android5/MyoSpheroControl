package de.nachregenkommtsonne.myospherocontrol.service;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

public class ServiceControllerBroadcastReceiver extends BroadcastReceiver
{
  public ServiceController _serviceController;

  public ServiceControllerBroadcastReceiver(ServiceController serviceController)
  {
    _serviceController = serviceController;
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
        _serviceController._state.setBluetoothState(BluetoothState.off);
        break;

      case BluetoothAdapter.STATE_TURNING_OFF:
        _serviceController._state.setControlMode(false);
        _serviceController._state.setBluetoothState(BluetoothState.turningOff);

        if (_serviceController._state.isRunning())
        {
          _serviceController._myoController.stopConnecting();
          _serviceController._spheroController.stopForBluetooth();
        }

        break;

      case BluetoothAdapter.STATE_ON:
        new Handler().post(new ServiceControllerStarter(this));
        break;

      case BluetoothAdapter.STATE_TURNING_ON:
        _serviceController._state.setBluetoothState(BluetoothState.turningOn);
        break;
      }
      _serviceController.onChanged();
    }
  }
}