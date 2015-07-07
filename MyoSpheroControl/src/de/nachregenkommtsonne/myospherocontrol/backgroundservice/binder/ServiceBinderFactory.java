package de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder;

import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller.ButtonClickHandler;

public class ServiceBinderFactory
{
  public ServiceBinderFactory()
  {

  }

  public ServiceBinder create(ServiceState serviceState, ISpheroController spheroController, IMyoController myoController)
  {
    ButtonClickHandler buttonClickHandler = new ButtonClickHandler(spheroController, myoController, serviceState);
    ServiceBinder serviceBinder = new ServiceBinder(serviceState, buttonClickHandler);

    return serviceBinder;
  }
}