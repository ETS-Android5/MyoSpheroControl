package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import android.os.Binder;

public class ServiceBinder extends Binder
{
  private IBinderEvents _binderEvents;
  private ServiceController _serviceController;
  private ServiceState _serviceState;

  public ServiceBinder(ServiceController serviceController, ServiceState serviceState)
  {
    _serviceController = serviceController;
    _serviceState = serviceState;
  }

  // Called by BackgroundService
  public void onChanged()
  {
    if (_binderEvents != null)
      _binderEvents.changed();
  }

  // Called by Activity
  public void setChangedListener(IBinderEvents binderEvents)
  {
    _binderEvents = binderEvents;
  }

  // Called by Activity
  public ServiceState getState()
  {
    return _serviceState;
  }

  // Called by Activity
  public void buttonClicked()
  {
    _serviceController.buttonClicked();
  }

  // Called by Activity
  public void unlinkClicked()
  {
    _serviceController.unlinkClicked();
  }
}