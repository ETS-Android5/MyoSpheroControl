package de.nachregenkommtsonne.myospherocontrol.gui;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import de.nachregenkommtsonne.myospherocontrol.service.MyBinder;

public class MyServiceConnection implements ServiceConnection
{
  private ControlFragment _controlFragment;
  private MyBinder _myBinder;

  MyServiceConnection(ControlFragment controlFragment)
  {
    _controlFragment = controlFragment;
  }

  public void onServiceConnected(ComponentName name, IBinder service)
  {
    _myBinder = (MyBinder) service;
    _myBinder.setChangedListener(new ServiceConnectionChangedListener(_controlFragment));

    _controlFragment.updateUiOnUiThread();
  }

  public void onServiceDisconnected(ComponentName name)
  {
    _myBinder.setChangedListener(null);
  }

  public MyBinder get_myBinder()
  {
    return _myBinder;
  }
}
