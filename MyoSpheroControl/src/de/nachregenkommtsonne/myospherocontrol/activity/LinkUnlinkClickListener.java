package de.nachregenkommtsonne.myospherocontrol.activity;

import com.thalmic.myo.scanner.ScanActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class LinkUnlinkClickListener implements OnClickListener
{
  private ViewAccessor _viewAccessor;
  BackgroundServiceConnection _serviceConnection;

  public LinkUnlinkClickListener(ViewAccessor viewAccessor, BackgroundServiceConnection serviceConnection)
  {
    _viewAccessor = viewAccessor;
    _serviceConnection = serviceConnection;
  }

  public void onClick(View v)
  {
    if (!_serviceConnection.get_myBinder().getState().isRunning())
    {
      _serviceConnection.get_myBinder().unlinkClicked();
    }
    else
    {
      Activity activity = _viewAccessor.getActivity();
      Intent intent = new Intent(activity, ScanActivity.class);

      activity.startActivity(intent);
    }
  }
}