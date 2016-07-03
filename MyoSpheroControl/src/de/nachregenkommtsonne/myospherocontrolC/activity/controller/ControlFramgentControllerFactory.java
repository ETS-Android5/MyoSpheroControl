package de.nachregenkommtsonne.myospherocontrolC.activity.controller;

import android.app.Fragment;
import de.nachregenkommtsonne.myospherocontrolC.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrolC.IGuiStateHinter;
import de.nachregenkommtsonne.myospherocontrolC.activity.controller.ui.IUiOnUiThreadUpdater;
import de.nachregenkommtsonne.myospherocontrolC.activity.controller.ui.IViewAccessor;
import de.nachregenkommtsonne.myospherocontrolC.activity.controller.ui.UiOnUiThreadUpdater;
import de.nachregenkommtsonne.myospherocontrolC.activity.controller.ui.UiUpdaterFactory;
import de.nachregenkommtsonne.myospherocontrolC.activity.controller.ui.ViewAccessor;

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
