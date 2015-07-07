package de.nachregenkommtsonne.myospherocontrol.activity.controller.ui;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;

public interface IUiOnUiThreadUpdater
{
  public abstract void updateUiOnUiThread(ServiceState state);
}
