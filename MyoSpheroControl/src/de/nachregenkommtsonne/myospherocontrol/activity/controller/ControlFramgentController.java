package de.nachregenkommtsonne.myospherocontrol.activity.controller;

import com.thalmic.myo.scanner.ScanActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import de.nachregenkommtsonne.myospherocontrol.activity.ControlActivity;
import de.nachregenkommtsonne.myospherocontrol.activity.controller.ui.IViewAccessor;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.BackgroundService;

public class ControlFramgentController implements IControlFramgentController
{
  private IViewAccessor _viewAccessor;
  private BackgroundServiceConnection _myServiceConnection;

  public ControlFramgentController(IViewAccessor viewAccessor, BackgroundServiceConnection myServiceConnection)
  {
    _viewAccessor = viewAccessor;
    _myServiceConnection = myServiceConnection;
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
    _myServiceConnection.get_myBinder().startStopButtonClicked();
  }

  public void linkUnlinkClick(View v)
  {
    if (!_myServiceConnection.get_myBinder().getState().isRunning())
    {
      _myServiceConnection.get_myBinder().linkUnlinkButtonClicked();
    } else
    {
      Activity activity = _viewAccessor.getActivity();
      Intent intent = new Intent(activity, ScanActivity.class);

      activity.startActivity(intent);
    }
  }
}