package de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.bluetooth;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroController;

public class BluetoothControllerFactory
{
  public BluetoothControllerFactory()
  {

  }

  public BluetoothController create(ServiceState serviceState, ISpheroController spheroController, IMyoController myoController)
  {
    BluetoothStateHandler bluetoothStateHandler = new BluetoothStateHandler(serviceState, myoController, spheroController);
    BluetoothController bluetoothController = new BluetoothController(bluetoothStateHandler);

    return bluetoothController;
  }
}
