package de.nachregenkommtsonne.myospherocontrol.activity.controller.ui;

import de.nachregenkommtsonne.myospherocontrol.controller.ServiceState;

public interface IUiOnUiThreadUpdater
{
  public abstract void updateUiOnUiThread(ServiceState state);
}
