package de.nachregenkommtsonne.myospherocontrol;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

public class ServiceControllerBroadcastReceiver extends BroadcastReceiver
{
  private ServiceController _serviceController;
  IServiceControllerStatusChangedHandler _serviceControllerStatusChangedHandler;
  
  public ServiceControllerBroadcastReceiver(ServiceController serviceController, IServiceControllerStatusChangedHandler serviceControllerStatusChangedHandler)
  {
    _serviceController = serviceController;
    _serviceControllerStatusChangedHandler = serviceControllerStatusChangedHandler;
  }

  public ServiceController get_serviceController()
  {
    return _serviceController;
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
        _serviceController.get_state().setBluetoothState(BluetoothState.off);
        break;

      case BluetoothAdapter.STATE_TURNING_OFF:
        _serviceController.get_state().setControlMode(false);
        _serviceController.get_state().setBluetoothState(BluetoothState.turningOff);

        if (_serviceController.get_state().isRunning())
        {
          _serviceController.get_myoController().stopConnecting();
          _serviceController.get_spheroController().stopForBluetooth();
        }

        break;

      case BluetoothAdapter.STATE_ON:
        new Handler().post(new ServiceControllerStarter(this, _serviceControllerStatusChangedHandler));
        break;

      case BluetoothAdapter.STATE_TURNING_ON:
        _serviceController.get_state().setBluetoothState(BluetoothState.turningOn);
        break;
      }
      _serviceControllerStatusChangedHandler.onChanged();
    }
  }
}