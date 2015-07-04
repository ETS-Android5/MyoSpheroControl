package de.nachregenkommtsonne.myospherocontrol.activity;

import android.app.Activity;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceState;

public class UiOnUiThreadUpdater
{
  private ControlFragmentUpdateUI _controlFragmentUpdateUI;
  private ViewAccessor _viewAccessor;
  
  public UiOnUiThreadUpdater(ControlFragmentUpdateUI controlFragmentUpdateUI, ViewAccessor viewAccessor)
  {
    _controlFragmentUpdateUI = controlFragmentUpdateUI;
    _viewAccessor = viewAccessor;
  }

  public void updateUiOnUiThread(ServiceState state)
  {

    Activity activity = _viewAccessor.getActivity();

    if (activity == null)
      return;

    UiUpdater uiUpdater = new UiUpdater(
        state,
        _controlFragmentUpdateUI,
        _viewAccessor.getView(),
        activity);
    
    activity.runOnUiThread(uiUpdater);
  }
}