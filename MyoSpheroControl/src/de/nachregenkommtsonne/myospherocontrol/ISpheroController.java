package de.nachregenkommtsonne.myospherocontrol;

public interface ISpheroController
{
  void move(float direction, float speed);

  void changeColor(int red, int green, int blue);

  void halt();

  void start();

  void stop();

  public abstract void setEventListener(ISpheroEvents eventListener);

  public abstract void stopForBluetooth();
}
