package de.nachregenkommtsonne.myospherocontrol.sphero;

import orbotix.robot.base.Robot;
import orbotix.sphero.ConnectionListener;

final class SpheroConnectionListener implements ConnectionListener
{
  private final SpheroController spheroController;

  SpheroConnectionListener(SpheroController spheroController)
  {
    this.spheroController = spheroController;
  }

  public void onDisconnected(Robot arg0)
  {
    this.spheroController._connected = false;
    if (!this.spheroController._running)
      return;

    if (this.spheroController._sphero == null)
      this.spheroController.startDiscovery();
    else
    {
      this.spheroController.startConnecting();
    }
  }

  public void onConnectionFailed(Robot sphero)
  {
    this.spheroController._connected = false;
    if (!this.spheroController._running)
      return;

    if (this.spheroController._sphero == null)
      this.spheroController.startDiscovery();
    else
    {
      this.spheroController.startConnecting();
    }
  }

  public void onConnected(Robot arg0)
  {
    this.spheroController.onSpheroStateChanged(SpheroStatus.connected);
    this.spheroController._connected = true;
  }
}