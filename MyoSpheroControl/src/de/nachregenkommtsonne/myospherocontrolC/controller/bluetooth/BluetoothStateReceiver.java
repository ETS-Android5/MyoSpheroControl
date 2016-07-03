package de.nachregenkommtsonne.myospherocontrolC.controller.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BluetoothStateReceiver extends BroadcastReceiver
{
  private IBluetoothStateHandler _bluetoothStateHandler;

  public BluetoothStateReceiver(IBluetoothStateHandler bluetoothStateHandler)
  {
    _bluetoothStateHandler = bluetoothStateHandler;
  }
  
  public IBluetoothStateHandler get_bluetoothStateHandler()
  {
    return _bluetoothStateHandler;
  }

  public void onReceive(Context context, Intent intent)
  {
    String action = intent.getAction();

    if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED))
    {
      int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
      _bluetoothStateHandler.handleState(state);
    }
  }
}
