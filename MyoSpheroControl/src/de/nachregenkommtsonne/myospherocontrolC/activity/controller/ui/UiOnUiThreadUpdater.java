package de.nachregenkommtsonne.myospherocontrolC.activity.controller.ui;

import android.app.Activity;
import de.nachregenkommtsonne.myospherocontrolC.controller.IServiceState;

public class UiOnUiThreadUpdater implements IUiOnUiThreadUpdater
{
  private IUiUpdaterFactory _uiUpdaterFactory;
  private IViewAccessor _viewAccessor;

  public UiOnUiThreadUpdater(IUiUpdaterFactory uiUpdaterFactory, IViewAccessor viewAccessor)
  {
    _uiUpdaterFactory = uiUpdaterFactory;
    _viewAccessor = viewAccessor;
  }

  public void updateUiOnUiThread(IServiceState state)
  {
    Activity activity = _viewAccessor.getActivity();

    if (activity == null)
      return;

    UiUpdater uiUpdater = _uiUpdaterFactory.create(state);

    activity.runOnUiThread(uiUpdater);
  }
}