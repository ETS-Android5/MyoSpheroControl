package de.nachregenkommtsonne.myospherocontrol.service;

import android.os.Binder;
import de.nachregenkommtsonne.myospherocontrol.service.BackgroundService.IBinderEvents;

public class MyBinder extends Binder
{
  private final BackgroundService backgroundService;

  MyBinder(BackgroundService backgroundService)
  {
    this.backgroundService = backgroundService;
  }

  IBinderEvents _binderEvents;

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
    return this.backgroundService._serviceController.getState();
  }

  public void buttonClicked()
  {
    this.backgroundService._serviceController.buttonClicked();
  }

  public void unlinkClicked()
  {
    this.backgroundService._serviceController.unlinkClicked();

  }
}