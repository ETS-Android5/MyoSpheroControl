package de.nachregenkommtsonne.myospherocontrol.activity.controller;

import android.app.Activity;
import de.nachregenkommtsonne.myospherocontrol.IGuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceState;

public class UiOnUiThreadUpdater
{
  private IViewAccessor _viewAccessor;
  private IGuiStateHinter _guiStateHinter;
  
  public UiOnUiThreadUpdater(
		  IViewAccessor viewAccessor,
		  IGuiStateHinter guiStateHinter)
  {
    _viewAccessor = viewAccessor;
    _guiStateHinter = guiStateHinter;
  }

  public void updateUiOnUiThread(ServiceState state)
  {
    Activity activity = _viewAccessor.getActivity();

    if (activity == null)
      return;

    //TODO: create factory
    UiUpdater uiUpdater = new UiUpdater(
        state,
        _viewAccessor,
        _guiStateHinter);
    
    activity.runOnUiThread(uiUpdater);
  }
}