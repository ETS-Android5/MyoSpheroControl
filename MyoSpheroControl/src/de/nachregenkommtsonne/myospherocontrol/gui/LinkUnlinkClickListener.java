package de.nachregenkommtsonne.myospherocontrol.gui;

import com.thalmic.myo.scanner.ScanActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import de.nachregenkommtsonne.myospherocontrol.service.ServiceState;

public class LinkUnlinkClickListener implements OnClickListener
{
  private ControlFragment _controlFragment;
  private MyServiceConnection _myServiceConnection;

  public LinkUnlinkClickListener(ControlFragment controlFragment, MyServiceConnection myServiceConnection)
  {
    _controlFragment = controlFragment;
    _myServiceConnection = myServiceConnection;
  }

  public void onClick(View v)
  {
    ServiceState state = _myServiceConnection.get_myBinder().getState();
    if (!state.isRunning())
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