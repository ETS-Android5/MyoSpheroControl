package de.nachregenkommtsonne.myospherocontrol.sphero;

import java.util.List;

import orbotix.sphero.DiscoveryListener;
import orbotix.sphero.Sphero;

public class SpheroDiscoveryListener implements DiscoveryListener
{
  private SpheroController _spheroController;

  public SpheroDiscoveryListener(SpheroController spheroController)
  {
    _spheroController = spheroController;
  }

  public void onFound(List<Sphero> spheros)
  {
    _spheroController._sphero = spheros.iterator().next();

    _spheroController.startConnecting();
  }

  public void onBluetoothDisabled()
  {
    _spheroController._connected = false;
  }

  public void discoveryComplete(List<Sphero> spheros)
  {
  }
}