package de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder;

import android.os.Binder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ButtonClickHandler;

public class ServiceBinder extends Binder implements IServiceBinderForUI
{
  private IBinderEvents _binderEvents;
  private ServiceState _serviceState;
  private ButtonClickHandler _buttonClickHandler;

  public ServiceBinder(ServiceState serviceState, ButtonClickHandler buttonClickHandler)
  {
    _serviceState = serviceState;
    _buttonClickHandler = buttonClickHandler;
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
  public void startStopButtonClicked()
  {
    _buttonClickHandler.startStopButtonClicked();
    onChanged();
  }

  // Called by Activity
  public void linkUnlinkButtonClicked()
  {
    _buttonClickHandler.linkUnlinkButtonClicked();
    onChanged();
  }
}