package de.nachregenkommtsonne.myospherocontrol.activity;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.IBinderEvents;

public class ServiceConnectionChangedListener implements IBinderEvents
{
  private ControlFragment _controlFragment;

  public ServiceConnectionChangedListener(ControlFragment controlFragment)
  {
    _controlFragment = controlFragment;
  }

  public void changed()
  {
    _controlFragment.updateUiOnUiThread();
  }
}