package de.nachregenkommtsonne.myospherocontrol.backgroundservice;


public class ChangedNotifier implements IChangedNotifier
{
  private INotificationUpdater _notificationUpdater;
  private ServiceBinder _binder;

  public ChangedNotifier(INotificationUpdater notificationUpdater)
  {
    _notificationUpdater = notificationUpdater;
  }

  public void setServiceBinder(ServiceBinder binder)
  {
    _binder = binder;
  }

  public void onChanged()
  {
    if (_binder != null)
      _binder.onChanged();

    _notificationUpdater.updateNotification();
  }
}