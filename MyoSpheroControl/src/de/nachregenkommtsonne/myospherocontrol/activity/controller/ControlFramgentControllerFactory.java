package de.nachregenkommtsonne.myospherocontrol.activity.controller;

import android.app.Fragment;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.IGuiStateHinter;

public class ControlFramgentControllerFactory
{
  Fragment _fragment;
  
  public ControlFramgentControllerFactory(Fragment fragment)
  {
    _fragment = fragment;
  }

  public ControlFramgentController create()
  {
    IGuiStateHinter guiStateHinter = new GuiStateHinter();
    ViewAccessor viewAccessor = new ViewAccessor(_fragment);

    ControlFragmentUpdateUI controlFragmentUpdateUI = new ControlFragmentUpdateUI(viewAccessor, guiStateHinter);
    UiOnUiThreadUpdater uiOnUiThreadUpdater = new UiOnUiThreadUpdater(controlFragmentUpdateUI, viewAccessor);

    BackgroundServiceConnection myServiceConnection = new BackgroundServiceConnection(uiOnUiThreadUpdater);

    return new ControlFramgentController(viewAccessor, myServiceConnection);
  }
}
