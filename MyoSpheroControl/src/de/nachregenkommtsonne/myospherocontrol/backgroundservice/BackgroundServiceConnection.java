package de.nachregenkommtsonne.myospherocontrol.backgroundservice;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import de.nachregenkommtsonne.myospherocontrol.ControlFragment;

public class BackgroundServiceConnection implements ServiceConnection
{
  private ControlFragment _controlFragment;
  private ServiceBinder _myBinder;

  public BackgroundServiceConnection(ControlFragment controlFragment)
  {
    _controlFragment = controlFragment;
  }

  public void onServiceConnected(ComponentName name, IBinder service)
  {
    ServiceConnectionChangedListener binderEvents = new ServiceConnectionChangedListener(_controlFragment);
    
    _myBinder = (ServiceBinder) service;
    _myBinder.setChangedListener(binderEvents);

    _controlFragment.updateUiOnUiThread();
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
