package de.nachregenkommtsonne.myospherocontrol.activity.controller;

import android.app.Fragment;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.IGuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.activity.BackgroundServiceConnection;
import de.nachregenkommtsonne.myospherocontrol.activity.ControlFragmentUpdateUI;
import de.nachregenkommtsonne.myospherocontrol.activity.UiOnUiThreadUpdater;
import de.nachregenkommtsonne.myospherocontrol.activity.ViewAccessor;

public class ControlFramgentControllerFactory
{
  public ControlFramgentControllerFactory()
  {

  }

  public ControlFramgentController create(Fragment fragment)
  {
    IGuiStateHinter guiStateHinter = new GuiStateHinter();
    ViewAccessor viewAccessor = new ViewAccessor(fragment);

    ControlFragmentUpdateUI controlFragmentUpdateUI = new ControlFragmentUpdateUI(viewAccessor, guiStateHinter);
    UiOnUiThreadUpdater uiOnUiThreadUpdater = new UiOnUiThreadUpdater(controlFragmentUpdateUI, viewAccessor);

    BackgroundServiceConnection myServiceConnection = new BackgroundServiceConnection(uiOnUiThreadUpdater);

    return new ControlFramgentController(viewAccessor, myServiceConnection);
  }
}
