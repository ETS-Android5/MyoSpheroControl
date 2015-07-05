package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.IChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoEvents;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoEventHandler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroEvents;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroEventHandler;
import de.nachregenkommtsonne.myospherocontrol.movement.IMovementCalculator;
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
    GuiStateHinter guiStateHinter = new GuiStateHinter();
    ServiceState serviceState = new ServiceState(guiStateHinter);

    ISpheroController spheroController = new SpheroController(_context);
    IMovementCalculator mMovementCalculator = new MovementCalculator();

    INotificationUpdater serviceControllerStatusChangedHandler = new NotificationUpdater(
        _context,
        serviceState);

    IChangedNotifier changedNotifier = new ChangedNotifier(serviceControllerStatusChangedHandler);

    IMyoEvents myoEventHandler = new MyoEventHandler(
        serviceState,
        mMovementCalculator,
        spheroController,
        changedNotifier);

    SettingsEditor settingsEditor = new SettingsEditor();
    IMyoController myoController = new MyoController(_context, myoEventHandler, settingsEditor);
    ISpheroEvents spheroEventHandler = new SpheroEventHandler(changedNotifier, spheroController, serviceState);

    BroadcastReceiver serviceControllerBroadcastReceiver = new ServiceControllerBroadcastReceiver(
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