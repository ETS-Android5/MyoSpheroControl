package de.nachregenkommtsonne.myospherocontrol.controller.sphero;

import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;

public class SpheroControllerFactory
{
  public SpheroControllerFactory()
  {
  }

  public SpheroController create(Context context, IServiceState serviceState)
  {
    SpheroManager spheroManager = new SpheroManager();
    SpheroController spheroController = new SpheroController(context, spheroManager, serviceState);

    return spheroController;
  }
}