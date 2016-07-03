package de.nachregenkommtsonne.myospherocontrolC.controller.sphero;

import com.orbotix.ConvenienceRobot;

public class SpheroMovementController implements ISpheroMovementController
{
  private SpheroManager _spheroManager;

  public SpheroMovementController(SpheroManager spheroManager)
  {
    _spheroManager = spheroManager;
  }

  public void halt()
  {
    ConvenienceRobot sphero = _spheroManager.getSphero();

    if (sphero != null && sphero.isConnected())
      sphero.stop();
  }

  public void changeColor(float red, float green, float blue)
  {
    ConvenienceRobot sphero = _spheroManager.getSphero();

    if (sphero != null && sphero.isConnected())
      sphero.setLed(red, green, blue);
  }

  public void move(float direction, float speed)
  {
    ConvenienceRobot sphero = _spheroManager.getSphero();

    if (!_spheroManager.isConnected() && sphero != null && sphero.isConnected())
      _spheroManager.setConnected(true);

    if (sphero != null && sphero.isConnected())
      sphero.drive(direction, speed);
  }
}