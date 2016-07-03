package de.nachregenkommtsonne.myospherocontrolC.backgroundservice.binder;

import de.nachregenkommtsonne.myospherocontrolC.controller.notification.INotificationUpdater;

public class ChangedNotifier implements IChangedNotifier
{
  private INotificationUpdater _notificationUpdater;
  private ServiceBinder _serviceBinder;

  public ChangedNotifier(INotificationUpdater notificationUpdater, ServiceBinder serviceBinder)
  {
    _notificationUpdater = notificationUpdater;
    _serviceBinder = serviceBinder;
  }

  public void onChanged()
  {
    if (_serviceBinder != null)
      _serviceBinder.onServiceStateChanged();

    _notificationUpdater.updateNotification();
  }
}