package de.nachregenkommtsonne.myospherocontrol.controller.sphero;

import orbotix.sphero.Sphero;

public class SpheroMovementController implements ISpheroMovementController
{
  private SpheroManager _spheroManager;

  public SpheroMovementController(SpheroManager spheroManager)
	{
		_spheroManager = spheroManager;
	}

	public void halt()
	{
    Sphero sphero = _spheroManager.getSphero();

    if (sphero != null && sphero.isConnected())
      sphero.stop();
	}

	public void changeColor(int red, int green, int blue)
	{
		Sphero sphero = _spheroManager.getSphero();

    if (sphero != null && sphero.isConnected())
      sphero.setColor(red, green, blue);
	}

	public void move(float direction, float speed)
	{
    Sphero sphero = _spheroManager.getSphero();

    if (!_spheroManager.isConnected() && sphero != null && sphero.isConnected())
    	_spheroManager.setConnected(true);

    if (sphero != null && sphero.isConnected())
      sphero.drive(direction, speed);
	}
}