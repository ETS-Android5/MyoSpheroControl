package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

public interface IServiceControllerStatusChangedHandler
{
  void updateNotification();

  void onChanged();
}
