package de.nachregenkommtsonne.myospherocontrol.controller.sphero;

public interface ISpheroController
{
  public abstract void stopForBluetooth();

  public abstract void stop();

  public abstract void startConnecting();

  public abstract void startDiscovery();

  public abstract void start();
}
