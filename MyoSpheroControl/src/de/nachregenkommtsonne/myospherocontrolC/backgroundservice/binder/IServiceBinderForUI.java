package de.nachregenkommtsonne.myospherocontrolC.backgroundservice.binder;

import de.nachregenkommtsonne.myospherocontrolC.controller.IServiceState;

public interface IServiceBinderForUI
{
  public abstract IServiceState getState();

  public abstract void setServiceStateChangedListener(IServiceStateChangedListener binderEvents);

  public abstract void linkUnlinkButtonClicked();

  public abstract void startStopButtonClicked();
}
