package de.nachregenkommtsonne.myospherocontrol.activity;

import android.app.Activity;
import android.view.View;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ServiceState;

public class UiUpdater implements Runnable
{
  private ServiceState _state;
  private ControlFragmentUpdateUI _controlFragmentUpdateUI;
  private View _view;
  private Activity _activity;
  
  public UiUpdater(
      ServiceState state,
      ControlFragmentUpdateUI controlFragmentUpdateUI,
      View view,
      Activity activity)
  {
    _state = state;
    _controlFragmentUpdateUI = controlFragmentUpdateUI;
    _view = view;
    _activity = activity;
  }

  public void run()
  {
    _controlFragmentUpdateUI.updateUI(_state,_view, _activity);
  }
}