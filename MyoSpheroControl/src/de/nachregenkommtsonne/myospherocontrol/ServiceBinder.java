package de.nachregenkommtsonne.myospherocontrol;

import android.os.Binder;
import de.nachregenkommtsonne.myospherocontrol.BackgroundService.IBinderEvents;

public class ServiceBinder extends Binder
{
  private BackgroundService _backgroundService;
  private IBinderEvents _binderEvents;

  public ServiceBinder(BackgroundService backgroundService)
  {
    _backgroundService = backgroundService;
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
    return _backgroundService.get_serviceController().getState();
  }

  public void buttonClicked()
  {
    _backgroundService.get_serviceController().buttonClicked();
  }

  public void unlinkClicked()
  {
    _backgroundService.get_serviceController().unlinkClicked();
  }
}