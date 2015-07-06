package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.BluetoothStateBroadcastReceiver;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.BluetoothStateHandler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.IBluetoothStateHandler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.INotificationUpdater;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.NotificationUpdater;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoEvents;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoEventHandler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.SettingsEditor;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.ISpheroController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroEventHandler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroManager;
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

		// Connectivity
    INotificationUpdater notificationUpdater = new NotificationUpdater(_context, serviceState);
    ChangedNotifier changedNotifier = new ChangedNotifier(notificationUpdater);
    
    // Sphero Factory
    SpheroManager spheroManager = new SpheroManager();
    SpheroEventHandler spheroEventHandler = new SpheroEventHandler(changedNotifier, serviceState, spheroManager);
    ISpheroController spheroController = new SpheroController(_context, spheroEventHandler, spheroManager);

    // Myo Factory
		IMovementCalculator mMovementCalculator = new MovementCalculator();
    IMyoEvents myoEventHandler = new MyoEventHandler(
        serviceState,
        mMovementCalculator,
        spheroController,
        changedNotifier);
    SettingsEditor settingsEditor = new SettingsEditor();
    IMyoController myoController = new MyoController(myoEventHandler, settingsEditor, _context, serviceState);

    // Bluetooth Factory
    IBluetoothStateHandler bluetoothStateHandler = new BluetoothStateHandler(changedNotifier, serviceState, myoController, spheroController);
		BroadcastReceiver serviceControllerBroadcastReceiver = new BluetoothStateBroadcastReceiver(
    		bluetoothStateHandler,
        changedNotifier);

		// Connectivity again
		ButtonClickHandler buttonClickHandler = new ButtonClickHandler(myoController, spheroController, serviceState);
		ServiceBinder serviceBinder = new ServiceBinder(serviceState, buttonClickHandler);
		changedNotifier.setServiceBinder(serviceBinder);
		

		return new ServiceController(
        myoController,
        spheroController,
        changedNotifier,
        serviceState,
        serviceControllerBroadcastReceiver,
        serviceBinder, 
        _context);
	}
}
