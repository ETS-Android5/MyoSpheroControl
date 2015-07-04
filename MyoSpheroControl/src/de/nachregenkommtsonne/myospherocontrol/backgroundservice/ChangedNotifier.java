package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.IServiceControllerStatusChangedHandler;

public class ChangedNotifier implements IChangedNotifier
{
  private IServiceControllerStatusChangedHandler _serviceControllerNotificationUpdater;
  private ServiceBinder _binder;

  public ChangedNotifier(IServiceControllerStatusChangedHandler serviceControllerNotificationUpdater)
  {
    _serviceControllerNotificationUpdater = serviceControllerNotificationUpdater;
  }

  public void setServiceBinder(ServiceBinder binder)
  {
    _binder = binder;
  }

  public void onChanged()
  {
    if (_binder != null)
      _binder.onChanged();

    _serviceControllerNotificationUpdater.updateNotification();
  }
}