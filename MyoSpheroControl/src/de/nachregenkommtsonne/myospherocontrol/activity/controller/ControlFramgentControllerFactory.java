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
    IViewAccessor viewAccessor = new ViewAccessor(_fragment);

    UiUpdaterFactory uiUpdaterFactory = new UiUpdaterFactory(viewAccessor, guiStateHinter);
    UiOnUiThreadUpdater uiOnUiThreadUpdater = new UiOnUiThreadUpdater(uiUpdaterFactory, viewAccessor);

    BackgroundServiceConnection myServiceConnection = new BackgroundServiceConnection(uiOnUiThreadUpdater);

    return new ControlFramgentController(viewAccessor, myServiceConnection);
  }
}
