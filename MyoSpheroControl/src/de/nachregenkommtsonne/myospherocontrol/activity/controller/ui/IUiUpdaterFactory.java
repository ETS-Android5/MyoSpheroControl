package de.nachregenkommtsonne.myospherocontrol.activity.controller.ui;

import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;

public interface IUiUpdaterFactory
{
  public abstract UiUpdater create(IServiceState state);
}
