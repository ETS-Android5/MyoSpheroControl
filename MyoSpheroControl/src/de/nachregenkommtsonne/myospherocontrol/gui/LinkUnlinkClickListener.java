package de.nachregenkommtsonne.myospherocontrol.gui;

import com.thalmic.myo.scanner.ScanActivity;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import de.nachregenkommtsonne.myospherocontrol.service.ServiceState;

final class LinkUnlinkClickListener implements OnClickListener
{
  private final ControlFragment _controlFragment;
  private MyServiceConnection _myServiceConnection;

  LinkUnlinkClickListener(ControlFragment controlFragment, MyServiceConnection myServiceConnection)
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
      Intent intent = new Intent(this._controlFragment.getActivity(), ScanActivity.class);
      _controlFragment.getActivity().startActivity(intent);
    }
  }
}