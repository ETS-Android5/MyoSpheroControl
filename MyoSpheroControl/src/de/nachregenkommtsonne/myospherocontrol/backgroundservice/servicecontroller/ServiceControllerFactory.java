package de.nachregenkommtsonne.myospherocontrol.backgroundservice.servicecontroller;

import android.content.Context;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ChangedNotifier;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ServiceBinder;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.binder.ServiceBinderFactory;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.ServiceState;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.bluetooth.BluetoothController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.bluetooth.BluetoothControllerFactory;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.myo.MyoControllerFactory;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.notification.INotificationUpdater;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.notification.NotificationUpdater;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroController;
import de.nachregenkommtsonne.myospherocontrol.backgroundservice.controller.sphero.SpheroControllerFactory;

public class ServiceControllerFactory
{
	public ServiceControllerFactory()
	{
	}
	
	public ServiceController create(Context context, ServiceState serviceState)
	{
		SpheroController spheroController = new SpheroControllerFactory().create(context, serviceState);
		MyoController myoController = new MyoControllerFactory().create(context, serviceState, spheroController);
    BluetoothController bluetoothController = new BluetoothControllerFactory().create(serviceState, spheroController, myoController);
    ServiceBinder serviceBinder = new ServiceBinderFactory().create(serviceState, spheroController, myoController);

    ServiceController serviceController = new ServiceController(
        context,
        serviceState,
        spheroController,
        myoController,
        bluetoothController,
        serviceBinder);
    
    INotificationUpdater notificationUpdater = new NotificationUpdater(context, serviceState);
    ChangedNotifier changedNotifier = new ChangedNotifier(notificationUpdater, serviceBinder);
    
    spheroController.setChangedNotifier(changedNotifier);
    myoController.setChangedNotifier(changedNotifier);
    bluetoothController.setChangedNotifier(changedNotifier);
    
    return serviceController;
	}
}