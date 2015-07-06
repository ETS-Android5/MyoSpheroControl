package de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero;

import orbotix.sphero.Sphero;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.IChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;

public class SpheroEventHandler implements ISpheroEvents
{
  private IChangedNotifier _changedNotifier;
  private ServiceState _state;
  private SpheroManager _spheroManager;
  
  public SpheroEventHandler(IChangedNotifier changedNotifier, ServiceState state, SpheroManager spheroManager)
  {
    _changedNotifier = changedNotifier;
    _state = state;
    _spheroManager = spheroManager;
  }
 
  public void spheroStateChanged(SpheroStatus spheroStatus)
  {
    if (spheroStatus == SpheroStatus.connected)
    {
    	Sphero sphero = _spheroManager.get_sphero();

    	if (sphero != null && sphero.isConnected())
    		sphero.setColor(0, 0, 255);
    }

    _state.setSpheroStatus(spheroStatus);
    _changedNotifier.onChanged();
  }

  public void bluetoothDisabled()
  {
  }
}