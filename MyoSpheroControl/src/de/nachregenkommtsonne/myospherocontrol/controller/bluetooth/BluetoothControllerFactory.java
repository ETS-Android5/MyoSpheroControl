package de.nachregenkommtsonne.myospherocontrol.controller.bluetooth;

import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroController;

public class BluetoothControllerFactory
{
  public BluetoothControllerFactory()
  {
  }

  public BluetoothController create(IServiceState serviceState, ISpheroController spheroController, IMyoController myoController)
  {
    BluetoothStateHandler bluetoothStateHandler = new BluetoothStateHandler(myoController, spheroController);
    BluetoothController bluetoothController = new BluetoothController(bluetoothStateHandler, serviceState);
    return bluetoothController;
  }
}
