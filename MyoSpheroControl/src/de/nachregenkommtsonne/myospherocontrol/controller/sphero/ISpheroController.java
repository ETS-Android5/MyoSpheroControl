package de.nachregenkommtsonne.myospherocontrol.controller.sphero;

public interface ISpheroController
{
  void move(float direction, float speed);

  void changeColor(int red, int green, int blue);

  void halt();

  void start();

  void stop();

  void stopForBluetooth();

  void onCreate();
}
