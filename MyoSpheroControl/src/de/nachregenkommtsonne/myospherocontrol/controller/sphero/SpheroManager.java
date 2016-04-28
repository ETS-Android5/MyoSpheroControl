package de.nachregenkommtsonne.myospherocontrol.controller.sphero;

import com.orbotix.ConvenienceRobot;


public class SpheroManager
{
  private ConvenienceRobot _sphero;
  private boolean _connected;

  public SpheroManager()
  {
    _sphero = null;
    _connected = false;
  }

  public ConvenienceRobot getSphero()
  {
    return _sphero;
  }

  public void setSphero(ConvenienceRobot sphero)
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