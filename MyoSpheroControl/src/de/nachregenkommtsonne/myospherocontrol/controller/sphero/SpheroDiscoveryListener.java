package de.nachregenkommtsonne.myospherocontrol.controller.sphero;

import java.util.List;

import orbotix.sphero.DiscoveryListener;
import orbotix.sphero.Sphero;

public class SpheroDiscoveryListener implements DiscoveryListener
{
  private SpheroController _spheroController;
  private SpheroManager _spheroManager;

  public SpheroDiscoveryListener(SpheroController spheroController, SpheroManager spheroManager)
  {
    _spheroController = spheroController;
    _spheroManager = spheroManager;
  }

  public void onFound(List<Sphero> spheros)
  {
    _spheroManager.setSphero(spheros.iterator().next());
    _spheroController.startConnecting();
  }

  public void onBluetoothDisabled()
  {
  	_spheroManager.setConnected(false);
  }

  public void discoveryComplete(List<Sphero> spheros)
  {
  }
}