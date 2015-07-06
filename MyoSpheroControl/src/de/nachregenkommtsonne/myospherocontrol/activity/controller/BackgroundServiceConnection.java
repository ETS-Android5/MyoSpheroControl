package de.nachregenkommtsonne.myospherocontrol.activity.controller;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import de.nachregenkommtsonne.myospherocontrol.activity.controller.ui.IUiOnUiThreadUpdater;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.IBinderEvents;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.IServiceBinderForUI;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;

public class BackgroundServiceConnection implements ServiceConnection
{
  private IUiOnUiThreadUpdater _uiOnUiThreadUpdater;
  private IServiceBinderForUI _myBinder;

  public BackgroundServiceConnection(IUiOnUiThreadUpdater uiOnUiThreadUpdater)
  {
    _uiOnUiThreadUpdater = uiOnUiThreadUpdater;
  }

  public void onServiceConnected(ComponentName name, IBinder service)
  {
    _myBinder = (IServiceBinderForUI) service;

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

  //TODO: move to own interface
  public IServiceBinderForUI get_myBinder()
  {
    return _myBinder;
  }
}
