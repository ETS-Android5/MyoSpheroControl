package de.nachregenkommtsonne.myospherocontrol.service;

import de.nachregenkommtsonne.myospherocontrol.sphero.ISpheroEvents;
import de.nachregenkommtsonne.myospherocontrol.sphero.SpheroStatus;

public class SpheroEvents implements ISpheroEvents
{
  private ServiceController _serviceController;

  public SpheroEvents(ServiceController serviceController)
  {
    _serviceController = serviceController;
  }

  public void spheroStateChanged(SpheroStatus spheroStatus)
  {
    if (spheroStatus == SpheroStatus.connected)
    {
      _serviceController._spheroController.changeColor(0, 0, 255);
    }

    _serviceController._state.setSpheroStatus(spheroStatus);
    _serviceController.onChanged();
  }

  public void bluetoothDisabled()
  {
  }
}