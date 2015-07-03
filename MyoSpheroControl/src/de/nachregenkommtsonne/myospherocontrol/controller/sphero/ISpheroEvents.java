package de.nachregenkommtsonne.myospherocontrol.controller.sphero;

public interface ISpheroEvents
{
  void bluetoothDisabled();

  void spheroStateChanged(SpheroStatus spheroStatus);
}
