package de.nachregenkommtsonne.myospherocontrol;

import com.thalmic.myo.scanner.ScanActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class LinkUnlinkClickListener implements OnClickListener
{
  private ControlFragment _controlFragment;
  ServiceState _serviceState;
  
  public LinkUnlinkClickListener(ControlFragment controlFragment,
      ServiceState serviceState)
  {
    _controlFragment = controlFragment;
    _serviceState = serviceState;
  }

  public void onClick(View v)
  {
    if (!_serviceState.isRunning())
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