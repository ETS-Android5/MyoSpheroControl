package de.nachregenkommtsonne.myospherocontrol.controller.bluetooth;

import de.nachregenkommtsonne.myospherocontrol.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroController;

public class BluetoothStateHandler implements IBluetoothStateHandler
{
  private IMyoController _myoController;
  private ISpheroController _spheroController;

  public BluetoothStateHandler(IMyoController myoController, ISpheroController spheroController)
  {
    _myoController = myoController;
    _spheroController = spheroController;
  }

  public void start()
  {
    _myoController.startConnecting();
    _spheroController.start();
  }

  public void stop()
  {
    _myoController.stopConnecting();
    _spheroController.stopForBluetooth();
  }
}
