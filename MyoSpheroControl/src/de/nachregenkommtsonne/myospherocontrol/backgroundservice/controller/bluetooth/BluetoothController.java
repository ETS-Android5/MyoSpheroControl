package de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.IChangedNotifier;

public class BluetoothController extends BroadcastReceiver
{
  private IBluetoothStateHandler _bluetoothStateHandler;
  private IChangedNotifier _changedNotifier;

  public BluetoothController(IBluetoothStateHandler bluetoothStateHandler)
  {
    _bluetoothStateHandler = bluetoothStateHandler;
  }

  public void setChangedNotifier(ChangedNotifier changedNotifier)
  {
    _changedNotifier = changedNotifier;

    _bluetoothStateHandler.setChangedNotifier(changedNotifier);
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
        _bluetoothStateHandler.updateBluetoothState(BluetoothStatus.off);
        break;

      case BluetoothAdapter.STATE_TURNING_OFF:
        _bluetoothStateHandler.updateBluetoothState(BluetoothStatus.turningOff);
        _bluetoothStateHandler.deactivate();
        break;

      case BluetoothAdapter.STATE_ON:
        _bluetoothStateHandler.updateBluetoothState(BluetoothStatus.on);
        _bluetoothStateHandler.activate();
        break;

      case BluetoothAdapter.STATE_TURNING_ON:
        _bluetoothStateHandler.updateBluetoothState(BluetoothStatus.turningOn);
        break;
      }
      _changedNotifier.onChanged();
    }
  }
}