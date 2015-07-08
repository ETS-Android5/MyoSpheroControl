package de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder;

import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;

public interface IServiceBinderForUI
{
  public abstract IServiceState getState();

  public abstract void setChangedListener(IBinderEvents binderEvents);

  public abstract void linkUnlinkButtonClicked();

  public abstract void startStopButtonClicked();
}
