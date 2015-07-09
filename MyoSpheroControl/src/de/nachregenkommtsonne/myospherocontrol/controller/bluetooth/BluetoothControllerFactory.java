package de.nachregenkommtsonne.myospherocontrol.controller.bluetooth;

import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroStartStopController;

public class BluetoothControllerFactory
{
  public BluetoothControllerFactory()
  {
  }

  public BluetoothController create(
      IServiceState serviceState,
      ISpheroStartStopController spheroController,
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