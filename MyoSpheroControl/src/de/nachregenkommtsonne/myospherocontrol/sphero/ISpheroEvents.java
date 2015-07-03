package de.nachregenkommtsonne.myospherocontrol.sphero;

public interface ISpheroEvents
{
  void bluetoothDisabled();

  void spheroStateChanged(SpheroStatus spheroStatus);
}
