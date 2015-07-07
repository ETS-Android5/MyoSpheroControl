package de.nachregenkommtsonne.myospherocontrol.activity.controller.ui;

import android.app.Activity;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;

public class UiOnUiThreadUpdater implements IUiOnUiThreadUpdater
{
  private IUiUpdaterFactory _uiUpdaterFactory;
  private IViewAccessor _viewAccessor;

  public UiOnUiThreadUpdater(IUiUpdaterFactory uiUpdaterFactory, IViewAccessor viewAccessor)
  {
    _uiUpdaterFactory = uiUpdaterFactory;
    _viewAccessor = viewAccessor;
  }

  public void updateUiOnUiThread(ServiceState state)
  {
    Activity activity = _viewAccessor.getActivity();

    if (activity == null)
      return;

    UiUpdater uiUpdater = _uiUpdaterFactory.create(state);

    activity.runOnUiThread(uiUpdater);
  }
}