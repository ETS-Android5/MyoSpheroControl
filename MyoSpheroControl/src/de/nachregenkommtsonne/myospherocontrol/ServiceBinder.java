package de.nachregenkommtsonne.myospherocontrol;

import android.os.Binder;
import de.nachregenkommtsonne.myospherocontrol.BackgroundService.IBinderEvents;

public class ServiceBinder extends Binder
{
  private IBinderEvents _binderEvents;
  private ServiceController _serviceController;

  public ServiceBinder(ServiceController serviceController)
  {
    _serviceController = serviceController;
  }

  public void onChanged()
  {
    if (_binderEvents != null)
      _binderEvents.changed();
  }

  public void setChangedListener(IBinderEvents binderEvents)
  {
    _binderEvents = binderEvents;
  }

  public ServiceState getState()
  {
    return _serviceController.get_state();
  }

  public void buttonClicked()
  {
    _serviceController.buttonClicked();
  }

  public void unlinkClicked()
  {
    _serviceController.unlinkClicked();
  }
}