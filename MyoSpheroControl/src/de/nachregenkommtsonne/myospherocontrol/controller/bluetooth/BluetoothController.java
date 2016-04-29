package de.nachregenkommtsonne.myospherocontrol.controller.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;

public class BluetoothController extends BroadcastReceiver
{
  private IBluetoothStateHandler _bluetoothStateHandler;
  private IServiceState _serviceState;

  public BluetoothController(IBluetoothStateHandler bluetoothStateHandler, IServiceState serviceState)
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
          _serviceState.setBluetoothState(BluetoothStatus.off);
          break;

        case BluetoothAdapter.STATE_TURNING_OFF:
          _serviceState.setBluetoothState(BluetoothStatus.turningOff);

          if (_serviceState.isRunning())
            _bluetoothStateHandler.deactivate();
          break;

        case BluetoothAdapter.STATE_ON:
          _serviceState.setBluetoothState(BluetoothStatus.on);

          if (_serviceState.isRunning())
            _bluetoothStateHandler.activate();
          break;

        case BluetoothAdapter.STATE_TURNING_ON:
          _serviceState.setBluetoothState(BluetoothStatus.turningOn);
          break;
        }
    }
  }
}
