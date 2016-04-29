package de.nachregenkommtsonne.myospherocontrol.controller;

import de.nachregenkommtsonne.myospherocontrol.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroController;

public class ControllerStarter implements Runnable
{
  private IMyoController _myoController;
  private ISpheroController _spheroController;

  public ControllerStarter(IMyoController myoController, ISpheroController spheroController)
  {
    _myoController = myoController;
    _spheroController = spheroController;
  }

  public void run()
  {
    _myoController.startConnecting();
    _spheroController.start();
  }
}