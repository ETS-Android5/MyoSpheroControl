package de.nachregenkommtsonne.myospherocontrol.gui;

import de.nachregenkommtsonne.myospherocontrol.service.ServiceState;

public class UiUpdater implements Runnable
{
  private ControlFragment _controlFragment;
  private ServiceState _state;

  public UiUpdater(ControlFragment controlFragment, ServiceState state)
  {
    _controlFragment = controlFragment;
    _state = state;
  }

  public void run()
  {
    _controlFragment.updateUI(_state);
  }
}