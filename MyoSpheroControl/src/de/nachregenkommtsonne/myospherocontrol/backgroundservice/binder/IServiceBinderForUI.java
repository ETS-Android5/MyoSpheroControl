package de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder;

import de.nachregenkommtsonne.myospherocontrol.controller.ServiceState;

public interface IServiceBinderForUI
{
  public abstract ServiceState getState();

  public abstract void setChangedListener(IBinderEvents binderEvents);

  public abstract void linkUnlinkButtonClicked();

  public abstract void startStopButtonClicked();
}
