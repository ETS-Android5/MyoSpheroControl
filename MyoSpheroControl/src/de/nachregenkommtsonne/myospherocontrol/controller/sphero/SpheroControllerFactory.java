package de.nachregenkommtsonne.myospherocontrol.controller.sphero;

import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.controller.ServiceState;

public class SpheroControllerFactory
{
  public SpheroControllerFactory()
  {
  }

  public SpheroController create(Context context, ServiceState serviceState)
  {
    SpheroManager spheroManager = new SpheroManager();
    SpheroController spheroController = new SpheroController(context, spheroManager, serviceState);

    return spheroController;
  }
}