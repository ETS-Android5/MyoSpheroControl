package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.BluetoothStateBroadcastReceiver;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.BluetoothStateHandler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.IBluetoothStateHandler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.IChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.INotificationUpdater;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.NotificationUpdater;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceState;
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
	private Context _context;
	
	public ServiceControllerFactory(Context context)
	{
		_context = context;
	}
	
	public ServiceController create()
	{
		GuiStateHinter guiStateHinter = new GuiStateHinter();
    ServiceState serviceState = new ServiceState(guiStateHinter);
    INotificationUpdater notificationUpdater = new NotificationUpdater(_context, serviceState);

    ServiceBinder serviceBinder = new ServiceBinder(serviceState);
    IChangedNotifier changedNotifier = new ChangedNotifier(notificationUpdater, serviceBinder);
    
    ISpheroController spheroController = new SpheroController();
    IMovementCalculator mMovementCalculator = new MovementCalculator();

    IMyoEvents myoEventHandler = new MyoEventHandler(
        serviceState,
        mMovementCalculator,
        spheroController,
        changedNotifier);

    SettingsEditor settingsEditor = new SettingsEditor();
    IMyoController myoController = new MyoController(myoEventHandler, settingsEditor);
    ISpheroEvents spheroEventHandler = new SpheroEventHandler(changedNotifier, spheroController, serviceState);

    IBluetoothStateHandler bluetoothStateHandler = new BluetoothStateHandler(changedNotifier, serviceState, myoController, spheroController);
		BroadcastReceiver serviceControllerBroadcastReceiver = new BluetoothStateBroadcastReceiver(
    		bluetoothStateHandler,
        changedNotifier);
    
		return new ServiceController(
        myoController,
        spheroController,
        changedNotifier,
        serviceState,
        spheroEventHandler,
        serviceControllerBroadcastReceiver,
        serviceBinder, 
        _context);
	}
}
