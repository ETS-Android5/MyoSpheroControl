package de.nachregenkommtsonne.myospherocontrol.activity;

import android.app.Activity;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceState;

public class UiOnUiThreadUpdater
{
  private ControlFragment _controlFragment;

  public UiOnUiThreadUpdater(ControlFragment controlFragment)
  {
    _controlFragment = controlFragment;
  }

  public void updateUiOnUiThread(ServiceState state)
  {

    Activity activity = _controlFragment.getActivity();

    if (activity == null)
      return;

    UiUpdater uiUpdater = new UiUpdater(
        state,
        _controlFragment._controlFragmentUpdateUI,
        _controlFragment.getView(),
        activity);
    
    activity.runOnUiThread(uiUpdater);
  }
}