package de.nachregenkommtsonne.myospherocontrol.gui;

import de.nachregenkommtsonne.myospherocontrol.service.ServiceState;

final class UiUpdater implements Runnable
{
  private final ControlFragment controlFragment;
  private final ServiceState state;

  UiUpdater(ControlFragment controlFragment, ServiceState state)
  {
    this.controlFragment = controlFragment;
    this.state = state;
  }

  public void run()
  {
    this.controlFragment.updateUI(state);
  }
}