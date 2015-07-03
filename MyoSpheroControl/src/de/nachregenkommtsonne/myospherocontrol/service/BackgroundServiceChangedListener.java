package de.nachregenkommtsonne.myospherocontrol.service;

public class BackgroundServiceChangedListener implements IServiceControllerEvents
{
  private BackgroundService _backgroundService;

  public BackgroundServiceChangedListener(BackgroundService backgroundService)
  {
    _backgroundService = backgroundService;
  }

  public void changed()
  {
    _backgroundService._binder.onChanged();
  }
}