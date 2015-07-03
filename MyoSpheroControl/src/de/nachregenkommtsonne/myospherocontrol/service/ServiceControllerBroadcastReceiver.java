package de.nachregenkommtsonne.myospherocontrol.service;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

final class ServiceControllerBroadcastReceiver extends BroadcastReceiver
{
  final ServiceController serviceController;

  ServiceControllerBroadcastReceiver(ServiceController serviceController)
  {
    this.serviceController = serviceController;
  }

  public void onReceive(Context context, Intent intent)
  {
    final String action = intent.getAction();

    if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED))
    {
      final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
      switch (state)
      {
      case BluetoothAdapter.STATE_OFF:
        this.serviceController._state.setBluetoothState(BluetoothState.off);
        break;

      case BluetoothAdapter.STATE_TURNING_OFF:
        this.serviceController._state.setControlMode(false);
        this.serviceController._state.setBluetoothState(BluetoothState.turningOff);

        if (this.serviceController._state.isRunning())
        {
          this.serviceController._myoController.stopConnecting();
          this.serviceController._spheroController.stopForBluetooth();
        }

        break;

      case BluetoothAdapter.STATE_ON:
        new Handler().post(new ServiceControllerStarter(this));
        break;

      case BluetoothAdapter.STATE_TURNING_ON:
        this.serviceController._state.setBluetoothState(BluetoothState.turningOn);
        break;
      }
      this.serviceController.onChanged();
    }
  }
}