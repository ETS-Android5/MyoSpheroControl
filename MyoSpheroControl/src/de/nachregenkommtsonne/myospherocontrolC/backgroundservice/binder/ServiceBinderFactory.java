package de.nachregenkommtsonne.myospherocontrolC.backgroundservice.binder;

import de.nachregenkommtsonne.myospherocontrolC.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrolC.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrolC.controller.sphero.ISpheroController;

public class ServiceBinderFactory
{
  public ServiceBinderFactory()
  {
  }

  public ServiceBinder create(
      IServiceState serviceState,
      IServiceState notifyingServiceState,
      ISpheroController spheroController,
      IMyoController myoController)
  {
    ButtonClickHandler buttonClickHandler = new ButtonClickHandler(spheroController, myoController, notifyingServiceState);
    ServiceBinder serviceBinder = new ServiceBinder(serviceState, buttonClickHandler);

    return serviceBinder;
  }
}