package de.nachregenkommtsonne.myospherocontrol.activity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceBinder;

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
    ServiceConnectionChangedListener binderEvents = new ServiceConnectionChangedListener(_uiOnUiThreadUpdater, this);

    _myBinder = (ServiceBinder) service;
    _myBinder.setChangedListener(binderEvents);

    _uiOnUiThreadUpdater.updateUiOnUiThread(_myBinder.getState());
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
