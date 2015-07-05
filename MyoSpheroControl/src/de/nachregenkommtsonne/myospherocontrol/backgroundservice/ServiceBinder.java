package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import android.os.Binder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceState;

public class ServiceBinder extends Binder implements IServiceBinderForUI
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
  @Override
	public void setChangedListener(IBinderEvents binderEvents)
  {
    _binderEvents = binderEvents;
  }

  // Called by Activity
  @Override
	public ServiceState getState()
  {
    return _serviceState;
  }

  // Called by Activity
  @Override
	public void buttonClicked()
  {
    _serviceController.buttonClicked();
  }

  // Called by Activity
  @Override
	public void unlinkClicked()
  {
    _serviceController.unlinkClicked();
  }
}