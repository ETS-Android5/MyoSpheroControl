package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.bluetooth.BluetoothStatus;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroController;

//TODO: move away
public class ServiceControllerStarter implements Runnable
{
  private IServiceState _serviceState;
  private IMyoController _myoController;
  private ISpheroController _spheroController;

  public ServiceControllerStarter(
      IServiceState serviceState,
      IMyoController myoController,
      ISpheroController spheroController)
  {
    _serviceState = serviceState;
    _myoController = myoController;
    _spheroController = spheroController;
  }

  public void run()
  {
    try
    {
      Thread.sleep(1400);
    }
    catch (InterruptedException e)
    {
    }

    _serviceState.setBluetoothState(BluetoothStatus.on);

    if (_serviceState.isRunning())
    {
      _myoController.startConnecting();
      _spheroController.start();
    }
  }
}