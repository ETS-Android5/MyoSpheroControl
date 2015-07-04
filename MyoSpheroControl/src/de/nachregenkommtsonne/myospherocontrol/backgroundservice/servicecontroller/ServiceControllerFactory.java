package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.SettingsEditor;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoEventHandler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroEventHandler;
import de.nachregenkommtsonne.myospherocontrol.movement.MovementCalculator;

public class ServiceControllerFactory
{
  public Context _context;
  
  public ServiceControllerFactory(Context context)
  {
    _context = context;
  }

  public ServiceController createServiceController()
  {
    ServiceState serviceState = new ServiceState();

    ISpheroController spheroController = new SpheroController(_context);
    MovementCalculator mMovementCalculator = new MovementCalculator();

    IServiceControllerStatusChangedHandler serviceControllerStatusChangedHandler = new ServiceControllerNotificationUpdater(
        _context,
        serviceState);

    ChangedNotifier changedNotifier = new ChangedNotifier(serviceControllerStatusChangedHandler);

    MyoEventHandler myoEventHandler = new MyoEventHandler(
        serviceState,
        mMovementCalculator,
        spheroController,
        changedNotifier);

    SettingsEditor settingsEditor = new SettingsEditor();
    IMyoController myoController = new MyoController(_context, myoEventHandler, settingsEditor);
    SpheroEventHandler spheroEventHandler = new SpheroEventHandler(changedNotifier, spheroController, serviceState);

    ServiceControllerBroadcastReceiver serviceControllerBroadcastReceiver = new ServiceControllerBroadcastReceiver(
        changedNotifier,
        serviceState,
        myoController,
        spheroController);

    return new ServiceController(
        myoController,
        spheroController,
        changedNotifier,
        serviceState,
        serviceControllerStatusChangedHandler,
        spheroEventHandler,
        serviceControllerBroadcastReceiver);
  }
}