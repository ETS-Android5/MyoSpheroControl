package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

public class ChangedNotifier
{
  private IServiceControllerStatusChangedHandler _serviceControllerNotificationUpdater;
  private ServiceBinder _binder;
  
  public ChangedNotifier(IServiceControllerStatusChangedHandler serviceControllerNotificationUpdater)
  {
    _serviceControllerNotificationUpdater = serviceControllerNotificationUpdater;
  }

  public void setServiceBinder(ServiceBinder binder){
    _binder = binder;
  }
  
  public void onChanged()
  {
    if (_binder != null)
      _binder.onChanged();

    _serviceControllerNotificationUpdater.updateNotification();
  }
}
