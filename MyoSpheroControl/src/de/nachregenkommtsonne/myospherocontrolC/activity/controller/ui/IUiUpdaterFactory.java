package de.nachregenkommtsonne.myospherocontrolC.activity.controller.ui;

import de.nachregenkommtsonne.myospherocontrolC.controller.IServiceState;

public interface IUiUpdaterFactory
{
  public abstract UiUpdater create(IServiceState state);
}
