package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.GuiStateHinter;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ServiceBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.bluetooth.BluetoothStateBroadcastReceiver;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.bluetooth.BluetoothStateHandler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.IMyoEvents;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoEventHandler;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.SettingsEditor;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.notification.INotificationUpdater;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.notification.NotificationUpdater;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroController;
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
    
    // Sphero Factory
    SpheroManager spheroManager = new SpheroManager();
    SpheroController spheroController = new SpheroController(_context, spheroManager, serviceState);

    // Myo Factory
    SettingsEditor settingsEditor = new SettingsEditor();
		IMovementCalculator mMovementCalculator = new MovementCalculator();
    IMyoEvents myoEventHandler = new MyoEventHandler(serviceState, mMovementCalculator, spheroController);
    MyoController myoController = new MyoController(myoEventHandler, settingsEditor, _context, serviceState);

    // Bluetooth Factory
    BluetoothStateHandler bluetoothStateHandler = new BluetoothStateHandler(serviceState, myoController, spheroController);
    BluetoothStateBroadcastReceiver bluetoothController = new BluetoothStateBroadcastReceiver(bluetoothStateHandler);

		// Connectivity again
		ButtonClickHandler buttonClickHandler = new ButtonClickHandler(myoController, spheroController, serviceState);
		ServiceBinder serviceBinder = new ServiceBinder(serviceState, buttonClickHandler);

    ChangedNotifier changedNotifier = new ChangedNotifier(notificationUpdater, serviceBinder);
    
    spheroController.setChangedNotifier(changedNotifier);
    myoController.setChangedNotifier(changedNotifier);
    bluetoothController.setChangedNotifier(changedNotifier);
    
		return new ServiceController(
        myoController,
        spheroController,
        changedNotifier,
        serviceState,
        bluetoothController,
        serviceBinder, 
        _context);
	}
}
