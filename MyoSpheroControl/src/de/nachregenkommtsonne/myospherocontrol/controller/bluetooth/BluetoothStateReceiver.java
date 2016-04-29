package de.nachregenkommtsonne.myospherocontrol.controller.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;

public class BluetoothStateReceiver extends BroadcastReceiver
{
  private IBluetoothStateHandler _bluetoothStateHandler;
  private IServiceState _serviceState;

  public BluetoothStateReceiver(IBluetoothStateHandler bluetoothStateHandler, IServiceState serviceState)
  {
    _bluetoothStateHandler = bluetoothStateHandler;
    _serviceState = serviceState;
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
          _serviceState.setBluetoothState(BluetoothState.turningOff);

          if (_serviceState.isRunning())
            _bluetoothStateHandler.stop();
          break;

        case BluetoothAdapter.STATE_ON:
          _serviceState.setBluetoothState(BluetoothState.on);

          if (_serviceState.isRunning())
            _bluetoothStateHandler.start();
          break;

        case BluetoothAdapter.STATE_TURNING_ON:
          _serviceState.setBluetoothState(BluetoothState.turningOn);
          break;
        }
    }
  }
}
