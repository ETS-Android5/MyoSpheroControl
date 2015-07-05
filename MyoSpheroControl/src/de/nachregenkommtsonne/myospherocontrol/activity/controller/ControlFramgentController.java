package de.nachregenkommtsonne.myospherocontrol.activity.controller;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import de.nachregenkommtsonne.myospherocontrol.activity.BackgroundServiceConnection;
import de.nachregenkommtsonne.myospherocontrol.activity.ControlActivity;
import de.nachregenkommtsonne.myospherocontrol.activity.LinkUnlinkClickListener;
import de.nachregenkommtsonne.myospherocontrol.activity.StartStopClickListener;
import de.nachregenkommtsonne.myospherocontrol.activity.ViewAccessor;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.BackgroundService;

public class ControlFramgentController
{
  private ViewAccessor _viewAccessor;
  private BackgroundServiceConnection _myServiceConnection;
  private StartStopClickListener _startStopClickListener;
  private LinkUnlinkClickListener _linkUnlinkClickListener;

  public ControlFramgentController(ViewAccessor viewAccessor, BackgroundServiceConnection myServiceConnection)
  {
    _viewAccessor = viewAccessor;
    _myServiceConnection = myServiceConnection;
    _startStopClickListener = new StartStopClickListener(_myServiceConnection);
    _linkUnlinkClickListener = new LinkUnlinkClickListener(_viewAccessor, _myServiceConnection);
}

  public void startService()
  {
    Activity activity = _viewAccessor.getActivity();

    Intent intent = new Intent(activity, BackgroundService.class);
    activity.startService(intent);
  }

  public void bindService()
  {
    Activity activity = _viewAccessor.getActivity();

    Intent intent = new Intent(activity, BackgroundService.class);
    activity.bindService(intent, _myServiceConnection, ControlActivity.BIND_AUTO_CREATE);
  }

  public void unbindService()
  {
    // TODO: stop service when not running here!
    Activity activity = _viewAccessor.getActivity();
    activity.unbindService(_myServiceConnection);
  }
  

  public void startStopClick(View v)
  {
    _startStopClickListener.onClick(v);
  }

  public void linkUnlinkClick(View v)
  {
    _linkUnlinkClickListener.onClick(v);
  }
}
