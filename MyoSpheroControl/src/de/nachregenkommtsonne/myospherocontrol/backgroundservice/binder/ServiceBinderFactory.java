package de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder;

import de.nachregenkommtsonne.myospherocontrol.controller.IServiceState;
import de.nachregenkommtsonne.myospherocontrol.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.controller.sphero.ISpheroConnectionController;

public class ServiceBinderFactory
{
  public ServiceBinderFactory()
  {
  }

  public ServiceBinder create(
      IServiceState serviceState,
      IServiceState notifyingServiceState,
      ISpheroConnectionController spheroController,
      IMyoController myoController)
  {
    ButtonClickHandler buttonClickHandler = new ButtonClickHandler(spheroController, myoController, notifyingServiceState);
    ServiceBinder serviceBinder = new ServiceBinder(serviceState, buttonClickHandler);

    return serviceBinder;
  }
}