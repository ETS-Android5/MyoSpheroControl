package de.nachregenkommtsonne.myospherocontrol.activity;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.IBinderEvents;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceState;

public class ServiceConnectionChangedListener implements IBinderEvents
{
  private UiOnUiThreadUpdater _uiOnUiThreadUpdater;
  private BackgroundServiceConnection _myServiceConnection;
  
  public ServiceConnectionChangedListener(UiOnUiThreadUpdater uiOnUiThreadUpdater, BackgroundServiceConnection myServiceConnection)
  {
    _uiOnUiThreadUpdater = uiOnUiThreadUpdater;
    _myServiceConnection = myServiceConnection;
  }

  public void changed()
  {
    ServiceState state = _myServiceConnection.get_myBinder().getState();

    _uiOnUiThreadUpdater.updateUiOnUiThread(state);
  }
}