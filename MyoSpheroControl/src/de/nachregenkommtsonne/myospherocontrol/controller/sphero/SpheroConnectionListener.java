package de.nachregenkommtsonne.myospherocontrol.controller.sphero;

import orbotix.robot.base.Robot;
import orbotix.sphero.ConnectionListener;

public class SpheroConnectionListener implements ConnectionListener
{
  private SpheroController _spheroController;
  private SpheroManager _spheroManager;

  public SpheroConnectionListener(SpheroController spheroController, SpheroManager spheroManager)
  {
    _spheroController = spheroController;
    _spheroManager = spheroManager;
  }

  public void onDisconnected(Robot sphero)
  {
  	_spheroManager.setConnected(false);
    if (!_spheroController.isRunning())
      return;

    if (_spheroManager.getSphero() == null)
      _spheroController.startDiscovery();
    else
    {
      _spheroController.startConnecting();
    }
  }

  public void onConnectionFailed(Robot sphero)
  {
  	_spheroManager.setConnected(false);
    if (!_spheroController.isRunning())
      return;

    if (_spheroManager.getSphero() == null)
      _spheroController.startDiscovery();
    else
    {
      _spheroController.startConnecting();
    }
  }

  public void onConnected(Robot arg0)
  {
    _spheroController.onSpheroStateChanged(SpheroStatus.connected);
    _spheroManager.setConnected(true);
  }
}