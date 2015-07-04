package de.nachregenkommtsonne.myospherocontrol.activity;

import com.thalmic.myo.scanner.ScanActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class LinkUnlinkClickListener implements OnClickListener
{
  private ControlFragment _controlFragment;
  BackgroundServiceConnection _serviceConnection;
  
  public LinkUnlinkClickListener(ControlFragment controlFragment,
      BackgroundServiceConnection serviceConnection)
  {
    _controlFragment = controlFragment;
    _serviceConnection = serviceConnection;
  }

  public void onClick(View v)
  {
    if (!_serviceConnection.get_myBinder().getState().isRunning())
    {
      _controlFragment.unlinkClicked();
    }
    else
    {
      Activity activity = _controlFragment.getActivity();
      Intent intent = new Intent(activity, ScanActivity.class);

      activity.startActivity(intent);
    }
  }
}