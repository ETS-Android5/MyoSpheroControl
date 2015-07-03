package de.nachregenkommtsonne.myospherocontrol.service;

final class BackgroundServiceChangedListener implements IServiceControllerEvents
{
  private final BackgroundService backgroundService;

  BackgroundServiceChangedListener(BackgroundService backgroundService)
  {
    this.backgroundService = backgroundService;
  }

  public void changed()
  {
    this.backgroundService._binder.onChanged();

  }
}