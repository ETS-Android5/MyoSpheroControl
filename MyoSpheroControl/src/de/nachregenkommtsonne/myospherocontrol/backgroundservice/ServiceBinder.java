package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import android.os.Binder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceController;

public class ServiceBinder extends Binder implements IServiceBinderForUI
{
  private IBinderEvents _binderEvents;
  private ServiceController _serviceController;
  private ServiceState _serviceState;

  public ServiceBinder(ServiceState serviceState)
  {
    _serviceState = serviceState;
  }

  public void setServiceController(ServiceController serviceController)
  {
    _serviceController = serviceController;
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