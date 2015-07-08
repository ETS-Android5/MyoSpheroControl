package de.nachregenkommtsonne.myospherocontrol.controller.bluetooth;

import de.nachregenkommtsonne.myospherocontrol.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroController;

public class BluetoothControllerFactory
{
  public BluetoothControllerFactory()
  {
  }

  public BluetoothController create(
      ServiceState serviceState,
      ISpheroController spheroController,
      IMyoController myoController)
  {
    BluetoothStateHandler bluetoothStateHandler = new BluetoothStateHandler(
        serviceState,
        myoController,
        spheroController);
    BluetoothController bluetoothController = new BluetoothController(bluetoothStateHandler);

    return bluetoothController;
  }
}