package de.nachregenkommtsonne.myospherocontrolC.activity.controller.ui;

import de.nachregenkommtsonne.myospherocontrolC.controller.IServiceState;

public interface IUiOnUiThreadUpdater
{
  public abstract void updateUiOnUiThread(IServiceState state);
}
