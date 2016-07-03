package de.nachregenkommtsonne.myospherocontrolC.controller.myo;

public interface IMyoController
{
  void start();

  void stop();

  public abstract void stopConnecting();

  public abstract void startConnecting();

  public abstract void updateDisabledState();

  void connectAndUnlinkButtonClicked();

  void onCreate();
}
