package de.nachregenkommtsonne.myospherocontrol.controller.bluetooth;

import android.os.Handler;
import de.nachregenkommtsonne.myospherocontrol.controller.ControllerStarter;
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

  public void activate()
  {
    //TODO: remove class ControllerStarter
    //ControllerStarter controllerStarter = new ControllerStarter(_myoController, _spheroController);

    //Handler handler = new Handler();
    //handler.post(controllerStarter);
    
    _myoController.startConnecting();
    _spheroController.start();
    
  }

  public void deactivate()
  {
    _myoController.stopConnecting();
    _spheroController.stopForBluetooth();
  }
}
