package de.nachregenkommtsonne.myospherocontrol.backgroundservice;


public class ChangedNotifier implements IChangedNotifier
{
  private INotificationUpdater _notificationUpdater;
  private ServiceBinder _serviceBinder;

  public ChangedNotifier(INotificationUpdater notificationUpdater)
  {
    _notificationUpdater = notificationUpdater;
  }
  
  public void setServiceBinder(ServiceBinder serviceBinder)
  {
  	_serviceBinder = serviceBinder;
  }

  public void onChanged()
  {
    if (_serviceBinder != null)
      _serviceBinder.onChanged();

    _notificationUpdater.updateNotification();
  }
}