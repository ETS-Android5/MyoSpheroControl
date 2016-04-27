package de.nachregenkommtsonne.myospherocontrol.activity.controller.ui;

import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;

public interface IUiOnUiThreadUpdater
{
  public abstract void updateUiOnUiThread(IServiceState state);
}
