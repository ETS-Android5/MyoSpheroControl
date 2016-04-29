package de.nachregenkommtsonne.myospherocontrol.controller.bluetooth;

import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroController;

public class BluetoothStateReceiverFactory
{
  public BluetoothStateReceiverFactory()
  {
  }

  public BluetoothStateReceiver create(IServiceState serviceState, ISpheroController spheroController, IMyoController myoController)
  {
    BluetoothStateHandler bluetoothStateHandler = new BluetoothStateHandler(myoController, spheroController);

    return new BluetoothStateReceiver(bluetoothStateHandler, serviceState);
  }
}
