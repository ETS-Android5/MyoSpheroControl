package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import de.nachregenkommtsonne.myospherocontrol.ControlFragment;

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