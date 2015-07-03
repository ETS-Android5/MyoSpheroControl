package de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero;

public interface ISpheroEvents
{
  void bluetoothDisabled();

  void spheroStateChanged(SpheroStatus spheroStatus);
}
