package de.nachregenkommtsonne.myospherocontrol.controller.sphero;

import orbotix.sphero.Sphero;

public class SpheroManager
{
  private Sphero _sphero;
  private boolean _connected;

  public SpheroManager()
  {
    _sphero = null;
    _connected = false;
  }

  public Sphero getSphero()
  {
    return _sphero;
  }

  public void setSphero(Sphero sphero)
  {
    _sphero = sphero;
  }

	public boolean isConnected()
	{
		return _connected;
	}

	public void setConnected(boolean connected)
	{
		_connected = connected;
	}
}