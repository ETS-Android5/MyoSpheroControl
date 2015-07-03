package de.nachregenkommtsonne.myospherocontrol;

import orbotix.robot.base.Robot;
import orbotix.sphero.ConnectionListener;

public class SpheroConnectionListener implements ConnectionListener
{
  private SpheroController _spheroController;

  public SpheroConnectionListener(SpheroController spheroController)
  {
    _spheroController = spheroController;
  }

  public void onDisconnected(Robot arg0)
  {
    _spheroController.set_connected(false);
    if (!_spheroController.is_running())
      return;

    if (_spheroController.get_sphero() == null)
      _spheroController.startDiscovery();
    else
    {
      _spheroController.startConnecting();
    }
  }

  public void onConnectionFailed(Robot sphero)
  {
    _spheroController.set_connected(false);
    if (!_spheroController.is_running())
      return;

    if (_spheroController.get_sphero() == null)
      _spheroController.startDiscovery();
    else
    {
      _spheroController.startConnecting();
    }
  }

  public void onConnected(Robot arg0)
  {
    _spheroController.onSpheroStateChanged(SpheroStatus.connected);
    _spheroController.set_connected(true);
  }
}