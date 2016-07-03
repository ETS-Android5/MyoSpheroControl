package de.nachregenkommtsonne.myospherocontrolC.controller.bluetooth;

import de.nachregenkommtsonne.myospherocontrolC.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrolC.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrolC.controller.sphero.ISpheroController;

public class BluetoothStateReceiverFactory
{
  public BluetoothStateReceiver create(IServiceState serviceState, ISpheroController spheroController, IMyoController myoController)
  {
    IBluetoothStateHandler bluetoothStateHandler = new BluetoothStateHandler(serviceState, myoController, spheroController);

    return new BluetoothStateReceiver(bluetoothStateHandler);
  }
}
