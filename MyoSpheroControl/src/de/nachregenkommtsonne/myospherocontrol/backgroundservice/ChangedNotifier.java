package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

public class ChangedNotifier
{
  private IServiceControllerStatusChangedHandler _serviceControllerNotificationUpdater;
  private ServiceBinder _binder;
  
  public ChangedNotifier(IServiceControllerStatusChangedHandler serviceControllerNotificationUpdater,
      ServiceBinder binder)
  {
    _serviceControllerNotificationUpdater = serviceControllerNotificationUpdater;
    _binder = binder;
  }

  public void onChanged()
  {
    _binder.onChanged();

    _serviceControllerNotificationUpdater.updateNotification();
  }
}
