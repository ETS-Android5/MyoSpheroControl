package de.nachregenkommtsonne.myospherocontrol.gui;

import de.nachregenkommtsonne.myospherocontrol.service.BackgroundService.IBinderEvents;

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