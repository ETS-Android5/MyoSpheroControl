package de.nachregenkommtsonne.myospherocontrol.controller.bluetooth;

import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroController;

public class BluetoothStateReceiverFactory
{
  public BluetoothStateReceiver create(IServiceState serviceState, ISpheroController spheroController, IMyoController myoController)
  {
    IBluetoothStateHandler bluetoothStateHandler = new BluetoothStateHandler(serviceState, myoController, spheroController);

    return new BluetoothStateReceiver(bluetoothStateHandler);
  }
}
