package de.nachregenkommtsonne.myospherocontrol.activity.controller;

import android.app.Fragment;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.IGuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.activity.controller.ui.IUiOnUiThreadUpdater;
import de.nachregenkommtsonne.myospherocontrol.activity.controller.ui.IViewAccessor;
import de.nachregenkommtsonne.myospherocontrol.activity.controller.ui.UiOnUiThreadUpdater;
import de.nachregenkommtsonne.myospherocontrol.activity.controller.ui.UiUpdaterFactory;
import de.nachregenkommtsonne.myospherocontrol.activity.controller.ui.ViewAccessor;

public class ControlFramgentControllerFactory
{
  Fragment _fragment;

  public ControlFramgentControllerFactory(Fragment fragment)
  {
    _fragment = fragment;
  }

  public IControlFramgentController create()
  {
    IGuiStateHinter guiStateHinter = new GuiStateHinter();
    IViewAccessor viewAccessor = new ViewAccessor(_fragment);

    UiUpdaterFactory uiUpdaterFactory = new UiUpdaterFactory(viewAccessor, guiStateHinter);
    IUiOnUiThreadUpdater uiOnUiThreadUpdater = new UiOnUiThreadUpdater(uiUpdaterFactory, viewAccessor);

    BackgroundServiceConnection myServiceConnection = new BackgroundServiceConnection(uiOnUiThreadUpdater);

    return new ControlFramgentController(viewAccessor, myServiceConnection);
  }
}
