package de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero;

import orbotix.sphero.Sphero;

public class SpheroManager
{
	private Sphero _sphero;

	public SpheroManager()
	{
		_sphero = null;
	}
	
	public Sphero get_sphero()
	{
		return _sphero;
	}

	public void setSphero(Sphero sphero)
	{
		_sphero = sphero;
	}

}
