package de.nachregenkommtsonne.myospherocontrol;

public interface IMyoController
{
  void start();

  void stop();

  public abstract void setEventListener(IMyoEvents eventListener);

  public abstract void stopConnecting();

  public abstract void startConnecting();

  public abstract void updateDisabledState();

  void connectAndUnlinkButtonClicked();

  public abstract void connectViaDialog();
}
