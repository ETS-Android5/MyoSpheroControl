package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.IServiceControllerStatusChangedHandler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceControllerBroadcastReceiver;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceControllerNotificationUpdater;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoEventHandler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroEventHandler;
import de.nachregenkommtsonne.myospherocontrol.movement.MovementCalculator;

public class ServiceControllerFactory
{

  public ServiceController createServiceController(Context context)
  {
    ServiceState serviceState = new ServiceState();

    SpheroController spheroController = new SpheroController(context);
    MovementCalculator mMovementCalculator = new MovementCalculator();
    
    IServiceControllerStatusChangedHandler serviceControllerStatusChangedHandler = new ServiceControllerNotificationUpdater(
        context,
        serviceState);
    
    ChangedNotifier changedNotifier = new ChangedNotifier(serviceControllerStatusChangedHandler);
  
    MyoEventHandler myoEventHandler = new MyoEventHandler(
        serviceState,
        mMovementCalculator,
        spheroController,
        changedNotifier);
    
    MyoController myoController = new MyoController(context, myoEventHandler);
    SpheroEventHandler spheroEventHandler = new SpheroEventHandler(changedNotifier, spheroController, serviceState);

    ServiceControllerBroadcastReceiver serviceControllerBroadcastReceiver = new ServiceControllerBroadcastReceiver(
        changedNotifier,
        serviceState,
        myoController,
        spheroController);

    return new ServiceController(
        myoController,
        spheroController,
        context,
        changedNotifier,
        serviceState,
        serviceControllerStatusChangedHandler,
        spheroEventHandler,
        serviceControllerBroadcastReceiver);
  }
}