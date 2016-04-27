package de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder;

import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;

public interface IServiceBinderForUI
{
  public abstract IServiceState getState();

  public abstract void setServiceStateChangedListener(IServiceStateChangedListener binderEvents);

  public abstract void linkUnlinkButtonClicked();

  public abstract void startStopButtonClicked();
}
