package de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceState;

public class SpheroEventHandler implements ISpheroEvents
{
  private ChangedNotifier _changedNotifier;
  private ISpheroController _spheroController;
  private ServiceState _state;
  
  public SpheroEventHandler(ChangedNotifier changedNotifier, ISpheroController spheroController, ServiceState state)
  {
    _changedNotifier = changedNotifier;
    _spheroController = spheroController;
    _state = state;
  }

  public void spheroStateChanged(SpheroStatus spheroStatus)
  {
    if (spheroStatus == SpheroStatus.connected)
    {
      _spheroController.changeColor(0, 0, 255);
    }

    _state.setSpheroStatus(spheroStatus);
    _changedNotifier.onChanged();
  }

  public void bluetoothDisabled()
  {
  }
}