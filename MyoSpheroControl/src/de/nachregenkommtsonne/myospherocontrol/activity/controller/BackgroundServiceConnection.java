package de.nachregenkommtsonne.myospherocontrol.activity.controller;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.IBinderEvents;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceState;

public class BackgroundServiceConnection implements ServiceConnection
{
  private UiOnUiThreadUpdater _uiOnUiThreadUpdater;
  private ServiceBinder _myBinder;

  public BackgroundServiceConnection(UiOnUiThreadUpdater uiOnUiThreadUpdater)
  {
    _uiOnUiThreadUpdater = uiOnUiThreadUpdater;
  }

  public void onServiceConnected(ComponentName name, IBinder service)
  {
    _myBinder = (ServiceBinder) service;

    _myBinder.setChangedListener(new IBinderEvents()
    {
      public void changed()
      {
        updateUi();
      }
    });
    
    updateUi();
  }

  public void updateUi()
  {
    ServiceState state = _myBinder.getState();
    _uiOnUiThreadUpdater.updateUiOnUiThread(state);
  }

  public void onServiceDisconnected(ComponentName name)
  {
    _myBinder.setChangedListener(null);
  }

  public ServiceBinder get_myBinder()
  {
    return _myBinder;
  }
}
