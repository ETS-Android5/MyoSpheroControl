package de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero;

import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;

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
