package de.nachregenkommtsonne.myospherocontrolC.activity.controller.ui;

import de.nachregenkommtsonne.myospherocontrolC.IGuiStateHinter;
import de.nachregenkommtsonne.myospherocontrolC.controller.IServiceState;

public class UiUpdaterFactory implements IUiUpdaterFactory
{
  private IViewAccessor _viewAccessor;
  private IGuiStateHinter _guiStateHinter;

  public UiUpdaterFactory(IViewAccessor viewAccessor, IGuiStateHinter guiStateHinter)
  {
    _viewAccessor = viewAccessor;
    _guiStateHinter = guiStateHinter;
  }

  public UiUpdater create(IServiceState state)
  {
    return new UiUpdater(state, _viewAccessor, _guiStateHinter);
  }
}
